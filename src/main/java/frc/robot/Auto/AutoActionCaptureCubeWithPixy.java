package frc.robot.Auto;

import edu.wpi.first.hal.FRCNetComm.tResourceType;
import frc.robot.Components;
import frc.robot.DriveTrain;
import frc.robot.SensorInputs;

public class AutoActionCaptureCubeWithPixy extends AutoAction {

    private double initialLeftEncoderCount = 0.0;
    private double initialRightEncoderCount = 0.0;
    private double leftEncoderCountLimit;
    private double rightEncoderCountLimit;
    private final double baseMotorPower = .25;

    @Override
    public void Init(DriveTrain driveTrain, Components components, SensorInputs sensor) {
        initialLeftEncoderCount = driveTrain.getFrontLeftPosition();
        initialRightEncoderCount = driveTrain.getFrontRightPosition();

        //27.63 inches from cube to line
        leftEncoderCountLimit = initialLeftEncoderCount + 0;
        rightEncoderCountLimit = initialRightEncoderCount + 0;

        sensor.calculatePixyBlockIndex();

    }

    @Override
    public boolean Execute(DriveTrain driveTrain, Components components, SensorInputs sensor) {
        if (sensor.pixyAvailable == false) {
            driveTrain.arcadeDrive(0,0);
            return true;
        } 

        if (sensor.intakeProxySensor)
        {
            components.intakeArmClamp.set(true);
            driveTrain.arcadeDrive(0,0);
            return true;
        }

        sensor.getPixyBlocks();
        if (!sensor.objectDetected) {
            driveTrain.arcadeDrive(0,0);
            return true;
        } 

        if ( (sensor.objectXCoordinate < 143) || (sensor.objectXCoordinate > 163) ) {
            if (sensor.objectXCoordinate < 143) {
                driveTrain.arcadeDrive(0,-0.25);
            }
            else {
                driveTrain.arcadeDrive(0.0, -0.25);
            }
        }
        else {
            driveTrain.arcadeDrive(baseMotorPower,0.0);
        }

        return false;

    }

    @Override
    public void Finalize(DriveTrain driveTrain, Components components, SensorInputs sensor) {
        
    }
    
}
