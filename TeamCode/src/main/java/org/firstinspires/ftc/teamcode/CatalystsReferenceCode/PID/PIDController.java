package org.firstinspires.ftc.teamcode.CatalystsReferenceCode.PID;

import com.qualcomm.robotcore.util.ElapsedTime;

public class PIDController {
    private PidParams params;
    private ElapsedTime timer = new ElapsedTime();

    private double prevError = 0.0;
    private double integral = 0.0;
    private double pastTime = 0.0;

    public PIDController(PidParams params) {
        this.params = params;
    }

    // P - Proportional D- Derivative I - Integral F - FeedForward
    public void setPID(PidParams pidParams) {
        params = pidParams;
    }


    /**
     * @param error Error in encoder Count
     * @param armAngle Error in Radians
     * @return Motor Power
     */
    public double calculate(double error, double armAngle) {
        double dt = timer.seconds() - pastTime;
        integral += (error * dt);

        double derivative = (error - prevError) / dt;
        prevError = error;

        double ff;
        if (armAngle < Math.PI) {
            ff = Math.max(0.0, Math.sin(armAngle)) * params.kf;
        } else {
            ff = Math.min(0.0, -Math.sin(Math.PI - (armAngle - Math.PI))) * params.kf;
        }

        double controlEffort = Math.max(-1.0, Math.min(1.0, (derivative * params.kd + integral * params.ki + error * params.kp) + ff));

        pastTime = timer.seconds();

        return controlEffort;
    }

    public double calculate(double error) {
        double dt = timer.seconds() - pastTime;
        integral += (error * dt);

        double derivative = (error - prevError) / dt;
        prevError = error;

        double controlEffort = Math.max(-1.0, Math.min(1.0, (derivative * params.kd + integral * params.ki + error * params.kp)));

        pastTime = timer.seconds();

        return controlEffort;
    }


}

