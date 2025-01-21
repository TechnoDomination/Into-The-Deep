package org.firstinspires.ftc.teamcode.TestOpmodes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.TouchSensor;


import org.firstinspires.ftc.teamcode.Subsystems.Arm;
import org.firstinspires.ftc.teamcode.Subsystems.Slides;
import org.firstinspires.ftc.teamcode.Util.PIDFParams;
@Disabled
@TeleOp(name="SlidesTest", group="TestOpModes")
public class SlidesTest extends LinearOpMode {

    TouchSensor limitSwitch;

    //todo - Arya - FTC dashboard tune PID

    @Override
    public void runOpMode() throws InterruptedException {

        Slides slides = new Slides(hardwareMap);
        Arm arm = new Arm(hardwareMap);
        limitSwitch = hardwareMap.get(TouchSensor.class, "LimitSwitch");

        waitForStart();
        while (opModeIsActive() && !isStopRequested()) {
           /* slides.update();
            arm.state = Arm.State.VERTICAL;
            arm.update();
            //slides.controller.setPIDF(new PIDFParams());

            if (gamepad1.y) {
                slides.state = Slides.State.HIGHBASKETSAMPLEDROP;
            } else if (gamepad1.a) {
                slides.state = Slides.State.FULLDOWN;
            } else if (gamepad1.x) {
                slides.state = Slides.State.SPECIMENALIGNDOWN;
            }

            telemetry.addData("Slides Telemetry = ", slides.getSlidesTelemetry());
           // telemetry.addData("Limit Switch Telemetry = ", slides.getLimitSwitchTelemetry());
            */
            // send the info back to driver station using telemetry function.
            if (limitSwitch.isPressed()) {
                telemetry.addData("Touch Sensor", "Is Pressed");
            } else {
                telemetry.addData("Touch Sensor", "Is Not Pressed");
            }

            telemetry.update();

        }

    }
}
