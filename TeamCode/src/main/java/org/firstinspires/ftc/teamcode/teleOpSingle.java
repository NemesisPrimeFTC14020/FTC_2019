package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="speed test", group="Linear Opmode")
public class teleOpSingle extends LinearOpMode {
    public Methods methods = new Methods();
    @Override
    public void runOpMode() {
        methods.hardware.hardwareMap(this);
        waitForStart();
        methods.variables.runtime.reset();
        double speedlimiter = 0;
        while (opModeIsActive()) {
            if (gamepad1.y) {
                speedlimiter = 1.0;
            } else {
                speedlimiter = 0.5;
            }
            if (gamepad1.a) {
                methods.hardware.elevatorDrive.setPower(1);
            } else if (gamepad1.b) {
                methods.hardware.elevatorDrive.setPower(-1);
            } else {
                methods.hardware.elevatorDrive.setPower(0);
            }
            if (gamepad1.right_trigger >= 0.2) {
                methods.hardware.linearSlide.setPower(1);
            } else if (gamepad1.left_trigger >= 0.2) {
                methods.hardware.linearSlide.setPower(-1);
            } else {
                methods.hardware.linearSlide.setPower(0);
            } if (gamepad1.x) {
                methods.variables.servoPos = !methods.variables.servoPos;
            } if (methods.variables.servoPos) {
                methods.hardware.intakeServo.setPosition(1);
            } else {
                methods.hardware.intakeServo.setPosition(0);
            }
            methods.teleopInput(gamepad1.left_stick_y,gamepad1.right_stick_x,speedlimiter, methods.hardware.leftDrive, methods.hardware.rightDrive);
            telemetry.addData("elevatorPosition %7d", methods.hardware.elevatorDrive.getCurrentPosition());
            telemetry.addData("servoPosition %7d", methods.hardware.intakeServo.getPosition());
            telemetry.addData ("leftPower", methods.hardware.leftDrive.getPower());
            telemetry.addData ("rightPower", methods.hardware.rightDrive.getPower());
            telemetry.update();
        }
    }
}
