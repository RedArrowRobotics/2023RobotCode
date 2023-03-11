package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ComponentsControl {

    //Belts
    private final double beltK = -0.25;
    private final double beltGyroRotationInsideDegreeToleranceFromY = 70; //From ±90 degrees in each direction from start point
    //Intake
    private final double intakeUprightCount = 320.0;
    private final double intakeOutCount = 972.0;
    private final double intakeRotationUprightToleranceCounts = 50.0;
    private final double intakeRotationMaxSpeed = 0.7; //Must be a + value
    private final double intakeRotationMinSpeed = 0.4; //Must be a + value
    private double intakeLastSetTarget = 0.0;
    private double intakeStartPos = 0.0;
    private final double intakeMotionScalerConstant = 0.06;
    private boolean intakeHomed = false;
    private boolean intakePressureSet = false;
    private boolean intakeEStopped = false;
    public boolean intakeClamp = false;
    private int intakeRotationCycleLock = 0; //Must always be set to a + value
    private boolean intakeManualMode = false;

    public void runComponents(Components components, ControlInputs controlInputs, SensorInputs sensorInputs) {
        
        //Variable Defintions
        double mainSideBeltSpeed = 0.0;
        intakeClamp = controlInputs.intakeClamp || intakePressureSet;
        int intakeEncoderPosition = components.intakeEncoder.get(); //Assume this value is + towards out
        double intakeRotationSpeed = 0.0;
        double intakeTarget = intakeEncoderPosition;
        SmartDashboard.putNumber("Intake Rotation Count", intakeEncoderPosition);
        boolean intakePressureSensor = sensorInputs.intakePressure;
        boolean beltsEnable = (intakeEncoderPosition >= (intakeUprightCount - intakeRotationUprightToleranceCounts));

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
                    intakeRotationCycleLock = 25;
                }
            }
            SmartDashboard.putBoolean("Intake PressureSet", intakePressureSet);
                //Intake Rotation
                    //Negative -> Intake rotation towards home/in
                    //Positive -> Intake rotation towards out
            if (intakeManualMode == false) {
                //Intake Automatic Mode
                if (intakeHomed) {
                    //Intake Movement Unlocked (Homed)
                    if (intakeRotationCycleLock <= 0) {
                        //Intake Movement Unlocked (Cycles)
                        if (controlInputs.intakeRotate == false) {
                            if (intakePressureSet) {
                                //Head in towards home
                                if (sensorInputs.intakeLimitHome == false) {
                                    intakeTarget = 0;
                                    SmartDashboard.putString("Intake Movement", "In -> Home");
                                } else {
                                    intakeTarget = intakeEncoderPosition;
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
                    } else {
                        intakeRotationCycleLock -= 1;
                    }
                    SmartDashboard.putNumber("Intake Target", intakeTarget);
                    SmartDashboard.putNumber("Intake Cycle Lock", intakeRotationCycleLock);
                    
                    //Intake Rotation Math
                    if (intakeLastSetTarget != intakeTarget) {
                        intakeStartPos = intakeEncoderPosition;
                        intakeLastSetTarget = intakeTarget;
                        SmartDashboard.putNumber("Intake Start Pos", intakeStartPos);
                    }
                    double intakeDistRemainTravel = (intakeTarget-intakeEncoderPosition);
                    double intakeMath = 0.0;
                    if (intakeTarget != intakeEncoderPosition) {
                        intakeMath = Math.sqrt(Math.abs(intakeDistRemainTravel));
                        intakeMath *= intakeMotionScalerConstant;
                        if (intakeDistRemainTravel < 0) {
                            intakeMath = Math.min(-intakeRotationMinSpeed, -intakeMath);
                        } else if (intakeDistRemainTravel > 0) {
                            intakeMath = Math.max(intakeRotationMinSpeed, intakeMath);
                        }
                    } else {
                        intakeMath = 0.0;
                    }
                    SmartDashboard.putNumber("Intake Math", intakeMath);
                    SmartDashboard.putNumber("Intake Dist Travel", intakeDistRemainTravel);

                    double intakeMathClamped = Math.max(-intakeRotationMaxSpeed, Math.min(intakeRotationMaxSpeed, intakeMath));
                    SmartDashboard.putNumber("Intake Math Clamped", intakeMathClamped);
                    intakeRotationSpeed = intakeMathClamped;
                } else {
                    //Intake Movement Locked (Not Homed)
                    if (sensorInputs.intakeLimitHome == false) {
                        intakeRotationSpeed = -intakeRotationMinSpeed;
                        SmartDashboard.putString("Intake Movement", "In (Homing)");
                    } else {
                        intakeHomed = true;
                        components.intakeEncoder.reset();
                    }
                }
            } else {
                //Intake Manual Mode
                if (controlInputs.intakeManualModeOut) {
                    intakeRotationSpeed = intakeRotationMinSpeed;
                } else if (controlInputs.intakeManualModeIn) {
                    intakeRotationSpeed = -intakeRotationMinSpeed;
                }
            }
            SmartDashboard.putBoolean("Intake Unlocked", intakeHomed);
            SmartDashboard.putNumber("Intake Power", intakeRotationSpeed);
        //Intake Code End (Exit E Stop)
        } else {
            intakeClamp = false;
        }
            //Belts
        if (beltsEnable) {
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

        components.autoFlipPlatformRelay.setAngle(controlInputs.flipper ? 0 : 90);
    }
}