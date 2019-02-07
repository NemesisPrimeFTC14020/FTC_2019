package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="NW teleop", group="A")
public class teleop extends LinearOpMode {

    public Methods methods = new Methods();
    public TelePad gamepad = new TelePad();

    @Override
    public void runOpMode() {
        methods.Hardware.initHardware(this);
        waitForStart();
        methods.variables.runtime.reset();

        while (opModeIsActive()) {

            gamepad.Gamepad(this);

            switch (gamepad.elevator) {
                case "latch":
                    methods.Hardware.elevatorDrive.setPower(1);
                    break;
                case "unlatch":
                    methods.Hardware.elevatorDrive.setPower(-1);
                    break;
                case "none":
                    methods.Hardware.elevatorDrive.setPower(0);
                    break;
            }
            switch (gamepad.linearSlide) {
                case "forward":
                    methods.Hardware.linearSlide.setPower(1);
                    break;
                case "backward":
                    methods.Hardware.linearSlide.setPower(-1);
                    break;
                case "none":
                    methods.Hardware.linearSlide.setPower(0);
                    break;
            }

            methods.teleopInput(gamepad1.left_stick_y, gamepad1.right_stick_x, methods.Hardware.leftDrive, methods.Hardware.rightDrive, this);
            telemetry.addData("elevatorPosition %7d", methods.Hardware.elevatorDrive.getCurrentPosition());
            // telemetry.addData("servoPosition %7d", methods.Hardware.intakeServo.getPosition());
            telemetry.addData("leftPower", methods.Hardware.leftDrive.getPower());
            telemetry.addData("rightPower", methods.Hardware.rightDrive.getPower());
            telemetry.update();
        }
    }
}