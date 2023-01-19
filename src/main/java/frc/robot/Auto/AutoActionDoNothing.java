package frc.robot.Auto;

import frc.robot.Components;
import frc.robot.DriveTrain;
import frc.robot.SensorInputs;

public class AutoActionDoNothing extends AutoAction {
    @Override
    public void Init(DriveTrain driveTrain, Components components, SensorInputs sensor) {}
    
    @Override
    public boolean Execute(DriveTrain driveTrain, Components components, SensorInputs sensors) {
        components.compressor.enableDigital();
        driveTrain.arcadeDrive(0.0, 0.0);
        return false;
    }

    @Override
    public void Finalize(DriveTrain driveTrain, Components components, SensorInputs sensor) {}
}
