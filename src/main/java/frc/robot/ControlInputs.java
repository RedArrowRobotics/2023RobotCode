package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

public class ControlInputs {
    
    //Joysticks IDs
    private final int driveStickDeviceId = 0;
    private final int mechanismStickRightId = 1;
    private final int mechanismStickLeftId = 2;

    //Buttons IDs
    private final int beltLeftId = 12;
    private final int beltRightId = 11;

    //Joystick Definitions
    private final Joystick driveStick = new Joystick(driveStickDeviceId);
    private final Joystick mechanismStickRight = new Joystick(mechanismStickRightId);
    private final Joystick mechanismStickLeft = new Joystick(mechanismStickLeftId);

    //Variable Defintions
    public double driveStickX = 0.0;
    public double driveStickY = 0.0;
    //public boolean speedButton = false;
    public boolean beltLeft = false;
    public boolean beltRight = false;

    //Reading the controls
    public final void readControls() {
        
        //Drivestick
        driveStickX = driveStick.getX();
        driveStickY = driveStick.getY();
        
        //Buttons
        //speedButton = driveStick.getRawButton(12);
        beltLeft = mechanismStickRight.getRawButton(beltLeftId);
        beltRight = mechanismStickRight.getRawButton(beltRightId);
    }
}