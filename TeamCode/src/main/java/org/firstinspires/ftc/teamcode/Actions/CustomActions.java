package org.firstinspires.ftc.teamcode.Actions;

import com.qualcomm.robotcore.hardware.HardwareMap;
import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;

import org.firstinspires.ftc.teamcode.Subsystems.Claw;

public class CustomActions {
    public Claw claw;

    public CustomActions(HardwareMap hardwareMap){
        claw = new Claw(hardwareMap);
    }

    public Action closeClaw = new Action() {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if(claw.state == Claw.State.OUT){
                claw.state = Claw.State.IN;
            }
            else {
                claw.state = Claw.State.IN;
            }
            return false;
        }
    };

    public Action openClaw = new Action() {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if(claw.state == Claw.State.IN){
                claw.state = Claw.State.OUT;
            }
            else {
                claw.state = Claw.State.OUT;
            }

            if (claw.isTargetReached) {
                return false;
            } else {
                return true;
            }
        }
    };
}
