package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.BallFondlerSubsystem;

public class CommandShootFeed extends Command {
    private final BallFondlerSubsystem subsystem;

    public CommandShootFeed(BallFondlerSubsystem subsystem) {
        this.subsystem = subsystem;
        addRequirements(subsystem);
    }

    @Override
    public void execute() {
        subsystem.shootFeed();
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
