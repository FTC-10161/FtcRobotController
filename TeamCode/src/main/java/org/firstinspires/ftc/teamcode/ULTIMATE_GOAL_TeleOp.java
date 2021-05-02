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
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;




@TeleOp(name="ULTIMATE_GOAL_TeleOp", group="On_Season")
//@Disabled
public class ULTIMATE_GOAL_TeleOp extends LinearOpMode {

    /* Declare OpMode members. */
    ULTIMATE_GOAL_HARDWARE_MAP calculester = new ULTIMATE_GOAL_HARDWARE_MAP();

    double turn;
    double driveSpeed;
    double translation;
    double endEffectorGateState = 0.1;
    boolean xButtonPreviousState = false;

    @Override
    public void runOpMode() {


        double speed = 0;

        calculester.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("We're ready for takeoff", "");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        int prevPosition = calculester.flywheel.getCurrentPosition();
        ElapsedTime timer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        timer.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            turn = gamepad1.left_stick_x;
            translation = -gamepad1.right_trigger;

            // check bumpers of controller 1 in order to set drive speed
            if (gamepad1.left_bumper) {
                driveSpeed = 0.2; //if left bumpers is pressed, then change drive speed to 20%
            } else if (gamepad1.right_bumper) {
                driveSpeed = 1.0; //if right bumpers is pressed, then change drive speed to 100%
            } else {
                driveSpeed = 0.7; //if neither bumpers is pressed, then change drive speed to 50%
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
            } else if (gamepad1.dpad_up) {
                calculester.frontLeft.setPower(driveSpeed);  //If the right control stick of gamepad1 is pushed more than
                calculester.backLeft.setPower(driveSpeed);   //20% forward or the up Dpad is pressed, the robot drives forward.
                calculester.frontRight.setPower(driveSpeed);
                calculester.backRight.setPower(driveSpeed);
            } else if (gamepad1.dpad_down) {
                calculester.frontLeft.setPower(-driveSpeed);  //If the right control stick of gamepad1 is pushed more than
                calculester.backLeft.setPower(-driveSpeed);   //20% backward or the down Dpad is pressed, the robot drives backward.
                calculester.frontRight.setPower(-driveSpeed);
                calculester.backRight.setPower(-driveSpeed);
            } else if (gamepad1.dpad_right) {
                calculester.frontLeft.setPower(driveSpeed);  //If the right control stick of gamepad1 is pushed more than
                calculester.backLeft.setPower(-driveSpeed);   //20% right or the right Dpad is pressed, the robot drives right.
                calculester.frontRight.setPower(-driveSpeed);
                calculester.backRight.setPower(driveSpeed);
            } else if (gamepad1.dpad_left) {
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

            if (gamepad1.a) {
                calculester.wobbleGoalArm.setTargetPosition(-75);    //1260 == 1 revolution of arm
                calculester.wobbleGoalArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                calculester.wobbleGoalArm.setPower(driveSpeed);
            } else if (gamepad1.b) {
                calculester.wobbleGoalArm.setTargetPosition(-1200);
                calculester.wobbleGoalArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                calculester.wobbleGoalArm.setPower(driveSpeed);
            } else if (gamepad1.y) {
                calculester.wobbleGoalArm.setTargetPosition(-2500);
                calculester.wobbleGoalArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                calculester.wobbleGoalArm.setPower(driveSpeed);
            }

            if (gamepad1.x && !xButtonPreviousState) {
                xButtonPreviousState = true;

                if (endEffectorGateState == 0.1) {
                    endEffectorGateState = 0.6;
                } else {
                    endEffectorGateState = 0.1;
                }
                calculester.endEffector.setPosition(endEffectorGateState);

                telemetry.addLine("Case One");
                telemetry.update();

            } else if (!gamepad1.x && xButtonPreviousState) {
                xButtonPreviousState = false;

                telemetry.addLine("Case Two");
                telemetry.update();
            }


            if (translation > 0.2 || translation < -0.2) {
                calculester.translation.setPower(translation);
            } else {
                calculester.translation.setPower(0);
            }

            if (gamepad1.right_stick_y > 0.2) {
                calculester.intake.setPower(-0.6);
            } else if (gamepad1.right_stick_y < -0.2) {
                calculester.intake.setPower(0.6);
            } else {
                calculester.intake.setPower((0.0));
            }

            if (gamepad1.left_trigger > 0.2) {
                calculester.flywheel.setPower(-1.0);
            } else {
                calculester.flywheel.setPower(0.0);
            }

            if (timer.time() > 100) {
                speed = (double) (-calculester.flywheel.getCurrentPosition() - prevPosition) / timer.time();
                prevPosition = -calculester.flywheel.getCurrentPosition();
                timer.reset();
            }


            //telemetry.addData("cm", "%.2f cm", calculester.rearDistanceSensor.getDistance(DistanceUnit.CM));
            //telemetry.addData("cm", "%.2f cm", calculester.rightDistanceSensor.getDistance(DistanceUnit.CM));
            telemetry.addData("Flywheel RPM: ", ((speed/28)) * 60000);
            telemetry.update();
        }
    }
}

