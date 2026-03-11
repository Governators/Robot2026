// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import frc.robot.Constants.DriveConstants;;

public class BallFondlerSubsystem extends SubsystemBase {

  private SparkMax shootingMotor; // Shoots balls into hoop
  private SparkMax intakeMotor; // Loads balls into loadingMotor from the ground
  // intakeMotor also helps load balls by reversing direction I THINK
  private SparkMax loadingMotor; // Loads balls into shootingMotor
  // Also reverses for shooting? IDK
  private boolean isShooting = false; // Changes motor to on/off

  /** Creates a new ExampleSubsystem. */
  public BallFondlerSubsystem() {
    intakeMotor = new SparkMax(DriveConstants.kIntakeMotorCanId, MotorType.kBrushless); // CAN 19
    loadingMotor = new SparkMax(DriveConstants.kLoadingMotorCanId, MotorType.kBrushless); // CAN 18
    shootingMotor = new SparkMax(DriveConstants.kShootingMotorCanId, MotorType.kBrushless); // CAN 10
    intakeMotor.set(-0.33d); // temporary
    loadingMotor.set(0.33d); // temporary
    shootingMotor.set(0.00d); // temporary
  }

  private double getShootingMotorRPM() {
    return shootingMotor.getEncoder().getVelocity();
  }

  public Command toggleShooter() { // Turn the shooter on/off
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return runOnce(
        () -> {
          if (isShooting = !isShooting) { // Returns not flip, so be careful
            shootingMotor.set(1.00d);
          } else {
            shootingMotor.set(0.0d);
          }
        });
  }

  /**
   * An example method querying a boolean state of the subsystem (for example, a
   * digital sensor).
   *
   * @return value of some boolean subsystem state, such as a digital sensor.
   */
  public boolean exampleCondition() {
    return false;
    // conditionTester = !conditionTester; // This is for testing if I can on/off
    // motors with a button press like this
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    System.out.println(getShootingMotorRPM());
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
