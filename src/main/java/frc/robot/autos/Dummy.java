package frc.robot.autos;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.CommandShootFeed;
import frc.robot.subsystems.SubsystemRegistry;

public class Dummy {
    public static final class Autos {
        public Command getShootForwardSlow() {
            return new CommandShootFeed(SubsystemRegistry.ballFondlerSubsystem, .05);
        }
        public Command getShootBackwardSlow() {
            return new CommandShootFeed(SubsystemRegistry.ballFondlerSubsystem, -.05);
        }
    }
}
