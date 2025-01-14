package org.firstinspires.ftc.teamcode.OpModes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.Arm;

@TeleOp(name="SpecialTeleOp", group="TeleOp")
public class ArmBackTeleOp extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        Arm arm = new Arm(hardwareMap);
        waitForStart();
        while (opModeIsActive()) {
            arm.update();
            //if (gamepad1.left_bumper) {
                arm.state = Arm.State.BACKSPECIALTELEOP;
            //}
            telemetry.addData("Arm Telemetry = ", arm.getArmTelemetry());
            telemetry.update();
        }
    }
}
