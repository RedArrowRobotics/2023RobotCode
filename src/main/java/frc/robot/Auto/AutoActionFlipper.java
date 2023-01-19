package frc.robot.Auto;

import frc.robot.Components;
import frc.robot.DriveTrain;
import frc.robot.SensorInputs;
import edu.wpi.first.wpilibj.Relay;

public class AutoActionFlipper extends AutoAction {
    private int count = 0;
    private Components comp = new Components();

    @Override
    public void Init(DriveTrain driveTrain, Components components, SensorInputs sensor) {
        comp.autoFlipPlatform.set(Relay.Value.kOn);
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