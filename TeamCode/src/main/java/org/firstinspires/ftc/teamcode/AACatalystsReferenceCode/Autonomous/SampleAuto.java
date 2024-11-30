package org.firstinspires.ftc.teamcode.AACatalystsReferenceCode.Autonomous;

import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.GoBildaPinPointOdo.Localizer;
import org.firstinspires.ftc.teamcode.Subsystems.Claw;

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



