package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="encoderTest", group="Linear Opmode")
public class encoderTesting extends LinearOpMode {
    public Hardware hardware = new Hardware();
    public Methods methods = new Methods();
    public Variables variables = new Variables();
    public void runOpMode() {
        hardware.hardwareMap(this);
        waitForStart();
        methods.encoderDrive(variables.DRIVE_SPEED, 300, 300, 10);
    }
}
