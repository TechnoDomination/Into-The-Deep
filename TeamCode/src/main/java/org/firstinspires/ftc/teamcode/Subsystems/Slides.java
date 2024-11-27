package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.CatalystsReferenceCode.PID.PidParams;

public class Slides {
    public DcMotorEx SlideMotor1;
    public DcMotorEx SlideMotor2;
    public static double angle = 0.0;
    public Slides.State state = Slides.State.IDLE;

    PIDFController controller = new PIDFController(new PIDFParams(0.0,0.0,0.0,0.0));

    public enum State {
        SAMPLEDROP(Math.toRadians(90)),
        IDLE(0.0);
        public final double target;
        State(double Target) {
            this.target = Math.toRadians(Target);
        }
    }

    public Slides(HardwareMap hardwareMap){
        SlideMotor2.setDirection(DcMotor.Direction.REVERSE);
        SlideMotor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        SlideMotor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //SlideMotor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        SlideMotor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        SlideMotor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        SlideMotor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    double ticksPerRev = 384.5;

    public void update() {
        int encoder = SlideMotor1.getCurrentPosition();
        angle = encoder * 2 *Math.PI / ticksPerRev;

        double motorPower = controller.calculate(state.target - angle, angle);
        SlideMotor1.setPower(0.5);
    }

    public String getSlidesTelemetry(){

        String telemetry = "";
        telemetry = telemetry + "\n Current Position = " + SlideMotor1.getCurrentPosition();
        telemetry = telemetry + "\n ";
        return telemetry;
    }


}
