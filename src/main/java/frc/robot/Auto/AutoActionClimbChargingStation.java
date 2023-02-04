package frc.robot.Auto;

import frc.robot.Components;
import frc.robot.DriveTrain;
import frc.robot.SensorInputs;

public class AutoActionClimbChargingStation extends AutoAction {
    private int levelCyclesCount = 0;
    private final float outerPitchLimitInDegrees = 10;
    private final float innerPitchLimitInDegrees = .5f;
    private final double maxDrivePower = .3;
    
    @Override
    public void Init(DriveTrain driveTrain, Components components, SensorInputs sensor) {}

    @Override
    public boolean Execute(DriveTrain driveTrain, Components components, SensorInputs sensor) {
        float currentPitch = sensor.currentPitchDegrees;
        if (currentPitch < -outerPitchLimitInDegrees)
        {
            levelCyclesCount = 0;
            driveTrain.arcadeDrive(maxDrivePower, 0);
        }
        if (currentPitch > outerPitchLimitInDegrees)
        {
            levelCyclesCount = 0;
            driveTrain.arcadeDrive(maxDrivePower, 0);
        }
        if (currentPitch < innerPitchLimitInDegrees && currentPitch > -innerPitchLimitInDegrees)
        {
            driveTrain.arcadeDrive(0,0);
            levelCyclesCount++;
        }
        else
        {
            levelCyclesCount = 0;
            driveTrain.arcadeDrive(-(maxDrivePower * (currentPitch / 10)) , 0);
        }
        return levelCyclesCount > 74;
    }

    @Override
    public void Finalize(DriveTrain driveTrain, Components components, SensorInputs sensor) {}    
}
