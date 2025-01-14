package org.firstinspires.ftc.teamcode.TestOpmodes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
@Disabled
@TeleOp(name="Small Claw Test", group="TestOpModes")
public class SmallClawTest extends LinearOpMode {
    public CRServo ClawServo1;
    @Override
    public void runOpMode() throws InterruptedException {

        ClawServo1 = hardwareMap.get(CRServo.class, "SmallClaw1");
        ClawServo1.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();
        while (opModeIsActive() && !isStopRequested()) {

            if(gamepad2.dpad_up) {
                ClawServo1.setPower(1);

            }
            if(gamepad2.dpad_down) {
                ClawServo1.setPower(-1);

            }
            if(gamepad2.dpad_right) {
                ClawServo1.setPower(0);

            }
        }

    }
}
