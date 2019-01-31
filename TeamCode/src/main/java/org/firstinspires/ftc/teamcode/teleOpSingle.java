package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="speed test", group="Linear Opmode")
public class teleOpSingle extends LinearOpMode {
    public Methods methods = new Methods();
    @Override
    public void runOpMode() {
        methods.Hardware.initHardware(this);
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
                methods.Hardware.elevatorDrive.setPower(1);
            } else if (gamepad1.b) {
                methods.Hardware.elevatorDrive.setPower(-1);
            } else {
                methods.Hardware.elevatorDrive.setPower(0);
            }
            if (gamepad1.right_trigger >= 0.2) {
                methods.Hardware.linearSlide.setPower(1);
            } else if (gamepad1.left_trigger >= 0.2) {
                methods.Hardware.linearSlide.setPower(-1);
            } else {
                methods.Hardware.linearSlide.setPower(0);
            } if (gamepad1.x) {
                methods.variables.servoPos = !methods.variables.servoPos;
            } if (methods.variables.servoPos) {
                methods.Hardware.intakeServo.setPosition(1);
            } else {
                methods.Hardware.intakeServo.setPosition(0);
            }
            methods.teleopInput(gamepad1.left_stick_y,gamepad1.right_stick_x,speedlimiter, methods.Hardware.leftDrive, methods.Hardware.rightDrive, this);
            telemetry.addData("elevatorPosition %7d", methods.Hardware.elevatorDrive.getCurrentPosition());
            telemetry.addData("servoPosition %7d", methods.Hardware.intakeServo.getPosition());
            telemetry.addData ("leftPower", methods.Hardware.leftDrive.getPower());
            telemetry.addData ("rightPower", methods.Hardware.rightDrive.getPower());
            telemetry.update();
        }
    }
}
