package org.firstinspires.ftc.teamcode.Autonomous;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.GoBuildaComputer.Localizer;
import org.firstinspires.ftc.teamcode.PID.PidParams;
import org.firstinspires.ftc.teamcode.PathPlanning.Positions;
import org.firstinspires.ftc.teamcode.Subsystems.Claw;
import org.firstinspires.ftc.teamcode.Subsystems.Drive;
@Config
public class PidAuto extends LinearOpMode {

    public static double p,i,d =0.0;
    public static double p2,i2,d2 =0.0;

    @Override
    public void runOpMode() {

        Localizer localizer = new Localizer(hardwareMap, new Localizer.Poses(0.0,0.0,0.0));
        Drive drive = new Drive(hardwareMap);

        waitForStart();

        Actions.runBlocking(
                new ParallelAction(
                        telemetryPacket -> {
                            localizer.update();
                            drive.xPid.setPID(new PidParams(p,i,d));
                            drive.yPid.setPID(new PidParams(p2,i2,d2));
                            return true;
                        },
                        new SequentialAction(
                                Positions.Test.runToExact,
                                Positions.Test2.runToExact,
                                Positions.Test.runToExact,
                                Positions.Test2.runToExact,
                                Positions.Test.runToExact
                        )
                )
        );

    }
}



