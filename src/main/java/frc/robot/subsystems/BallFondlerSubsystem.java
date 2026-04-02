package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.ClosedLoopConfig;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.FeedbackSensor;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

public class BallFondlerSubsystem extends SubsystemBase {

  private final SparkFlex shootingMotor;
  private final SparkMax intakeMotor;
  private final SparkMax loadingMotor;
  private final SparkFlexConfig config;

  private final SparkClosedLoopController shootingController;

  private final Double kP;
  private final Double kI;
  private final Double kD;
  private final Double kFF;

  public BallFondlerSubsystem() {
    intakeMotor = new SparkMax(DriveConstants.kIntakeMotorCanId, MotorType.kBrushless);
    loadingMotor = new SparkMax(DriveConstants.kLoadingMotorCanId, MotorType.kBrushless);
    shootingMotor = new SparkFlex(DriveConstants.kShootingMotorCanId, MotorType.kBrushless);
    shootingController = shootingMotor.getClosedLoopController();
    config = new SparkFlexConfig();
    // Baseline PID/FF values for a NEO on a shooter (in RPM units). These are
    // conservative starting values and should be tuned on the real robot.
    kP = 0.0002; // proportional
    kI = 0.000001; // integral
    kD = 0.0005; // derivative
    // Feedforward: 1 / free speed (RPM) so that setpoint ~= free speed -> output
    // 1.0
    kFF = 1.0 / frc.robot.Constants.FlexMotorConstants.kFreeSpeedRpm; // ~0.000176

    applyConfig();
    stopAll();
  }

  public void applyConfig() {
    config.closedLoop
        .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
        .pid(kP, kI, kD)
        .velocityFF(kFF)
        .outputRange(-1, 1);
    // Apply configuration to the motor and persist it so it survives power cycles.
    shootingMotor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  // ===== SHOOTER =====

  public void shootFeed(double d) {
    shoot(d);
    feed();
  }

  public void shoot(double d) {
    shootingMotor.set(d);
  }

  public void rpmShootFeed(double d) {
    rpmShoot(d);
    feed();
  }

  public void rpmShoot(double d) {
    shootingController.setSetpoint(d, ControlType.kVelocity, ClosedLoopSlot.kSlot0);
  }

  public void stopShootFeed() {
    stopFeed();
    shooterOff();
  }

  public void feed() {
    intakeMotor.set(1);
    loadingMotor.set(-1);
  }

  public void stopFeed() {
    intakeMotor.set(0);
    loadingMotor.set(0);
  }

  public void shooterOff() {
    shootingController.setSetpoint(0, ControlType.kVelocity, ClosedLoopSlot.kSlot0);
  }

  // ===== INTAKE =====

  public void intakeForward() {
    intakeMotor.set(1);
    loadingMotor.set(1);
  }

  public void intakeReverse() {
    intakeMotor.set(-1);
    loadingMotor.set(-1);
  }

  // ===== SHOOT FEED =====

  // ===== STOP =====

  public void stopIntake() {
    intakeMotor.set(0.0);
    loadingMotor.set(0.0);
  }

  public void stopAll() {
    intakeMotor.set(0.0);
    loadingMotor.set(0.0);
    shootingController.setSetpoint(0, ControlType.kVelocity, ClosedLoopSlot.kSlot0);
  }

  // ===== DATA =====
  public double getShooterVelocity() {
    return shootingMotor.getEncoder().getVelocity();
  }

  
}