package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;

@TeleOp(name="touchtest", group="Linear Opmode")
public class touchTest extends LinearOpMode {
    public void runOpMode() {
        DcMotor motorguy = null;
        motorguy = hardwareMap.get(DcMotor.class, "motorguy");
        DigitalChannel digitalTouch;
        digitalTouch = hardwareMap.get(DigitalChannel.class, "sensor_digital");
        digitalTouch.setMode(DigitalChannel.Mode.INPUT);
        if(digitalTouch.getState()){
            motorguy.setPower(10);
        }

        else{
            motorguy.setPower(0);
        }

        waitForStart();
    }
}
