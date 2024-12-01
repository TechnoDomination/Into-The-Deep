package org.firstinspires.ftc.teamcode.TestOpmodes;

import com.acmerobotics.dashboard.FtcDashboard;

import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


import org.firstinspires.ftc.teamcode.AACatalystsReferenceCode.PathPlanning.Positions;
import org.firstinspires.ftc.teamcode.GoBildaPinPointOdo.Localizer;
import org.firstinspires.ftc.teamcode.Subsystems.Arm;
import org.firstinspires.ftc.teamcode.Subsystems.Claw;
import org.firstinspires.ftc.teamcode.Subsystems.Drive;
import org.firstinspires.ftc.teamcode.Util.FieldPositions;
import org.firstinspires.ftc.teamcode.Util.PIDFParams;
import org.firstinspires.ftc.teamcode.Subsystems.Slides;
import org.firstinspires.ftc.teamcode.Actions.CustomActions;


@Autonomous(name = "Auto Test", group = "Test OpModes")
public class AutoTest extends LinearOpMode {

    public static double p = 0.08, i = 0.0, d = 0.01;
    public static double p2 = 0.08,i2 = 0.0, d2 = 0.01;
    public static double p3 = 1.1,i3 = 0.0,d3 = 0.0;

    @Override
    public void runOpMode() {
        Localizer localizer = new Localizer(hardwareMap, new Localizer.Poses(0.0,0.0,0.0));
        Drive drive = new Drive(hardwareMap);
        Claw claw = new Claw(hardwareMap);
        Slides slides = new Slides(hardwareMap);
        Arm arm = new Arm(hardwareMap);
        CustomActions customActions = new CustomActions(hardwareMap);

        waitForStart();

        Actions.runBlocking(
                new ParallelAction(
                        telemetryPacket -> {
                            localizer.update();
                            drive.xPid.setPIDF(new PIDFParams(p,i,d));
                            drive.yPid.setPIDF(new PIDFParams(p2,i2,d2));
                            drive.rPid.setPIDF(new PIDFParams(p3,i3,d3));
                            claw.update();
                            arm.update();
                            slides.update();
                            telemetry.addData("X pos", Localizer.pose.getX());
                            telemetry.addData("Y pos", Localizer.pose.getY());
                            telemetry.addData("Heading pos", Localizer.pose.getHeading());
                            telemetry.addData("Arm Telemetry = ", arm.getArmTelemetry());
                            telemetry.addData("Claw Telemetry = ", claw.getClawTelemetry());
                            telemetry.addData("Slides Telemetry = ", slides.getSlidesTelemetry());
                            telemetry.update();
                            return true;
                        },
                        new SequentialAction(
                                customActions.closeClaw,
                                FieldPositions.Test.runToExact,
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



