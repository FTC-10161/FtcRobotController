package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.hardware.bosch.BNO055IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class RobotOpMode extends OpMode {

    ULTIMATE_GOAL_HARDWARE_MAP hardware  = new ULTIMATE_GOAL_HARDWARE_MAP();
    ElapsedTime runtime = new ElapsedTime();
    BNO055IMU imu;
    BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

    @Override
    public void init() {
        hardware.init(hardwareMap);

        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = false;

        imu = hardwareMap.get(BNO055IMU.class, "imu");

        imu.initialize(parameters);

        telemetry.addData("Mode", "calibrating...");
        telemetry.update();

        telemetry.addData("Mode", "waiting for start");
        telemetry.addData("imu calib status", imu.getCalibrationStatus().toString());
        telemetry.update();

    }

    @Override
    public void loop() {    }



    //////////////////////////////////////////////////////////// WAIT FUNCTION ////////////////////////////////////////////////////////////
    public void pause(double duration) {
        runtime.reset();
        while (runtime.milliseconds() < (duration * 1000)) {
            telemetry.addData("Waiting", duration);
            telemetry.update();
        }
        runtime.reset();
    }


    //////////////////////////////////////////////////////////// DRIVE FUNCTION ////////////////////////////////////////////////////////////
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
        runtime.reset();
        while(runtime.milliseconds() < (duration*1000)) {
            telemetry.addData("Driving", direction);
            telemetry.update();
        }

        //Stop all motors
        hardware.frontLeft.setPower(0);
        hardware.backLeft.setPower(0);
        hardware.frontRight.setPower(0);
        hardware.backRight.setPower(0);
        telemetry.addLine("Robot Stopped");
        telemetry.update();
    }



    //////////////////////////////////////////////////////////// GYRO DRIVE FUNCTION ////////////////////////////////////////////////////////////
    public void gyroDrive(String direction, double speed, double duration){
        double heading;
        double correction;
        runtime.reset();

        //Make sure that speed value is a value between 0 and 100 inclusive. If not, say so via telemetry.
        if (speed <= 0 || speed >= 100) {
            telemetry.addData("Invalid speed value of", speed);
            telemetry.addLine(". Must be between 0 and 100 inclusive.");
            telemetry.update();
        }

        while(runtime.milliseconds() < (duration*1000)) {
            Orientation angles = this.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            this.imu.getPosition();
            heading = angles.firstAngle;
            correction = heading / 100;

            telemetry.addData("angle", heading);
            telemetry.update();

            //Drive in the specified direction
            switch (direction) {
                case "forward":
                    hardware.frontLeft.setPower((speed/100) + correction);
                    hardware.backLeft.setPower((speed/100) + correction);
                    hardware.frontRight.setPower((speed/100) - correction);
                    hardware.backRight.setPower((speed/100) - correction);
                    break;
                case "backward":
                    hardware.frontLeft.setPower(-(speed/100) + correction);
                    hardware.backLeft.setPower(-(speed/100) + correction);
                    hardware.frontRight.setPower(-(speed/100) - correction);
                    hardware.backRight.setPower(-(speed/100) - correction);
                    break;
                case "rightward":
                    hardware.frontLeft.setPower((speed/100) + correction);
                    hardware.backLeft.setPower((-speed/100) - correction);
                    hardware.frontRight.setPower((-speed/100) - correction);
                    hardware.backRight.setPower((speed/100) + correction);
                    break;
                case "leftward":
                    hardware.frontLeft.setPower((-speed/100) - correction);
                    hardware.backLeft.setPower((speed/100) + correction);
                    hardware.frontRight.setPower((speed/100) + correction);
                    hardware.backRight.setPower((-speed/100) - correction);
                    break;
                default:                                 //If direction is not valid, says so via telemetry.
                    telemetry.addData("Invalid direction: ", direction, ". Must be forward, backward, leftward, or rightward.");
                    telemetry.update();
                    break;
            }
        }
        //Stop all motors
        hardware.frontLeft.setPower(0);
        hardware.backLeft.setPower(0);
        hardware.frontRight.setPower(0);
        hardware.backRight.setPower(0);
        telemetry.addLine("Robot Stopped");
        telemetry.update();
    }
}
