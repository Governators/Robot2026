package frc.robot.subsystems;

import frc.robot.Constants;
import edu.wpi.first.util.datalog.DoubleLogEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.RelativeEncoder;

//23.5 / 2pi = num rotations of elevator
// each rotation = 6.3 cm

/*import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Rotation;
import static edu.wpi.first.units.Units.Rotations;
import edu.wpi.first.wpilibj.DataLogManager;
import com.revrobotics.sim.SparkMaxSim;
import com.revrobotics.sim.SparkFlexSim;
import com.revrobotics.sim.SparkLimitSwitchSim;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkBaseConfig; // Import SparkBaseConfig
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import frc.robot.Constants.ElevatorConstants.ElevatorSetpoints;
import com.revrobotics.spark.config.SparkMaxConfig;
import frc.robot.Configs;
import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.util.datalog.DataLog;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.simulation.ElevatorSim;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import edu.wpi.first.wpilibj2.command.Command;*/

public class ElevatorSubsystem extends SubsystemBase {

  /** Instantiates elevator motors */
  private static SparkMax leftElevator = new SparkMax(Constants.DriveConstants.kLeftElevatorCanId, MotorType.kBrushless);
  private static SparkMax rightElevator = new SparkMax(Constants.DriveConstants.kRightElevatorCanId, MotorType.kBrushless);
  private final SparkMax wristMotor = new SparkMax(Constants.DriveConstants.kWristCanId, MotorType.kBrushless); 

  //private final SparkMaxConfig leftElevatorConfig;
  //private final SparkMaxConfig rightElevatorConfig;
  //private final SparkMaxConfig wristMotorConfig;

  //private double elevatorPowerLevel = 0;
  //private double wristPowerLevel = 0;


  public enum Setpoint {
    kFeederStation,
    kLevel1,
    kLevel2,
    kLevel3,
    kLevel4,
    kUnblock;
  }

  //private boolean wristBlocking;
  //private boolean elevatorBlocking;

  // Initialize elevator SPARK. We will use MAXMotion position control for the elevator, so we also
  // need to initialize the closed loop controller and encoder.
  
  /*private static SparkClosedLoopController elevatorClosedLoopController =
      leftElevator.getClosedLoopController();

  private SparkClosedLoopController wristClosedLoopController =
      wristMotor.getClosedLoopController();*/

  private RelativeEncoder elevatorEncoder = rightElevator.getEncoder(); //need a relative encoder to get number of ticks
   //private RelativeEncoder elevatorEncoder = leftElevator.getEncoder(); we might want to get both left and right encoder and average the values


  private AbsoluteEncoder wristEncoder = wristMotor.getAbsoluteEncoder();
  //wristEncoder.scaledInputs();



  // Member variables for subsystem state management
  //private boolean wasResetByButton = false;
  
  private static double elevatorCurrentTarget = Constants.ElevatorConstants.ElevatorSetpoints.kFeederStation; 
  private static double wristCurrentTarget = Constants.ElevatorConstants.ElevatorSetpoints.kFeederStation;
  

  private boolean wristManuallyMoving = true;
  private boolean elevatorManuallyMoving = true;


  DoubleLogEntry elevatorLog;
  DoubleLogEntry wristLog;

  private boolean wasResetByLimit = false;

  //the following code is stuff i added will need to be fixed later

  private final DigitalInput elevatorLimitSwitch = new DigitalInput(Constants.ElevatorConstants.ElevatorSetpoints. kElevatorLimitSwitchPort); // Initialize the limit switch

  //private static ElevatorFeedforward elevatorFeedforward = new ElevatorFeedforward(Constants.ElevatorConstants.kS, Constants.ElevatorConstants.kG, Constants.ElevatorConstants.kV);
  //private static ElevatorFeedforward wristFeedforward = new ElevatorFeedforward(Constants.ElevatorConstants.kWristS, Constants.ElevatorConstants.kWristG, Constants.ElevatorConstants.kWristV);

  // this thing is probalby un needed
  /*public ElevatorSubsystem() {
      // Option to Configure SparkMax settings (current limits, etc.)

      //leftElevator.setSmartCurrentLimit(Constants.ElevatorConstants.kCurrentLimit);
      //rightElevator.setSmartCurrentLimit(Constants.ElevatorConstants.kCurrentLimit);
      //wristMotor.setSmartCurrentLimit(Constants.ElevatorConstants.kWristCurrentLimit);
      leftElevatorConfig = new SparkMaxConfig();
      leftElevatorConfig.idleMode(IdleMode.kBrake);
      leftElevator.configure(leftElevatorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

      rightElevatorConfig = new SparkMaxConfig();
      rightElevatorConfig.idleMode(IdleMode.kBrake);
      rightElevator.configure(rightElevatorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

      wristMotorConfig = new SparkMaxConfig();
      wristMotorConfig.idleMode(IdleMode.kBrake);
      wristMotor.configure(wristMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

      //leftElevator.setClosedLoopRampRate(Constants.ElevatorConstants.kRampRate);
      //rightElevator.setClosedLoopRampRate(Constants.ElevatorConstants.kRampRate);
      //wristMotor.setClosedLoopRampRate(Constants.ElevatorConstants.kWristRampRate);

      //elevatorClosedLoopController.setP(Constants.ElevatorConstants.kP);
      //elevatorClosedLoopController.setI(Constants.ElevatorConstants.kI);
      //elevatorClosedLoopController.setD(Constants.ElevatorConstants.kD);

      //wristClosedLoopController.setP(Constants.ElevatorConstants.kWristP);
      //wristClosedLoopController.setI(Constants.ElevatorConstants.kWristI);
      //wristClosedLoopController.setD(Constants.ElevatorConstants.kWristD);


      //elevatorLog = new DoubleLogEntry(DataLogManager.getLog(), "Elevator Position");
      //wristLog = new DoubleLogEntry(DataLogManager.getLog(), "Wrist Position");

  }*/

  @Override
  public void periodic() {
      // This method will be called once per scheduler run
      elevatorLog.append(elevatorEncoder.getPosition());
      wristLog.append(wristEncoder.getPosition());

      // Check limit switch and reset encoder if triggered
      if (elevatorLimitSwitch.get()) {
          elevatorEncoder.setPosition(0); // Or a calibrated offset
          wasResetByLimit = true;
      }

      //smartdashboard is unused we gon have to figure out how to display this stuff another way
      //SmartDashboard.putNumber("Elevator Height", elevatorEncoder.getPosition());
      //SmartDashboard.putNumber("Wrist Angle", wristEncoder.getPosition());
      //SmartDashboard.putBoolean("Elevator Limit", elevatorLimitSwitch.get());

  }


  public static void setElevatorPosition(double targetPosition) {
    elevatorCurrentTarget = targetPosition;
    leftElevator.set(targetPosition);
    rightElevator.set(targetPosition);
    //double feedforward = elevatorFeedforward.calculate(0); // Calculate feedforward (velocity is 0 for position control)
    //elevatorClosedLoopController.setReference(targetPosition, ControlType.kPosition, ClosedLoopSlot.kSlot0, feedforward); // Corrected line
  }

  public void setWristPosition(double targetPosition) {
    wristCurrentTarget = targetPosition;
    wristMotor.set(targetPosition);
    //double feedforward = wristFeedforward.calculate(0); // Calculate feedforward (velocity is 0 for position control)
    //wristClosedLoopController.setReference(targetPosition, ControlType.kPosition, ClosedLoopSlot.kSlot0, feedforward); // Corrected line
  }

  public void setElevatorPower(double power) {
      elevatorManuallyMoving = true;
      leftElevator.set(power);
      rightElevator.set(power);
  }

  public void setWristPower(double power) {
      wristManuallyMoving = true;
      wristMotor.set(power);
  }

  public double getElevatorHeight() {
      return elevatorEncoder.getPosition(); //in units of revolution
  }

  public double getWristAngle() {
      return wristEncoder.getPosition();
  }

  public boolean isAtElevatorTarget() {
    return Math.abs(getElevatorHeight() - elevatorCurrentTarget) < Constants.ElevatorConstants.ElevatorSetpoints.kTolerance;
  }

  public boolean isAtWristTarget() {
      return Math.abs(getWristAngle() - wristCurrentTarget) < Constants.ElevatorConstants.ElevatorSetpoints.kWristTolerance;
  }

}