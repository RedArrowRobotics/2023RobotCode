package frc.robot.Auto;

import frc.robot.Components;
import frc.robot.DriveTrain;
import frc.robot.SensorInputs;

public class AutoActionClimbPlatform extends AutoAction {

    @Override
    public void Init(DriveTrain driveTrain, Components components, SensorInputs sensor) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean Execute(DriveTrain driveTrain, Components components, SensorInputs sensor) {
        float currentPitch = sensor.currentPitch;
        if (currentPitch > 10)
        {
            driveTrain.arcadeDrive(.4, 0);
        }
        if (currentPitch < .5 && currentPitch > -.5)
        {
            driveTrain.arcadeDrive(0,0);
        }
        else
        {
            driveTrain.arcadeDrive(.4 * (currentPitch / 10) , 0);
        }
        return false;
    }

    @Override
    public void Finalize(DriveTrain driveTrain, Components components, SensorInputs sensor) {
        // TODO Auto-generated method stub
        
    }
    
}
