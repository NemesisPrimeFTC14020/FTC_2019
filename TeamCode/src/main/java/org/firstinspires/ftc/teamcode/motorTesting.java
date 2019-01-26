package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="motor testing", group="Linear Opmode")
public class motorTesting extends LinearOpMode {
    DcMotor leftDrive = null;
    DcMotor rightDrive = null;
    BNO055IMU gyro;
    Servo markerServo;
    DcMotor elevatorDrive = null;
    Servo Markerservo = null;
    Servo intakeServo = null;
    DcMotor linearSlide = null;
    @Override
    public void runOpMode() {
        DcMotor motorboi = null;
        motorboi = hardwareMap.get(DcMotor.class, "testMotor");
        motorboi.setDirection(DcMotor.Direction.FORWARD);
        waitForStart();
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
