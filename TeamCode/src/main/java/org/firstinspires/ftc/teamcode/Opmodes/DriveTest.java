package org.firstinspires.ftc.teamcode.Opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Drive.Drive;
import org.firstinspires.ftc.teamcode.GoBuildaComputer.Localizer;
@TeleOp(name = "Drive Test",group = "Linear OpMode")
public class DriveTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        Localizer localizer = new Localizer(hardwareMap, new Localizer.Poses(0.0,0.0,0.0));
        Drive drive = new Drive(hardwareMap);

        waitForStart();
        while(opModeIsActive() && !isStopRequested()){
            localizer.update();
            telemetry.addData("X pos", Localizer.pose.getX());
            telemetry.addData("Y pos", Localizer.pose.getY());
            telemetry.addData("Heading pos", Localizer.pose.getHeading());
            drive.update(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
        }
    }
}
