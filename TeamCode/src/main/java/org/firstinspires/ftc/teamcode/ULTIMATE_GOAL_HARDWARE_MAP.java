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
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) CARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

//import org.firstinspires.ftc.robotcontroller.external.samples.SensorMRRangeSensor;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;


public class ULTIMATE_GOAL_HARDWARE_MAP
{
    /* Public OpMode members. */
    public DcMotor  frontLeft  = null;
    public DcMotor  frontRight = null;
    public DcMotor  backLeft   = null;
    public DcMotor  backRight  = null;
    public DcMotor wobbleGoalArm = null;
    public DcMotor translation = null;
    public DcMotor flywheel = null;
    public DcMotor intake = null;
    public Servo endEffector = null;
    public Servo ringPusher = null;

    public ModernRoboticsI2cRangeSensor rearDistanceSensor = null;
    public ModernRoboticsI2cRangeSensor rightDistanceSensor = null;


    /* local OpMode members. */
    HardwareMap hwMap           =  null;


    /* Constructor */
    public ULTIMATE_GOAL_HARDWARE_MAP(){

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        frontLeft  = hwMap.get(DcMotor.class, "front_left");
        frontRight = hwMap.get(DcMotor.class, "front_right");
        backLeft   = hwMap.get(DcMotor.class, "back_left");
        backRight  = hwMap.get(DcMotor.class, "back_right");
        wobbleGoalArm  = hwMap.get(DcMotor.class, "wobble_goal_arm");
        translation = hwMap.get(DcMotor.class, "translation");
        flywheel  = hwMap.get(DcMotor.class, "flywheel");
        intake  = hwMap.get(DcMotor.class, "intake");
        endEffector = hwMap.get(Servo.class, "end_effector");
        ringPusher = hwMap.get(Servo.class, "ring_pusher");

        rearDistanceSensor = hwMap.get(ModernRoboticsI2cRangeSensor.class, "rear_distance_sensor");
        rightDistanceSensor = hwMap.get(ModernRoboticsI2cRangeSensor.class, "right_distance_sensor");

        frontLeft.setDirection(DcMotor.Direction.REVERSE); // Reverse direction of left drive motors.
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.FORWARD);

        // Set all motors to zero power
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
        wobbleGoalArm.setPower(0);
        translation.setPower(0);
        flywheel.setPower(0);
        // Set all motors to run without encoders.

        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        wobbleGoalArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        translation.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        flywheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
}
