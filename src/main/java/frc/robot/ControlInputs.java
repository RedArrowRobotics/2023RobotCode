package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

public class ControlInputs {
    
    //Joysticks IDs
    private final int driveStickDeviceId = 0;

    //Joystick Definitions
    private final Joystick driveStick = new Joystick(driveStickDeviceId);
    
    //Variable Defintions
    public double driveStickX = 0.0;
    public double driveStickY = 0.0;

    //Reading the controls
    public final void readControls() {
        
        driveStickX = driveStick.getX();
        driveStickY = driveStick.getY();
    }
}