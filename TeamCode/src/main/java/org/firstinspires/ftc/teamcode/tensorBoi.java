/* Copyright (c) 2018 FIRST. All rights reserved.
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

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.util.List;


@Autonomous(name = "Tensorflow", group = "Concept")

public class tensorBoi extends LinearOpMode {
    public Methods methods = new Methods();
    @Override
    public void runOpMode() {
        methods.Hardware.initHardware(this);
        methods.initVuforia(this);
        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            methods.initTfod(this);
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
            telemetry.update();
        }
        telemetry.addLine("ready start");
        telemetry.update();
        waitForStart();
            if (methods.variables.tfod != null) {
                methods.variables.tfod.activate();
                telemetry.addLine("tfod On");
            }
            sleep(1000);
            if (methods.isThereGold(this)) {
                methods.Hardware.leftDrive.setPower(0);
                methods.Hardware.rightDrive.setPower(0);
                telemetry.addLine("there is gold");
                sleep(250);
                methods.gyroTurn(methods.variables.BIG_TURN, methods.goldAngle(this), this);
                telemetry.addLine("end ofdetections");
                telemetry.update();
                sleep(500);
                methods.encoderDrive(methods.variables.DRIVE_SPEED, 850, 850, 10, this);
//        if (methods.getHeading(this) <= 185 && methods.getHeading(this) >= 175) {
//            methods.gyroTurn(methods.variables.BIG_TURN, -20, this);
//            methods.encoderDrive(methods.variables.DRIVE_SPEED, 50, 50, 10, this);
//        }
                //methods.Hardware.markerServo.setPosition(1.0);
                sleep(1000);
            } else {
                telemetry.addLine("no Gold");
                telemetry.update();
                methods.gyroTurnTo(Variables.BIG_TURN, 220, this);
                sleep(500);
                if (methods.isThereGold(this)) {
                    telemetry.addLine("there is gold");
                    telemetry.update();
                    sleep(500);
                    methods.Hardware.leftDrive.setPower(0);
                    methods.Hardware.rightDrive.setPower(0);
                    methods.gyroTurn(methods.variables.BIG_TURN, methods.goldAngle(this), this);
                    telemetry.addLine("end ofdetections");
                    telemetry.update();
                    methods.encoderDrive(methods.variables.DRIVE_SPEED, 850, 850, 10, this);
//        if (methods.getHeading(this) <= 185 && methods.getHeading(this) >= 175) {
//            methods.gyroTurn(methods.variables.BIG_TURN, -20, this);
//            methods.encoderDrive(methods.variables.DRIVE_SPEED, 50, 50, 10, this);
//        }
                   // methods.Hardware.markerServo.setPosition(1.0);
                    sleep(1000);
                } else {
                    telemetry.addLine("there is no gold");
                    telemetry.update();
                    methods.gyroTurnTo(Variables.BIG_TURN, 140, this);
                    while (opModeIsActive() && methods.variables.sampled == false) {
                        if (methods.isThereGold(this)) {
                            methods.Hardware.leftDrive.setPower(0);
                            methods.Hardware.rightDrive.setPower(0);
                            telemetry.addLine("goldSeen");
                            telemetry.update();
                            methods.variables.sampled = true;
                            sleep(250);
                            methods.gyroTurn(methods.variables.BIG_TURN, methods.goldAngle(this), this);
                            telemetry.addLine("end ofdetections");
                            telemetry.update();
                            methods.encoderDrive(methods.variables.DRIVE_SPEED, 850, 850, 10, this);
//        if (methods.getHeading(this) <= 185 && methods.getHeading(this) >= 175) {
//            methods.gyroTurn(methods.variables.BIG_TURN, -20, this);
//            methods.encoderDrive(methods.variables.DRIVE_SPEED, 50, 50, 10, this);
//        }
                           // methods.Hardware.markerServo.setPosition(1.0);
                            sleep(1000);
                        }
                    }
                }
            }
        if (methods.variables.tfod != null) {
            methods.variables.tfod.shutdown();
        }
        if (methods.variables.tfod != null) {
            telemetry.addLine("tfod Shutdown");
            telemetry.update();
            methods.variables.tfod.shutdown();
        }
        methods.Hardware.Markerservo.setPosition(1);
        sleep(1000);
        telemetry.addLine("opmodefinished");
        telemetry.update();
    }
}