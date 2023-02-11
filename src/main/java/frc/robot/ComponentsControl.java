package frc.robot;

public class ComponentsControl {

    private final double beltK = 0.5;
    private final double beltGyroRotationInsideDegreeToleranceFromY = 70; //From ±90 degrees in each direction from start point
    
    public void runComponents(Components components, ControlInputs controlInputs, SensorInputs sensorInputs) {
        
        //Variable Defintions
        Double mainSideBeltSpeed = 0.0;
        Boolean intakeClamp = controlInputs.intakeClamp || sensorInputs.intakePressure;

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
                //Positive -> Intake rotation in
                //Negative -> Intake rotation out


        //Set Components
        components.mainSideBelt.set(mainSideBeltSpeed);
        components.intakeArmClamp.set(intakeClamp);
    }
}