package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import org.firstinspires.ftc.teamcode.Methods;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Disabled
@TeleOp(name="touchtest", group="Linear Opmode")
public class touchTest extends LinearOpMode {
    public Methods methods = new Methods();
    public void runOpMode() {
        waitForStart();
        while(opModeIsActive()) {
            methods.Hardware.initHardware(this);
            if(methods.Hardware.digitalTouch.getState()){
                methods.Hardware.linearSlide.setPower(-1);
            } else{
                methods.Hardware.linearSlide.setPower(0);
            }
        }
    }
}
