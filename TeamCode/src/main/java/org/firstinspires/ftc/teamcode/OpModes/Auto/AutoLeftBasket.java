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
import org.firstinspires.ftc.teamcode.Subsystems.Drive;
import org.firstinspires.ftc.teamcode.Util.Positions;

@Autonomous(name = "Auto Left Basket 0+1", group = "Auto")
public class AutoLeftBasket extends LinearOpMode {

    public static double p = 0.08, i = 0.0, d = 0.01;
    public static double p2 = 0.08,i2 = 0.0, d2 = 0.01;
    public static double p3 = 1.1,i3 = 0.0,d3 = 0.0;

    @Override
    public void runOpMode() {
        telemetry = FtcDashboard.getInstance().getTelemetry();

        Localizer localizer = new Localizer(hardwareMap, new Poses(-35.0,-63.0,-Math.PI/2));
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
                                Positions.GoFrontSample.runToExact,
                                customActions.stopDrive,
                                new SleepAction(0.1),
                                Positions.Basket.runToExact,
                                customActions.stopDrive,
                                customActions.prepareHighBasket,
                                new SleepAction(0.5),
                                customActions.dropSample,
                                new SleepAction(0.5),
                                customActions.afterBasketDrop,
                                Positions.YellowLeftbrick3.runToExact,
                                customActions.stopDrive,
                                new SleepAction(0.5),
                                customActions.armSpecimenPicking,
                                new SleepAction(1),
                                customActions.armSamplePicking,
                                new SleepAction(1),
                                customActions.closeClaw,
                                new SleepAction(0.5),
                                customActions.armVertical,
                                Positions.Basket.runToExact,
                                customActions.stopDrive,
                                new SleepAction(0.5),
                                customActions.prepareHighBasket,
                                new SleepAction(.5),
                                customActions.dropSample,
                                new SleepAction(0.5),
                                customActions.afterBasketDrop,
                                customActions.armRest

                        )

                )
        );

    }
}
