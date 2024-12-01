package org.firstinspires.ftc.teamcode.TestOpmodes;

import com.acmerobotics.dashboard.FtcDashboard;

import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


import org.firstinspires.ftc.teamcode.GoBildaPinPointOdo.Localizer;
import org.firstinspires.ftc.teamcode.GoBildaPinPointOdo.Poses;
import org.firstinspires.ftc.teamcode.Subsystems.Arm;
import org.firstinspires.ftc.teamcode.Subsystems.Claw;
import org.firstinspires.ftc.teamcode.Subsystems.Drive;
import org.firstinspires.ftc.teamcode.Util.Positions;
import org.firstinspires.ftc.teamcode.Util.PIDFParams;
import org.firstinspires.ftc.teamcode.Subsystems.Slides;
import org.firstinspires.ftc.teamcode.Actions.CustomActions;


@Autonomous(name = "Auto Test", group = "Test OpModes")
public class AutoTest extends LinearOpMode {
    @Override
    public void runOpMode() {
        Localizer localizer = new Localizer(hardwareMap, new Poses(0.0,0.0,0.0));
        Drive drive = new Drive(hardwareMap);
        CustomActions customActions = new CustomActions(hardwareMap);
        customActions.update();

        waitForStart();

        Actions.runBlocking(
                new ParallelAction(
                        telemetryPacket -> {
                            localizer.update();
                            customActions.update();
                            telemetry.addData("X pos", Localizer.pose.getX());
                            telemetry.addData("Y pos", Localizer.pose.getY());
                            telemetry.addData("Heading pos", Localizer.pose.getHeading());
                            for(String string: customActions.getTelemetry()) telemetry.addLine(string);
                            telemetry.update();
                            return true;
                        },
                        new SequentialAction(
                                Positions.Test.runToExact,
                                Action -> {
                                    drive.stopDrive();
                                    return false;
                                },
                                /*Action -> {
                                    claw.state = Claw.State.IN;
                                    return false;
                                }*/
                                customActions.prepareHighBasket,
                                new SleepAction(0.5),
                                customActions.dropSample,
                                new SleepAction(0.5),
                                customActions.slidesFullDown

                        )

                )
        );

    }
}



