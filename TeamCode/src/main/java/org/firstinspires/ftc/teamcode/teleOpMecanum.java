package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="teleOpMecanum", group="new")
public class teleOpMecanum extends LinearOpMode {

    public Methods methods = new Methods();
    public hardwareMecanum Hardware = new hardwareMecanum();
    double speedChange = 1;

    public void runOpMode() {
        methods.Hardware.initHardware(this);
        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.y && speedChange == 1) {
                speedChange = 0.4;
            } else if (gamepad1.y && speedChange == 0.4) {
                speedChange = 1;
            }

            double x = Math.pow(speedChange * gamepad1.left_stick_x, 2);
            double y = Math.pow(speedChange * gamepad1.left_stick_y, 2);
            double rotleft = -Math.pow(speedChange * gamepad1.left_trigger, 2);
            double rotright = Math.pow(speedChange * gamepad1.right_trigger, 2);
            double rotation = rotleft + rotright;
            double gyroAngle = methods.Hardware.gyro.getAngularOrientation().firstAngle;
            methods.holonomicDrive(x, y, rotation, gyroAngle, Hardware.frontLeft, Hardware.frontRight, Hardware.backLeft, Hardware.backRight);
        }
    }
}
