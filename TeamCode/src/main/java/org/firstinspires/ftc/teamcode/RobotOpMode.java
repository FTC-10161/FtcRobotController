package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

public class RobotOpMode extends OpMode {

    ULTIMATE_GOAL_HARDWARE_MAP hardware  = new ULTIMATE_GOAL_HARDWARE_MAP();
    ElapsedTime runtime = new ElapsedTime();

    @Override
    public void init() {
        hardware.init(hardwareMap);
    }

    @Override
    public void loop() {

    }

    public void drive(String direction,double speed, double duration){

        //Make sure that speed value is a value between 0 and 100 inclusive. If not, say so via telemetry.
        if (speed <= 0 || speed >= 100) {
            telemetry.addData("Invalid speed value of", speed);
            telemetry.addLine(". Must be between 0 and 100 inclusive.");
            telemetry.update();
        }

        //Drive in the specified direction.
        switch (direction) {
            case "forward":
                hardware.frontLeft.setPower(speed / 100);
                hardware.backLeft.setPower(speed / 100);
                hardware.frontRight.setPower(speed / 100);
                hardware.backRight.setPower(speed / 100);
                break;
            case "backward":
                hardware.frontLeft.setPower(-speed / 100);
                hardware.backLeft.setPower(-speed / 100);
                hardware.frontRight.setPower(-speed / 100);
                hardware.backRight.setPower(-speed / 100);
                break;
            case "rightward":
                hardware.frontLeft.setPower(speed / 100);
                hardware.backLeft.setPower(-speed / 100);
                hardware.frontRight.setPower(-speed / 100);
                hardware.backRight.setPower(speed / 100);
                break;
            case "leftward":
                hardware.frontLeft.setPower(-speed / 100);
                hardware.backLeft.setPower(speed / 100);
                hardware.frontRight.setPower(speed / 100);
                hardware.backRight.setPower(-speed / 100);
                break;
            default:                                 //If direction is not valid, says so via telemetry.
                telemetry.addData("Invalid direction: ", direction, ". Must be forward, backward, leftward, or rightward.");
                telemetry.update();
                break;
        }

        //Wait specified time-period while driving and displaying direction.
        while(runtime.milliseconds() < duration) {
            telemetry.addData("Driving", direction);
        }

        //Reset clock.
        runtime.reset();
    }
}
