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
@TeleOp(name = "Arm Tuning", group = "Tuning OpModes")
public class ArmTuning extends LinearOpMode {

    public static double p =0.0,i=0.0,d=0.0;
    public static double f=0.0;

    public static double target = 0;
    public DcMotorEx ArmMotor;
    public static double angle = 0.0;

    @Override
    public void runOpMode() {
        telemetry = new MultipleTelemetry(telemetry,FtcDashboard.getInstance().getTelemetry());

        double ticksPerRev = 1425.1;
        ArmMotor = hardwareMap.get(DcMotorEx.class, "ArmMotor1");
        ArmMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        ArmMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        ArmMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        PIDFController controller = new PIDFController(new PIDFParams(3.345,7,0.095,0.0));

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {

            controller.setPIDF(new PIDFParams(p,i,d,f));
            int encoder = ArmMotor.getCurrentPosition();
            angle = encoder * 2 * Math.PI / ticksPerRev;

            double motorPower = controller.calculate(Math.toRadians(target) - angle, angle);
            //ArmMotor.setPower(Range.clip(motorPower * .75,-0.75,0.75));
            ArmMotor.setPower(motorPower);


            String telemetryData = "";
            telemetryData = telemetryData + "\n Current Position = " + ArmMotor.getCurrentPosition();
            telemetryData = telemetryData + "\n State target in radians = " + Math.toRadians(target);
            telemetryData = telemetryData + "\n Angle from encoder = " + angle;
            telemetryData = telemetryData + "\n Motor power = " + controller.calculate(Math.toRadians(target) - angle, angle);
            telemetryData = telemetryData + "\n ";

            telemetry.addData("Arm Telemetry = ", telemetryData);
            telemetry.update();
        }

    }

}
