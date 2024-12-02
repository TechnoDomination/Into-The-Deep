package org.firstinspires.ftc.teamcode.TestOpmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.ClawRotater;

@TeleOp(name="ClawRotaterTest", group="TestOpModes")
public class ClawRotaterTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        //initalization phase
        ClawRotater clawRotater = new ClawRotater(hardwareMap);
        waitForStart();
        while (opModeIsActive() && !isStopRequested()) {
            if (gamepad2.left_bumper) {
                clawRotater.state = ClawRotater.State.IN;
            }
            if (gamepad2.right_bumper) {
                clawRotater.state = ClawRotater.State.OUT;
            }
            clawRotater.update();

            telemetry.addData("Claw Rotater Telemetry = ", clawRotater.getClawRotaterTelemetry());
            telemetry.update();
        }

    }
}
