package org.firstinspires.ftc.teamcode.Autonomous;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.GoBuildaComputer.Localizer;
import org.firstinspires.ftc.teamcode.PathPlanning.P2P;
import org.firstinspires.ftc.teamcode.PathPlanning.Positions;
import org.firstinspires.ftc.teamcode.Subsystems.Claw;

import com.acmerobotics.roadrunner.Action;

public class SampleAuto extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        Localizer localizer = new Localizer(hardwareMap, new Localizer.Poses(0.0,0.0,0.0));

        //subsystems
        Claw claw = new Claw(hardwareMap);

        waitForStart();

        Actions.runBlocking(
                new ParallelAction(
                        telemetryPacket -> {
                            localizer.update();
                            claw.update();
                            return true;
                        },
                        new SequentialAction(
//                                new P2P(new Vector2d()),
                                telemetryPacket -> {
                                    claw.state = Claw.State.IN;
                                    return false;
                                }
                        )
                )
        );

    }
}



