package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="encoderTesting", group="Linear Opmode")
public class encoderTesting extends LinearOpMode {
    public Methods methods = new Methods();

    public void runOpMode() {
        methods.Hardware.initHardware(this);
        waitForStart();
        methods.encoderDrive(methods.variables.DRIVE_SPEED, 300, 300, 10, this);
    }
}
