package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

public class BallFondlerSubsystem extends SubsystemBase {

    private final SparkMax shootingMotor;
    private final SparkMax intakeMotor;
    private final SparkMax loadingMotor;

    private boolean shooterEnabled = false;

    // Tune these later if needed
    private static final double SHOOT_SPEED = 1.0;
    private static final double INTAKE_SPEED = -0.33;
    private static final double LOAD_SPEED = 0.33;
    private static final double REVERSE_INTAKE_SPEED = 0.33;
    private static final double REVERSE_LOAD_SPEED = -0.33;

    public BallFondlerSubsystem() {
        intakeMotor = new SparkMax(DriveConstants.kIntakeMotorCanId, MotorType.kBrushless);
        loadingMotor = new SparkMax(DriveConstants.kLoadingMotorCanId, MotorType.kBrushless);
        shootingMotor = new SparkMax(DriveConstants.kShootingMotorCanId, MotorType.kBrushless);

        stopAll();
    }

    public void shooterOn() {
        shooterEnabled = true;
        shootingMotor.set(SHOOT_SPEED);
    }

    public void shooterOff() {
        shooterEnabled = false;
        shootingMotor.set(0.0);
    }

    public void toggleShooter() {
        if (shooterEnabled) {
            shooterOff();
        } else {
            shooterOn();
        }
    }

    public boolean isShooterEnabled() {
        return shooterEnabled;
    }

    public void intakeIn() {
        intakeMotor.set(INTAKE_SPEED);
        loadingMotor.set(LOAD_SPEED);
    }

    public void intakeReverse() {
        intakeMotor.set(REVERSE_INTAKE_SPEED);
        loadingMotor.set(REVERSE_LOAD_SPEED);
    }

    public void feedShooter() {
        loadingMotor.set(LOAD_SPEED);
    }

    public void stopIntake() {
        intakeMotor.set(0.0);
        loadingMotor.set(0.0);
    }

    public void stopAll() {
        intakeMotor.set(0.0);
        loadingMotor.set(0.0);
        shootingMotor.set(0.0);
        shooterEnabled = false;
    }

    public double getShootingMotorRPM() {
        return shootingMotor.getEncoder().getVelocity();
    }

    @Override
    public void periodic() {
        // Uncomment if needed, but spammy as hell:
        // System.out.println("Shooter RPM: " + getShootingMotorRPM());
    }
}