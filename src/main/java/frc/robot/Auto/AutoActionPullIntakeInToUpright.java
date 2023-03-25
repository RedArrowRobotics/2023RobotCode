package frc.robot.Auto;

import frc.robot.Components;
import frc.robot.DriveTrain;
import frc.robot.SensorInputs;

public class AutoActionPullIntakeInToUpright extends AutoAction {

    private int encoderTarget;
    private final int targetDelta = 770;

    @Override
    public void Init(DriveTrain driveTrain, Components components, SensorInputs sensor) {
        encoderTarget = components.intakeEncoder.get() - targetDelta;
        
    }

    @Override
    public boolean Execute(DriveTrain driveTrain, Components components, SensorInputs sensor) {
        driveTrain.arcadeDrive(0.0,0.0 );
        if (components.intakeEncoder.get() > encoderTarget)
        {
            components.intakeRotationMotor.set(0.8);
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
        // TODO Auto-generated method stub
        
    }
    
}
