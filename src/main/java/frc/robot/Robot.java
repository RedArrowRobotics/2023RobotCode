// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Auto.AutoAction;

import frc.robot.Auto.AutoActionDoNothing;
import frc.robot.Auto.AutoActionCrossCommunity;
import frc.robot.Auto.AutoActionFlipper;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  //Class Initiation
  private final DriveTrain driveTrain = new DriveTrain();
  private final ControlInputs controlInputs = new ControlInputs();
  private final SensorInputs sensorInputs = new SensorInputs();
  private Components components = new Components();
  
  //Variable Initiation
  private double forwardPower = 1.0;
  private double turnPower = 1.0;

  //Auto Variable Initiation
  private String autoSelected;
  private final String kAutoModeNull = "Do Nothing";
  private final String kAutoCrossCommunity = "Cross Community";
  private ArrayList<AutoAction> autonomousSequence;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    SmartDashboard.putStringArray("Auto List", 
      new String[]{
        kAutoModeNull,
        kAutoCrossCommunity,
      });
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {}

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    
    autonomousSequence = new ArrayList<AutoAction>();
    autoSelected = SmartDashboard.getString("Auto Selector", kAutoModeNull);
    switch (autoSelected) {
      case kAutoCrossCommunity:
        autonomousSequence.add(new AutoActionCrossCommunity());
        break;
      default:
        autonomousSequence.add(new AutoActionDoNothing());
        break;
    }
    autonomousSequence.get(0).Init(driveTrain, components, sensorInputs);
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {

    if (autonomousSequence.size() > 0) {
      if (autonomousSequence.get(0).Execute(driveTrain, components, sensorInputs)) {
        autonomousSequence.get(0).Finalize(driveTrain, components, sensorInputs);
        autonomousSequence.remove(0);
        if (autonomousSequence.size() > 0) {
          autonomousSequence.get(0).Init(driveTrain, components, sensorInputs);
        }
      }
    }
    else
    {
      driveTrain.arcadeDrive(0.0, 0.0);
    }
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    controlInputs.readControls();

    driveTrain.arcadeDrive(
      -controlInputs.driveStickY*forwardPower,
      controlInputs.driveStickX*turnPower);
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
