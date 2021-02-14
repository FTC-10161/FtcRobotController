package org.firstinspires.ftc.teamcode;


@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="Autonomous", group="ULTIMATE_GOAL")
//@Disabled
public class Autonomous extends RobotOpMode {

    @Override
    public void runOpMode() {
        waitForStart();

        hardwareInit();

        gyroEncoderDrive("leftward", 80, 5.25);
        gyroEncoderDrive("forward", 80, 0.5);
        pause(1);
        gyroEncoderDrive("rightward", 80, 0.7);
    }
}