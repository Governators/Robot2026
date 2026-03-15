package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.BallFondlerSubsystem;

public class CommandShoot extends Command {

  private final BallFondlerSubsystem subsystem;

  public CommandShoot(BallFondlerSubsystem subsystem) {
    this.subsystem = subsystem;
    addRequirements(subsystem);
  }

  @Override
  public void execute() {
    subsystem.shootFeed();
    
  }

  @Override
  public void end(boolean interrupted) {
    subsystem.stopAll();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}