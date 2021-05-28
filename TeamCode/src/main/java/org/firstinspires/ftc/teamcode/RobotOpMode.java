package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.hardware.bosch.BNO055IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;


import static java.lang.Math.abs;
import static java.lang.Double.isNaN;


public class RobotOpMode extends LinearOpMode {

    ULTIMATE_GOAL_HARDWARE_MAP hardware = new ULTIMATE_GOAL_HARDWARE_MAP();
    ElapsedTime runtime = new ElapsedTime();
    BNO055IMU imu;
    BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

    public OpenCvInternalCamera phoneCam;
    public Starter_Stack_Configuration_Detector starter_stack_detector = new Starter_Stack_Configuration_Detector();

    int driveAngleOffset = 0;

    @Override
    public void runOpMode() {

    }

    //////////////////////////////////////////////////////////// GYRO INIT FUNCTION ////////////////////////////////////////////////////////////
    public void hardwareInit() {
        hardware.init(hardwareMap);

        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = false;

        imu = hardwareMap.get(BNO055IMU.class, "imu");

        imu.initialize(parameters);

        telemetry.addData("Mode", "calibrating...");
        telemetry.update();

        telemetry.addData("imu calibration status", imu.getCalibrationStatus().toString());
        telemetry.update();

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        phoneCam = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
        phoneCam.openCameraDevice();
        phoneCam.setPipeline(starter_stack_detector);
        phoneCam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
    }

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
    public void drive(String direction, double speed, double duration) {

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
            case "forward-right":
                hardware.frontLeft.setPower(speed / 100);
                hardware.backLeft.setPower(0);
                hardware.frontRight.setPower(0);
                hardware.backRight.setPower(speed / 100);
                break;
            case "forward-left":
                hardware.frontLeft.setPower(0);
                hardware.backLeft.setPower(speed / 100);
                hardware.frontRight.setPower(speed / 100);
                hardware.backRight.setPower(0);
                break;
            case "backward-right":
                hardware.frontLeft.setPower(0);
                hardware.backLeft.setPower(-speed / 100);
                hardware.frontRight.setPower(-speed / 100);
                hardware.backRight.setPower(0);
                break;
            case "backward-left":
                hardware.frontLeft.setPower(-speed / 100);
                hardware.backLeft.setPower(0);
                hardware.frontRight.setPower(0);
                hardware.backRight.setPower(-speed / 100);
                break;
            default:                                 //If direction is not valid, says so via telemetry.
                telemetry.addData("Invalid direction: ", direction, ". Must be forward, backward, leftward, or rightward.");
                telemetry.update();
                break;
        }

        //Wait specified time-period while driving and displaying direction.
        runtime.reset();
        while (runtime.milliseconds() < (duration * 1000)) {
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


    //////////////////////////////////////////////////////////// GYRO DRIVE ELAPSED TIME FUNCTION ////////////////////////////////////////////////////////////
    public void gyroTimeDrive(String direction, double speed, double duration) {
        double heading;
        double correction;
        runtime.reset();

        //Make sure that speed value is a value between 0 and 100 inclusive. If not, say so via telemetry.
        if (speed <= 0 || speed >= 100) {
            telemetry.addData("Invalid speed value of", speed);
            telemetry.addLine(". Must be between 0 and 100 inclusive.");
            telemetry.update();
        }

        while (runtime.milliseconds() < (duration * 1000)) {
            Orientation angles = this.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            this.imu.getPosition();
            heading = angles.firstAngle;                        //Measure angle from gyroscope
            heading = heading + driveAngleOffset;               //Add drive offset to angle; used for driving when turned
            correction = heading / 100;

            telemetry.addData("angle", heading);
            telemetry.update();

            //Drive in the specified direction
            switch (direction) {
                case "forward":
                    hardware.frontLeft.setPower((speed / 100) + correction);
                    hardware.backLeft.setPower((speed / 100) + correction);
                    hardware.frontRight.setPower((speed / 100) - correction);
                    hardware.backRight.setPower((speed / 100) - correction);
                    break;
                case "backward":
                    hardware.frontLeft.setPower(-(speed / 100) + correction);
                    hardware.backLeft.setPower(-(speed / 100) + correction);
                    hardware.frontRight.setPower(-(speed / 100) - correction);
                    hardware.backRight.setPower(-(speed / 100) - correction);
                    break;
                case "rightward":
                    hardware.frontLeft.setPower((speed / 100) + correction);
                    hardware.backLeft.setPower((-speed / 100) + correction);
                    hardware.frontRight.setPower((-speed / 100) - correction);
                    hardware.backRight.setPower((speed / 100) - correction);
                    break;
                case "leftward":
                    hardware.frontLeft.setPower((-speed / 100) + correction);
                    hardware.backLeft.setPower((speed / 100) + correction);
                    hardware.frontRight.setPower((speed / 100) - correction);
                    hardware.backRight.setPower((-speed / 100) - correction);
                    break;
                case "forward-right":
                    hardware.frontLeft.setPower((speed / 100) + correction);
                    hardware.backLeft.setPower((0) + correction);
                    hardware.frontRight.setPower((0) - correction);
                    hardware.backRight.setPower((speed / 100) - correction);
                    break;
                case "forward-left":
                    hardware.frontLeft.setPower((0) + correction);
                    hardware.backLeft.setPower((speed / 100) + correction);
                    hardware.frontRight.setPower((speed / 100) - correction);
                    hardware.backRight.setPower((0) - correction);
                    break;
                case "backward-right":
                    hardware.frontLeft.setPower((0) + correction);
                    hardware.backLeft.setPower((-speed / 100) + correction);
                    hardware.frontRight.setPower((-speed / 100) - correction);
                    hardware.backRight.setPower((0) - correction);
                    break;
                case "backward-left":
                    hardware.frontLeft.setPower((-speed / 100) + correction);
                    hardware.backLeft.setPower((0) + correction);
                    hardware.frontRight.setPower((0) - correction);
                    hardware.backRight.setPower((-speed / 100) - correction);
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


    //////////////////////////////////////////////////////////// GYRO DRIVE ENCODER FUNCTION ////////////////////////////////////////////////////////////
    public void gyroEncoderDrive(String direction, double speed, double target_revolution_count) {
        double heading;
        double correction;
        double current_encoder_count = 0;
        int angle = 0;


        //Make sure that speed value is a value between 0 and 100 inclusive. If not, say so via telemetry.
        if (speed <= 0 || speed >= 100) {
            telemetry.addData("Invalid speed value of", speed);
            telemetry.addLine(". Must be between 0 and 100 inclusive.");
            telemetry.update();
        }

        //Determine whether the robot will drove on an angle
        switch (direction) {
            case "forward":
            case "backward":
            case "rightward":
            case "leftward":
                angle = 4;
                break;
            case "forward-right":
            case "forward-left":
            case "backward-right":
            case "backward-left":
                angle = 2;
                break;
            default:                                 //If direction is not valid, says so via telemetry.
                telemetry.addData("Invalid direction: ", direction, ". Must be forward, backward, leftward, or rightward.");
                telemetry.update();
                break;
        }

        hardware.frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hardware.frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        hardware.frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hardware.frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        hardware.backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hardware.backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        hardware.backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hardware.backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        while (current_encoder_count < (target_revolution_count * 1120)) {
            Orientation angles = this.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            this.imu.getPosition();
            heading = angles.firstAngle;                        //Measure angle from gyroscope
            heading = heading + driveAngleOffset;               //Add drive offset to angle; used for driving when turned
            correction = heading / 100;

            telemetry.addData("angle", heading);
            telemetry.update();

            //Drive in the specified direction
            switch (direction) {
                case "forward":
                    hardware.frontLeft.setPower((speed / 100) + correction);
                    hardware.backLeft.setPower((speed / 100) + correction);
                    hardware.frontRight.setPower((speed / 100) - correction);
                    hardware.backRight.setPower((speed / 100) - correction);
                    break;
                case "backward":
                    hardware.frontLeft.setPower(-(speed / 100) + correction);
                    hardware.backLeft.setPower(-(speed / 100) + correction);
                    hardware.frontRight.setPower(-(speed / 100) - correction);
                    hardware.backRight.setPower(-(speed / 100) - correction);
                    break;
                case "rightward":
                    hardware.frontLeft.setPower((speed / 100) + correction);
                    hardware.backLeft.setPower((-speed / 100) + correction);
                    hardware.frontRight.setPower((-speed / 100) - correction);
                    hardware.backRight.setPower((speed / 100) - correction);
                    break;
                case "leftward":
                    hardware.frontLeft.setPower((-speed / 100) + correction);
                    hardware.backLeft.setPower((speed / 100) + correction);
                    hardware.frontRight.setPower((speed / 100) - correction);
                    hardware.backRight.setPower((-speed / 100) - correction);
                    break;
                case "forward-right":
                    hardware.frontLeft.setPower((speed / 100) + correction);
                    hardware.backLeft.setPower((0) + correction);
                    hardware.frontRight.setPower((0) - correction);
                    hardware.backRight.setPower((speed / 100) - correction);
                    break;
                case "forward-left":
                    hardware.frontLeft.setPower((0) + correction);
                    hardware.backLeft.setPower((speed / 100) + correction);
                    hardware.frontRight.setPower((speed / 100) - correction);
                    hardware.backRight.setPower((0) - correction);
                    break;
                case "backward-right":
                    hardware.frontLeft.setPower((0) + correction);
                    hardware.backLeft.setPower((-speed / 100) + correction);
                    hardware.frontRight.setPower((-speed / 100) - correction);
                    hardware.backRight.setPower((0) - correction);
                    break;
                case "backward-left":
                    hardware.frontLeft.setPower((-speed / 100) + correction);
                    hardware.backLeft.setPower((0) + correction);
                    hardware.frontRight.setPower((0) - correction);
                    hardware.backRight.setPower((-speed / 100) - correction);
                    break;
                default:                                 //If direction is not valid, says so via telemetry.
                    telemetry.addData("Invalid direction: ", direction, ". Must be forward, backward, leftward, or rightward.");
                    telemetry.update();
                    break;
            }

            if (angle == 4) {
                current_encoder_count = (abs(hardware.frontRight.getCurrentPosition()) +
                        abs(hardware.frontLeft.getCurrentPosition()) +
                        abs(hardware.backRight.getCurrentPosition()) +
                        abs(hardware.backLeft.getCurrentPosition())
                ) / 4.0;                                                  //Store encoder position as the average of the absolute value of all wheels
            } else if (angle == 2) {
                current_encoder_count = (abs(hardware.frontRight.getCurrentPosition()) +
                        abs(hardware.frontLeft.getCurrentPosition()) +
                        abs(hardware.backRight.getCurrentPosition()) +
                        abs(hardware.backLeft.getCurrentPosition())
                ) / 2.0;                                                  //Store encoder position as the average of the absolute value of two wheels
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


    //////////////////////////////////////////////////////////// GYRO DRIVE ORIENTATION CHANGE METHOD ////////////////////////////////////////////////////////////
    public void gyroDriveOrientationChange(String orientation) {
        switch (orientation) {
            case "North":   //North is considered to be the robot's starting orientation.
                driveAngleOffset = 0;
                break;
            case "East":
                driveAngleOffset = 90;
                break;
            case "South":
                driveAngleOffset = 180;
                break;
            case "West":
                driveAngleOffset = -90;
                break;
            default:
                telemetry.addLine("Invalid direction passed to gyroDriveOrientationChange");
                telemetry.update();
                break;
        }
    }



    //////////////////////////////////////////////////////////// ABSOLUTE POSITION FUNCTION ////////////////////////////////////////////////////////////
    public void findAbsolutePosition(double x_target, double y_target) {
        double heading;
        double correction;

        double x_diff;
        double y_diff;
        double x_current;
        double y_current;
        double frontLeftPower;
        double backLeftPower;
        double frontRightPower;
        double backRightPower;

        do {
	        Orientation angles = this.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            this.imu.getPosition();
            heading = angles.firstAngle;                        //Measure angle from gyroscope
            heading = heading + driveAngleOffset;               //Add drive offset to angle; used for driving when turned
            correction = heading / 100;
            telemetry.addData("angle", heading);

            x_current = 0;
            y_current = 0;

            for(int i=0; i<=4; i++) {
                x_current = x_current + hardware.rearDistanceSensor.getDistance(DistanceUnit.INCH);     //Read distances in inches
                y_current = y_current + hardware.rightDistanceSensor.getDistance(DistanceUnit.INCH);
            }
            x_current = x_current/5;
            y_current = y_current/5;

            x_diff = (x_target * 12) - x_current;
            y_diff = (y_target * 12) - y_current;

            if (isNaN(x_current)) {
                gyroEncoderDrive("forward", 100, 0.1);
                continue;
            }
            if (isNaN(y_current)) {
                gyroEncoderDrive("leftward", 100, 0.1);
                continue;
            }
            
            if (x_current > 180 || y_current > 180) {
                continue;
            }

            frontLeftPower = (x_diff - y_diff);
            backLeftPower = (x_diff + y_diff);
            frontRightPower = (x_diff + y_diff);
            backRightPower = (x_diff - y_diff);


            while (abs(frontLeftPower) > 0.75 || abs(backLeftPower) > 0.75 || abs(frontRightPower) > 0.75 || abs(backRightPower) > 0.75) {
                frontLeftPower = frontLeftPower / 1.2;                                   //Reduce power values to below 0.75
                backLeftPower = backLeftPower / 1.2;
                frontRightPower = frontRightPower / 1.2;
                backRightPower = backRightPower / 1.2;
            }
            while (abs(frontLeftPower) + abs(backLeftPower) + abs(frontRightPower) + abs(backRightPower) < 0.5) {
                frontLeftPower = frontLeftPower * 1.2;
                backLeftPower = backLeftPower * 1.2;
                frontRightPower = frontRightPower * 1.2;
                backRightPower = backRightPower * 1.2;
            }

                frontLeftPower = frontLeftPower + correction;
                backLeftPower = backLeftPower + correction;
                frontRightPower = frontRightPower - correction;
                backRightPower = backRightPower - correction;

            telemetry.addData("X diff :", x_diff);
            telemetry.addData("Y diff :", y_diff);
            telemetry.update();


            hardware.frontLeft.setPower(frontLeftPower);
            hardware.backLeft.setPower(backLeftPower);
            hardware.frontRight.setPower(frontRightPower);
            hardware.backRight.setPower(backRightPower);
        } while (abs(x_diff) > 2.0 || abs(y_diff) > 2.0);

        //Stop all motors
        hardware.frontLeft.setPower(0);
        hardware.backLeft.setPower(0);
        hardware.frontRight.setPower(0);
        hardware.backRight.setPower(0);
        telemetry.addLine("Robot Stopped");
        telemetry.update();
    }


    //////////////////////////////////////////////////////////// STARTER STACK CONFIGURATION FUNCTION ////////////////////////////////////////////////////////////
    public char starterStackConfiguration() {
        int RowsExceedingRingDetectionThreshold = 0;
        char configuration;

        runtime.reset();
        while (runtime.milliseconds() < 1000) {
            RowsExceedingRingDetectionThreshold = starter_stack_detector.numberOfTimesRingsDetected;
            telemetry.addData("Rows above Threshold:", RowsExceedingRingDetectionThreshold);
            telemetry.update();
        }

        if(RowsExceedingRingDetectionThreshold > 15) {
            configuration = 'C';
        }
        else if (RowsExceedingRingDetectionThreshold > 4) {
            configuration = 'B';
        }
        else {
            configuration = 'A';
        }

        runtime.reset();
        return configuration;
    }

/*
    //////////////////////////////////////////////////////////// RUN FLYWHEEL FUNCTION ////////////////////////////////////////////////////////////
    public void launchRings(double targetRotationNumber) {
        double measured_speed = 0;
        double power = -1.0;

        hardware.translation.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hardware.translation.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        ElapsedTime timer = new ElapsedTime();
        runtime.reset();

        int prevPosition = hardware.flywheel.getCurrentPosition();

        //Run method until the translation ramp run a specific distance
        while (hardware.translation.getCurrentPosition() > -targetRotationNumber*288) {
            //Measure flywheel speed and store its value in measured_speed
            if (timer.milliseconds() > 15) {
                measured_speed = (double) (hardware.flywheel.getCurrentPosition() - prevPosition) / timer.time();
                prevPosition = hardware.flywheel.getCurrentPosition();
                timer.reset();

                //Run translation ramp when flywheel speed is within threshold
                if (-2000 < measured_speed && measured_speed < -1800) {
                    hardware.translation.setPower(-0.6);
                }
                //Turn translation ramp off if flywheel is below threshold, and increase flywheel speed if it is not already at 100%
                else if (measured_speed > -1800 && power >= -1.0) {
                    hardware.translation.setPower(0.0);
                    power = power - 0.01;
                }
                //Turn translation ramp off if flywheel is above threshold, and decrease flywheel speed if it is not already at 0%
                else if (measured_speed < -2000 && power <= 0.0) {
                    hardware.translation.setPower(0.0);
                    power = power + 0.01;
                }

                hardware.flywheel.setPower(power);
            }

            //telemetry.addData("-4800 < Target < -5200:  ", measured_speed);
            telemetry.addData("Translation", hardware.translation.getCurrentPosition());
            telemetry.addData("-2000 < Target < -1800:  ", measured_speed);
            telemetry.update();
        }
    }


    //////////////////////////////////////////////////////////// LAUNCH RINGS FUNCTION ////////////////////////////////////////////////////////////
    public void launchThreeRings() {

        hardware.flywheel.setPower(-0.95);
        pause(2.0);
        hardware.translation.setPower(-0.6);
        pause(3.0);
        hardware.ringPusher.setPosition(0.1);
        pause(1.0);
        hardware.flywheel.setPower(0);
        hardware.translation.setPower(0);
        hardware.ringPusher.setPosition(0.9);


        launchRings(2.5);
        hardware.ringPusher.setPosition(0.1);
        launchRings(1);
        hardware.flywheel.setPower(0);
        hardware.translation.setPower(0);
        hardware.ringPusher.setPosition(0.9);
    }
    */


    //////////////////////////////////////////////////////////// RUN FLYWHEEL FUNCTION ////////////////////////////////////////////////////////////
    public void launchRings() {
        double measured_speed = 0;
        double power = -1.0;

        hardware.translation.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hardware.translation.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        ElapsedTime timer = new ElapsedTime();
        runtime.reset();

        int prevPosition = hardware.flywheel.getCurrentPosition();

        //Run method until the translation ramp run a specific distance
        while (hardware.translation.getCurrentPosition() > -864) {
            //Measure flywheel speed and store its value in measured_speed
            if (timer.milliseconds() > 15) {
                measured_speed = (double) (hardware.flywheel.getCurrentPosition() - prevPosition) / timer.time();
                prevPosition = hardware.flywheel.getCurrentPosition();
                timer.reset();

                //Run translation ramp when flywheel speed is within threshold
                if (-2020 < measured_speed && measured_speed < -1820) {
                    hardware.translation.setPower(-0.8);
                }
                //Turn translation ramp off if flywheel is below threshold, and increase flywheel speed if it is not already at 100%
                else if (measured_speed > -1820 && power >= -1.0) {
                    hardware.translation.setPower(0.0);
                    power = power - 0.01;
                }
                //Turn translation ramp off if flywheel is above threshold, and decrease flywheel speed if it is not already at 0%
                else if (measured_speed < -2020 && power <= 0.0) {
                    hardware.translation.setPower(0.0);
                    power = power + 0.01;
                }

                hardware.flywheel.setPower(power);
            }

            if (hardware.translation.getCurrentPosition() < -576) {
                hardware.ringPusher.setPosition(0.1);
            }

            //telemetry.addData("-4800 < Target < -5200:  ", measured_speed);
            telemetry.addData("Translation", hardware.translation.getCurrentPosition());
            telemetry.addData("-2020 < Target < -1820:  ", measured_speed);
            telemetry.update();
        }

        hardware.flywheel.setPower(0);
        hardware.translation.setPower(0);
        hardware.ringPusher.setPosition(0.9);
    }
}