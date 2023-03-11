package frc.robot.Auto;

import frc.robot.Components;
import frc.robot.DriveTrain;
import frc.robot.SensorInputs;

public class AutoActionLevelAfterCross extends AutoAction {
    private final double baseDrivePower = .5;
    private final float flatPitchTolerance = 0.66f;
    @Override
    public void Init(DriveTrain driveTrain, Components components, SensorInputs sensor) {
        
    }

    @Override
    public boolean Execute(DriveTrain driveTrain, Components components, SensorInputs sensor) {
        float currentPitch = sensor.currentPitchDegrees;
        
        if ( (currentPitch > flatPitchTolerance) && (currentPitch < flatPitchTolerance + 1.0f) ) {
            driveTrain.arcadeDrive(baseDrivePower / 2, 0);
        }
        else {
            driveTrain.arcadeDrive(baseDrivePower, 0);
        }

        boolean isFlat = (currentPitch < flatPitchTolerance) && (currentPitch >-flatPitchTolerance);

        return isFlat;
    }

    @Override
    public void Finalize(DriveTrain driveTrain, Components components, SensorInputs sensor) {
        
    }
    
}
