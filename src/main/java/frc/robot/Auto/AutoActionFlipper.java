package frc.robot.Auto;

import frc.robot.Components;
import frc.robot.DriveTrain;
import frc.robot.SensorInputs;

public class AutoActionFlipper extends AutoAction {
    private int count = 0;
    private int delayCyclesBeforeComplete = 25;
    @Override
    public void Init(DriveTrain driveTrain, Components components, SensorInputs sensor) {
        components.autoFlipPlatformRelay.setAngle(0);
    }

    @Override
    public boolean Execute(DriveTrain driveTrain, Components components, SensorInputs sensor) {
        driveTrain.arcadeDrive(0, 0);
        if (count < delayCyclesBeforeComplete){
            count++;
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void Finalize(DriveTrain driveTrain, Components components, SensorInputs sensor) {
        components.autoFlipPlatformRelay.setAngle(90);
    }
}