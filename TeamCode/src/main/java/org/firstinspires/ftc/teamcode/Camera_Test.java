package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


@Autonomous(name="Camera Test", group="ULTIMATE_GOAL")
//@Disabled
public class Camera_Test extends RobotOpMode {

    @Override
    public void runOpMode() {
        waitForStart();

        hardwareInit();

        int configuration = starter_stack_detector.configuration;

         telemetry.addData("sum:", configuration);
         telemetry.update();

         pause(2);
    }
}