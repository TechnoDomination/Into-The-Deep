package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.CatalystsReferenceCode.PID.PidParams;

public class Arm {
    public DcMotorEx ArmMotor;

    public static double angle = 0.0;
    PIDFController controller = new PIDFController(new PIDFParams(0.0,0.0,0.0,0.0));
    State state = State.IDLE;

    public enum State {
        VERTICAL(Math.toRadians(90)),
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
    }

    double ticksPerRev = 1425.1;

    public void update() {
        int encoder = ArmMotor.getCurrentPosition();
        angle = encoder * 2 *Math.PI / ticksPerRev;

        double motorPower = controller.calculate(state.target - angle,angle);
        ArmMotor.setPower(motorPower);
    }

    /*public void moveArm(int target) {
        PIDFController armController = new PIDFController(new PIDFParams(0.018, 0, 0.0009, 0.77));
        angle = Math.toRadians(1425.1) + encoder * (2 * Math.PI / 1425.1);
        double power = armController.calculate(state.target -angle , angle);
        ArmMotor.setPower(Range.clip(power * .75,-0.5,0.5));


    }*/

    public String getArmTelemetry(){

        String telemetry = "";
        telemetry = telemetry + "\n pos - " + ArmMotor.getCurrentPosition();
        telemetry = telemetry + "\n ";
        return telemetry;
    }


}
