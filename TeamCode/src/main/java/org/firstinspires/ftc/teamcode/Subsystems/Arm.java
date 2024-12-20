package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Util.PIDFController;
import org.firstinspires.ftc.teamcode.Util.PIDFParams;

public class Arm {
    public DcMotorEx ArmMotor;

    public static double angle = 0.0;
    //public PIDFController controller = new PIDFController(new PIDFParams(0.855,0.0005,0.0175,0.0));
    public PIDFController controller = new PIDFController(new PIDFParams(0,0,0,0));
    public State state = State.IDLE;

    public boolean isTargetReached = false;
    public static Arm instance;


    public enum State {
        SAMPLEPICKING(180),
        SPECIMENPICKING(170),
        SPECIMENPICKING2(175),
        SUBMERSIBLE(150),
        SAMPLEDEPOSIT(105),
        VERTICAL(90),
        SAMPLEPREPARATION(60),
        REST(0),
        IDLE(0.0);
        public final double target;
        State(double Target) {
            this.target = Math.toRadians(Target);
        }
    }

    public Arm(HardwareMap hardwareMap) {
        ArmMotor = hardwareMap.get(DcMotorEx.class, "ArmMotor1");
        ArmMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        ArmMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        ArmMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        controller.setPIDF(new PIDFParams(3.345,7,0.095,0.0));

        instance = this;

    }

    double ticksPerRev = 1425.1;

    public void update() {
        int encoder = ArmMotor.getCurrentPosition();
        angle = encoder * 2 * Math.PI / ticksPerRev;

        double motorPower = controller.calculate(state.target - angle, angle);
        ArmMotor.setPower(Range.clip(motorPower * .75,-0.75,0.75));

        if (Math.abs(state.target - angle) < Math.toRadians(5)){
            isTargetReached = true;
        } else {
            isTargetReached = false;
        }

    }

    public String getArmTelemetry(){

        String telemetry = "";
        telemetry = telemetry + "\n Current Position = " + ArmMotor.getCurrentPosition();
        telemetry = telemetry + "\n State current = " + state;
        telemetry = telemetry + "\n State target in radians = " + state.target;
        telemetry = telemetry + "\n Angle from encoder = " + angle;
        telemetry = telemetry + "\n Radians of 2 = " + Math.toRadians(5.0);
        telemetry = telemetry + "\n Motor power = " + controller.calculate(state.target - angle, angle);
        telemetry = telemetry + "\n Is Target Reached? --> " + isTargetReached;
        telemetry = telemetry + "\n Is Target calculation " + (Math.abs(state.target - angle) < Math.toRadians(5));
        telemetry = telemetry + "\n ";
        return telemetry;
    }
}