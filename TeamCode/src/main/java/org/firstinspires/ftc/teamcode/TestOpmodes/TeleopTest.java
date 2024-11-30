package org.firstinspires.ftc.teamcode.TestOpmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.GoBildaPinPointOdo.Localizer;
import org.firstinspires.ftc.teamcode.Subsystems.Claw;
import org.firstinspires.ftc.teamcode.Subsystems.Drive;
import org.firstinspires.ftc.teamcode.Subsystems.Slides;
import org.firstinspires.ftc.teamcode.Subsystems.Arm;

@TeleOp(name = "TeleopTest",group = "TestOpModes")
public class TeleopTest extends LinearOpMode {

    @Override
    public void runOpMode() {

        Localizer localizer = new Localizer(hardwareMap, new Localizer.Poses(0.0,0.0,0.0));
        Drive drive = new Drive(hardwareMap);
        Claw claw = new Claw(hardwareMap);
        Slides slides = new Slides(hardwareMap);
        Arm arm = new Arm(hardwareMap);

        waitForStart();
        while(opModeIsActive() && !isStopRequested()){
            localizer.update();
            slides.update();
            //claw.update();
            arm.update();

            telemetry.addData("X pos", Localizer.pose.getX());
            telemetry.addData("Y pos", Localizer.pose.getY());
            telemetry.addData("Heading pos", Localizer.pose.getHeading());

            //Drive Controls
            //drive.update(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);

            //Claw Controls
            /*if (gamepad2.left_bumper) {
                claw.state = Claw.State.IN;
            }
            if (gamepad2.right_bumper) {
                claw.state = Claw.State.OUT;
            }
            telemetry.addData("Claw Telemetry = ", claw.getClawTelemetry());
*/
            //Slides Controls
            if (gamepad1.y) {
                slides.state = Slides.State.HIGHBASKETSAMPLEDROP;
            } else if (gamepad1.a) {
                slides.state = Slides.State.FULLDOWN;
            } else if (gamepad1.x) {
                slides.state = Slides.State.SPECIMENALIGN;
            } else if (gamepad1.b) {
                slides.state = Slides.State.SPECIMENPULL;
            }
            telemetry.addData("Slides Telemetry = ", slides.getSlidesTelemetry());

            //Arm Controls
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