package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


@Autonomous(name="Movement Test", group="ULTIMATE_GOAL")
//@Disabled
public class Movement_Test extends RobotOpMode {

    @Override
    public void loop() {
        gyroDrive("forward", 40, 1.2);
        gyroDrive("rightward", 40, 1.2);
        gyroDrive("backward", 40, 1.2);
        gyroDrive("leftward", 40, 1.2);
        pause(3);
    }
}