package org.firstinspires.ftc.teamcode.Util;

import static java.lang.Math.PI;

import com.acmerobotics.roadrunner.Vector2d;

import org.firstinspires.ftc.teamcode.Actions.P2P;


public enum Positions {
    //Samples on ground
    YellowLeftbrick3(new Vector2d(-49, -33.0), 0.0),

    //Sample related movement
    Basket(new Vector2d(-47, -48),-PI*0.67),
    GoFrontSample(new Vector2d(-35, -53), 0.0),

    //Specmien related movement
    HighRung(new Vector2d(8, -34), 0.0),
    GoFrontSpecimen(new Vector2d(9, -34), 0.0),
    GoBackSpecimen(new Vector2d(8, -60), 0.0),
    SpecimenOpZone(new Vector2d(27, 58), PI * 0.5),

    Test(new Vector2d(0.0,15.0),0.0),
    Test2(new Vector2d(0.0,0.0),0.0);

    Positions(Vector2d vector, Double rotation) {
        runToExact = new P2P(vector, rotation);
    }

    public final P2P runToExact;
}
