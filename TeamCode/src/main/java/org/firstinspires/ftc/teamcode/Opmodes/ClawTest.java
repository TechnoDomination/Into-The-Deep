package org.firstinspires.ftc.teamcode.Opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
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
/*
    @Override
    public void init() {
        Claw claw = new Claw(hardwareMap);
    }

    //hello
    //this is prem hello
    @Override
    public void loop() {

        while (opModeInInit() && !isStopRequested()) {
            if (gamepad1.left_bumper) {
                claw.state = Claw.State.IN;
            }
            if (gamepad1.right_bumper) {
                claw.state = Claw.State.OUT;
            }
            claw.update();
        }
    } */
}
