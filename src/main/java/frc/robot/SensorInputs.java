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
    private DigitalInput intakePressureSwitch = new DigitalInput(2);

    //Variable Defintions
    public boolean intakeLimitDown = false;
    public boolean intakeLimitUp = false;

    public float currentPitchDegrees = (float) 0.0;
    public float currentYawDegrees = (float)0.0;
    public float currentRollDegrees = (float)0.0; 
    public boolean intakePressure = false;

    //Reading the sensors
    public final void readSensors() {
        
        intakeLimitDown = intakeLimitSwitchDown.get();
        intakeLimitUp = intakeLimitSwitchUp.get();
        currentPitchDegrees = navxAhrs.getPitch();
        currentYawDegrees = navxAhrs.getYaw();
        currentRollDegrees = navxAhrs.getRoll();
        SmartDashboard.putNumber("NavX Pitch", currentPitchDegrees);
        SmartDashboard.putNumber("NavX Yaw", currentYawDegrees);
        SmartDashboard.putNumber("NavX Roll", currentRollDegrees);

        intakePressure = intakePressureSwitch.get();
    }
}