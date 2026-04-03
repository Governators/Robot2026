package frc.robot.subsystems;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class TargetAngleSubsystem extends SubsystemBase {
    private double kP = 0.02;
    private double kI = 0d;
    private double kD = 0d;
    private double targetAngle = 0;
    private boolean isTargeting;
    private boolean isTargetingCenter;
    public PIDController controller = new PIDController(kP, kI, kD);

    public TargetAngleSubsystem() {
        isTargeting = false;
        isTargetingCenter = false;
        controller.enableContinuousInput(-180, 180);
    }

    public double getTargetAngle() {
        return targetAngle;
    }
    public void setTargetAngle(double newAngle) {
        targetAngle = newAngle;
    }

    public boolean isTargeting() {
        return isTargeting;
    }

    public void setTargeting(boolean state) {
        isTargeting = state;
    }

    public boolean isTargetingCenter() {
        return isTargetingCenter;
    }

    public void setTargetingCenter(boolean state) {
        isTargetingCenter = state;
    }

    public void calculateCenterTarget() {
        targetAngle = 120; // TODO: actually implement
    }

    
    public double getSetpoint(double currentAngle) {
        if (isTargetingCenter) {
            calculateCenterTarget();
        }
        return controller.calculate(currentAngle, targetAngle);
    }

    public double getKP() {
        return kP;
    }
    public void setKP(double kayP) {
        kP = kayP;
        applyConfig();
    }
    public double getKI() {
        return kI;
    }
    public void setKI(double kayI) {
        kI = kayI;
        applyConfig();
    }
    public double getKD() {
        return kD;
    }
    public void setKD(double kayD) {
        kD = kayD;
        applyConfig();
    }
    private void applyConfig() {
        controller = new PIDController(kP, kI, kD);
        controller.enableContinuousInput(-180, 180);
    }
}
