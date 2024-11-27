package org.firstinspires.ftc.teamcode.TestOpmodes;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.GoBuildaComputer.Localizer;
import org.firstinspires.ftc.teamcode.Subsystems.Arm;

@TeleOp(name="ArmTest", group="TestOpModes")
public class ArmTest extends LinearOpMode {


    //
    @Override
    public void runOpMode() throws InterruptedException {
        //initalization phase
        Arm arm = new Arm(hardwareMap);
        waitForStart();
        while (opModeIsActive() && !isStopRequested()) {
            if (gamepad2.y) {
                telemetry.addData("Arm Position After Button Pressed = ", arm.getArmTelemetry());
                telemetry.update();
                //arm.state = Arm.State.VERTICAL;
                telemetry.addData("Arm Position After Arm Moved = ", arm.getArmTelemetry());
                telemetry.update();
            }

            arm.update();
        }

    }


}
