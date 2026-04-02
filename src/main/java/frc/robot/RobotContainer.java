// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.subsystems.LimelightSubsystem;
import frc.robot.subsystems.NetworkingSubsystem;
import frc.robot.subsystems.SubsystemRegistry;
import frc.robot.commands.Autos;
import frc.robot.commands.CommandShoot;
import frc.robot.commands.CommandShootFeed;
import frc.robot.commands.CommandShootRpmFeed;
import frc.robot.commands.CommandStopShoot;
import frc.robot.commands.CommandXStop;
import frc.robot.subsystems.BallFondlerSubsystem;
import frc.robot.subsystems.WheeeeelSubsystem;

import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.OIConstants;
import frc.robot.Constants.ShooterConstants;
import frc.robot.commands.CommandIntake;
import frc.robot.commands.CommandReverseIntake;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  public final BallFondlerSubsystem ballFondlerSubsystem = new BallFondlerSubsystem();
  public final NetworkingSubsystem networkingSubsystem;

  public final CommandXboxController m_driverController = new CommandXboxController(
      OIConstants.kDriverControllerPort); // kDriverControllerPort is int = 0

  public WheeeeelSubsystem m_robotDrive;

  public final CommandXboxController m_shooterController = new CommandXboxController(
      OIConstants.kShootControllerPort);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */

  public void configureAutoCommands() {

    /*
     * Here are our auto commands if you want to creat another auto command to make
     * your auto do stuff
     * you first need to create another command which I have helpfully created a
     * command folder for
     * go into the command folder copy an existing command and paste it into a new
     * file
     * it should be self explanitory from there
     * All our auto/command stuff is stolen from 3939
     * https://github.com/frc-team3939/2024-RobotCode/blob/main/2024-RobotCode/src/
     * main/java/frc/robot/RobotContainer.java
     * 
     */
    // Intake Commands

    // JOEL DID THE SPEED TO .5
    /*
     * NamedCommands.registerCommand("startIntake", new startIntake(slurper));
     * //just the one above this tho
     * NamedCommands.registerCommand("shoot", new shoot(buper, slurper));
     * // NamedCommands.registerCommand("stopShooter", new shoot(buper, slurper,
     * 0));
     * NamedCommands.registerCommand("stopIntake", new stopIntake(slurper));
     */

  }

  public final LimelightSubsystem limelightSubsystem = new LimelightSubsystem();

  public RobotContainer() {
    // Configure the trigger bindings
    configureAutoCommands();

    // autoChooser = AutoBuilder.buildAutoChooser(auto);

    // autoChooser = AutoBuilder.buildAutoChooser();
    // SmartDashboard.putData(autoChooser);
    SubsystemRegistry.ballFondlerSubsystem = ballFondlerSubsystem;
    m_robotDrive = new WheeeeelSubsystem();
    SubsystemRegistry.m_robotDrive = m_robotDrive;

    // configureButtonBindings();

    m_robotDrive.setDefaultCommand(
        // The left stick controls translation of the robot.
        // Turning is controlled by the X axis of the right stick.
        new RunCommand(
            () -> m_robotDrive.drive(
                -MathUtil.applyDeadband(m_driverController.getLeftY(), OIConstants.kDriveDeadband),
                -MathUtil.applyDeadband(m_driverController.getLeftX(), OIConstants.kDriveDeadband),
                -MathUtil.applyDeadband(m_driverController.getRightX(), OIConstants.kDriveDeadband),
                true, true),
            m_robotDrive));

    networkingSubsystem = new NetworkingSubsystem(ballFondlerSubsystem, m_robotDrive);
    networkingSubsystem.initDashboards();

    configureBindings();
    NamedCommands.registerCommand("stopShoot", new CommandStopShoot(ballFondlerSubsystem));
    NamedCommands.registerCommand("intake", new CommandIntake(ballFondlerSubsystem));
    NamedCommands.registerCommand("shootFeed",
        new CommandShootRpmFeed(ballFondlerSubsystem, ShooterConstants.kShortRpm));
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be
   * created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with
   * an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for
   * {@link
   * CommandXboxController
   * Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers
   */
  private void configureBindings() {
    m_driverController.x().whileTrue(new CommandXStop(m_robotDrive));
    m_shooterController.rightBumper()
        .whileTrue(new CommandShoot(ballFondlerSubsystem));

    m_shooterController.leftBumper()
        .whileTrue(new CommandIntake(ballFondlerSubsystem));

    m_shooterController.a()
        .whileTrue(new CommandReverseIntake(ballFondlerSubsystem));
    m_shooterController.b()
        .whileTrue(new CommandShootFeed(ballFondlerSubsystem, ShooterConstants.kShortSetpoint));
    m_shooterController.x()
        .whileTrue(new CommandShootFeed(ballFondlerSubsystem, ShooterConstants.kLongSetpoint));
    m_shooterController.y()
        .whileTrue(new CommandShootRpmFeed(ballFondlerSubsystem, ShooterConstants.kShortRpm));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    // return Autos.exampleAuto(ballFondlerSubsystem);
    //return getAutoFondler();
    return networkingSubsystem.getSelectedAutoCommand();
  }

  public Command getAutoFondler() {
    return new PathPlannerAuto("AutoFondler");
  }

  public Command getAutoSit() {
    return new PathPlannerAuto("AutoSit");
  }
}
