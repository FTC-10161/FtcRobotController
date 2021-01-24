package org.firstinspires.ftc.teamcode;


@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="Autonomous", group="ULTIMATE_GOAL")
//@Disabled
public class Autonomous extends RobotOpMode {

    @Override
    public void runOpMode() {
        waitForStart();

        hardwareInit();

        gyroTimeDrive("leftward", 35, 8.5);
        gyroTimeDrive("forward", 35, 1);
        pause(1);
        gyroTimeDrive("rightward", 35, 1.5);
    }
}