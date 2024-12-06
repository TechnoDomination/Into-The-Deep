package org.firstinspires.ftc.teamcode.OpModes.Auto;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Actions.CustomActions;
import org.firstinspires.ftc.teamcode.GoBildaPinPointOdo.Localizer;
import org.firstinspires.ftc.teamcode.GoBildaPinPointOdo.Poses;
import org.firstinspires.ftc.teamcode.Subsystems.Arm;
import org.firstinspires.ftc.teamcode.Subsystems.Claw;
import org.firstinspires.ftc.teamcode.Subsystems.Drive;
import org.firstinspires.ftc.teamcode.Subsystems.Slides;
import org.firstinspires.ftc.teamcode.Util.PIDFParams;
import org.firstinspires.ftc.teamcode.Util.Positions;


@Autonomous(name = "Auto Yellow Sample 1+0", group = "Auto")
public class AutoYellowSample1 extends LinearOpMode {

    public static double p = 0.08, i = 0.0, d = 0.01;
    public static double p2 = 0.08,i2 = 0.0, d2 = 0.01;
    public static double p3 = 1.1,i3 = 0.0,d3 = 0.0;


    @Override
    public void runOpMode() {
        telemetry = FtcDashboard.getInstance().getTelemetry();

        Localizer localizer = new Localizer(hardwareMap, new Poses(-35.0,-63.0,0.0));
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
                                new SleepAction(1),
                                Positions.GoFrontSample.runToExact,
                                Action -> {
                                    drive.stopDrive();
                                    return false;
                                },
                                new SleepAction(1),
                                Positions.Basket.runToExact,
                                Action -> {
                                    drive.stopDrive();
                                    return false;
                                },
                                new SleepAction(1),
                                customActions.prepareHighBasket,
                                new SleepAction(1),
                                customActions.dropSample,
                                new SleepAction(1),
                                customActions.slidesFullDown,
                                new SleepAction(1),
                                Positions.LeftSample1.runToExact,
                                Action -> {
                                    drive.stopDrive();
                                    return false;
                                },
                                Action -> {
                                    arm.state = Arm.State.REST;
                                    return false;
                                }
                                //customActions.prepareHighBasket,
                                //customActions.openClaw
                        )

                )
        );

    }
}



