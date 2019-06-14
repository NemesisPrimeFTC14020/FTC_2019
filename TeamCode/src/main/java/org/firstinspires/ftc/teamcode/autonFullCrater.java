package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.util.List;
@Disabled
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
        if (methods.getHeading(this) > 195 && methods.getHeading(this) < 175) {
            methods.variables.goldPlacement = "center";
        }
        methods.encoderDrive(methods.variables.DRIVE_SPEED, 900, 900, 4, this);
        if (methods.variables.goldPlacement != "center") {
            methods.gyroTurnTo(methods.variables.BIG_TURN, 180, this);
        }
        methods.encoderDrive(methods.variables.DRIVE_SPEED, 200, 200, 5, this);
        methods.Hardware.Markerservo.setPosition(1);
        sleep(1000);
        telemetry.addLine("opmodefinished");
        telemetry.addData("heading:", methods.getHeading(this));
        telemetry.update();
        }
    }
