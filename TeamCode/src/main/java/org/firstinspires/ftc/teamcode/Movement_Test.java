package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


@Autonomous(name="Movement Test", group="ULTIMATE_GOAL")
//@Disabled
public class Movement_Test extends RobotOpMode {

    @Override
    public void runOpMode() {
        waitForStart();

        gyroInit();

        gyroDrive("forward", 25, 6);
        pause(1);
        gyroDrive("rightward", 25, 6);
        pause(1);
        gyroDrive("backward", 25, 6);
        pause(1);
        gyroDrive("leftward", 25, 6);
        pause(1);
    }
}