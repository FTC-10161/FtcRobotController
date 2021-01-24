package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


@Autonomous(name="Movement Test", group="ULTIMATE_GOAL")
//@Disabled
public class Movement_Test extends RobotOpMode {

    @Override
    public void runOpMode() {
        waitForStart();

        hardwareInit();

        gyroTimeDrive("forward", 35, 4);
        pause(1);
        gyroTimeDrive("rightward", 35, 4);
        pause(1);
        gyroTimeDrive("backward", 35, 4);
        pause(1);
        gyroTimeDrive("leftward", 35, 4);
        pause(1);
    }
}