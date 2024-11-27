package org.firstinspires.ftc.teamcode.TestOpmodes;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.GoBuildaComputer.Localizer;
import org.firstinspires.ftc.teamcode.Subsystems.Arm;
import org.firstinspires.ftc.teamcode.Subsystems.Claw;
import org.firstinspires.ftc.teamcode.Subsystems.Drive;

@Config
@TeleOp(name="ArmTest", group="TestOpModes")
public class ArmTest extends LinearOpMode {

    public static double p = 0.0, i = 0.0, d = 0.0;

    @Override
    public void runOpMode() throws InterruptedException {
        Localizer armLocalizer = new Localizer(hardwareMap, new Localizer.Poses(0.0,0.0,0.0));
        Arm arm = new Arm(hardwareMap);

        waitForStart();
        while (opModeIsActive() && !isStopRequested()) {
            if (gamepad2.y) {
                telemetry.addData("Arm Position After Button Pressed = ", arm.getArmTelemetry());
                arm.ArmMotor.setPower(0.5);
                telemetry.addData("Arm Position After Arm Moved = ", arm.getArmTelemetry());
                telemetry.update();
            }

            arm.update();
        }

    }

}
