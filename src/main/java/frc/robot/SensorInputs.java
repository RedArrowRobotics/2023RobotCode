package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.kauailabs.navx.frc.AHRS;

public class SensorInputs {
    
    //Sensor Definitions
    private DigitalInput intakeLimitSwitchHome = new DigitalInput(0);
    private DigitalInput intakePressureSwitch = new DigitalInput(1);
    private DigitalInput intakePhotoEyeSensor = new DigitalInput(2);

    private final AHRS navxAhrs = new AHRS(SPI.Port.kMXP);

    //Variable Defintions
    public boolean intakeLimitHome = false;
    public boolean intakePressure = false;
    public boolean intakeProxySensor = false;

    public float currentPitchDegrees = (float) 0.0;
    public float currentYawDegrees = (float)0.0;
    public float currentRollDegrees = (float)0.0; 

    //Reading the sensors
    public final void readSensors() {
        
        //Intake
        intakeLimitHome = intakeLimitSwitchHome.get();
        intakePressure = intakePressureSwitch.get();
        intakeProxySensor = !intakePhotoEyeSensor.get();

        SmartDashboard.putBoolean("Intake Homed", intakeLimitHome);
        SmartDashboard.putBoolean("Intake Pressure", intakePressure);
        SmartDashboard.putBoolean("Intake Proxy", intakeProxySensor);

        //NavX
        currentPitchDegrees = navxAhrs.getPitch();
        currentYawDegrees = navxAhrs.getYaw();
        currentRollDegrees = navxAhrs.getRoll();
        SmartDashboard.putNumber("NavX Pitch", currentPitchDegrees);
        SmartDashboard.putNumber("NavX Yaw", currentYawDegrees);
        SmartDashboard.putNumber("NavX Roll", currentRollDegrees);
    }
}