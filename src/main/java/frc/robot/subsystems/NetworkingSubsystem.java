package frc.robot.subsystems;

import java.util.prefs.Preferences;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.dashboard.SelectableAutoRegistry;

public class NetworkingSubsystem extends SubsystemBase {
    private final BallFondlerSubsystem ballFondlerSubsystem;
    private final WheeeeelSubsystem m_robotDrive;
    private final SendableChooser<String> autoSelector;

    public NetworkingSubsystem(BallFondlerSubsystem ballFondlerSubsystem, WheeeeelSubsystem m_robotDrive) {
        this.ballFondlerSubsystem = ballFondlerSubsystem;
        this.m_robotDrive = m_robotDrive;
        autoSelector = new SendableChooser<>();
        for (String autoTitle : SelectableAutoRegistry.getAutoTitles()) {
            autoSelector.addOption(autoTitle, autoTitle);
        }
    }

    public void initDashboards() {
        addSwerveDrive();
        addSelectableAutos();
    }

    private void addSwerveDrive() {
        SmartDashboard.putData("Swerve Drive", new Sendable() {
            @Override
            public void initSendable(SendableBuilder builder) {
                builder.setSmartDashboardType("SwerveDrive");

                builder.addDoubleProperty("Front Left Angle", () -> m_robotDrive.getFrontLeft().getPositionTurning(), null);
                builder.addDoubleProperty("Front Left Velocity", () -> m_robotDrive.getFrontLeft().getVelocityDrive(), null);

                builder.addDoubleProperty("Front Right Angle", () -> m_robotDrive.getFrontRight().getPositionTurning(), null);
                builder.addDoubleProperty("Front Right Velocity", () -> m_robotDrive.getFrontRight().getVelocityDrive(), null);

                builder.addDoubleProperty("Back Left Angle", () -> m_robotDrive.getRearLeft().getPositionTurning(), null);
                builder.addDoubleProperty("Back Left Velocity", () -> m_robotDrive.getRearLeft().getVelocityDrive(), null);

                builder.addDoubleProperty("Back Right Angle", () -> m_robotDrive.getRearRight().getPositionTurning(), null);
                builder.addDoubleProperty("Back Right Velocity", () -> m_robotDrive.getRearRight().getVelocityDrive(), null);

                builder.addDoubleProperty("Robot Angle", () -> m_robotDrive.getCurrentRotation(), null);
            }
        });
        SmartDashboard.putData("Shooter data", new Sendable() {
            @Override
            public void initSendable(SendableBuilder builder) {
                builder.addDoubleProperty("Shooter RPM", () -> ballFondlerSubsystem.getShooterVelocity(), null);
            }
        });

    }

    private void addSelectableAutos() {
        SmartDashboard.putData(getName(), autoSelector);
    }

    public Command getSelectedAutoCommand() {
        return SelectableAutoRegistry.getCommandByTitle(autoSelector.getSelected());
    }
}
