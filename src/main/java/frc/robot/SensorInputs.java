package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Port;

import com.kauailabs.navx.frc.AHRS;

public class SensorInputs {
    
    //Sensor Definitions
    private DigitalInput intakeLimitSwitchHome = new DigitalInput(0);
    private DigitalInput intakePressureSwitch = new DigitalInput(1);
    private DigitalInput intakePhotoEyeSensor = new DigitalInput(2);
    private SerialPort pixyPort;
    private byte[] pixyGetBlockRequest = new byte[]{0x61};
    private byte[] pixyCalculateTargetBlockIndexRequest = new byte[]{0x62};
    private final AHRS navxAhrs = new AHRS(SPI.Port.kMXP);
    
    //Variable Defintions
    public boolean intakeLimitHome = false;
    public boolean intakePressure = false;
    public boolean intakeProxySensor = false;
    public float currentPitchDegrees = (float) 0.0;
    public float currentYawDegrees = (float)0.0;
    public float currentRollDegrees = (float)0.0; 

    public boolean pixyAvailable = false;
    public boolean objectDetected;
    public int objectWidth;
    public int objectXCoordinate;

    public SensorInputs() {
        try {
            pixyPort = new SerialPort(115200, Port.kUSB1);
            pixyAvailable = true;
            System.out.print("Arduino/Pixy Serial Port Created\n");
        }
        catch (Exception ex)
        {
            //An exception is thrown if there serial port fails to open,
            //catch it so it doesn't crash the robot code
            System.out.print("Arduino/Pixy serial port create/open failed\n");
        }
    }

    //Reading the sensors
    public final void readSensors() {
        
        //Intake
        intakeLimitHome = intakeLimitSwitchHome.get();
        intakePressure = intakePressureSwitch.get();
        intakeProxySensor = !intakePhotoEyeSensor.get();

        SmartDashboard.putBoolean("Intake Homed", intakeLimitHome);
        SmartDashboard.putBoolean("Intake Pressure", intakePressure);
        SmartDashboard.putBoolean("Intake PhotoEye", intakeProxySensor);

        //NavX
        currentPitchDegrees = navxAhrs.getPitch();
        currentYawDegrees = navxAhrs.getYaw();
        currentRollDegrees = navxAhrs.getRoll();
        SmartDashboard.putNumber("NavX Pitch", currentPitchDegrees);
        SmartDashboard.putNumber("NavX Yaw", currentYawDegrees);
        SmartDashboard.putNumber("NavX Roll", currentRollDegrees);

        
    }

    public final void calculatePixyBlockIndex()
    {
        if (pixyAvailable) {
            pixyPort.write(pixyCalculateTargetBlockIndexRequest, 1);
            byte[] arduinoResponse = pixyPort.read(1);
            if (arduinoResponse[0] == 0x01)
            {
                SmartDashboard.putBoolean("Pixy Block Index Set", true);
            }
            else
            {
                SmartDashboard.putNumber("Foo", arduinoResponse[0]);
            }
        }
    }

    public final void getPixyBlocks()
    {
        if (pixyAvailable) {
            pixyPort.write(pixyGetBlockRequest, 1);
            byte[] arduinoResponse = pixyPort.read(1);
            if (arduinoResponse[0] == 0x01) {
                SmartDashboard.putBoolean("Detected", true);
                byte[] objectData = new byte[8];
                objectData = pixyPort.read(8);
                objectXCoordinate = (int)(objectData[0] & 0xFF) * 256 + (int)(objectData[1] & 0xFF);
                objectWidth = (int)(objectData[4] & 0xFF) * 256 + (int)(objectData[5] & 0xFF);
                SmartDashboard.putNumber("Pixy X", objectXCoordinate);
                SmartDashboard.putNumber("Pixy Width", objectWidth);
                objectDetected = true;
            }
            else {
                SmartDashboard.putBoolean("Detected", false);
                objectDetected = false;
            }
        }
        else {
            objectDetected = false;
        }
    }
}