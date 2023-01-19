package frc.robot.Auto;

import frc.robot.Components;
import frc.robot.DriveTrain;
import frc.robot.SensorInputs;
import edu.wpi.first.wpilibj.Relay;

public class AutoActionFlipper extends AutoAction {
    private int count;
    private Components comp;

    @Override
    public void Init(DriveTrain driveTrain, Components components, SensorInputs sensor) {
        comp = new Components();
        comp.autoFlipPlatform.set(Relay.Value.kOn);
        count = 0;
    }

    @Override
    public boolean Execute(DriveTrain driveTrain, Components components, SensorInputs sensor) {
        if (count < 20){
            count++;
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void Finalize(DriveTrain driveTrain, Components components, SensorInputs sensor) {
        comp.autoFlipPlatform.set(Relay.Value.kOff);
    }
}