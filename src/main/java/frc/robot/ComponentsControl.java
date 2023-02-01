package frc.robot;

public class ComponentsControl {
    
    public void runComponents(Components components, ControlInputs controlInputs, SensorInputs sensorInputs) {
        
        //Variable Defintions
        Double mainSideBeltSpeed = 0.0;
        Boolean intakeClamp = controlInputs.intakeClamp || sensorInputs.intakePressure;

        //Controls
            //Belts
        if (controlInputs.beltAuto) {

        } else {
            if (controlInputs.dumpBeltLeft) {
                mainSideBeltSpeed = 1.0;
            } else if (controlInputs.dumpBeltRight) {
                mainSideBeltSpeed = -1.0;
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