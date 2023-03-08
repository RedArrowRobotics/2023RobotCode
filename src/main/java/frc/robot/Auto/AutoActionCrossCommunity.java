package frc.robot.Auto;

import frc.robot.Components;
import frc.robot.DriveTrain;
import frc.robot.SensorInputs;

public class AutoActionCrossCommunity extends AutoAction {
    private AutoMove auto = new AutoMove();

    /*
        encoderCount = inches * gearRatio / (diameter * pi);
    */

    //Time in seconds to execute the move
    private double maxTime = 2.0;

    //Distance of move (edge of community) in inches
    private double distance = 160.0;

    private double toleranceInches = 2.0;
    
    //Allowable tolerance (error) of the error in encoder counts
    private double tolerance = toleranceInches * 12.75 / (Math.PI * 6);

    //Scaler constant applied to the power output
    private double moveK = 0.25;

    @Override
    public void Init(DriveTrain driveTrain, Components components, SensorInputs sensor) {
        auto.MoveInit(maxTime, distance, tolerance, moveK);
    }

    @Override
    public boolean Execute(DriveTrain driveTrain, Components components, SensorInputs sensor) {
        return auto.MoveExecute(driveTrain);
    }

    @Override
    public void Finalize(DriveTrain driveTrain, Components components, SensorInputs sensor) {}
}