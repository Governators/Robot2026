package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.BallFondlerSubsystem;
import ;
public class CommandShoot extends Command {

  private final BallFondlerSubsystem subsystem;

  public CommandShoot(BallFondlerSubsystem subsystem) {
    this.subsystem = subsystem;
    addRequirements(subsystem);
  }

  @Override
  public void initialize() {
    subsystem.toggleShooter();
  }

  public Command runIntakeCommand() {
    return run(() -> {
        intakeMotor.set(0.5);
        loadingMotor.set(0.3);
    }, this);
}

public Command reverseIntakeCommand() {
    return run(() -> {
        intakeMotor.set(-0.5);
        loadingMotor.set(-0.3);
    }, this);
}

public Command stopAllCommand() {
    return runOnce(() -> {
        intakeMotor.set(0);
        loadingMotor.set(0);
        shootingMotor.set(0);
    });
}

  @Override
  public boolean isFinished() {
    return true; // runs once then ends
  }
}