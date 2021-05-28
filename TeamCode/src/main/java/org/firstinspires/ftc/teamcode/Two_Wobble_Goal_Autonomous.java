package org.firstinspires.ftc.teamcode;


import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="Two Wobble Goal Autonomous", group="ULTIMATE_GOAL")
//@Disabled
public class Two_Wobble_Goal_Autonomous extends RobotOpMode {

    @Override
    public void runOpMode() {

        hardwareInit();
        char StarterStackConfiguration;

        waitForStart();

        StarterStackConfiguration = starterStackConfiguration();


        gyroEncoderDrive("backward", 80, 0.2);
        gyroEncoderDrive("leftward", 80, 3);

        if(StarterStackConfiguration == 'B') {
            gyroEncoderDrive("leftward", 100, 1);
            gyroEncoderDrive("forward-left", 100, 1.5);
            gyroEncoderDrive("leftward", 100, 0.75);

            //Launch Rings
            gyroEncoderDrive("rightward", 100, 2.4);
            gyroDriveOrientationChange("East");
            gyroEncoderDrive("forward", 60, 0.8);
            gyroEncoderDrive("backward", 40, 0.6);
            launchRings();
        }
        else {
            gyroEncoderDrive("backward-left", 80, 2);
            gyroEncoderDrive("leftward", 80, 0.1);

            if (StarterStackConfiguration == 'A') {

                //Launch Rings
                gyroEncoderDrive("rightward", 80, 1.3);
                gyroDriveOrientationChange("East");
                gyroEncoderDrive("leftward", 80, 1.7);
                launchRings();
            }
            else {
                gyroEncoderDrive("leftward", 80, 2.8);

                //Launch Rings
                gyroEncoderDrive("forward-right", 40, 0.3);
                gyroEncoderDrive("rightward", 100, 4.0);
                gyroDriveOrientationChange("East");
                gyroEncoderDrive("leftward", 50, 1.5);
                launchRings();
                gyroEncoderDrive("rightward", 100, 0.4);
                gyroEncoderDrive("backward-right", 100, 0.3);
            }
        }

        gyroDriveOrientationChange("North");
        gyroEncoderDrive("forward", 60, 0.75);
        gyroEncoderDrive("backward-right", 60, 1.5);
        findAbsolutePosition(0.5,0.25);
        gyroEncoderDrive("forward-right", 50 ,0.2);
        gyroEncoderDrive("forward", 50 ,2.0);


        if(StarterStackConfiguration == 'B') {
            gyroEncoderDrive("leftward", 100, 4.4);
            gyroEncoderDrive("backward-left", 100, 1.75);

            //Park on line
            gyroEncoderDrive("rightward", 100, 0.7);
        }
        else {
            gyroEncoderDrive("backward-left", 80, 5);

            if (StarterStackConfiguration == 'A') {
                gyroEncoderDrive("leftward", 80, 1.1);

                //Park on line
                gyroEncoderDrive("rightward", 100, 0.7);
                gyroEncoderDrive("forward", 100, 2);
                gyroEncoderDrive("leftward", 100, 1.3);
            }
            else {
                gyroEncoderDrive("leftward", 100, 4.0);

                //Park on line
                gyroEncoderDrive("rightward", 100, 2.2);
            }
        }
    }
}