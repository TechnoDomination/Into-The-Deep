package org.firstinspires.ftc.teamcode.OpModes.Auto;

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
import org.firstinspires.ftc.teamcode.Subsystems.ClawRotater;
import org.firstinspires.ftc.teamcode.Subsystems.Drive;
import org.firstinspires.ftc.teamcode.Subsystems.Slides;
import org.firstinspires.ftc.teamcode.Util.Positions;

@Autonomous(name = "Auto Basket", group = "Auto")
public class AutoBasket extends LinearOpMode {

    public static double p = 0.18, i = 0.0, d = 0.0175;
    public static double p2 = 0.09,i2 = 0.0, d2 = 0.018;
    public static double p3 = 1.1,i3 = 0.0,d3 = 0.0;

    
    @Override
    public void runOpMode() {
        //telemetry = FtcDashboard.getInstance().getTelemetry();

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
//.asihfdaus
                            return true;
                        },

                        new SequentialAction(
                                //Dropping sample 0
                                Positions.GoFrontSample.runToExact,
                                customActions.stopDrive,
                                new SleepAction(0.1),

                                Positions.Basket.runToExact,
                                customActions.stopDrive,
                                new SleepAction(0.1),

                                customActions.prepareHighBasket,
                                new SleepAction(0.5),
                                customActions.dropSample,
                                new SleepAction(0.75),
                                //customActions.afterBasketDrop,
                                customActions.armSamplePreperation,
                                new SleepAction(0.75),
                                customActions.slidesFullDown,

                                //Pick sample 1
                                Positions.LeftSample1.runToExact,
                                customActions.stopDrive,
                                new SleepAction(0.5),
                                customActions.armSubmersible,
                                new SleepAction(0.25),
                                customActions.armSpecimenPicking,
                                new SleepAction(0.75),
                                customActions.armBasketSamplePicking,
                                new SleepAction(0.5),
                                customActions.closeClaw,
                                new SleepAction(0.5),
                                customActions.armVertical,
                                
                                //Drop sample 1
                                Positions.Basket2.runToExact,
                                customActions.stopDrive,
                                new SleepAction(0.5),
                                customActions.prepareHighBasket,
                                new SleepAction(.5),
                                customActions.dropSample,
                                new SleepAction(0.5),
                                //customActions.afterBasketDrop,
                                customActions.armSamplePreperation,
                                new SleepAction(0.75),
                                customActions.slidesFullDown,

                                //Pick sample 2
                                Positions.LeftSample2.runToExact,
                                customActions.stopDrive,
                                new SleepAction(0.25),
                                customActions.armSubmersible,
                                new SleepAction(0.25),
                                customActions.armSpecimenPicking,
                                new SleepAction(0.25),
                                customActions.armBasketSamplePicking,
                                new SleepAction(0.5),
                                customActions.closeClaw,
                                new SleepAction(0.25),
                                customActions.armVertical,

                                //Drop sample 2
                                Positions.Basket2.runToExact,
                                customActions.stopDrive,
                                new SleepAction(0.5),
                                customActions.prepareHighBasket,
                                new SleepAction(.5),
                                customActions.dropSample,
                                new SleepAction(0.5),
                                customActions.afterBasketDrop,

                                //Pick sample 3
                                Positions.LeftSample3.runToExact,
                                customActions.stopDrive,
                                new SleepAction(0.5),
                                customActions.armSpecimenPicking,
                                new SleepAction(0.5),
                                customActions.armBasketSamplePicking,
                                new SleepAction(0.5),
                                Positions.LeftSample3pt2.runToExact,
                                customActions.stopDrive,
                                new SleepAction(0.5),
                                customActions.closeClaw,
                                new SleepAction(0.25),
                                Positions.LeftSample3.runToExact,
                                customActions.stopDrive,
                                new SleepAction(0.25),
                                customActions.armVertical,

                                //Drop sample 3
                                Positions.Basket.runToExact,
                                customActions.stopDrive,
                                new SleepAction(0.5),
                                customActions.prepareHighBasket,
                                new SleepAction(0.5),
                                customActions.dropSample,
                                new SleepAction(0.5),
                                customActions.afterBasketDrop,

                                //Reset for teleop
                                customActions.armRest

                        )

                )
        );

    }
}
