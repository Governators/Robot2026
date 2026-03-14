package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.BallFondlerSubsystem;

public class CommandReverseIntake extends Command {
    private final BallFondlerSubsystem subsystem;

    public CommandReverseIntake(BallFondlerSubsystem subsystem) {
        this.subsystem = subsystem;
        addRequirements(subsystem);
    }

    @Override
    public void execute() {
        subsystem.intakeReverse();
    }

    @Override
    public void end(boolean interrupted) {
        subsystem.stopIntake();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}