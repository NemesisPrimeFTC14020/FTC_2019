package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "spark", group = "Linear Opmode")
public class spark extends LinearOpMode {
    public Hardware hardware = new Hardware();
    public Methods methods = new Methods();
    public Variables variables = new Variables();

    public void runOpMode() {
        hardware.hardwareMap(this);
        waitForStart();

    }
}