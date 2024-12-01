package org.firstinspires.ftc.teamcode.Actions;

import com.qualcomm.robotcore.hardware.HardwareMap;
import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;

import org.firstinspires.ftc.teamcode.Subsystems.Arm;
import org.firstinspires.ftc.teamcode.Subsystems.Claw;
import org.firstinspires.ftc.teamcode.Subsystems.Drive;
import org.firstinspires.ftc.teamcode.Subsystems.Slides;

public class CustomActions {
    public Claw claw;
    public Arm arm;
    public Slides slides;

    public CustomActions(HardwareMap hardwareMap){
         claw = Claw.instance;
         arm = Arm.instance;
         slides = Slides.instance;
    }

    public Action closeClaw = new Action() {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {

            claw.state = Claw.State.IN;

            if (claw.isTargetReached) {
                return false;
            } else {
                return true;
            }
        }
    };

    public Action openClaw = new Action() {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {

            claw.state = Claw.State.OUT;

            if (claw.isTargetReached) {
                return false;
            } else {
                return true;
            }
        }
    };

    public Action armVertical = new Action() {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {

            arm.state = Arm.State.VERTICAL;

            if (arm.isTargetReached) {
                return false;
            } else {
                return true;
            }
        }
    };

    public Action armSamplePicking = new Action() {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {

            arm.state = Arm.State.SAMPLEPICKING;

            if (arm.isTargetReached) {
                return false;
            } else {
                return true;
            }
        }
    };

    public Action slidesHighBasket = new Action() {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {

            slides.state = Slides.State.HIGHBASKETSAMPLEDROP;

            if (slides.isTargetReached) {
                return false;
            } else {
                return true;
            }
        }
    };

    public Action slidesFullDown = new Action() {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {

            slides.state = Slides.State.FULLDOWN;

            if (slides.isTargetReached) {
                return false;
            } else {
                return true;
            }
        }
    };

    public Action prepareHighBasket = new Action() {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {

            arm.state = Arm.State.VERTICAL;
            slides.state = Slides.State.HIGHBASKETSAMPLEDROP;

            if (slides.isTargetReached) {
                return false;
            } else {
                return true;
            }
        }
    };

    public Action dropSample = new Action() {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {

            if (slides.isTargetReached){
                arm.state = Arm.State.SAMPLEDEPOSIT;
                claw.state = Claw.State.OUT;
            }

            if (claw.isTargetReached) {
                arm.state = Arm.State.VERTICAL;
                return false;
            } else {
                return true;
            }
        }
    };



}
