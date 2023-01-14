package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Compressor;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Components {
    
    public final TalonSRX mainSideBelt = new TalonSRX(6);
    public final CANSparkMax intakeRotationMotor = new CANSparkMax(6, MotorType.kBrushless);

    public final Compressor compressor = new Compressor(0, PneumaticsModuleType.CTREPCM);
}