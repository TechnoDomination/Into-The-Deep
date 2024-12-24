package org.firstinspires.ftc.teamcode.TestOpmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.Subsystems.Slides;

@TeleOp(name="MizumiSlidesTest", group="TestOpModes")
public class MizumiTest extends LinearOpMode {
    public DcMotorEx SlideMotor1;
    @Override
    public void runOpMode() throws InterruptedException {

        SlideMotor1 = hardwareMap.get(DcMotorEx.class, "MizumiMotor");
        SlideMotor1.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();
        while (opModeIsActive() && !isStopRequested()) {

            if(gamepad2.dpad_up) {
                SlideMotor1.setPower(1);

            }
            if(gamepad2.dpad_down) {
                SlideMotor1.setPower(-1);

            }
            if(gamepad2.dpad_right) {
                SlideMotor1.setPower(0);

            }
        }

    }
}
