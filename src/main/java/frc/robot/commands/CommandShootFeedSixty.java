package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.BallFondlerSubsystem;

public class CommandShootFeedSixty extends Command {
    private final BallFondlerSubsystem subsystem;

    public CommandShootFeedSixty(BallFondlerSubsystem subsystem) {
        this.subsystem = subsystem;
        addRequirements(subsystem);
    }

    @Override
    public void execute() {
        subsystem.shootFeed(-.6);
    }

    @Override
    public void end(boolean interrupted) {
        subsystem.stopShootFeed();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
