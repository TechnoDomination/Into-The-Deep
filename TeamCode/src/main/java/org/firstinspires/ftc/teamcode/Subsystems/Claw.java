package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw {
    private final Servo ClawServo1;
    public State state = State.OUT;

    public enum State {
        IN, OUT, STOP
    }

    public Claw(HardwareMap hardwareMap) {
        ClawServo1 = hardwareMap.get(Servo.class, "ClawServo1");
    }

    public void update() {
        switch (state) {
            case IN:
                ClawServo1.setPosition(1);
                break;

            case OUT:
                ClawServo1.setPosition(0);
                break;

            case STOP:
                ClawServo1.setPosition(0);
                break;
        }
    }

    public double getPosition(Servo servo){
        return servo.getPosition();
    }

    public String getTelemetryForArm(){
        String telemetry = "";
        telemetry = telemetry + "\n ClawServo1 Position = " + ClawServo1.getPosition();
        telemetry = telemetry + "\n ";
        return telemetry;
    }

}
