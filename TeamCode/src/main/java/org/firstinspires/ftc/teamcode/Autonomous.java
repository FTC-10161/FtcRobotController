package org.firstinspires.ftc.teamcode;



@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="Autonomous", group="ULTIMATE_GOAL")
//@Disabled
public class Autonomous extends RobotOpMode {

    @Override
    public void runOpMode() {

        hardwareInit();
		int RowsExceedingRingDetectionThreshold;
        char StarterStackConfiguration;

        waitForStart();
	
	RowsExceedingRingDetectionThreshold = starter_stack_detector.rowsContainingRings;

        if(RowsExceedingRingDetectionThreshold > 2) {
            StarterStackConfiguration = 'C';
        }
        else if (RowsExceedingRingDetectionThreshold > 0) {
            StarterStackConfiguration = 'B';
        }
        else {
            StarterStackConfiguration = 'A';
        }


        gyroEncoderDrive("backward", 40, 0.5);
        pause(1);
        gyroEncoderDrive("leftward", 40, 2.25);
        pause(1);

        if(StarterStackConfiguration == 'B') {
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
