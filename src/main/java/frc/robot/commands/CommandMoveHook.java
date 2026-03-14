package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.HookerSubsystem;

public class CommandMoveHook extends Command {
    public enum Direction {
        UP,
        DOWN
    }

    private final HookerSubsystem subsystem;
    private final Direction direction;

    public CommandMoveHook(HookerSubsystem subsystem, Direction direction) {
        this.subsystem = subsystem;
        this.direction = direction;
        addRequirements(subsystem);
    }

    @Override
    public void execute() {
        if (direction == Direction.UP) {
            subsystem.moveUp();
        } else {
            subsystem.moveDown();
        }
    }

    @Override
    public void end(boolean interrupted) {
        subsystem.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}