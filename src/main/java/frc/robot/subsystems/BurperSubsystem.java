package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.BMotor;
import frc.robot.Constants;

//WILL HANDLE CORAL SPINNING
public class BurperSubsystem extends SubsystemBase{
        /** Creates a new ShooterSubsystem. */

    private final BMotor RightCoralSpinner;
    private final BMotor LeftCoralSpinner;

    public BurperSubsystem() {
        RightCoralSpinner = new BMotor(Constants.DriveConstants.kRightCoralSpinnerCanId);
        LeftCoralSpinner = new BMotor(Constants.DriveConstants.kLeftCoralSpinnerCanId);
    }

    @Override
    public void periodic() {
    // This method will be called once per scheduler run
    }

    public void setSpeed(double speed) {
        RightCoralSpinner.setSpeed(speed);
        LeftCoralSpinner.setSpeed(-speed);
    }
}
