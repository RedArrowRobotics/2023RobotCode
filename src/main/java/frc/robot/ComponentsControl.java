package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Relay;

public class ComponentsControl {

    //Belts
    private final double beltK = -0.25;
    private final double beltGyroRotationInsideDegreeToleranceFromY = 70; //From ±90 degrees in each direction from start point
    //Intake
    private final double intakeUprightCount = 340.0;
    private final double intakeOutCount = 972.0;
    private final double intakeRotationUprightToleranceCounts = 30.0;
    private final double intakeRotationMaxSpeed = 1.0; //Must be a + value (0.7)
    private final double intakeMinSpeed = 0.5; //Must be a + value
    private final double intakePerCountConstant = (intakeRotationMaxSpeed/intakeOutCount);
    private boolean intakeHomed = false;
    private boolean intakePressureSet = false;
    private boolean intakeEStopped = false;

    public void runComponents(Components components, ControlInputs controlInputs, SensorInputs sensorInputs) {
        
        //Variable Defintions
        double mainSideBeltSpeed = 0.0;
        boolean intakeClamp = controlInputs.intakeClamp || intakePressureSet;
        int intakeEncoderPosition = components.intakeEncoder.get(); //Assume this value is + towards out
        double intakeRotationSpeed = 0.0;
        double intakeTarget = 0.0;
        SmartDashboard.putNumber("Intake Rotation Count", intakeEncoderPosition);
        boolean intakeUpright = false;
        boolean intakePressureSensor = sensorInputs.intakePressure;

        //Controls
            //Intake
        if (controlInputs.intakeEStop) {
            intakeEStopped = true;
        }
        SmartDashboard.putBoolean("Intake E Stopped", intakeEStopped);
        if (intakeEStopped == false) {
        //Intake Code Start (Enter E Stop)
            //Intake Clamping
        if (controlInputs.intakeClampSwitchModes == false) {
            SmartDashboard.putString("Intake Clamp Mode", "Clamp Only");
        } else {
            intakePressureSensor = controlInputs.intakeClamp;
            SmartDashboard.putString("Intake Clamp Mode", "Clamp / Pressure Override");
        }
        if (intakePressureSensor && controlInputs.intakeClamp) {
            intakePressureSet = true;
        }
        if (controlInputs.intakeRelease && intakeClamp) {
            intakeClamp = false;
            if (intakePressureSensor == false) {
                intakePressureSet = false;
            }
        }
        SmartDashboard.putBoolean("Intake PressureSet", intakePressureSet);
            //Intake Rotation
                //Negative -> Intake rotation towards home/in
                //Positive -> Intake rotation towards out
        if (intakeHomed) {
            //Intake Movement Unlocked (Homed)
            if (controlInputs.intakeRotate == false) {
                if (intakePressureSet) {
                    //Head in towards home
                    intakeTarget = 0;
                    if (sensorInputs.intakeLimitHome == false) {
                        SmartDashboard.putString("Intake Movement", "In -> Home");
                    } else {
                        SmartDashboard.putString("Intake Movement", "Homed");
                    }
                } else {
                    //Head towards intakeUprightCount
                    if (intakeEncoderPosition <= (intakeUprightCount - intakeRotationUprightToleranceCounts)) {
                        //Move out towards Upright
                        intakeTarget = intakeUprightCount;
                        SmartDashboard.putString("Intake Movement", "Out -> Upright");
                    } else if (intakeEncoderPosition >= (intakeUprightCount + intakeRotationUprightToleranceCounts)) {
                        //Move in towards Upright
                        intakeTarget = intakeUprightCount;
                        SmartDashboard.putString("Intake Movement", "In -> Upright");
                    } else {
                        intakeTarget = intakeEncoderPosition;
                        intakeUpright = true;
                        SmartDashboard.putString("Intake Movement", "Upright");
                    }
                }
            } else {
                //Head out towards intakeOutCount
                if (intakeEncoderPosition < intakeOutCount) {
                    intakeTarget = intakeOutCount;
                    SmartDashboard.putString("Intake Movement", "Out -> Out");
                } else {
                    intakeTarget = intakeEncoderPosition;
                    SmartDashboard.putString("Intake Movement", "Out");
                }
            }
            SmartDashboard.putNumber("Intake Target", intakeTarget);
                //Intake Rotation Math
            double intakeDistTravel = (intakeTarget-intakeEncoderPosition);
            double intakeMath = 0.0;
            if (intakeTarget != intakeEncoderPosition) {
                intakeMath = intakePerCountConstant*intakeDistTravel;
                if (intakeMath < 0) {
                    intakeMath = Math.min(-intakeMinSpeed, intakeMath);
                } else if (intakeMath > 0) {
                    intakeMath = Math.max(intakeMinSpeed, intakeMath);
                }
                SmartDashboard.putNumber("Intake Math", intakeMath);
            } else {
                intakeMath = 0.0;
                SmartDashboard.putNumber("Intake Math", intakeMath);
            }
            double intakeMathClamped = Math.max(-intakeRotationMaxSpeed, Math.min(intakeRotationMaxSpeed, intakeMath));
            SmartDashboard.putNumber("Intake Math Clamped", intakeMathClamped);
            SmartDashboard.putNumber("Intake Dist Travel", intakeDistTravel);
            intakeRotationSpeed = intakeMathClamped;
        } else {
            //Intake Movement Locked (Not Homed)
            if (sensorInputs.intakeLimitHome == false) {
                intakeRotationSpeed = -0.2;
                SmartDashboard.putString("Intake Movement", "In (Homing)");
            } else {
                intakeHomed = true;
                components.intakeEncoder.reset();
            }
        }
        SmartDashboard.putBoolean("Intake Unlocked", intakeHomed);
        SmartDashboard.putNumber("Intake Power", intakeRotationSpeed);       
        if (controlInputs.setHome) {
            components.intakeEncoder.reset();
        }
        //Intake Code End (Exit E Stop)
        } else {
            intakeClamp = false;
        }
            //Belts
        if (intakeUpright) {
            if (controlInputs.beltAuto) {
                if (sensorInputs.currentYawDegrees <= -(90 - beltGyroRotationInsideDegreeToleranceFromY) && sensorInputs.currentYawDegrees >= -(90 + beltGyroRotationInsideDegreeToleranceFromY)) {
                    mainSideBeltSpeed = beltK;
                } else if (sensorInputs.currentYawDegrees >= (90 - beltGyroRotationInsideDegreeToleranceFromY) && sensorInputs.currentYawDegrees <= (90 + beltGyroRotationInsideDegreeToleranceFromY)) {
                    mainSideBeltSpeed = -beltK;
                }
            } else {
                if (controlInputs.dumpBeltLeft) {
                    mainSideBeltSpeed = beltK;
                } else if (controlInputs.dumpBeltRight) {
                    mainSideBeltSpeed = -beltK;
                }
            }
        }

        //Set Components
        components.mainSideBelt.set(mainSideBeltSpeed);
        components.intakeArmClamp.set(intakeClamp);
        components.intakeRotationMotor.set(-intakeRotationSpeed);

        components.autoFlipPlatform.set(controlInputs.flipper ? Relay.Value.kOn : Relay.Value.kOff);
    }
}