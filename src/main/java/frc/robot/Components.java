package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Servo;

public class Components {
    
    //Components Definitions
        //Teleop
    public final CANSparkMax mainSideBelt = new CANSparkMax(6, MotorType.kBrushless);
    public final CANSparkMax intakeRotationMotor = new CANSparkMax(5, MotorType.kBrushed);
    public final Solenoid intakeArmClamp = new Solenoid(16, PneumaticsModuleType.REVPH, 0);
    public Encoder intakeEncoder = new Encoder(8, 9);

        //Auto
    //public final Relay autoFlipPlatform = new Relay(0, Relay.Direction.kForward);
    public final Servo autoFlipPlatformRelay = new Servo(9);

        //Both
    public final Compressor compressor = new Compressor(16, PneumaticsModuleType.REVPH);
}