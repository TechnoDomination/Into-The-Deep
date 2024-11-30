package org.firstinspires.ftc.teamcode.TestOpmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.Arm;


@TeleOp(name="ArmTest", group="TestOpModes")
public class ArmTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        Arm arm = new Arm(hardwareMap);

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {

            arm.update();

            if (gamepad2.y) {
                arm.state = Arm.State.VERTICAL;
            } else if (gamepad2.a) {
                arm.state = Arm.State.SAMPLEPICKING;
            } else if (gamepad2.x) {
                arm.state = Arm.State.SPECIMENPICKING;
            } else if (gamepad2.b) {
                arm.state = Arm.State.SUBMERSIBLE;
            } else if (gamepad2.b && gamepad2.y) {
                arm.state = Arm.State.SAMPLEDEPOSIT;
            }

            telemetry.addData("Arm Telemetry = ", arm.getArmTelemetry());
            telemetry.update();

        }

    }

}
