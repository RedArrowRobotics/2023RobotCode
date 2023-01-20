package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Compressor;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Relay;

public class Components {
    
    //Components Definitions
    //public final TalonSRX mainSideBelt = new TalonSRX(6);
    //public final CANSparkMax intakeRotationMotor = new CANSparkMax(5, MotorType.kBrushless);
    public final Solenoid intakeArmClamp = new Solenoid(PneumaticsModuleType.CTREPCM, 0);
    public final Relay autoFlipPlatform = new Relay(0, Relay.Direction.kForward);

    public final Compressor compressor = new Compressor(0, PneumaticsModuleType.CTREPCM);
}