package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp(name="Starter Stack Detection Test", group="ULTIMATE_GOAL")
//@Disabled
public class Starter_Stack_Detection_Test extends RobotOpMode {


	@Override
	public void runOpMode() {


		waitForStart();
		hardwareInit();

		while (opModeIsActive()) {

					telemetry.addData("Number of rows", starter_stack_detector.rowsContainingRings);
					telemetry.update();
		}
	}
}