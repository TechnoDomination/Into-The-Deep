package org.firstinspires.ftc.teamcode.TuningOpModes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Util.PIDFController;
import org.firstinspires.ftc.teamcode.Util.PIDFParams;


@Config
@TeleOp(name = "Slides Tuning", group = "Tuning OpModes")
public class SlidesTuning extends LinearOpMode {

    public static double p =0.0,i=0.0,d=0.0;
    public static double f=0.0;

    public static int target = 0;

    private DcMotorEx SlideMotor1;
    private DcMotorEx SlideMotor2;

    public void runOpMode() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        double ticksPerRev = 384.5;

        SlideMotor1 = hardwareMap.get(DcMotorEx.class, "SlideMotor1");
        SlideMotor2 = hardwareMap.get(DcMotorEx.class, "SlideMotor2");
        SlideMotor2.setDirection(DcMotorEx.Direction.REVERSE);
        SlideMotor1.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        SlideMotor2.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        SlideMotor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        SlideMotor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        SlideMotor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        PIDFController controller = new PIDFController(new PIDFParams(0.0,0.0,0.0,0.0));

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {

            controller.setPIDF(new PIDFParams(p,i,d,f));
            int encoder = SlideMotor1.getCurrentPosition();

            double motorPower = controller.calculate(target-encoder);
            SlideMotor1.setPower(Range.clip(motorPower * .75,-0.75,0.75));
            SlideMotor2.setPower(Range.clip(motorPower * .75,-0.75,0.75));

            String telemetryData = "";
            telemetryData = telemetryData + "\n Current Position = " + SlideMotor1.getCurrentPosition();
            telemetryData = telemetryData + "\n State target = " + target;
            telemetryData = telemetryData + "\n Motor power = " + motorPower;
            telemetryData = telemetryData + "\n ";

            telemetry.addData("Slides Telemetry = ", telemetryData);
            telemetry.update();
        }


    }

}
