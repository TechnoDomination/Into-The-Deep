package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.PID.PIDController;
import org.firstinspires.ftc.teamcode.PID.PidParams;

import java.util.Arrays;
import java.util.List;

public class Drive {
    public static Drive instance;

    public DcMotorEx FrontLeftDCMotor;
    public DcMotorEx FrontRightDCMotor;
    public DcMotorEx BackLeftDCMotor;
    public DcMotorEx BackRightDCMotor;

    public PIDController xPid = new PIDController(new PidParams(0.0,0.0,0.0));
    public PIDController yPid = new PIDController(new PidParams(0.0,0.0,0.0));
    public PIDController rPid = new PIDController(new PidParams(0.0,0.0,0.0));

    public Drive(HardwareMap hardwareMap){

        FrontLeftDCMotor = hardwareMap.get(DcMotorEx.class, "FrontLeftDCMotor");
        FrontRightDCMotor = hardwareMap.get(DcMotorEx.class, "FrontRightDCMotor");
        BackLeftDCMotor = hardwareMap.get(DcMotorEx.class, "BackLeftDCMotor");
        BackRightDCMotor = hardwareMap.get(DcMotorEx.class, "BackRightDCMotor");

        FrontLeftDCMotor.setDirection(DcMotor.Direction.REVERSE);
        FrontRightDCMotor.setDirection(DcMotor.Direction.FORWARD);
        BackLeftDCMotor.setDirection(DcMotor.Direction.REVERSE);
        BackRightDCMotor.setDirection(DcMotor.Direction.FORWARD);

        List<DcMotorEx> motors = Arrays.asList(FrontLeftDCMotor,FrontRightDCMotor,BackLeftDCMotor,BackRightDCMotor);
        for (DcMotorEx motor: motors) {
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motor.setPower(0);
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }

        instance = this;

    }


    public void update(double forward, double strafe, double rotate) {

//        double h = -Localizer.pose.getHeading();
//        double rotX = strafe * Math.cos(h) - forward * Math.sin(h);
//        double rotY = strafe * Math.sin(h) + forward * Math.cos(h);
//
//        double frontLeftPower = (rotY + rotX + rotate);
//        double frontRightPower = (rotY - rotX - rotate);
//        double backLeftPower = (rotY - rotX + rotate);
//        double backRightPower = (rotY + rotX - rotate);
        double frontLeftPower = (forward + strafe + rotate);
        double frontRightPower = (forward - strafe - rotate);
        double backLeftPower = (forward - strafe + rotate);
        double backRightPower = (forward + strafe - rotate);

        FrontLeftDCMotor.setPower(frontLeftPower);
        FrontRightDCMotor.setPower(frontRightPower);
        BackLeftDCMotor.setPower(backLeftPower);
        BackRightDCMotor.setPower(backRightPower);
    }


}