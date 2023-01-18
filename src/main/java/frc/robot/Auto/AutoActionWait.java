package frc.robot.Auto;

import frc.robot.Components;
import frc.robot.DriveTrain;
import frc.robot.SensorInputs;

public class AutoActionWait extends AutoAction {
    
    //Variable Defintions
    private int cycleCount = 0;
    private int maxCount;

    //Initializer
    public AutoActionWait(int max) {
        maxCount = max;
    }

    @Override
    public void Init(DriveTrain driveTrain, Components components, SensorInputs sensor) {
        cycleCount = 0;
    }

    @Override
    public boolean Execute(DriveTrain driveTrain, Components components, SensorInputs sensor) {
        cycleCount++;
        return (cycleCount > maxCount);
    }

    @Override
    public void Finalize(DriveTrain driveTrain, Components components, SensorInputs sensor) {}
}