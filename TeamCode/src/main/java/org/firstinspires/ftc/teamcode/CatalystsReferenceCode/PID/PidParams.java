package org.firstinspires.ftc.teamcode.CatalystsReferenceCode.PID;

public class PidParams {
    public final double kp;
    public final double ki;
    public final double kd;
    public double kf = 0.0;

    public PidParams(double kp, double ki, double kd, double kf) {
        this.kp = kp;
        this.ki = ki;
        this.kd = kd;
        this.kf = kf;
    }

    public PidParams(double kp, double ki, double kd) {
        this.kp = kp;
        this.ki = ki;
        this.kd = kd;
    }

}

