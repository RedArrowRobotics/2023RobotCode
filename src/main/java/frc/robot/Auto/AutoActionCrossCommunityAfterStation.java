package frc.robot.Auto;

import frc.robot.Components;
import frc.robot.DriveTrain;
import frc.robot.SensorInputs;

public class AutoActionCrossCommunityAfterStation extends AutoAction {

    double initialLeftEncoderCount;
    double initialRightEncoderCount;
    double encoderDelta = 5.0;
    final double motorPower = .3;

    @Override
    public void Init(DriveTrain driveTrain, Components components, SensorInputs sensor) {
        initialLeftEncoderCount = driveTrain.getFrontLeftPosition();
        initialRightEncoderCount = driveTrain.getFrontRightPosition();
    }

    @Override
    public boolean Execute(DriveTrain driveTrain, Components components, SensorInputs sensor) {
        driveTrain.arcadeDrive(motorPower, 0);

        return ( driveTrain.getFrontLeftPosition() > (initialLeftEncoderCount + encoderDelta) ) &&
        ( driveTrain.getFrontRightPosition() > (initialRightEncoderCount + encoderDelta) );
    }

    @Override
    public void Finalize(DriveTrain driveTrain, Components components, SensorInputs sensor) {
        
    }
    
}
