package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

public class BallFondlerSubsystem extends SubsystemBase {

  private final SparkMax shootingMotor;
  private final SparkMax intakeMotor;
  private final SparkMax loadingMotor;

  public BallFondlerSubsystem() {
    intakeMotor = new SparkMax(DriveConstants.kIntakeMotorCanId, MotorType.kBrushless);
    loadingMotor = new SparkMax(DriveConstants.kLoadingMotorCanId, MotorType.kBrushless);
    shootingMotor = new SparkMax(DriveConstants.kShootingMotorCanId, MotorType.kBrushless);

    stopAll();
  }

  // ===== SHOOTER =====

  public void shooterOn() {
    System.out.println("Shooter ON");
    shootingMotor.set(1.0);
  }

  public void shooterOff() {
    System.out.println("Shooter OFF");
    shootingMotor.set(0.0);
  }

  // ===== INTAKE =====

  public void intakeForward() {
    System.out.println("Intake Forward");
    intakeMotor.set(1);
    loadingMotor.set(1);
  }

  public void intakeReverse() {
    System.out.println("Intake Reverse");
    shootingMotor.set(-1);
    intakeMotor.set(-1);
    loadingMotor.set(-1);
  }

  // ===== SHOOT FEED =====

  public void shootFeed() {
    System.out.println("Shoot Feed");
    shootingMotor.set(1.0);
    
    loadingMotor.set(-1);
    intakeMotor.set(1);
  }

  // ===== STOP =====

  public void stopIntake() {
    shootingMotor.set(0.0);
    intakeMotor.set(0.0);
    loadingMotor.set(0.0);
  }

  public void stopAll() {
    intakeMotor.set(0.0);
    loadingMotor.set(0.0);
    shootingMotor.set(0.0);
  }

  public double getShootingMotorRPM() {
    return shootingMotor.getEncoder().getVelocity();
  }
}