package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.BallFondlerSubsystem;
import frc.robot.subsystems.WheeeeelSubsystem;

public class CommandXStop extends Command {

  private final WheeeeelSubsystem subsystem;
  public CommandXStop(WheeeeelSubsystem subsystem) {
    this.subsystem = subsystem;
    addRequirements(subsystem);
  }

  @Override
  public void execute() {
    subsystem.setX();
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}