package org.firstinspires.ftc.teamcode;


@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="Autonomous", group="ULTIMATE_GOAL")
//@Disabled
public class Autonomous extends RobotOpMode {

    @Override
    public void runOpMode() {

        hardwareInit();
        char StarterStackConfiguration;

        waitForStart();

        if(starter_stack_detector.rowsContainingRings > 2) {
            StarterStackConfiguration = 'C';
        }
        else if (starter_stack_detector.rowsContainingRings > 0) {
            StarterStackConfiguration = 'B';
        }
        else {
            StarterStackConfiguration = 'A';
        }


        gyroEncoderDrive("backward", 40, 0.5);
        pause(1);
        gyroEncoderDrive("leftward", 80, 2.25);
        pause(1);

        if(StarterStackConfiguration == 'B') {
            gyroEncoderDrive("forward", 40, 1.0);
            pause(1);
            gyroEncoderDrive("leftward", 80, 3.0);
            gyroEncoderDrive("rightward", 80, 0.7);
        }
        else {
            gyroEncoderDrive("forward", 40, 1.0);
            pause(1);
            gyroEncoderDrive("leftward", 80, 3.0);

            if (StarterStackConfiguration == 'A') {
                gyroEncoderDrive("rightward", 80, 0.7);
            }
            else {
                gyroEncoderDrive("leftward", 80, 4.2);
                gyroEncoderDrive("rightward", 80, 1.9);
            }
        }
    }
}