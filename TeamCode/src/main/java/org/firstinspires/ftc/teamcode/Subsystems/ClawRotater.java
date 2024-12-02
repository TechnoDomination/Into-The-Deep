package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ClawRotater {
    private final Servo ClawRotaterServo1;
    public State state = State.IN;
    public boolean isTargetReached = false;

    public enum State {
        IN, OUT, STOP
    }

    public ClawRotater(HardwareMap hardwareMap) {
        ClawRotaterServo1 = hardwareMap.get(Servo.class, "ClawRotaterServo1");


    }

    public void update() {
        switch (state) {
            case IN:
                ClawRotaterServo1.setPosition(1);
                break;

            case OUT:
                ClawRotaterServo1.setPosition(0);
                break;

            case STOP:
                ClawRotaterServo1.setPosition(0);
                break;
        }

        if (state == State.IN && ClawRotaterServo1.getPosition() == 1){
            isTargetReached = true;
        } else if (state == State.OUT && ClawRotaterServo1.getPosition() == 0) {
            isTargetReached = true;
        } else {
            isTargetReached = false;
        }

    }


    public String getClawRotaterTelemetry(){
        String telemetry = "";
        telemetry = telemetry + "\n ClawRotaterServo1 Position = " + ClawRotaterServo1.getPosition();
        telemetry = telemetry + "\n ";
        return telemetry;
    }

}