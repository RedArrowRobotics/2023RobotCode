package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

public class ControlInputs {
    
    //Joysticks IDs
    private final int driveStickDeviceId = 0;
    private final int mechanismStickRightId = 1;
    private final int mechanismStickLeftId = 2;

    //Buttons IDs
        //Mechanism Stick Right
    private final int beltLeftId = 12;
    private final int beltRightId = 11;
    private final int intakeClampId = 10;
    private final int intakeReleaseId = 8;
    private final int beltsAutoId = 9;
        //Mechanism Stick Left
    private final int intakeRotateId = 12;

    //Joystick Definitions
    private final Joystick driveStick = new Joystick(driveStickDeviceId);
    private final Joystick mechanismStickRight = new Joystick(mechanismStickRightId);
    private final Joystick mechanismStickLeft = new Joystick(mechanismStickLeftId);

    //Variable Defintions
    public double driveStickX = 0.0;
    public double driveStickY = 0.0;
    //public boolean speedButton = false;
    public boolean dumpBeltRight = false;
    public boolean dumpBeltLeft = false;
    public boolean beltAuto = false;
    public boolean intakeRotate = false;
    public boolean intakeClamp = false;
    public boolean intakeRelease = false;
    public boolean intakeClampSwitchModes = false; //Switch
    public boolean intakeEStop = false;

    //debug
    public boolean flipper = false;

    //Reading the controls
    public final void readControls() {
        
        //Drivestick
        driveStickX = driveStick.getX();
        driveStickY = driveStick.getY();
        
        //Buttons
        //speedButton = driveStick.getRawButton(12);
            //Mechanism Stick Right
        beltAuto = mechanismStickRight.getRawButton(beltsAutoId);
        dumpBeltLeft = mechanismStickRight.getRawButton(beltLeftId);
        dumpBeltRight = mechanismStickRight.getRawButton(beltRightId);
        intakeClamp = mechanismStickRight.getRawButton(intakeClampId);
        intakeRelease = mechanismStickRight.getRawButton(intakeReleaseId);
        intakeClampSwitchModes = (mechanismStickRight.getX() >= -0.5);
            //Mechanism Stick Left
        intakeRotate = mechanismStickLeft.getRawButton(intakeRotateId);
        intakeEStop = (mechanismStickLeft.getX() >= -0.5);

        //Intake Release
        intakeRelease = intakeRelease || beltAuto || dumpBeltLeft || dumpBeltRight;

        //debug
        flipper = driveStick.getRawButton(2);
    }
}