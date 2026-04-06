package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

public class TurboSubsystem extends SubsystemBase {
    private static final double defaultMaxVelocity = 3;
    private static final double defaultMaxAngularSpeed = 2*Math.PI;

    private static double turboMaxVelocity = 4.5;
    private static double turboMaxAngularSpeed = 4*Math.PI;
    private boolean turboActivated;
    private long lastEnableMs;

    public TurboSubsystem() {
        turboActivated = false;
        lastEnableMs = 0;
    }

    @Override 
    public void periodic() {
        // timeout of 5s
        if (System.currentTimeMillis()-lastEnableMs < 5000 && turboActivated) {
            DriveConstants.kMaxSpeedMetersPerSecond = turboMaxVelocity;
            DriveConstants.kMaxAngularSpeed = turboMaxAngularSpeed;
        } else {
            turboActivated = false;
            DriveConstants.kMaxAngularSpeed = defaultMaxAngularSpeed;
            DriveConstants.kMaxSpeedMetersPerSecond = defaultMaxVelocity;
        }
    }

    public boolean getTurbo() {
        return turboActivated;
    }

    public void setTurbo(boolean b) {
        if (b) {
            enableTurbo();
        } else {
            disableTurbo();
        }
    }

    public void enableTurbo() {
        turboActivated = true;
        lastEnableMs = System.currentTimeMillis();
    }

    public void disableTurbo() {
        turboActivated = false;
    }

    public double getCurrentMaxVelocity() {
        return DriveConstants.kMaxSpeedMetersPerSecond;
    }

    public double getCurrentMaxAngularSpeed() {
        return DriveConstants.kMaxAngularSpeed;
    }

    public double getTurboMaxVelocity() {
        return turboMaxVelocity;
    }

    public void setTurboMaxVelocity(double d) {
        turboMaxVelocity = d;
    }

    public double getTurboMaxAngularSpeed() {
        return turboMaxAngularSpeed;
    }
    public void setTurboMaxAngularSpeed(double d) {
        turboMaxAngularSpeed = d;
    }

    public Command getTurboCommand() {
        return new Command() {
            @Override
            public void execute() {
                SubsystemRegistry.turboSubsystem.enableTurbo();
            }

            @Override
            public boolean isFinished() {
                return true;
            }
        };
    }

    
}
