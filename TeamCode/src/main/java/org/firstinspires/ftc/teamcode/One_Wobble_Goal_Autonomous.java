package org.firstinspires.ftc.teamcode;



@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="One Wobble Goal Autonomous", group="ULTIMATE_GOAL")
//@Disabled
public class One_Wobble_Goal_Autonomous extends RobotOpMode {

    @Override
    public void runOpMode() {

        hardwareInit();
        char StarterStackConfiguration;

        waitForStart();
	
	    StarterStackConfiguration = starterStackConfiguration();


        gyroEncoderDrive("backward", 80, 0.2);
        gyroEncoderDrive("leftward", 80, 3);

        if(StarterStackConfiguration == 'B') {
            gyroEncoderDrive("leftward", 50, 1);
            gyroEncoderDrive("forward-left", 50, 1.5);
            gyroEncoderDrive("leftward", 50, 0.75);
            gyroEncoderDrive("rightward", 50, 0.7);
        }
        else {
            gyroEncoderDrive("backward-left", 50, 2);

            if (StarterStackConfiguration == 'A') {
                gyroEncoderDrive("rightward", 80, 0.7);

		        gyroEncoderDrive("forward", 50, 2);
	        	gyroEncoderDrive("leftward", 50, 1.3);
            }
            else {
                gyroEncoderDrive("leftward", 50, 3.5);
                gyroEncoderDrive("rightward", 50, 2.2);
            }
        }


	while(opModeIsActive()) {
		telemetry.addData("Configuration ==", StarterStackConfiguration);
		telemetry.update();
	}
    }
}
