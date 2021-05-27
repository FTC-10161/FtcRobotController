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
            gyroEncoderDrive("leftward", 80, 1);
            gyroEncoderDrive("forward-left", 80, 1.5);
            gyroEncoderDrive("leftward", 80, 0.75);
            gyroEncoderDrive("rightward", 50, 0.7);

            //Launch Rings
            gyroEncoderDrive("rightward", 50, 2.0);
            gyroDriveOrientationChange("East");
            gyroEncoderDrive("leftward", 50, 0.29);
            gyroEncoderDrive("forward", 50, 0.8);
            gyroEncoderDrive("backward", 50, 0.6);
            launchRings();
            gyroEncoderDrive("backward", 80, 1.0);
        }
        else {
            gyroEncoderDrive("backward-left", 80, 2);

            if (StarterStackConfiguration == 'A') {

                //Launch Rings
                gyroEncoderDrive("rightward", 80, 1.3);
                gyroDriveOrientationChange("East");
                gyroEncoderDrive("leftward", 80, 1.7);
                launchRings();
                gyroEncoderDrive("backward", 80, 1.7);
            }
            else {
                gyroEncoderDrive("leftward", 80, 3.2);

                //Launch Rings
                gyroEncoderDrive("rightward", 100, 4.2);
                gyroDriveOrientationChange("East");
                gyroEncoderDrive("leftward", 100, 1.7);
                launchRings();
                gyroEncoderDrive("backward", 80, 1.0);
            }
        }

        gyroDriveOrientationChange("North");
        findAbsolutePosition(0.5,0.25);
        gyroEncoderDrive("forward", 50 ,2.1);


        if(StarterStackConfiguration == 'B') {
            gyroEncoderDrive("leftward", 80, 1);
            gyroEncoderDrive("leftward", 80, 2);
            gyroEncoderDrive("backward-left", 100, 1.75);
            gyroEncoderDrive("leftward", 80, 1);

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
                gyroEncoderDrive("leftward", 80, 4.1);

                //Park on line
                gyroEncoderDrive("rightward", 100, 2.2);
            }
        }
    }
}
