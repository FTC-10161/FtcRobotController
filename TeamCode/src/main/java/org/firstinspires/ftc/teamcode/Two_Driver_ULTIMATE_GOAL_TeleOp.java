/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import static java.lang.Math.abs;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;


/**
 * This OpMode uses the common Pushbot hardware class to define the devices on the robot.
 * All device access is managed through the HardwarePushbot class.
 * The code is structured as a LinearOpMode
 *
 * This particular OpMode executes a POV Game style Teleop for a PushBot
 * In this mode the left stick moves the robot FWD and back, the Right stick turns left and right.
 * It raises and lowers the claw using the Gampad Y and A buttons respectively.
 * It also opens and closes the claws slowly using the left and right Bumper buttons.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="Two_Driver_ULTIMATE_GOAL_TeleOp", group="On_Season")
//@Disabled
public class Two_Driver_ULTIMATE_GOAL_TeleOp extends LinearOpMode {

    /* Declare OpMode members. */
    ULTIMATE_GOAL_HARDWARE_MAP calculester = new ULTIMATE_GOAL_HARDWARE_MAP();   // Use a Pushbot's hardware

    double xDrive;
    double yDrive;
    double turn;
    double driveSpeed;

    double endEffectorGateState = 0.1;
    boolean xButtonPreviousState = false;

    double ringPusherState = 0.9;
    boolean leftBumperPreviousState = false;

    double measured_speed = 0;
    double flywheelpower = -1.0;



    @Override
    public void runOpMode() {


        int wobble_goal_arm_position = 0;



        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        calculester.init(hardwareMap);

        calculester.translation.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        calculester.translation.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        ElapsedTime timer = new ElapsedTime();
        ElapsedTime runtime = new ElapsedTime();
        runtime.reset();

        int prevPosition = calculester.flywheel.getCurrentPosition();

        // Send telemetry message to signify robot waiting;
        telemetry.addData("We're ready for takeoff", "");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            xDrive = gamepad1.right_stick_x;
            yDrive = gamepad1.right_stick_y;
            turn = gamepad1.left_stick_x;

            if (gamepad1.left_trigger > 0.2 || gamepad2.left_bumper) {
                driveSpeed = 0.2;
            } else if (gamepad1.right_trigger > 0.2) {
                driveSpeed = 1.0;
            } else {
                driveSpeed = 0.7;
            }


            if (turn < -0.2) {
                calculester.frontLeft.setPower(-driveSpeed);  //If the left control stick of gamepad1 is pushed more than
                calculester.backLeft.setPower(-driveSpeed);   //20% right, the robot turns rightward.
                calculester.frontRight.setPower(driveSpeed);
                calculester.backRight.setPower(driveSpeed);
            } else if (turn > 0.2) {
                calculester.frontLeft.setPower(driveSpeed);  //If the left control stick of gamepad1 is pushed more than
                calculester.backLeft.setPower(driveSpeed);   //20% left, the robot turns left.
                calculester.frontRight.setPower(-driveSpeed);
                calculester.backRight.setPower(-driveSpeed);
            } else if (yDrive < -0.3 || gamepad1.dpad_up) {
                calculester.frontLeft.setPower(driveSpeed);  //If the right control stick of gamepad1 is pushed more than
                calculester.backLeft.setPower(driveSpeed);   //20% forward or the up Dpad is pressed, the robot drives forward.
                calculester.frontRight.setPower(driveSpeed);
                calculester.backRight.setPower(driveSpeed);
            } else if (yDrive > 0.3 || gamepad1.dpad_down) {
                calculester.frontLeft.setPower(-driveSpeed);  //If the right control stick of gamepad1 is pushed more than
                calculester.backLeft.setPower(-driveSpeed);   //20% backward or the down Dpad is pressed, the robot drives backward.
                calculester.frontRight.setPower(-driveSpeed);
                calculester.backRight.setPower(-driveSpeed);
            } else if (xDrive > 0.2 || gamepad1.dpad_right) {
                calculester.frontLeft.setPower(driveSpeed);  //If the right control stick of gamepad1 is pushed more than
                calculester.backLeft.setPower(-driveSpeed);   //20% right or the right Dpad is pressed, the robot drives right.
                calculester.frontRight.setPower(-driveSpeed);
                calculester.backRight.setPower(driveSpeed);
            } else if (xDrive < -0.2 || gamepad1.dpad_left) {
                calculester.frontLeft.setPower(-driveSpeed);  //If the right control stick of gamepad1 is pushed more than
                calculester.backLeft.setPower(driveSpeed);   //20% left or the left Dpad is pressed, the robot drives left.
                calculester.frontRight.setPower(driveSpeed);
                calculester.backRight.setPower(-driveSpeed);
            } else {
                calculester.frontLeft.setPower(0);  //If no drives sticks are pushed, the robot stops.
                calculester.backLeft.setPower(0);
                calculester.frontRight.setPower(0);
                calculester.backRight.setPower(0);
            }

            if (gamepad2.a || gamepad1.a) {
                wobble_goal_arm_position = -75;
            } else if (gamepad2.b || gamepad1.b) {
                wobble_goal_arm_position = -1200;
            } else if (gamepad2.y || gamepad1.y) {
                wobble_goal_arm_position = -2500;
            } else if (gamepad2.dpad_up) {
                wobble_goal_arm_position = wobble_goal_arm_position + 30;
            } else if (gamepad2.dpad_down) {
                wobble_goal_arm_position = wobble_goal_arm_position - 30;
            }

            calculester.wobbleGoalArm.setTargetPosition(wobble_goal_arm_position);
            calculester.wobbleGoalArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            calculester.wobbleGoalArm.setPower(driveSpeed);


            if (gamepad2.right_stick_y > 0.2 || gamepad2.right_stick_y < -0.2) {
                calculester.translation.setPower(gamepad2.right_stick_y);
            } else {
                calculester.translation.setPower(0);
            }

            if (gamepad2.left_stick_y > 0.2) {
                calculester.intake.setPower(-1.0);
            }
            else if (gamepad2.left_stick_y < -0.2) {
                 calculester.intake.setPower(0.2);
            }
            else {
                calculester.intake.setPower(0);
            }

            if (gamepad2.right_trigger > 0.2) {
                if (timer.milliseconds() > 15) {
                    measured_speed = (double) (calculester.flywheel.getCurrentPosition() - prevPosition) / timer.time();
                    prevPosition = calculester.flywheel.getCurrentPosition();
                    timer.reset();

                    if (measured_speed > -1820 && flywheelpower >= -1.0) {
                            flywheelpower = flywheelpower - 0.01;
                    }
                    else if (measured_speed < -2020 && flywheelpower <= 0.0) {
                        flywheelpower = flywheelpower + 0.01;
                    }
                }
                    calculester.flywheel.setPower(flywheelpower);

                    //telemetry.addData("-4800 < Target < -5200:  ", measured_speed);
                    telemetry.addData("Translation", calculester.translation.getCurrentPosition());
                    telemetry.addData("-2020 < Target < -1820:  ", measured_speed);
                    telemetry.update();
            }
            else if (gamepad2.left_trigger > 0.2) {
                calculester.flywheel.setPower(-0.9);
            }
            else if (gamepad2.left_bumper) {
                calculester.flywheel.setPower(0.3);
            }
            else {
                calculester.flywheel.setPower(0);
            }


            if ((gamepad1.x || gamepad2.x) && !xButtonPreviousState) {
                xButtonPreviousState = true;

                if (endEffectorGateState == 0.1) {
                    endEffectorGateState = 0.6;
                } else {
                    endEffectorGateState = 0.1;
                }
                calculester.endEffector.setPosition(endEffectorGateState);

            } else if (!gamepad1.x && !gamepad2.x && xButtonPreviousState) {
                xButtonPreviousState = false;
            }


            if (gamepad2.dpad_left) {
                leftBumperPreviousState = true;

                if (ringPusherState == 0.1) {
                    ringPusherState = 0.9;
                } else {
                    ringPusherState = 0.1;
                }
                calculester.ringPusher.setPosition(ringPusherState);

            } else if (!gamepad2.left_bumper && leftBumperPreviousState) {
                leftBumperPreviousState = false;
            }


            telemetry.addData("cm", "%.2f cm", calculester.rearDistanceSensor.getDistance(DistanceUnit.CM));
            telemetry.addData("cm", "%.2f cm", calculester.rightDistanceSensor.getDistance(DistanceUnit.CM));
            telemetry.update();
        }
    }
}