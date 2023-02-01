package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;

public class SensorInputs {
    
    //Sensor Definitions
    private DigitalInput intakeLimitSwitchDown = new DigitalInput(0);
    private DigitalInput intakeLimitSwitchUp = new DigitalInput(1);
    private DigitalInput intakePressureSwitch = new DigitalInput(2);

    //Variable Defintions
    public boolean intakeLimitDown = false;
    public boolean intakeLimitUp = false;
    public boolean intakePressure = false;

    //Reading the sensors
    public final void readSensors() {
        
        intakeLimitDown = intakeLimitSwitchDown.get();
        intakeLimitUp = intakeLimitSwitchUp.get();
        intakePressure = intakePressureSwitch.get();
    }
}