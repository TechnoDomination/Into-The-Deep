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

    public static int highBasketTarget = 2150;
    public static int lowBasketTarget = 1000;
    public static int specimenAlignDownTarget = 710;
    public static int specimenAlignUpTarget = 600;
    public static int specimenPullTarget = 800;
    public static int fullDownTarget = 0;

    public enum State {
        HIGHBASKETSAMPLEDROP(highBasketTarget),
        LOWBASKETSAMPLEDROP(lowBasketTarget),
        SPECIMENALIGNDOWN(specimenAlignDownTarget),
        SPECIMENPULL(specimenPullTarget),
        SPECIMENALIGNUP(specimenAlignUpTarget),
        FULLDOWN(fullDownTarget),
        IDLE(0);
        public final int target;
        State(int Target) {
            this.target = Target;
        }
    }

    public Slides(HardwareMap hardwareMap){

        limitSwitch = hardwareMap.get(TouchSensor.class,"LimitSwitch");

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
        //SlideMotor1.setPower(Range.clip(motorPower * .75,-0.75,0.75));
        //SlideMotor2.setPower(Range.clip(motorPower * .75,-0.75,0.75));

        SlideMotor1.setPower(motorPower);
        SlideMotor2.setPower(motorPower);


        if (state == State.HIGHBASKETSAMPLEDROP && (Math.abs(highBasketTarget-encoder) < 25)) {
            isTargetReached = true;
        } else if (state == State.SPECIMENALIGNUP && (Math.abs(specimenAlignUpTarget-encoder) < 50)) {
            isTargetReached = true;
        } else if (state == State.SPECIMENALIGNDOWN && (Math.abs(specimenAlignDownTarget-encoder) < 50)) {
            isTargetReached = true;
        } else if (state == State.SPECIMENPULL && (Math.abs(specimenPullTarget-encoder) < 50)) {
            isTargetReached = true;
        } else if (state == State.LOWBASKETSAMPLEDROP && (Math.abs(lowBasketTarget-encoder) < 50)) {
            isTargetReached = true;
        } else if (state == State.FULLDOWN && (Math.abs(fullDownTarget-encoder) < 50)) {
            isTargetReached = true;
        } else {
            isTargetReached = false;
        }

        if (limitSwitch.isPressed()){
            SlideMotor1.setPower(0);
            SlideMotor2.setPower(0);
        }

    }

    public String getSlidesTelemetry(){

        String telemetry = "";
        telemetry = telemetry + "\n Current Position = " + SlideMotor1.getCurrentPosition();
        telemetry = telemetry + "\n State current = " + state;
        telemetry = telemetry + "\n State Target = " + state.target;
        telemetry = telemetry + "\n Is Target Reached? --> " + isTargetReached;

        telemetry = telemetry + "\n ";

        return telemetry;
    }

    public String getLimitSwitchTelemetry(){

        String telemetry = "";
        telemetry = telemetry + "\n Limit Switch Value = " + limitSwitch.getValue();
        telemetry = telemetry + "\n Limit Switch isPressed = " + limitSwitch.isPressed();
        telemetry = telemetry + "\n ";

        return telemetry;
    }

    public int getPosition() {
        return SlideMotor1.getCurrentPosition();
    }


}
