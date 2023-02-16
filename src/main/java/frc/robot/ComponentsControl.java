package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ComponentsControl {

    //Belts
    private final double beltK = -0.5;
    private final double beltGyroRotationInsideDegreeToleranceFromY = 70; //From ±90 degrees in each direction from start point
    //Intake
    private Double intakeUprightCount = 50.0;
    private Double intakeOutCount = 100.0;
    private Double intakeRotationSetSpeed = 0.4; //Must be a + value
    private Double intakeRotationUprightToleranceCounts = 10.0;

    public void runComponents(Components components, ControlInputs controlInputs, SensorInputs sensorInputs) {
        
        //Variable Defintions
        Double mainSideBeltSpeed = 0.0;
        Boolean intakeClamp = controlInputs.intakeClamp || sensorInputs.intakePressure;
        Double intakeEncoderPosition = components.intakeEncoder.getPosition(); //Assume this value is + towards out
        Double intakeRotationSpeed = 0.0;
        SmartDashboard.putNumber("Intake Rotation Count", intakeEncoderPosition);

        //Controls
            //Belts
        if (controlInputs.beltAuto) {
            if (sensorInputs.currentYawDegrees <= -(90 - beltGyroRotationInsideDegreeToleranceFromY) && sensorInputs.currentYawDegrees >= -(90 + beltGyroRotationInsideDegreeToleranceFromY)) {
                mainSideBeltSpeed = beltK;
            } else if (sensorInputs.currentYawDegrees >= (90 - beltGyroRotationInsideDegreeToleranceFromY) && sensorInputs.currentYawDegrees <= (90 + beltGyroRotationInsideDegreeToleranceFromY)) {
                mainSideBeltSpeed = -beltK;
            }
        } else {
            if (controlInputs.dumpBeltLeft) {
                mainSideBeltSpeed = beltK;
            } else if (controlInputs.dumpBeltRight) {
                mainSideBeltSpeed = -beltK;
            }
        }
            //Intake Clamping
        if (controlInputs.intakeRelease && intakeClamp) {
            intakeClamp = false;
        }
            //Intake Rotation
                //Negative -> Intake rotation towards home/in
                //Positive -> Intake rotation towards out
        if (controlInputs.intakeRotate == false) {
            if (sensorInputs.intakePressure) {
                //Head in towards home
                if (sensorInputs.intakeLimitHome == false) {
                    intakeRotationSpeed = -intakeRotationSetSpeed;
                } else {
                    components.intakeEncoder.setPosition(0);
                }
            } else {
                //Head towards intakeUprightCount
                if (intakeEncoderPosition <= (intakeUprightCount - intakeRotationUprightToleranceCounts)) {
                    //Move out towards Upright
                    intakeRotationSpeed = intakeRotationSetSpeed;
                } else if (intakeEncoderPosition >= (intakeUprightCount + intakeRotationUprightToleranceCounts)) {
                    //Move in towards Upright
                    intakeRotationSpeed = -intakeRotationSetSpeed;
                }
            }
        } else {
            //Head out towards intakeOutCount
            if (intakeEncoderPosition < intakeOutCount) {
                intakeRotationSpeed = intakeRotationSetSpeed;
            }
        }


        //Set Components
        components.mainSideBelt.set(mainSideBeltSpeed);
        components.intakeArmClamp.set(intakeClamp);
        components.intakeRotationMotor.set(-intakeRotationSpeed);
    }
}