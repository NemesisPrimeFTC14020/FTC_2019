package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="EXAMPLE", group="Linear Opmode")
public class exampleClass extends LinearOpMode {
    public Methods methods = new Methods();
    public void runOpMode() {
        methods.Hardware.initHardware(this);
        waitForStart();
    }
}
