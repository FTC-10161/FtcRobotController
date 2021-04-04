package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


@Autonomous(name="Movement Test", group="ULTIMATE_GOAL")
//@Disabled
public class Movement_Test extends RobotOpMode {

    @Override
    public void runOpMode() {
        waitForStart();

        hardwareInit();

        findAbsolutePosition(4, 4);
        pause(1);
        findAbsolutePosition(4, 2);
        pause(1);
        findAbsolutePosition(2, 2);

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