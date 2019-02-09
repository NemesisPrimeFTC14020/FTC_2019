package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="RoverRuckusSingle", group="Linear Opmode")
public class teleOpSingle extends LinearOpMode {
    public Methods methods = new Methods();

    double pastleftPower = 0;
    double pastrightPower = 0;
    double leftdiff;
    double rightdiff;

    @Override
    public void runOpMode() {
        methods.Hardware.initHardware(this);
        waitForStart();
        methods.variables.runtime.reset();

        while (opModeIsActive()) {
            if (gamepad1.a) {
                methods.Hardware.elevatorDrive.setPower(1);
            } else if (gamepad1.b) {
                methods.Hardware.elevatorDrive.setPower(-1);
            } else {
                methods.Hardware.elevatorDrive.setPower(0);

                if (gamepad1.right_trigger > 0.05) {
                    methods.Hardware.linearSlide.setPower(gamepad1.right_trigger);
                } else if (gamepad1.left_trigger > 0.05) {
                    methods.Hardware.linearSlide.setPower(-gamepad1.left_trigger);
                } else {
                    methods.Hardware.linearSlide.setPower(0);
                }

                methods.teleopDrive(gamepad1.left_stick_y, gamepad1.right_stick_x, methods.Hardware.leftDrive, methods.Hardware.rightDrive);

                
                leftdiff = Math.abs(Range.clip(gamepad1.left_stick_y + gamepad1.right_stick_x, -1, 1) - pastleftPower);
                rightdiff = Math.abs(Range.clip(gamepad1.left_stick_y - gamepad1.right_stick_x, -1, 1) - pastrightPower);
                pastleftPower = Range.clip(gamepad1.left_stick_y + gamepad1.right_stick_x, -1, 1);
                pastrightPower = Range.clip(gamepad1.left_stick_y - gamepad1.right_stick_x, -1, 1);

                telemetry.addData("elevatorPosition %7d", methods.Hardware.elevatorDrive.getCurrentPosition());
                //telemetry.addData("servoPosition %7d", methods.Hardware.intakeServo.getPosition());
                telemetry.addData("leftPower", methods.Hardware.leftDrive.getPower());
                telemetry.addData("rightPower", methods.Hardware.rightDrive.getPower());
                telemetry.update();
            }
        }
    }
}
