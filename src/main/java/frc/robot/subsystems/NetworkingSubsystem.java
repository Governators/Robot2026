package frc.robot.subsystems;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.TargetAngleSubsystem;
import frc.robot.dashboard.SelectableAutoRegistry;

public class NetworkingSubsystem extends SubsystemBase {
    private final BallFondlerSubsystem ballFondlerSubsystem;
    private final WheeeeelSubsystem m_robotDrive;
    private final TargetAngleSubsystem targetAngleSubsystem;
    private final SendableChooser<String> autoSelector;
    private final PowerDistribution pdu;
    private final Field2d dashField;

    public NetworkingSubsystem(BallFondlerSubsystem ballFondlerSubsystem, WheeeeelSubsystem m_robotDrive, TargetAngleSubsystem targetAngleSubsystem) {
        this.ballFondlerSubsystem = ballFondlerSubsystem;
        this.m_robotDrive = m_robotDrive;
        this.targetAngleSubsystem = targetAngleSubsystem;
        autoSelector = new SendableChooser<>();
        for (String autoTitle : SelectableAutoRegistry.getAutoTitles()) {
            autoSelector.addOption(autoTitle, autoTitle);
        }
        pdu = new PowerDistribution(1, ModuleType.kRev);
        dashField = new Field2d();
    }

    public void initDashboards() {
        addSwerveDrive();
        addSelectableAutos();
        addDashField();
        addPdu();
        addPidFields();
        addRotationalPid();
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
        SmartDashboard.putData("Auto Selector", autoSelector);
    }
    
    private void addDashField() {
        SmartDashboard.putData("Field2d", dashField);
    }

    private void addRotationalPid() {
        SmartDashboard.putData("Rotational Pid", new Sendable() {
            @Override
            public void initSendable(SendableBuilder builder) {
                builder.addDoubleProperty("kP", targetAngleSubsystem::getKP, targetAngleSubsystem::setKP);
                builder.addDoubleProperty("kI", targetAngleSubsystem::getKI, targetAngleSubsystem::setKI);
                builder.addDoubleProperty("kD", targetAngleSubsystem::getKD, targetAngleSubsystem::setKD);
            }
        });
    }

    private void addPidFields() {
        SmartDashboard.putData("Shooter Pid", new Sendable() {
            @Override
            public void initSendable(SendableBuilder builder) {
                builder.addDoubleProperty("kP", ballFondlerSubsystem::getKP, ballFondlerSubsystem::setKP);
                builder.addDoubleProperty("kI", ballFondlerSubsystem::getKI, ballFondlerSubsystem::setKI);
                builder.addDoubleProperty("kD", ballFondlerSubsystem::getKD, ballFondlerSubsystem::setKD);
            }
        });
    }
    private void addPdu() {
        SmartDashboard.putData("Pdu", pdu);
    }
    public Command getSelectedAutoCommand() {
        return SelectableAutoRegistry.getCommandByTitle(autoSelector.getSelected());
    }

    @Override
    public void periodic() {
        dashField.setRobotPose(m_robotDrive.getPose());
    }
}
