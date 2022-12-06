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

package org.firstinspires.ftc.teamcode.drive.opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.firstinspires.ftc.teamcode.drive.HardwareConfig;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;


@TeleOp(name="InteractiveFunk", group="Iterative Opmode")

public class Interactive extends OpMode
{
    private HardwareConfig config = new HardwareConfig();
    private static final double DEADZONE = 0.1;

    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");
        config.init(hardwareMap);
        config.setMotorBehave();
        config.liftEncoder();
        config.openServos();
        telemetry.addData("Status", "Initialized");

    }

    @Override
    public void init_loop() {
    }

    @Override
    public void start() {
    }

    @Override
    public void loop() {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);


        if (gamepad1.dpad_up){
            config.liftUp();
        }
        if (gamepad1.right_trigger>0){
            config.liftAdjust(gamepad1.right_trigger); 
        }
        if (gamepad1.left_trigger>0){
            config.liftAdjust(-gamepad1.left_trigger);
        }
        if (gamepad1.dpad_down) {
            config.liftDown();
        }
        if (gamepad1.right_bumper){
            config.openServos();
        }
        if (gamepad1.left_bumper){
            config.closeServos();
        }
        if (gamepad1.dpad_right){
            config.smallLift();
        }
        if (gamepad1.dpad_left){
            config.mediumLift();
        }

        drive.setWeightedDrivePower(new Pose2d(
                -gamepad1.left_stick_y,
                -gamepad1.left_stick_x,
                -gamepad1.right_stick_x
            )
        );
        drive.update();


        telemetry.addData("Lift", "target: %d; current: %d", config.lift.getTargetPosition(), config.lift.getCurrentPosition());
        telemetry.addData("Red: ", config.colorSensor.red());
        telemetry.addData("Blue: ", config.colorSensor.blue());
        telemetry.addData("Green: ", config.colorSensor.green());
        telemetry.addData("Color: ", config.getColor());
        telemetry.addData("Encoders", "x: %d, y: %d", config.parallelEncoder.getCurrentPosition(), config.perpEncoder.getCurrentPosition());
    }
    @Override
    public void stop() {
    }

}
