package frc.robot.Auto;

import frc.robot.Components;
import frc.robot.DriveTrain;
import frc.robot.SensorInputs;

public class AutoActionDriveToChargingStation extends AutoAction {
    private double drivePower = 0;
    private final double maxDrivePower = .25;
    private final double powerIncrementPerCycle = .025;
    private final float pitchLimit = -10;
    @Override
    public void Init(DriveTrain driveTrain, Components components, SensorInputs sensor) {
        
    }

    @Override
    public boolean Execute(DriveTrain driveTrain, Components components, SensorInputs sensor) {
        driveTrain.arcadeDrive(drivePower, 0);
        drivePower = Math.max(maxDrivePower, drivePower + powerIncrementPerCycle);
        return sensor.currentPitchDegrees < pitchLimit;
    }

    @Override
    public void Finalize(DriveTrain driveTrain, Components components, SensorInputs sensor) {
        System.out.println("Found ramp");
    }
    
}
