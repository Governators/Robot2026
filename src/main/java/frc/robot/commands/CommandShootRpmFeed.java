package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.BallFondlerSubsystem;

public class CommandShootRpmFeed extends Command {

  private final BallFondlerSubsystem subsystem;
  private final double rpmTarget;
  public CommandShootRpmFeed(BallFondlerSubsystem subsystem, double rpmTarget) {
    this.subsystem = subsystem;
    addRequirements(subsystem);
    this.rpmTarget = rpmTarget;
  }

  // Calls RPM shoot feed in BallFondlerSubsystem
  @Override
  public void execute() {
    subsystem.rpmShootFeed(rpmTarget);
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