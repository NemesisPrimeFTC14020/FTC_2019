package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;

public class Hardware {

        public void hardwareMap (LinearOpMode myOpMode) {
            leftDrive = myOpMode.hardwareMap.get(DcMotor.class, "left_drive");
            rightDrive = myOpMode.hardwareMap.get(DcMotor.class, "right_drive");
            elevatorDrive = myOpMode.hardwareMap.get(DcMotor.class, "elevator_drive");
            Markerservo = myOpMode.hardwareMap.get(Servo.class, "servo");
            // intakeServo = myOpMode.hardwareMap.get (Servo.class, "intake");
            linearSlide = myOpMode.hardwareMap.get (DcMotor.class, "linear_drive");
            //gyroParams
            BNO055IMU.Parameters parametersGyro = new BNO055IMU.Parameters();
            parametersGyro.angleUnit = BNO055IMU.AngleUnit.DEGREES;
            parametersGyro.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
            parametersGyro.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
            parametersGyro.loggingEnabled = true;
            parametersGyro.loggingTag = "IMU";
            parametersGyro.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
            gyro = myOpMode.hardwareMap.get(BNO055IMU.class, "imu");
            //motor polarity
            leftDrive.setDirection(DcMotor.Direction.REVERSE);
            rightDrive.setDirection(DcMotor.Direction.FORWARD);
            elevatorDrive.setDirection(DcMotor.Direction.FORWARD);
            linearSlide.setDirection(DcMotor.Direction.FORWARD);
            elevatorDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            elevatorDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            gyro.initialize(parametersGyro);
        }
    //variables
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
