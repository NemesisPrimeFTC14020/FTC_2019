package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.util.List;

@Autonomous(name = "autonFullCrater", group = "Minibot")
public class autonFullCrater extends LinearOpMode {

    //public variables variables = new variables();
    public Methods methods = new Methods();
    //public Hardware Hardware = new Hardware();
    public void runOpMode() {
        methods.Hardware.initHardware(this);
        methods.initVuforia(this);
        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            methods.initTfod(this);
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }
        telemetry.addLine("start ready");
        telemetry.update();
        waitForStart();
//        methods.elevatorDrive(1, -10, 4, this);
//        methods.encoderDrive(Variables.DRIVE_SPEED, 100, 100, 4, this);
//        methods.elevatorDrive(1, 10, 4, this);
        methods.encoderDrive(Variables.DRIVE_SPEED, -50, -50, 4, this);
            if (methods.variables.tfod != null) {
                methods.variables.tfod.activate();
            }
            telemetry.addLine("tfodactivated");
            telemetry.update();
            sleep(1000);
            if (methods.isThereGold(this)) {
                methods.Hardware.leftDrive.setPower(0);
                methods.Hardware.rightDrive.setPower(0);
                telemetry.addLine("there is gold");
                telemetry.update();
                methods.gyroTurn(methods.variables.BIG_TURN, methods.goldAngle(this), this);
            } else {
                telemetry.addLine("no Gold");
                telemetry.update();
                methods.gyroTurn(Variables.BIG_TURN, -30, this);
                sleep(1000);
                if (methods.isThereGold(this)) {
                    methods.Hardware.leftDrive.setPower(0);
                    methods.Hardware.rightDrive.setPower(0);
                    telemetry.addLine("there is gold");
                    telemetry.update();
                    sleep(1000);
                    methods.gyroTurn(methods.variables.BIG_TURN, methods.goldAngle(this), this);
                } else {
                    telemetry.addLine("there is no gold");
                    telemetry.update();
                    methods.Hardware.leftDrive.setPower(-.05);
                    methods.Hardware.rightDrive.setPower(.05);
                    while (opModeIsActive() && methods.variables.sampled == false) {

                        if (methods.isThereGold(this)) {
                            methods.Hardware.leftDrive.setPower(0);
                            methods.Hardware.rightDrive.setPower(0);
                            telemetry.addLine("goldSeen");
                            telemetry.update();
                            methods.variables.sampled = true;
                            methods.gyroTurn(methods.variables.BIG_TURN, methods.goldAngle(this), this);
                        }
                    }
                }
            }
            telemetry.addLine("end ofdetections");
            telemetry.update();
            sleep(500);
            methods.encoderDrive(methods.variables.DRIVE_SPEED, 850, 850, 10, this);
//            if (methods.getHeading(this) <= 185 && methods.getHeading(this) >= 175) {
//                methods.gyroTurn(methods.variables.BIG_TURN, -20, this);
//                methods.encoderDrive(methods.variables.DRIVE_SPEED, 50, 50, 10, this);
//            }
            if (methods.variables.tfod != null) {
                telemetry.addLine("tfod Shutdown");
                telemetry.update();
                methods.variables.tfod.shutdown();
            }
        telemetry.addLine("opmodefinished");
        telemetry.update();
        }
    }
