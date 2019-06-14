package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
@Disabled
@Autonomous(name = "autonFullDepot", group = "Minibot")
public class autonFullDepot extends LinearOpMode {

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
            telemetry.update();
        }
        telemetry.addLine("ready start");
        telemetry.update();
        waitForStart();
        methods.elevatorDrive(1,-10,4,this);
        methods.encoderDrive(methods.variables.DRIVE_SPEED, 150, 150, 4, this);
        methods.elevatorDrive(1,10,4,this);
        if (methods.variables.tfod != null) {
            methods.variables.tfod.activate();
            telemetry.addLine("tfod On");
        }
        methods.isThereGold(this);
        sleep(1000);
        if (methods.isThereGold(this)) {
            methods.Hardware.leftDrive.setPower(0);
            methods.Hardware.rightDrive.setPower(0);
            telemetry.addLine("there is gold");
            sleep(250);
            methods.gyroTurn(methods.variables.BIG_TURN, methods.goldAngle(this), this);
            telemetry.addLine("end ofdetections");
            telemetry.update();
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
                    }
                }
            }
        }
        if (methods.variables.tfod != null) {
            telemetry.addLine("tfod Shutdown");
            telemetry.update();
            methods.variables.tfod.shutdown();
        }
        if (methods.getHeading(this) >= 195) {
            methods.variables.goldPlacement = "left";
            methods.gyroTurn(methods.variables.BIG_TURN, -5, this);
        } else if (methods.getHeading(this) <= 175) {
            methods.variables.goldPlacement = "right";
            methods.gyroTurn(methods.variables.BIG_TURN, 5, this);
        }
        if (methods.getHeading(this) > 175 && methods.getHeading(this) < 195) {
            methods.variables.goldPlacement = "center";
        }
        switch (methods.variables.goldPlacement) {
            case "right":
                methods.encoderDrive(methods.variables.DRIVE_SPEED, 900, 900, 5, this);
                methods.gyroTurnTo(methods.variables.BIG_TURN, 225, this);
                methods.encoderDrive(methods.variables.DRIVE_SPEED, 800, 800, 5, this);
                break;
            case "left":
                methods.encoderDrive(methods.variables.DRIVE_SPEED, 900, 900, 5, this);
                methods.gyroTurnTo(methods.variables.BIG_TURN, 135, this);
                methods.encoderDrive(methods.variables.DRIVE_SPEED, 800, 800, 5, this);
                break;
            case "center":
                methods.encoderDrive(methods.variables.DRIVE_SPEED, 1300, 1300, 5, this);
                break;
                default:
                    break;
        }
        methods.Hardware.Markerservo.setPosition(1);
        sleep(2000);
        methods.encoderDrive(methods.variables.DRIVE_SPEED, -400, -400, 5, this);
        telemetry.addLine("opmodefinished");
        telemetry.addData("heading:", methods.getHeading(this));
        telemetry.update();
        }
    }
