package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Util.PIDFController;
import org.firstinspires.ftc.teamcode.Util.PIDFParams;


public class Slides {
    public DcMotorEx SlideMotor1;
    public DcMotorEx SlideMotor2;
    public Slides.State state = Slides.State.IDLE;
    public TouchSensor limitSwitch;

    public PIDFController controller = new PIDFController(new PIDFParams(0.0075,0.0,0.0,0.0));

    public boolean isTargetReached = false;
    public static Slides instance;

    public enum State {
        HIGHBASKETSAMPLEDROP(2200),
        LOWBASKETSAMPLEDROP(1000),
        SPECIMENALIGNDOWN(710),
        SPECIMENPULL(800),
        SPECIMENALIGNUP(600),
        FULLDOWN(0),
        IDLE(0);
        public final int target;
        State(int Target) {
            this.target = Target;
        }
    }

    public Slides(HardwareMap hardwareMap){

        SlideMotor1 = hardwareMap.get(DcMotorEx.class, "SlideMotor1");
        SlideMotor2 = hardwareMap.get(DcMotorEx.class, "SlideMotor2");
        SlideMotor2.setDirection(DcMotor.Direction.REVERSE);
        SlideMotor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        SlideMotor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //SlideMotor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        SlideMotor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        SlideMotor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        SlideMotor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        instance = this;

    }
    double ticksPerRev = 384.5;

    public void update() {
        int encoder = SlideMotor1.getCurrentPosition();

        double motorPower = controller.calculate(state.target - encoder);
        SlideMotor1.setPower(Range.clip(motorPower * .75,-0.75,0.75));
        SlideMotor2.setPower(Range.clip(motorPower * .75,-0.75,0.75));


        if (state == State.HIGHBASKETSAMPLEDROP && (Math.abs(2200-encoder) < 50)) {
            isTargetReached = true;
        } else if (state == State.SPECIMENALIGNUP && (Math.abs(600-encoder) < 50)) {
            isTargetReached = true;
        }  else if (state == State.FULLDOWN && (Math.abs(0-encoder) < 50)) {
            isTargetReached = true;
        } else {
            isTargetReached = false;
        }

    }

    public String getSlidesTelemetry(){

        String telemetry = "";
        telemetry = telemetry + "\n Current Position = " + SlideMotor1.getCurrentPosition();
        telemetry = telemetry + "\n " + state.target;
        telemetry = telemetry + "\n ";

        return telemetry;
    }


}
