package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.SubsystemRegistry;

public final class Autos {
    private Autos() {
        throw new UnsupportedOperationException("This is a utility class!");
    }

    public static Command shootFeed() {
        return new SequentialCommandGroup(SubsystemRegistry.shootControlSubsystem.spoolShooter(), SubsystemRegistry.shootControlSubsystem.feed());
    }

    
}