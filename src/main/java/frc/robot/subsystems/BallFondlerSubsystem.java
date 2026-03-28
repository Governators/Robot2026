package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;
import frc.robot.LimelightHelpers;

public class BallFondlerSubsystem extends SubsystemBase {

  private final SparkFlex shootingMotor;
  private final SparkMax intakeMotor;
  private final SparkMax loadingMotor;

  private final SparkClosedLoopController shootingController;

  public BallFondlerSubsystem() {
    intakeMotor = new SparkMax(DriveConstants.kIntakeMotorCanId, MotorType.kBrushless);
    loadingMotor = new SparkMax(DriveConstants.kLoadingMotorCanId, MotorType.kBrushless);
    shootingMotor = new SparkFlex(DriveConstants.kShootingMotorCanId, MotorType.kBrushless);
    shootingController = shootingMotor.getClosedLoopController();
    stopAll();
  }

  // ===== SHOOTER =====

  public void shooterOn(double d) {
    shoot(-.6);
  }
  public void shooterOn() {
    shoot(-.75);
  }

  public void shootFeed(double d) {
    shoot(d);
    feed();
  }

  private void shoot(double value) {
    shootingMotor.set(value);
  }

  public void sixtyShooter() {
    shoot(-.6);
  }

  public void shootFeed() {
    shooterOn();
    feed();
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
    shootingMotor.set(0);
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
    shootingMotor.set(0);
  }

 

  @Override
    public void periodic() {
      SmartDashboard.putNumber("Shooter RPM", shootingMotor.getEncoder().getVelocity());
    }
}