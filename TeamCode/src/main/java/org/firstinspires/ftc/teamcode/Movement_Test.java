package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


@Autonomous(name="Movement Test", group="ULTIMATE_GOAL")
//@Disabled
public class Movement_Test extends RobotOpMode {

    @Override
    public void runOpMode() {
        waitForStart();

        hardwareInit();

        gyroEncoderDrive("forward", 35, 4);
        pause(1);
        gyroEncoderDrive("rightward", 35, 4);
        pause(1);
        gyroEncoderDrive("backward", 35, 4);
        pause(1);
        gyroEncoderDrive("leftward", 35, 4);
        pause(1);
    }
}