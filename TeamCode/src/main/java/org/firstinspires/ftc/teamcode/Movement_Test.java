package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


@Autonomous(name="Movement Test", group="ULTIMATE_GOAL")
//@Disabled
public class Movement_Test extends RobotOpMode {

    @Override
    public void runOpMode() {
        waitForStart();

        hardwareInit();

        findAbsolutePosition(2.5, 2.5);
        pause(1);
        findAbsolutePosition(2.5, 1);
        pause(1);
        findAbsolutePosition(1, 1);

//        //gyroDriveOrientationChange("North");
//        gyroEncoderDrive("forward", 35, 4);
//        pause(1);
//
//        //gyroDriveOrientationChange("East");
//        gyroEncoderDrive("rightward", 35, 4);
//        pause(1);
//
//        //gyroDriveOrientationChange("South");
//        gyroEncoderDrive("backward", 35, 4);
//        pause(1);
//
//        //gyroDriveOrientationChange("West");
//        gyroEncoderDrive("leftward", 35, 4);
//        pause(1);
    }
}