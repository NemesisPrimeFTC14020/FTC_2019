package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;
@TeleOp(name="CADDRIVe", group="Linear Opmode")
public class justDriveTrain extends LinearOpMode {
    DcMotor leftDrive;
    DcMotor rightDrive;
    double drive = 0;
    double steer = 0;
    double speedChange = 1;
    public void runOpMode() {
        leftDrive = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");
        while (opModeIsActive()) {
            if (gamepad1.y && speedChange == 1) {
                speedChange = .4;
            } else if (gamepad1.y) {
                speedChange = 1;
            }
            drive = gamepad1.left_stick_y >= 0 ? Math.pow(speedChange * gamepad1.left_stick_y, 2) : Math.pow(speedChange * gamepad1.left_stick_y, 2) * -1.0;
            steer = gamepad1.right_stick_x >= 0 ? Math.pow(speedChange * gamepad1.right_stick_x, 2) : Math.pow(speedChange * gamepad1.right_stick_x, 2) * -1.0;
            if (speedChange == 1) {
                telemetry.addLine("fastMode");
            } else {
                telemetry.addLine("sensitive");
            }
            double leftPower = Range.clip(drive + steer, -1, 1);
            double rightPower = Range.clip(drive - steer, -1, 1);
            leftDrive.setPower(leftPower);
            rightDrive.setPower(rightPower);
            telemetry.addData("leftPower", leftDrive.getPower());
            telemetry.addData("rightPower", rightDrive.getPower());
            telemetry.addData("drive", drive);
            telemetry.addData("steer", steer);
            telemetry.addData("leftStick", gamepad1.left_stick_y);
            telemetry.addData("rightStick", gamepad1.right_stick_x);
            telemetry.update();
        }
    }
}
