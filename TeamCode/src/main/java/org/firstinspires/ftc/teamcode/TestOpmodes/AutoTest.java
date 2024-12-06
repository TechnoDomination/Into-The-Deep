package org.firstinspires.ftc.teamcode.TestOpmodes;

import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


import org.firstinspires.ftc.teamcode.GoBildaPinPointOdo.Localizer;
import org.firstinspires.ftc.teamcode.GoBildaPinPointOdo.Poses;
import org.firstinspires.ftc.teamcode.Subsystems.Arm;
import org.firstinspires.ftc.teamcode.Subsystems.Claw;
import org.firstinspires.ftc.teamcode.Subsystems.ClawRotater;
import org.firstinspires.ftc.teamcode.Subsystems.Drive;
import org.firstinspires.ftc.teamcode.Subsystems.Slides;
import org.firstinspires.ftc.teamcode.Util.Positions;
import org.firstinspires.ftc.teamcode.Actions.CustomActions;


@Autonomous(name = "Auto Test", group = "TestOpModes")
public class AutoTest extends LinearOpMode {
    @Override
    public void runOpMode() {
        Localizer localizer = new Localizer(hardwareMap, new Poses(-35,-63,0.0));
        Drive drive = new Drive(hardwareMap);
        Claw claw = new Claw(hardwareMap);
        ClawRotater clawRotater = new ClawRotater(hardwareMap);
        Slides slides = new Slides(hardwareMap);
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
                                Positions.LeftSample1.runToExact,
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