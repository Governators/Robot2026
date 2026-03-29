package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.BallFondlerSubsystem;

public class CommandShootFeed extends Command {

  private final BallFondlerSubsystem subsystem;
  private final double setpoint;
  public CommandShootFeed(BallFondlerSubsystem subsystem, double setpoint) {
    this.subsystem = subsystem;
    addRequirements(subsystem);
    this.setpoint = setpoint;
  }

  // Calls RPM shoot feed in BallFondlerSubsystem
  @Override
  public void execute() {
    subsystem.shootFeed(setpoint);
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