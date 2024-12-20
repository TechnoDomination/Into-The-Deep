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


@Autonomous(name = "Auto Right Specimen", group = "Auto")
public class AutoSpecimen extends LinearOpMode {

    public static double p = 0.08, i = 0.0, d = 0.01;
    public static double p2 = 0.08,i2 = 0.0, d2 = 0.01;
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
                                //Go to rung
                                customActions.prepareHighRung,
                                customActions.resestTimer,
                                new SleepAction(0.5),
                                Positions.HighRung.runToExact,
                                customActions.stopDrive,
                                new SleepAction(0.5),
                                Positions.GoFrontTinySpecimen.runToExact,
                                customActions.stopDrive,
                                new SleepAction(0.5),

                                //Pull down specimen
                                customActions.slidesFullDown,
                                new SleepAction(0.5),
                                Positions.GoBackSpecimen.runToExact,
                                customActions.stopDrive,
                                new SleepAction(0.5),
                                customActions.openClaw,
                                new SleepAction(2),

                                //Picking up new specimen
                                Positions.TurnSpecimen.runToExact,
                                customActions.stopDrive,
                                new SleepAction(0.5),
                                //customActions.armSubmersible,
                                //customActions.resestTimer,
                                //new SleepAction(0.5),
                                customActions.armSpecimenPicking,
                                customActions.resestTimer,
                                new SleepAction(0.5),
                                Positions.SpecimenObZone.runToExact,
                                customActions.stopDrive,
                                new SleepAction(0.5),
                                Positions.SpecimenObZoneTiny.runToExact,
                                customActions.stopDrive,
                                new SleepAction(0.5),
                                customActions.closeClaw,
                                new SleepAction(0.5),

                                //Going to rung again
                                customActions.prepareHighRung,
                                customActions.resestTimer,
                                new SleepAction(0.5),
                                Positions.StartingPosition.runToExact,
                                customActions.stopDrive,
                                new SleepAction(0.25),
                                Positions.HighRung2.runToExact,
                                customActions.stopDrive,
                                new SleepAction(.25),
                                Positions.GoFrontTinySpecimen2.runToExact,
                                customActions.stopDrive,
                                new SleepAction(.25),
                                customActions.slidesFullDown,
                                new SleepAction(0.5),

                                //Reseting for teleop
                                Positions.ObserservationZoneParkingSpecimen.runToExact,
                                customActions.stopDrive,
                                new SleepAction(1),
                                customActions.armRest

                        )

                )
        );

    }
}



