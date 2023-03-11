package frc.robot.Auto;

import frc.robot.Components;
import frc.robot.DriveTrain;
import frc.robot.SensorInputs;

public class AutoActionCrossChargingStation extends AutoAction {

    private final float outerPitchLimitInDegrees = 10;
    private final double maxDrivePower = .6;

    @Override
    public void Init(DriveTrain driveTrain, Components components, SensorInputs sensor) {
        
    }

    @Override
    public boolean Execute(DriveTrain driveTrain, Components components, SensorInputs sensor) {
        float currentPitch = sensor.currentPitchDegrees;
        if (currentPitch < -outerPitchLimitInDegrees) {
            driveTrain.arcadeDrive(maxDrivePower, 0);
        }

        if ( (currentPitch >= -outerPitchLimitInDegrees) && 
             (currentPitch < -outerPitchLimitInDegrees / 2.0) ) {
            driveTrain.arcadeDrive(maxDrivePower * 2.0f / 3.0f, 0);
        }

        if ( ( currentPitch >= -outerPitchLimitInDegrees / 2.0) && 
             ( currentPitch < 0.0) ) {
            driveTrain.arcadeDrive(maxDrivePower * 1.5f / 3.0f, 0);
        }

        if (currentPitch >= 0)
        {
            driveTrain.arcadeDrive(maxDrivePower * 1.0f / 3.0f, 0);
        }
        
        return (currentPitch >= outerPitchLimitInDegrees);
    }

    @Override
    public void Finalize(DriveTrain driveTrain, Components components, SensorInputs sensor) {
        
    }
    
}
