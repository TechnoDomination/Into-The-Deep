package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw {
    private final Servo ClawServo1;
    public State state = State.IN;
    public boolean isTargetReached = false;

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

        if (state == State.IN && ClawServo1.getPosition() == 1){
            isTargetReached = true;
        } else if (state == State.OUT && ClawServo1.getPosition() == 0) {
            isTargetReached = true;
        } else {
            isTargetReached = false;
        }

    }


    public String getClawTelemetry(){
        String telemetry = "";
        telemetry = telemetry + "\n ClawServo1 Position = " + ClawServo1.getPosition();
        telemetry = telemetry + "\n ";
        return telemetry;
    }

}
