package org.firstinspires.ftc.teamcode;



@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="Autonomous", group="ULTIMATE_GOAL")
//@Disabled
public class Autonomous extends RobotOpMode {

    @Override
    public void runOpMode() {

        hardwareInit();
        char StarterStackConfiguration;

        waitForStart();
	
	    StarterStackConfiguration = starterStackConfiguration();


        gyroEncoderDrive("backward", 40, 0.5);
        pause(1);
        gyroEncoderDrive("leftward", 40, 1.3);
        pause(1);

        if(StarterStackConfiguration == 'B') {
            gyroEncoderDrive("forward-left", 20, 2);
            pause(1);
            gyroEncoderDrive("leftward", 40, 3);
            gyroEncoderDrive("rightward", 40, 0.7);
        }
        else {
            gyroEncoderDrive("backward-left", 40, 1);
            pause(1);
            gyroEncoderDrive("leftward", 40, 0.75);

            if (StarterStackConfiguration == 'A') {
                gyroEncoderDrive("rightward", 80, 0.7);

		        gyroEncoderDrive("forward", 40, 2);
	        	gyroEncoderDrive("leftward", 40, 1.3);
            }
            else {
                gyroEncoderDrive("leftward", 40, 3.5);
                gyroEncoderDrive("rightward", 40, 2.2);
            }
        }


	while(opModeIsActive()) {
		telemetry.addData("Configuration ==", StarterStackConfiguration);
		telemetry.update();
	}
    }
}
