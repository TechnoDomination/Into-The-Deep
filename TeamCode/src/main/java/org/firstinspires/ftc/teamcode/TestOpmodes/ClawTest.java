package org.firstinspires.ftc.teamcode.TestOpmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.Subsystems.Claw;

@TeleOp(name="ClawTest", group="TestOpModes")
public class ClawTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        //initalization phase
        Claw claw = new Claw(hardwareMap);
        waitForStart();
        while (opModeIsActive() && !isStopRequested()) {
            if (gamepad2.left_bumper) {
                claw.state = Claw.State.IN;
            }
            if (gamepad2.right_bumper) {
                claw.state = Claw.State.OUT;
            }
            claw.update();
        }

    }
}
