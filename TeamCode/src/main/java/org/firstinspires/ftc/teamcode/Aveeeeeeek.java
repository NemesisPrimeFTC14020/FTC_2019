package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="AVEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEK", group="Linear Opmode")
public class Aveeeeeeek extends LinearOpMode {
    public Variables Variables = new Variables();
    public Methods Methods = new Methods();
    public Hardware Hardware = new Hardware();
    @Override
    public void runOpMode() {
        Hardware.hardwareMap();
        waitForStart();
        Variables.runtime.reset();
        DcMotor motorboi = null;
        motorboi = hardwareMap.get(DcMotor.class, "motorboi");
        motorboi.setDirection(DcMotor.Direction.FORWARD);
       // double speedlimiter = 0;
        while (opModeIsActive()) {
           if(gamepad1.a){
               motorboi.setPower(1);
               telemetry.addLine("Forward");
           }
           else if(gamepad1.b){
               motorboi.setPower(-1);
               telemetry.addLine("Backward");
           }
        }
    }
}
