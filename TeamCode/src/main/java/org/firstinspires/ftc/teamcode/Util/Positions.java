package org.firstinspires.ftc.teamcode.Util;

import static java.lang.Math.PI;

import com.acmerobotics.roadrunner.Vector2d;

import org.firstinspires.ftc.teamcode.Actions.P2P;


public enum Positions {
    //Samples on ground
    LeftSample1(new Vector2d(-42, -42), 0.0),
    LeftSample2(new Vector2d(-52, -42), 0.0),
    //LeftSample3(new Vector2d(-46, 0.0), -PI/2), //original position
    LeftSample3pt2(new Vector2d(-50, -34), -PI*0.25), //a little forward
    LeftSample3(new Vector2d(-44, -44), -PI * 0.25),
    TestNeg(new Vector2d(-40, -40), 0.0),

    //Basket related movement
    Basket(new Vector2d(-43, -48),-PI*0.72),
    Basket2(new Vector2d(-42.5, -47.5),-PI*0.72),
    GoFrontSample(new Vector2d(-35, -53), 0.0),

    //Specmien related movement
    HighRung(new Vector2d(8, -38), 0.0),
    HighRung2(new Vector2d(0, -40), 0.0), //For second specimen place
    GoFrontTinySpecimen(new Vector2d(8, -31), 0.0),
    GoFrontTinySpecimen2(new Vector2d(0, -31), 0.0),
    GoBackSpecimen(new Vector2d(8, -50), 0.0),
    TurnSpecimen(new Vector2d(8, -52), PI * 0.5),
    SpecimenObZone(new Vector2d(20, -52), PI * 0.5),
    SpecimenObZoneTiny(new Vector2d(28, -52), PI * 0.5),
    StartingPosition(new Vector2d(8, -52), 0.0),
    ObserservationZoneParkingSpecimen(new Vector2d(48, -63), 0.0),
    ObserservationZoneParkingSample(new Vector2d(50, -63), 0.0),


    //Test Specmien related movement
    HighRungTest(new Vector2d(6, -44), 0.0),
    GoFrontTinySpecimenTest(new Vector2d(6, -35), 0.0),
    HighRung2Test(new Vector2d(-3, -47), 0.0),
    GoFrontTiny2SpecimenTest(new Vector2d(-3, -35), 0.0),
    HighRung3Test(new Vector2d(4, -47), 0.0),
    GoFrontTiny3SpecimenTest(new Vector2d(4, -35), 0.0),
    HighRung4Test(new Vector2d(2, -47), 0.0),
    GoFrontTiny4SpecimenTest(new Vector2d(2, -35), 0.0),
    FirstColorSampleTest(new Vector2d(32, -36),PI * 0.25),
    SecondColorSampleTest(new Vector2d(42, -37.5), PI * 0.25),
    DropColorSampleTest(new Vector2d(32, -47), PI * 0.75),

    Test(new Vector2d(0.0,23.0),0.0),
    Test2(new Vector2d(0.0,48),0.0),
    TestStart(new Vector2d(0.0,0.0),0.0),
    TestRight(new Vector2d(40.0,0.0),0.0);

    Positions(Vector2d vector, Double rotation) {
        runToExact = new P2P(vector, rotation);
    }

    public final P2P runToExact;
}
