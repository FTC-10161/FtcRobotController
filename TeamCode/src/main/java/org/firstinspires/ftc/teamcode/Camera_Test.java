package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


@TeleOp(name="Camera Test", group="ULTIMATE_GOAL")
//@Disabled
public class Camera_Test extends RobotOpMode {


	@Override
	public void runOpMode() {
		boolean A_Button;
		boolean B_Button;
		int i = 0;
		boolean isPressed = false;

		waitForStart();
		hardwareInit();

		while (opModeIsActive()) {

			A_Button = gamepad1.a;
			B_Button = gamepad1.b;

			if (A_Button && i < 17 && !isPressed) {
				i++;
				isPressed = true;
			} else if (B_Button && i > 0 && !isPressed) {
				i--;
				isPressed = true;
			} else if (!A_Button && !B_Button) {
				isPressed = false;
			}

			switch (i) {
				case 0:
					telemetry.addData("RGB Red:          ", starter_stack_detector.RGB_Red_C0);
					telemetry.update();
					break;
				case 1:
					telemetry.addData("RGB Green:        ", starter_stack_detector.RGB_Green_Difference_C1);
					telemetry.update();
					break;
				case 2:
					telemetry.addData("RGB Blue:         ", starter_stack_detector.RGB_Blue_Difference_C2);
					telemetry.update();
					break;

				case 3:
					telemetry.addData("YCrBc Luma:       ", starter_stack_detector.YCrBc_Luma_C0);
					telemetry.update();
					break;
				case 4:
					telemetry.addData("YCrBc Red:        ", starter_stack_detector.YCrBc_Red_Difference_C1);
					telemetry.update();
					break;
				case 5:
					telemetry.addData("YCrBc Blue:       ", starter_stack_detector.YCrBc_Blue_Difference_C2);
					telemetry.update();
					break;

				case 6:
					telemetry.addData("HSV Hue:          ", starter_stack_detector.HSV_Hue_C0);
					telemetry.update();
					break;
				case 7:
					telemetry.addData("HSV Saturation:   ", starter_stack_detector.HSV_Saturation_C1);
					telemetry.update();
					break;
				case 8:
					telemetry.addData("HSV Value:        ", starter_stack_detector.HSV_Value_C2);
					telemetry.update();
					break;

				case 9:
					telemetry.addData("HLS Hue:          ", starter_stack_detector.HLS_Hue_C0);
					telemetry.update();
					break;
				case 10:
					telemetry.addData("HLS Lightness:    ", starter_stack_detector.HLS_Lightness_C1);
					telemetry.update();
					break;
				case 11:
					telemetry.addData("HLS Saturation:   ", starter_stack_detector.HLS_Saturation_C2);
					telemetry.update();
					break;

				case 12:
					telemetry.addData("CIELAB Lightness: ", starter_stack_detector.CIELAB_Lightness_C0);
					telemetry.update();
					break;
				case 13:
					telemetry.addData("CIELAB A:         ", starter_stack_detector.CIELAB_A_Difference_C1);
					telemetry.update();
					break;
				case 14:
					telemetry.addData("CIELAB B:         ", starter_stack_detector.CIELAB_B_Difference_C2);
					telemetry.update();
					break;

				case 15:
					telemetry.addData("CIELUV Lightness: ", starter_stack_detector.CIELUV_Lightness_C0);
					telemetry.update();
					break;
				case 16:
					telemetry.addData("CIELUV A:         ", starter_stack_detector.CIELUV_U_Difference_C1);
					telemetry.update();
					break;
				case 17:
					telemetry.addData("CIELUV B:         ", starter_stack_detector.CIELUV_V_Difference_C2);
					telemetry.update();
					break;

				default:
					telemetry.addLine("Error, no case found. Please tell Isaiah that he made a mistake.");
			}
		}
	}
}