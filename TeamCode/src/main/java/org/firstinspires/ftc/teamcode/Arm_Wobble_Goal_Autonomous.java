package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="Ring Launch One Wobble Goal Autonomous", group="ULTIMATE_GOAL")
@Disabled
public class Arm_Wobble_Goal_Autonomous extends RobotOpMode {

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
            //gyroEncoderDrive("leftward", 50, 0.75);

            hardware.wobbleGoalArm.setTargetPosition(-2500);
            hardware.wobbleGoalArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            hardware.wobbleGoalArm.setPower(0.5);
            pause(0.5);
            hardware.endEffector.setPosition(0.6);
            pause(0.5);

            gyroEncoderDrive("rightward", 50, 0.5);
            hardware.endEffector.setPosition(0.1);
            gyroDriveOrientationChange("East");
            gyroEncoderDrive("leftward", 50, 0.5);
            launchRings();
            gyroEncoderDrive("backward", 80, 1.0);
        }
        else {
            gyroEncoderDrive("backward-left", 50, 2);

            if (StarterStackConfiguration == 'A') {
                gyroEncoderDrive("rightward", 80, 0.3);

                hardware.wobbleGoalArm.setTargetPosition(-2500);
                hardware.wobbleGoalArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                hardware.wobbleGoalArm.setPower(0.5);
                pause(0.5);
                hardware.endEffector.setPosition(0.6);
                pause(0.5);

                gyroEncoderDrive("rightward", 80, 0.3);
                hardware.endEffector.setPosition(0.1);
                gyroDriveOrientationChange("East");
		        gyroEncoderDrive("leftward", 50, 1.5);
                launchRings();
                gyroEncoderDrive("backward", 80, 1.0);
            }
            else {
                gyroEncoderDrive("leftward", 50, 1.8);

                hardware.wobbleGoalArm.setTargetPosition(-2500);
                hardware.wobbleGoalArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                hardware.wobbleGoalArm.setPower(0.5);
                pause(0.5);
                hardware.endEffector.setPosition(0.6);
                pause(0.5);

                gyroEncoderDrive("rightward", 80, 2.0);
                hardware.endEffector.setPosition(0.1);
                gyroDriveOrientationChange("East");
                gyroEncoderDrive("leftward", 50, 1.5);
//                gyroEncoderDrive("backward", 50, 0.2);
                launchRings();
                gyroEncoderDrive("backward", 80, 1.0);
            }
        }


	while(opModeIsActive()) {
		telemetry.addData("Configuration ==", StarterStackConfiguration);
		telemetry.update();
	}
    }
}
