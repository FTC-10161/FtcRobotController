package org.firstinspires.ftc.teamcode;


@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="Autonomous", group="ULTIMATE_GOAL")
//@Disabled
public class Autonomous extends RobotOpMode {

    @Override
    public void runOpMode() {
        waitForStart();

        hardwareInit();

        gyroEncoderDrive("forward", 35, 5);
        pause(1);
        gyroEncoderDrive("backward", 35, 1.5);
    }
}