package org.firstinspires.ftc.teamcode.OpModes.Auto;

import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
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


@Autonomous(name = "Auto Right Specimen", group = "TestOpModes")
public class AutoSpecimen extends LinearOpMode {

    public static double p = 0.18, i = 0.0, d = 0.0175;
    public static double p2 = 0.09,i2 = 0.0, d2 = 0.018;
    public static double p3 = 1.1,i3 = 0.0,d3 = 0.0;


    @Override
    public void runOpMode() {
        //telemetry = FtcDashboard.getInstance().getTelemetry();

        Localizer localizer = new Localizer(hardwareMap, new Poses(8.0,-63.0,0.0));
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
                                //Go to rung - drop specimen 1
                                customActions.prepareHighRungTest,
                                customActions.resestTimer,
                                new SleepAction(0.25),
                                Positions.HighRungTest.runToExact,
                                new SleepAction(.25),
                                Positions.GoFrontTinySpecimenTest.runToExact,

                                new ParallelAction(
                                        Positions.GoFrontTinySpecimenTest.runToExact,
                                        customActions.slidesHighRungDownTest
                                ),

                                new SleepAction(0.2),

                                Positions.GoBackSpecimen.runToExact,
                                new SleepAction(0.2),

                                new ParallelAction(
                                        Positions.GoBackSpecimen.runToExact,
                                        customActions.openClaw
                                ),
                                customActions.stopDrive,
                                //Pick up sample 1 off ground
                                customActions.armSubmersible,
                                customActions.resestTimer,
                                new SleepAction(0.5),

                                new ParallelAction(
                                        Positions.FirstColorSampleTest.runToExact,
                                        customActions.midClawRotater
                                ),

                                customActions.stopDrive,

                                customActions.armSamplePicking,
                                new SleepAction(0.25),
                                customActions.resestTimer,
                                new SleepAction(0.25),
                                customActions.closeClaw,
                                new SleepAction(0.2),
                                //Deliver to observation zone
                                new ParallelAction(
                                        customActions.armSubmersible,
                                        Positions.DropColorSampleTest.runToExact
                                ),

                                customActions.stopDrive,
                                customActions.openClaw,
                                new SleepAction(0.25),
                                Positions.SpecimenObZone.runToExact,
                                new SleepAction(0.25),

                                new ParallelAction(
                                        Positions.SecondColorSampleTest.runToExact,
                                        customActions.armSubmersible
                                ),

                                customActions.stopDrive,

                                customActions.armSamplePicking,
                                new SleepAction(0.25),
                                customActions.resestTimer,
                                new SleepAction(0.25),
                                customActions.closeClaw,
                                new SleepAction(0.2),
                                //Deliver to observation zone
                                new ParallelAction(
                                        customActions.armSubmersible,
                                        Positions.DropColorSampleTest.runToExact
                                ),

                                customActions.stopDrive,
                                customActions.openClaw,
                                new SleepAction(0.2),
                                //Picking up new specimen
                                Positions.SpecimenObZone.runToExact,

                                new ParallelAction(
                                        Positions.SpecimenObZone.runToExact,
                                        customActions.armSpecimenPicking,
                                        customActions.outClawRotater
                                ),

                                customActions.stopDrive,
                                customActions.resestTimer,
                                new SleepAction(0.25),
                                Positions.SpecimenObZoneTiny.runToExact,
                                customActions.stopDrive,
                                new SleepAction(0.25),
                                customActions.closeClaw,
                                new SleepAction(0.25),

                                //Going to rung again
                                new ParallelAction(
                                        customActions.prepareHighRungTest,
                                        Positions.HighRung2Test.runToExact
                                ),

                                customActions.stopDrive,
                                customActions.resestTimer,
                                new SleepAction(0.5),
                                // Positions.HighRung2Test.runToExact,
                                //  customActions.stopDrive,
                                //new SleepAction(.25),
                                Positions.GoFrontTiny2SpecimenTest.runToExact,

                                new ParallelAction(
                                        customActions.slidesHighRungDownTest,
                                        Positions.GoFrontTiny2SpecimenTest.runToExact
                                ),
                                customActions.stopDrive,
                                new SleepAction(0.7),

                                new ParallelAction(
                                        customActions.openClaw,
                                        Positions.SpecimenObZone.runToExact
                                ),

                                new SleepAction(0.2),
                                customActions.stopDrive,

                                //Get specimen 3
                               /* Positions.TurnSpecimen.runToExact,
                                new SleepAction(0.5),

                                new ParallelAction(
                                        Positions.SpecimenObZone.runToExact,
                                        customActions.armSpecimenPicking
                                ),

                                customActions.stopDrive,*/
                                customActions.resestTimer,
                                new SleepAction(0.25),
                                customActions.armSpecimenPicking,
                                new SleepAction(0.2),
                                Positions.SpecimenObZoneTiny.runToExact,
                                customActions.stopDrive,
                                new SleepAction(0.25),
                                customActions.closeClaw,
                                new SleepAction(0.25),

                                new ParallelAction(
                                        customActions.prepareHighRungTest,
                                        Positions.HighRung3Test.runToExact
                                ),

                                //Go to rung - drop specimen 3
                                customActions.stopDrive,
                                customActions.resestTimer,
                                new SleepAction(0.25),
                                // Positions.HighRung3Test.runToExact,
                                //   customActions.stopDrive,
                                //   new SleepAction(.25),
                                Positions.GoFrontTiny3SpecimenTest.runToExact,

                                new ParallelAction(
                                        Positions.GoFrontTiny3SpecimenTest.runToExact,
                                        customActions.slidesHighRungDownTest
                                ),

                                customActions.stopDrive,
                                new SleepAction(0.5),

                                new ParallelAction(
                                        customActions.openClaw,
                                        Positions.SpecimenObZone.runToExact
                                ),

                                new SleepAction(0.2),
                                customActions.stopDrive,

                                //Get specimen 3
                               /* Positions.TurnSpecimen.runToExact,
                                new SleepAction(0.5),

                                new ParallelAction(
                                        Positions.SpecimenObZone.runToExact,
                                        customActions.armSpecimenPicking
                                ),

                                customActions.stopDrive,*/
                                customActions.resestTimer,
                                new SleepAction(0.25),
                                customActions.armSpecimenPicking,
                                new SleepAction(0.2),
                                Positions.SpecimenObZoneTiny.runToExact,
                                customActions.stopDrive,
                                new SleepAction(0.25),
                                customActions.closeClaw,
                                new SleepAction(0.25),

                                new ParallelAction(
                                        customActions.prepareHighRungTest,
                                        Positions.HighRung4Test.runToExact
                                ),

                                //Go to rung - drop specimen 3
                                customActions.stopDrive,
                                customActions.resestTimer,
                                new SleepAction(0.25),
                                // Positions.HighRung3Test.runToExact,
                                //   customActions.stopDrive,
                                //   new SleepAction(.25),
                                Positions.GoFrontTiny4SpecimenTest.runToExact,

                                new ParallelAction(
                                        Positions.GoFrontTiny4SpecimenTest.runToExact,
                                        customActions.slidesHighRungDownTest
                                ),

                                customActions.stopDrive,
                                new SleepAction(0.5),
                                //Reseting for teleop
                                customActions.armRest,
                                Positions.ObserservationZoneParkingSpecimen.runToExact,
                                customActions.stopDrive



                        )

                )
        );

    }
}



