package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.kauailabs.navx.frc.AHRS;

public class SensorInputs {
    
    //Sensor Definitions
    private DigitalInput intakeLimitSwitchDown = new DigitalInput(0);
    private DigitalInput intakeLimitSwitchUp = new DigitalInput(1);

    private final AHRS navxAhrs = new AHRS(Port.kMXP);

    //Variable Defintions
    public boolean intakeLimitDown = false;
    public boolean intakeLimitUp = false;

    public float currentPitch = (float) 0.0; 

    //Reading the sensors
    public final void readSensors() {
        
        intakeLimitDown = intakeLimitSwitchDown.get();
        intakeLimitUp = intakeLimitSwitchUp.get();
        currentPitch = navxAhrs.getPitch();
        SmartDashboard.putNumber("NavX Pitch", currentPitch);
    }
}