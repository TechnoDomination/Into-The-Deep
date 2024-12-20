package org.firstinspires.ftc.teamcode.TestOpmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


import org.firstinspires.ftc.teamcode.Subsystems.Slides;
import org.firstinspires.ftc.teamcode.Util.PIDFParams;

@TeleOp(name="SlidesTest", group="TestOpModes")
public class SlidesTest extends LinearOpMode {

    //todo - Arya - FTC dashboard tune PID

    @Override
    public void runOpMode() throws InterruptedException {

        Slides slides = new Slides(hardwareMap);

        waitForStart();
        while (opModeIsActive() && !isStopRequested()) {
            slides.update();
            //slides.controller.setPIDF(new PIDFParams());

            if (gamepad1.y) {
                slides.state = Slides.State.HIGHBASKETSAMPLEDROP;
            } else if (gamepad1.a) {
                slides.state = Slides.State.FULLDOWN;
            } else if (gamepad1.x) {
                slides.state = Slides.State.SPECIMENALIGNDOWN;
            }

            telemetry.addData("Slides Telemetry = ", slides.getSlidesTelemetry());
            telemetry.addData("Limit Switch Telemetry = ", slides.getLimitSwitchTelemetry());
            telemetry.update();

        }

    }
}
