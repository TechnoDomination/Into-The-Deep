package org.firstinspires.ftc.teamcode.CatalystsReferenceCode.Autonomous;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.GoBuildaComputer.Localizer;
import org.firstinspires.ftc.teamcode.CatalystsReferenceCode.PID.PidParams;
import org.firstinspires.ftc.teamcode.CatalystsReferenceCode.PathPlanning.Positions;
import org.firstinspires.ftc.teamcode.Subsystems.Claw;
import org.firstinspires.ftc.teamcode.Subsystems.Drive;
@Config
@TeleOp
public class PidAuto extends LinearOpMode {

    public static double p = 0.08, i = 0.0, d = 0.01;
    public static double p2 = 0.08,i2 = 0.0, d2 = 0.01;
    public static double p3 = 1.1,i3 = 0.0,d3 = 0.0;

    @Override
    public void runOpMode() {
        telemetry = FtcDashboard.getInstance().getTelemetry();

        Localizer localizer = new Localizer(hardwareMap, new Localizer.Poses(0.0,0.0,0.0));
        Drive drive = new Drive(hardwareMap);
        Claw claw = new Claw(hardwareMap);

        waitForStart();

        Actions.runBlocking(
                new ParallelAction(
                        telemetryPacket -> {
                            localizer.update();
                            drive.xPid.setPID(new PidParams(p,i,d));
                            drive.yPid.setPID(new PidParams(p2,i2,d2));
                            drive.rPid.setPID(new PidParams(p3,i3,d3));
                            claw.update();
                            return true;
                        },
                        new SequentialAction(
                                Positions.Test.runToExact,
                                Positions.Test2.runToExact,
                                Positions.Test.runToExact,
                                Positions.Test2.runToExact,
                                Positions.Test.runToExact,
                                Action -> {
                                    drive.stopDrive();
                                    return false;
                                },
                                Action -> {
                                    claw.state = claw.state.IN;
                                    return false;
                                }
                        )
                )
        );

    }
}



