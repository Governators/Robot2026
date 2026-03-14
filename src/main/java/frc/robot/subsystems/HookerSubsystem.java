package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.HookConstants;

public class HookerSubsystem extends SubsystemBase {
    private final SparkMax hookMotor;

    // Tune later
    private static final double HOOK_UP_SPEED = 0.5;
    private static final double HOOK_DOWN_SPEED = -0.5;

    public HookerSubsystem() {
        hookMotor = new SparkMax(HookConstants.kHookMotorCanId, MotorType.kBrushless);
        stop();
    }

    public void moveUp() {
        hookMotor.set(HOOK_UP_SPEED);
    }

    public void moveDown() {
        hookMotor.set(HOOK_DOWN_SPEED);
    }

    public void stop() {
        hookMotor.set(0.0);
    }
}