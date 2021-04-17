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


        gyroEncoderDrive("backward", 80, 0.5);
        gyroEncoderDrive("leftward", 80, 2.5);

        if(StarterStackConfiguration == 'B') {
            gyroEncoderDrive("leftward", 80, 1);
            gyroEncoderDrive("forward-left", 50, 1.5);
            gyroEncoderDrive("leftward", 80, 0.75);
            gyroEncoderDrive("rightward", 80, 0.7);
        }
        else {
            gyroEncoderDrive("backward-left", 50, 1.1);
            gyroEncoderDrive("leftward", 80, 0.75);

            if (StarterStackConfiguration == 'A') {
                gyroEncoderDrive("rightward", 80, 0.7);
            }
            else {
                gyroEncoderDrive("leftward", 80, 3.5);
                gyroEncoderDrive("rightward", 80, 2.2);
            }
        }





        findAbsolutePosition(2, 2);

        gyroTimeDrive("backward-right",50,  2.5);

        gyroEncoderDrive( "forward-left", 60,0.3);
        gyroEncoderDrive( "forward", 80,4);
        gyroEncoderDrive( "backward-left", 80,4);





        if(StarterStackConfiguration == 'B') {
            gyroEncoderDrive("leftward", 80, 1);
            gyroEncoderDrive("forward-left", 50, 1.5);
            gyroEncoderDrive("leftward", 80, 0.75);
            gyroEncoderDrive("rightward", 80, 0.7);
        }
        else {
            gyroEncoderDrive("backward-left", 50, 1.1);
            gyroEncoderDrive("leftward", 80, 0.75);

            if (StarterStackConfiguration == 'A') {
                gyroEncoderDrive("rightward", 80, 0.7);
            }
            else {
                gyroEncoderDrive("leftward", 80, 3.5);
                gyroEncoderDrive("rightward", 80, 2.2);
            }
        }

        while(opModeIsActive()) {
            telemetry.addData("Configuration ==", StarterStackConfiguration);
            telemetry.update();
        }
    }
}
