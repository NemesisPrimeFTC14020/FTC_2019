package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.Methods;
import org.firstinspires.ftc.teamcode.Variables;
@TeleOp(name="EXAMPLE", group="Linear Opmode")
public class exampleClass extends LinearOpMode {
    public Methods methods = new Methods();
    public void runOpMode() {
        methods.hardware.hardwareMap(this);
        waitForStart();
    }
}
