package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="Motor Testing", group="Linear Opmode")
public class motorTesting extends LinearOpMode {
    public Variables Variables = new Variables();
    public Methods Methods = new Methods();
    public Hardware Hardware = new Hardware();
    @Override
    public void runOpMode() {
        Hardware.hardwareMap(this);
        waitForStart();
        Variables.runtime.reset();
        DcMotor motor = null;
        motor = hardwareMap.get(DcMotor.class, "motorboi");
        motor.setDirection(DcMotor.Direction.FORWARD);
       // double speedlimiter = 0;
        while (opModeIsActive()) {
           if(gamepad1.a){
               motor.setPower(1);
               telemetry.addLine("Forward");
           }
           else if(gamepad1.b){
               motor.setPower(-1);
               telemetry.addLine("Backward");
           }
        }
    }
}
