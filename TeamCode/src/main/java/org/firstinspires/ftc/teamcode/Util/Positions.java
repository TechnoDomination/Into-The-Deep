package org.firstinspires.ftc.teamcode.Util;

import static java.lang.Math.PI;

import com.acmerobotics.roadrunner.Vector2d;

import org.firstinspires.ftc.teamcode.Actions.P2P;


public enum Positions {
    //Samples on ground
    YellowLeftbrick3(new Vector2d(-49, -33.0), 0.0),

    // point scoring spots
    Basket(new Vector2d(-47, -48),-PI*0.67),
    GoFront(new Vector2d(-35, -53), 0.0),

    HighRung(new Vector2d(9.0, -34), 0.0),

    Test(new Vector2d(0.0,15.0),0.0),
    Test2(new Vector2d(0.0,0.0),0.0);

    Positions(Vector2d vector, Double rotation) {
        runToExact = new P2P(vector, rotation);
    }

    public final P2P runToExact;
}
