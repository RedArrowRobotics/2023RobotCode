package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class ComponentsControl {
    
    public void runComponents(Components components, ControlInputs controlInputs, SensorInputs sensorInputs) {
        
        //Variable Defintions
        Double mainSideBeltSpeed =0.0;

        //Controls
        if (controlInputs.beltLeft) {
            mainSideBeltSpeed = 1.0;
        } else if (controlInputs.beltRight) {
            mainSideBeltSpeed = -1.0;
        }

        //Set Components
        components.mainSideBelt.set(ControlMode.PercentOutput, mainSideBeltSpeed);
    }
}