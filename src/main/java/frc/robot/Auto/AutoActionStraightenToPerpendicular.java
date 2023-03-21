package frc.robot.Auto;

import frc.robot.Components;
import frc.robot.DriveTrain;
import frc.robot.SensorInputs;

public class AutoActionStraightenToPerpendicular extends AutoAction {

    private final float yawThreshold = .5f;

    @Override
    public void Init(DriveTrain driveTrain, Components components, SensorInputs sensor) {
        
    }

    @Override
    public boolean Execute(DriveTrain driveTrain, Components components, SensorInputs sensor) {
        if ( (sensor.currentYawDegrees > -yawThreshold) && (sensor.currentYawDegrees > yawThreshold) )
        {
            driveTrain.arcadeDrive(0, 0);
            return true;
        }

        // facing left (-) means turn right (+), facing right (+) means turn left (-)
        driveTrain.arcadeDrive(0, -1 * Math.copySign(0.2, sensor.currentYawDegrees));

        return false;
    }

    @Override
    public void Finalize(DriveTrain driveTrain, Components components, SensorInputs sensor) {
        
    }
    
}
