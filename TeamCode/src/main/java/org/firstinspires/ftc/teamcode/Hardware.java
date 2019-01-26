package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import org.firstinspires.ftc.teamcode.motorTesting;

public class Hardware {
        static void hardwareMap (motorTesting myOpMode) {
            myOpMode.leftDrive = myOpMode.hardwareMap.get(DcMotor.class, "left_drive");
            myOpMode.rightDrive = myOpMode.hardwareMap.get(DcMotor.class, "right_drive");
            myOpMode.elevatorDrive = myOpMode.hardwareMap.get(DcMotor.class, "elevator_drive");
            myOpMode.Markerservo = myOpMode.hardwareMap.get(Servo.class, "servo");
            myOpMode.intakeServo = myOpMode.hardwareMap.get (Servo.class, "intake");
            myOpMode.linearSlide = myOpMode.hardwareMap.get (DcMotor.class, "linear slider");
            //gyroParams
            BNO055IMU.Parameters parametersGyro = new BNO055IMU.Parameters();
            parametersGyro.angleUnit = BNO055IMU.AngleUnit.DEGREES;
            parametersGyro.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
            parametersGyro.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
            parametersGyro.loggingEnabled = true;
            parametersGyro.loggingTag = "IMU";
            parametersGyro.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
            myOpMode.gyro = myOpMode.hardwareMap.get(BNO055IMU.class, "imu");
            //motor polarity
            myOpMode.leftDrive.setDirection(DcMotor.Direction.REVERSE);
            myOpMode. rightDrive.setDirection(DcMotor.Direction.FORWARD);
            myOpMode.elevatorDrive.setDirection(DcMotor.Direction.FORWARD);
            myOpMode.linearSlide.setDirection(DcMotor.Direction.FORWARD);
            myOpMode.elevatorDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            myOpMode.elevatorDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            myOpMode.gyro.initialize(parametersGyro);
            int cameraMonitorViewId = myOpMode.hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", myOpMode.hardwareMap.appContext.getPackageName());
        }
    //Variables
    DcMotor leftDrive = null;
    DcMotor rightDrive = null;
    BNO055IMU gyro;
    Servo markerServo;
    DcMotor elevatorDrive = null;
    Servo Markerservo = null;
    Servo intakeServo = null;
    DcMotor linearSlide = null;
    //gyroParams
}
