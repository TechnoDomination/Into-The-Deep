package org.firstinspires.ftc.teamcode.Actions;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Vector2d;

import org.firstinspires.ftc.teamcode.GoBildaPinPointOdo.Angle;
import org.firstinspires.ftc.teamcode.GoBildaPinPointOdo.Localizer;
import org.firstinspires.ftc.teamcode.Subsystems.Drive;

//RR - Actions
public class P2P implements Action {
    Vector2d targetVector;
    double rotation;

    public P2P(Vector2d vector2d, double rotation2d) {
        this.targetVector = vector2d;
        this.rotation = rotation2d;
    }


    @Override
    public boolean run(@NonNull TelemetryPacket telemetryPacket) {

        Localizer.Poses robotPosition = Localizer.pose;
        Drive motorController = Drive.instance;

        double latError = targetVector.y - robotPosition.getY();
        double axialError = targetVector.x - robotPosition.getX();
        double headingError = Angle.INSTANCE.wrap(rotation + robotPosition.getHeading());

        double lateral = motorController.yPid.calculate(latError);
        double axial = motorController.xPid.calculate(axialError);
        double turn = motorController.rPid.calculate(headingError);


        //field oriented drive
        double h = -robotPosition.getHeading();
        double rotX = axial * Math.cos(h) - lateral * Math.sin(h);
        double rotY = axial * Math.sin(h) + lateral * Math.cos(h);

        motorController.FrontLeftDCMotor.setPower(rotY + rotX + turn);
        motorController.BackLeftDCMotor.setPower(rotY - rotX + turn);
        motorController.FrontRightDCMotor.setPower(rotY - rotX - turn);
        motorController.BackRightDCMotor.setPower(rotY + rotX - turn);

        //checking if it has reached the point
        return !(
                Math.abs(latError) < 2.0 && Math.abs(axialError) < 2.0 && Math.abs(headingError) < Math.toRadians(5.0)
        );
    }
}
