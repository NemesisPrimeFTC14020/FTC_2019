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

@Disabled
@Autonomous(name = "Tensorflow", group = "Concept")

public class newtensorBoibutnewer extends LinearOpMode {
    public Methods methods = new Methods();
    @Override
    public void runOpMode() {
        final int i = 229;
        final int t = 1042;
        final int r = 381;
        final int segments = 2;
        double f = 0.3;
        double d = (t - i - r)*(1 + f);
        double drive = d/segments;
        methods.Hardware.initHardware(this);
        methods.initVuforia(this);
        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            methods.initTfod(this);
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }
        waitForStart();
        if (opModeIsActive()) {
            if (methods.variables.tfod != null) {
                methods.variables.tfod.activate();
            }
            while (opModeIsActive()) {
                methods.encoderDrive(methods.variables.DRIVE_SPEED, i, i, 20, this);
                if (methods.variables.tfod != null) {
                    List<Recognition> updatedRecognitions = methods.variables.tfod.getUpdatedRecognitions();
                            methods.Hardware.leftDrive.setPower(0.1);
                    methods.Hardware.rightDrive.setPower(-0.1);
                    methods.encoderDrive(methods.variables.DRIVE_SPEED, i, i ,10, this);
                    double angleboi = methods.goldAngle(this);
                    methods.gyroTurn(methods.variables.DRIVE_SPEED, angleboi, this);
                    methods.encoderDrive(methods.variables.DRIVE_SPEED, drive, drive ,10, this);
                    angleboi = methods.goldAngle(this);
                    methods.gyroTurn(methods.variables.DRIVE_SPEED, angleboi, this);
                    methods.encoderDrive(methods.variables.DRIVE_SPEED, drive, drive ,10, this);
                    telemetry.update();

                    }
                }
            }
        }
    }
