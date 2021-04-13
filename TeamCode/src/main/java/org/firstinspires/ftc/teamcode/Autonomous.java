package org.firstinspires.ftc.teamcode;



@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="Autonomous", group="ULTIMATE_GOAL")
//@Disabled
public class Autonomous extends RobotOpMode {

    @Override
    public void runOpMode() {

        hardwareInit();
        char starter_stack_configuration;

        waitForStart();
	
	    starter_stack_configuration = starterStackConfiguration();


        gyroEncoderDrive("backward", 40, 0.5);
        pause(1);
        gyroEncoderDrive("leftward", 40, 2.25);
        pause(1);

        if(starter_stack_configuration == 'B') {
            gyroEncoderDrive("forward", 20, 0.5);
            pause(1);
            gyroEncoderDrive("leftward", 50, 1.5);
            gyroEncoderDrive("forward", 20, 0.5);
            pause(1);
            gyroEncoderDrive("leftward", 40, 1.5);
            gyroEncoderDrive("rightward", 40, 0.7);
        }
        else {
            gyroEncoderDrive("backward", 40, 0.5);
            pause(1);
            gyroEncoderDrive("leftward", 40, 1.5);

            if (starter_stack_configuration == 'A') {
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
		telemetry.addData("Configuration ==", starter_stack_configuration);
		telemetry.update();
	}
    }
}
