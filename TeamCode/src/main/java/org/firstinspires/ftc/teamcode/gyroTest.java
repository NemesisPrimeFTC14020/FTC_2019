package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="EXAMPLE", group="Linear Opmode")
public class gyroTest extends LinearOpMode {
    public Methods methods = new Methods();
    public void runOpMode() {
        methods.Hardware.initHardware(this);
        waitForStart();
        methods.gyroTurn(methods.variables.TURN_SPEED, 90, this);
        while(true){
            methods.getHeading(this);
            telemetry.update();

        }
    }
}
