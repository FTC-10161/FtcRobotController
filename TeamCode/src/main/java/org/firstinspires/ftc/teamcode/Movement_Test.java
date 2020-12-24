package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


@Autonomous(name="Movement Test", group="ULTIMATE_GOAL")
//@Disabled
public class Movement_Test extends RobotOpMode {

    @Override
    public void start() {
//        gyroDrive("forward", 40, 5);
        gyroDrive("rightward", 25, 10);
//        gyroDrive("backward", 25, 10);
        pause(3);
        gyroDrive("leftward", 25, 10);
    }
}