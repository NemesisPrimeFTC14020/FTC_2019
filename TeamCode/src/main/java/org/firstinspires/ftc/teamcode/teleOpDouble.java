package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
@Disabled
@TeleOp(name="RoverRuckusDouble", group="Linear Opmode")
public class teleOpDouble extends LinearOpMode {
    public Methods methods = new Methods();

    double pastleftPower = 0;
    double pastrightPower = 0;
    double leftdiff;
    double rightdiff;
    double drive = 0;
    double steer = 0;
    double speedChange = 1;
    @Override
    public void runOpMode() {
        methods.Hardware.initHardware(this);
        waitForStart();
        methods.variables.runtime.reset();

        while (opModeIsActive()) {
            if (gamepad1.a) {
                methods.Hardware.elevatorDrive.setPower(1);
            } else if (gamepad2.a) {
                methods.Hardware.elevatorDrive.setPower(1);
            } else if (gamepad1.b) {
                methods.Hardware.elevatorDrive.setPower(-1);
            } else if (gamepad2.b) {
                methods.Hardware.elevatorDrive.setPower(-1);
            } else {
                methods.Hardware.elevatorDrive.setPower(0);
            }

                if (gamepad1.right_trigger > 0.05) {
                    methods.Hardware.linearSlide.setPower(gamepad1.right_trigger);
                } else if (gamepad2.right_trigger > 0.05) {
                    methods.Hardware.linearSlide.setPower(gamepad1.right_trigger);
                }else if (gamepad1.left_trigger > 0.05) {
                    methods.Hardware.linearSlide.setPower(-gamepad1.left_trigger);
                } else if (gamepad2.left_trigger > 0.05) {
                methods.Hardware.linearSlide.setPower(-gamepad1.left_trigger);
            } else {
                    methods.Hardware.linearSlide.setPower(0);
                }
                if (gamepad1.y && speedChange == 1) {
                    speedChange = .4;
                } else if (gamepad1.y) {
                    speedChange = 1;
                }
                drive = gamepad1.left_stick_y >= 0 ? Math.pow(speedChange * gamepad1.left_stick_y, 2) : Math.pow(speedChange * gamepad1.left_stick_y, 2) * -1.0;
                steer = gamepad1.right_stick_x >= 0 ? Math.pow(speedChange * gamepad1.right_stick_x, 2) : Math.pow(speedChange * gamepad1.right_stick_x, 2) * -1.0;
                methods.teleopDrive(drive, steer, methods.Hardware.leftDrive, methods.Hardware.rightDrive);
                telemetry.addData("elevatorPosition %7d", methods.Hardware.elevatorDrive.getCurrentPosition());
                //telemetry.addData("servoPosition %7d", methods.Hardware.intakeServo.getPosition());
                if (speedChange == 1) {
                    telemetry.addLine("fastMode");
                } else {
                    telemetry.addLine("sensitive");
                }
                telemetry.addData("leftPower", methods.Hardware.leftDrive.getPower());
                telemetry.addData("rightPower", methods.Hardware.rightDrive.getPower());
                telemetry.addData("drive", drive);
                telemetry.addData("steer", steer);
                telemetry.addData("leftStick", gamepad1.left_stick_y);
                telemetry.addData("rightStick", gamepad1.right_stick_x);
                telemetry.update();
            }
        }
    }
