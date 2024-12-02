package org.firstinspires.ftc.teamcode.OpModes.Auto;

import com.acmerobotics.dashboard.FtcDashboard;

import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.GoBildaPinPointOdo.Localizer;
import org.firstinspires.ftc.teamcode.GoBildaPinPointOdo.Poses;
import org.firstinspires.ftc.teamcode.Subsystems.Arm;
import org.firstinspires.ftc.teamcode.Subsystems.Drive;
import org.firstinspires.ftc.teamcode.Util.Positions;
import org.firstinspires.ftc.teamcode.Actions.CustomActions;


@Autonomous(name = "Auto Left Basket", group = "Auto")
public class AutoLeftBasket extends LinearOpMode {

    public static double p = 0.08, i = 0.0, d = 0.01;
    public static double p2 = 0.08,i2 = 0.0, d2 = 0.01;
    public static double p3 = 1.1,i3 = 0.0,d3 = 0.0;


    @Override
    public void runOpMode() {
        telemetry = FtcDashboard.getInstance().getTelemetry();

        Localizer localizer = new Localizer(hardwareMap, new Poses(-35.0,-63.0,0.0));
        Drive drive = new Drive(hardwareMap);
        Arm arm = new Arm(hardwareMap);
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
//.asihfdaus
                            return true;
                        },

                        new SequentialAction(

                                Positions.GoFront.runToExact,
                                customActions.stopDrive,
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
                                Positions.YellowLeftbrick3.runToExact,
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



