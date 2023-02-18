package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxRelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Relay;

public class Components {
    
    //Components Definitions
        //Teleop
    public final CANSparkMax mainSideBelt = new CANSparkMax(6, MotorType.kBrushless);
    public final CANSparkMax intakeRotationMotor = new CANSparkMax(5, MotorType.kBrushed);
    public final Solenoid intakeArmClamp = new Solenoid(PneumaticsModuleType.CTREPCM, 0);
    public final RelativeEncoder intakeEncoder = intakeRotationMotor.getEncoder(SparkMaxRelativeEncoder.Type.kQuadrature,8192);

        //Auto
    public final Relay autoFlipPlatform = new Relay(0, Relay.Direction.kForward);

        //Both
    public final Compressor compressor = new Compressor(0, PneumaticsModuleType.CTREPCM);
}