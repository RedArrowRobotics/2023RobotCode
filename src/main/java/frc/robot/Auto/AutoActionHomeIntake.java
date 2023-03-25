package frc.robot.Auto;

import frc.robot.Components;
import frc.robot.DriveTrain;
import frc.robot.SensorInputs;

public class AutoActionHomeIntake extends AutoAction {

    @Override
    public void Init(DriveTrain driveTrain, Components components, SensorInputs sensor) {
        
    }

    @Override
    public boolean Execute(DriveTrain driveTrain, Components components, SensorInputs sensor) {
        driveTrain.arcadeDrive(0, 0);
        if (!sensor.intakeLimitHome)
        {
            components.intakeRotationMotor.set(0.4);
            return false;
        }
        else
        {
            components.intakeRotationMotor.set(0.0);
            return true;
        }
    }

    @Override
    public void Finalize(DriveTrain driveTrain, Components components, SensorInputs sensor) {
        
    }


    
}
