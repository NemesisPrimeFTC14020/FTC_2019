package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
@Disabled
@TeleOp(name="encoderTesting", group="Linear Opmode")
public class encoderTesting extends LinearOpMode {
    public Methods methods = new Methods();

    public void runOpMode() {
        methods.Hardware.initHardware(this);
        waitForStart();
        methods.encoderDrive(methods.variables.DRIVE_SPEED, 470, 470, 10, this);
    }
}
