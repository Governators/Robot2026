package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.HookConstants;

public class HookerSubsystem extends SubsystemBase {
    private boolean isHooked = false; // Whether the hook is currently engaged
    private SparkMax hookMotor; // Motor controlling the hook mechanism

    public HookerSubsystem() {
        hookMotor = new SparkMax(HookConstants.kHookMotorCanId, MotorType.kBrushless); // Replace 1 with the actual CAN ID for the hook motor
    }
} 
