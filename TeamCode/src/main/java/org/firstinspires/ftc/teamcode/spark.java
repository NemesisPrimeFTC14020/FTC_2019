package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
@Disabled
@TeleOp(name = "spark", group = "Linear Opmode")
public class spark extends LinearOpMode {
    public Methods methods = new Methods();

    public void runOpMode() {
        methods.Hardware.initHardware(this);
        waitForStart();

    }
}