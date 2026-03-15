package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.BallFondlerSubsystem;

public class CommandStopShoot extends Command {

  private final BallFondlerSubsystem subsystem;

  public CommandStopShoot(BallFondlerSubsystem subsystem) {
    this.subsystem = subsystem;
    addRequirements(subsystem);
  }

  @Override
  public void execute() {
    subsystem.shooterOff();
  }

  @Override
  public void end(boolean interrupted) {
    subsystem.stopAll();
  }

  @Override
  public boolean isFinished() {
    return true;
  }
}