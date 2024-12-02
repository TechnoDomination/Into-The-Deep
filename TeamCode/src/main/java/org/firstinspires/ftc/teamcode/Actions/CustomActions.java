package org.firstinspires.ftc.teamcode.Actions;

import com.qualcomm.robotcore.hardware.HardwareMap;
import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;

import org.firstinspires.ftc.teamcode.Subsystems.Arm;
import org.firstinspires.ftc.teamcode.Subsystems.Claw;
import org.firstinspires.ftc.teamcode.Subsystems.ClawRotater;
import org.firstinspires.ftc.teamcode.Subsystems.Drive;
import org.firstinspires.ftc.teamcode.Subsystems.Slides;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomActions {
    public Claw claw;
    public ClawRotater clawRotater;
    public Arm arm;
    public Slides slides;
    public Drive drive = Drive.instance;

    public CustomActions(HardwareMap hardwareMap){
         claw = new Claw(hardwareMap);
         clawRotater = new ClawRotater(hardwareMap);
         arm = new Arm(hardwareMap);
         slides = new Slides(hardwareMap);
    }

    public void update(){
        claw.update();
        clawRotater.update();
        arm.update();
        slides.update();
    }

    public List<String> getTelemetry(){
        return Arrays.asList("Claw = "+claw.getClawTelemetry(),"Claw Rotater = "+clawRotater.getClawRotaterTelemetry(),"Arm = "+ arm.getArmTelemetry(), "Slides = "+ slides.getSlidesTelemetry());
    }

    public Action stopDrive = new Action() {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {

            drive.stopDrive();

            return false;
        }
    };

    public Action closeClaw = new Action() {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {

            claw.state = Claw.State.IN;

            return !claw.isTargetReached;
        }
    };

    public Action openClaw = new Action() {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {

            claw.state = Claw.State.OUT;

            return !claw.isTargetReached;
        }
    };

    public Action armVertical = new Action() {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {

            arm.state = Arm.State.VERTICAL;

            return !arm.isTargetReached;
        }
    };

    public Action armSamplePicking = new Action() {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {

            arm.state = Arm.State.SAMPLEPICKING;

            return !arm.isTargetReached;
        }
    };

    public Action slidesHighBasket = new Action() {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {

            slides.state = Slides.State.HIGHBASKETSAMPLEDROP;

            return !slides.isTargetReached;
        }
    };

    public Action slidesFullDown = new Action() {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {

            slides.state = Slides.State.FULLDOWN;

            return !slides.isTargetReached;
        }
    };

    public Action prepareHighBasket = new Action() {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {

            arm.state = Arm.State.SAMPLEPREPARATION;
            slides.state = Slides.State.HIGHBASKETSAMPLEDROP;

            return !slides.isTargetReached;
        }
    };

    public Action dropSample = new Action() {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {

            boolean stepDone = false;

            if (slides.isTargetReached){
                arm.state = Arm.State.SAMPLEDEPOSIT;
                if (arm.isTargetReached){
                    claw.state = Claw.State.OUT;
                    if (claw.isTargetReached) {
                        arm.state = Arm.State.SAMPLEPREPARATION;
                        stepDone = true;
                    }
                }
            }

            return !stepDone;
        }
    };

    //todo make custom action that stops drive
    public Action prepareHighRung = new Action() {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {

            arm.state = Arm.State.VERTICAL;
            slides.state = Slides.State.SPECIMENALIGNUP;

            if (slides.isTargetReached) {
                return false;
            } else {
                return true;
            }
        }
    };

    public Action hangSpecimen = new Action() {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {

            boolean stepDone = false;

            if (slides.isTargetReached){
                claw.state = Claw.State.OUT;
                if (claw.isTargetReached) {
                    arm.state = Arm.State.REST;
                    stepDone = true;
                }
            }

            if (stepDone) {
                return false;
            } else {
                return true;
            }
        }
    };



}
