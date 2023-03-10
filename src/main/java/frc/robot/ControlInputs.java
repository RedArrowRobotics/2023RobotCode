package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ControlInputs {
    
    //Control Mode
    private boolean GameMode = false;
    private boolean GameModeCanSwitch = false;
    private boolean GameModeIntakeUse = false;

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
    public boolean dumpBeltRight = false;
    public boolean dumpBeltLeft = false;
    public boolean beltAuto = false;
    public boolean intakeRotate = false;
    public boolean intakeClamp = false;
    public boolean intakeRelease = false;
    public boolean intakeClampSwitchModes = false; //Switch
    public boolean intakeEStop = false; //Switch
    public boolean intakeManualModeOut = false;
    public boolean intakeManualModeIn = false;

    //debug
    public boolean flipper = false;

    //Reading the controls
    public final void readControls(ComponentsControl componentsControl) {
        
        //Drivestick
        driveStickX = driveStick.getX();
        driveStickY = driveStick.getY();
        
        //Buttons
        intakeManualModeIn = driveStick.getRawButton(11);
        intakeManualModeOut = driveStick.getRawButton(9);
        if (GameMode == false) {
            //Mechanism Stick Right
            beltAuto = mechanismStickRight.getRawButton(beltsAutoId);
            dumpBeltLeft = mechanismStickRight.getRawButton(beltLeftId);
            dumpBeltRight = mechanismStickRight.getRawButton(beltRightId);
            intakeClamp = mechanismStickRight.getRawButton(intakeClampId);
            intakeRelease = mechanismStickRight.getRawButton(intakeReleaseId);
            intakeClampSwitchModes = (mechanismStickRight.getX() >= -0.5);
            //Mechanism Stick Left
            intakeRotate = mechanismStickLeft.getRawButton(intakeRotateId);
            intakeEStop = (mechanismStickLeft.getX() <= -0.5);
        } else if (GameMode) {
            //Mechanism Stick Right
            beltAuto = driveStick.getRawButton(5);
            dumpBeltLeft = driveStick.getRawButton(3);
            dumpBeltRight = driveStick.getRawButton(4);
            //intakeClamp = driveStick.getRawButton(1);
            //intakeRelease = driveStick.getRawButton(6);
            intakeClampSwitchModes = true;
            //Mechanism Stick Left
            intakeRotate = driveStick.getRawButton(2);
            intakeEStop = driveStick.getRawButton(7);

            //Intake Clamp
            if (driveStick.getRawButton(1)) {
                if (GameModeIntakeUse) {
                    GameModeIntakeUse = false;
                    if (componentsControl.intakeClamp) {
                        intakeRelease = true;
                    } else {
                        intakeClamp = true;
                    }
                }
            } else {
                GameModeIntakeUse = true;
                intakeClamp = false;
                intakeRelease = false;
            }
        }

        //Gamemode Toggle
        if (driveStick.getRawButton(10) && driveStick.getRawButton(12)) {
            if (GameModeCanSwitch) {
                GameModeCanSwitch = false;
                GameMode = !GameMode;
            }
        } else {
            GameModeCanSwitch = true;
        }
        SmartDashboard.putBoolean("GameMode", GameMode);

        //Intake Release
        intakeRelease = intakeRelease || beltAuto || dumpBeltLeft || dumpBeltRight;

        //debug
        flipper = driveStick.getRawButton(8);
    }
}