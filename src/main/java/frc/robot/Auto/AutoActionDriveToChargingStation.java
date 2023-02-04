package frc.robot.Auto;

import frc.robot.Components;
import frc.robot.DriveTrain;
import frc.robot.SensorInputs;

public class AutoActionDriveToChargingStation extends AutoAction {
    private double drivePower = 0;
    private final double maxDrivePower = .2;
    private final double powerIncrementPerCycle = .012;
    private final float pitchLimit = -10;
    
    private double initialLeftEncoderCount = 0f;
    private double initialRightEncoderCount = 0f;
    private final double encoderDeltaLimit = 150;
    @Override
    public void Init(DriveTrain driveTrain, Components components, SensorInputs sensor) {
        initialLeftEncoderCount = driveTrain.getFrontLeftPosition();
        initialRightEncoderCount = driveTrain.getFrontRightPosition();
    }

    @Override
    public boolean Execute(DriveTrain driveTrain, Components components, SensorInputs sensor) {
        driveTrain.arcadeDrive(drivePower, 0);
        drivePower = Math.max(maxDrivePower, drivePower + powerIncrementPerCycle);
        
        boolean beyondPitchLimit =  sensor.currentPitchDegrees < pitchLimit;
        boolean pastEncoderDeltaLimit = 
            ( driveTrain.getFrontLeftPosition() > (initialLeftEncoderCount + encoderDeltaLimit) ) ||
            ( driveTrain.getFrontRightPosition() > (initialRightEncoderCount + encoderDeltaLimit) );

        return beyondPitchLimit || pastEncoderDeltaLimit;
    }

    @Override
    public void Finalize(DriveTrain driveTrain, Components components, SensorInputs sensor) {
       
    }
    
}
