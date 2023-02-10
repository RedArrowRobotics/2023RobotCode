package frc.robot;

public class ComponentsControl {

    private final double beltK = 0.5;
    private final double beltGyroRotaionInsideDegreeToleranceFromY = 70; //From ±90 degrees in each direction from start point
    
    public void runComponents(Components components, ControlInputs controlInputs, SensorInputs sensorInputs) {
        
        //Variable Defintions
        Double mainSideBeltSpeed = 0.0;
        Boolean intakeClamp = controlInputs.intakeClamp || sensorInputs.intakePressure;

        //Controls
            //Belts
        if (controlInputs.beltAuto) {
            if (sensorInputs.currentYawDegrees <= -(90-beltGyroRotaionInsideDegreeToleranceFromY) && sensorInputs.currentYawDegrees >= -(90+beltGyroRotaionInsideDegreeToleranceFromY)) {
                mainSideBeltSpeed = beltK;
            } else if (sensorInputs.currentYawDegrees >= (90-beltGyroRotaionInsideDegreeToleranceFromY) && sensorInputs.currentYawDegrees <= (90+beltGyroRotaionInsideDegreeToleranceFromY)) {
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


        //Set Components
        components.mainSideBelt.set(mainSideBeltSpeed);
        components.intakeArmClamp.set(intakeClamp);
    }
}