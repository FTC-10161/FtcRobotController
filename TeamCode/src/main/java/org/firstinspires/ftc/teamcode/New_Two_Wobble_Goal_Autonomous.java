package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.DcMotor;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="New Two Wobble Goal Autonomous", group="ULTIMATE_GOAL")
//@Disabled
public class New_Two_Wobble_Goal_Autonomous extends RobotOpMode {

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
            gyroEncoderDrive("rightward", 50, 2.0);
            gyroDriveOrientationChange("East");
            gyroEncoderDrive("leftward", 50, 0.29);
            gyroEncoderDrive("forward", 50, 0.8);
            gyroEncoderDrive("backward", 50, 0.6);
            launchThreeRings();
            gyroEncoderDrive("backward", 80, 1.0);
        }
        else {
            gyroEncoderDrive("backward-left", 50, 2);

            if (StarterStackConfiguration == 'A') {
                gyroEncoderDrive("rightward", 80, 1.3);
                gyroDriveOrientationChange("East");
                gyroEncoderDrive("leftward", 50, 1.5);
//	        	gyroEncoderDrive("backward", 50, 0.2);
                launchThreeRings();
                gyroEncoderDrive("backward", 80, 1.5);
            }
            else {
                gyroEncoderDrive("leftward", 50, 3.5);
                gyroEncoderDrive("rightward", 80, 4.2);
                gyroDriveOrientationChange("East");
                gyroEncoderDrive("leftward", 50, 1.5);
//                gyroEncoderDrive("backward", 50, 0.2);
                launchThreeRings();
                gyroEncoderDrive("backward", 80, 1.0);
            }
        }


        findAbsolutePosition(10, 1.5);

        hardware.wobbleGoalArm.setTargetPosition(-2500);
        hardware.wobbleGoalArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hardware.wobbleGoalArm.setPower(0.5);

        while(opModeIsActive()) {
            telemetry.addData("Configuration ==", StarterStackConfiguration);
            telemetry.update();
        }
    }
}

