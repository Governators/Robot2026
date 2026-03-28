package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.BallFondlerSubsystem;

public class CommandFeed extends Command {
    private final BallFondlerSubsystem subsystem;

    public CommandFeed(BallFondlerSubsystem subsystem) {
        this.subsystem = subsystem;
        addRequirements(subsystem);
    }

    @Override
    public void execute() {
        subsystem.feed();
    }

    @Override
    public void end(boolean interrupted) {
        subsystem.stopFeed();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
