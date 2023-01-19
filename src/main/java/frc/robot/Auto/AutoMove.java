package frc.robot.Auto;

import frc.robot.DriveTrain;
import frc.robot.Motion;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoMove {
    
    //Class Defintions
    private final DriveTrain driveTrain = new DriveTrain();
    private Motion motion;

    //Variable Defintions
    private double rotationsToTravel;
    private double gearRatio = 12.75;
    private double wheelDiameter = 6;
    private double timeStarted;
    private double currentTime;
    private double power;
    private boolean done;

    //To convert inches to encoder count do: inches * gearRatio / (diameter * pi)
    public final void MoveInit(double maxTime, double inchesToTravel, double toleranceInEncoderCount, double scaler) {

        //Variable Defintions
        rotationsToTravel = inchesToTravel * gearRatio / (Math.PI * wheelDiameter);
        timeStarted = Timer.getFPGATimestamp();
        
        //Smart Dashboard Output
        SmartDashboard.putNumber("Auto Rotations To Travel", rotationsToTravel);

        //Class Defintion/Initialize
        motion = new Motion(maxTime, rotationsToTravel, toleranceInEncoderCount, scaler);
    }

    public final boolean MoveExecute() {
        
        //Variable Defintions
        double currentPosition = driveTrain.getFrontLeftPosition();
        currentTime = ((Timer.getFPGATimestamp()) - timeStarted);
        power = motion.getPower(currentPosition, currentTime);
        
        //Smart Dashboard Output
        SmartDashboard.putNumber("Auto Left Current Position", driveTrain.getFrontLeftPosition());
        SmartDashboard.putNumber("Auto Right Current Position", driveTrain.getFrontRightPosition());

        //Control drivetrain
        driveTrain.arcadeDrive(power, 0);

        //Return true if done
        done = motion.isDone(currentPosition, currentTime);
        return done;
    }
}