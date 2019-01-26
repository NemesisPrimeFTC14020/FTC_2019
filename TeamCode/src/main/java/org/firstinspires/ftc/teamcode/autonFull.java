//package org.firstinspires.ftc.teamcode;
//
//import com.qualcomm.hardware.bosch.BNO055IMU;
//import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.Servo;
//import com.qualcomm.robotcore.util.ElapsedTime;
//import com.qualcomm.robotcore.util.Range;
//import org.firstinspires.ftc.robotcore.external.ClassFactory;
//import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
//import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
//import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
//import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
//import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
//import java.util.List;
//
//@Autonomous(name="autonFull", group="Minibot")
//public class autonFull extends LinearOpMode {
//   // DigitalChannel digitalTouch;  // Hardware Device Object
//    private DcMotor leftDrive = null;
//    private DcMotor rightDrive = null;
//    private DcMotor elevatorDrive = null;
//    private Servo markerServo = null;
//    private ElapsedTime runtime = new ElapsedTime();
//    private BNO055IMU gyro;
//    String startQuadrant;
//
//    static final double COUNTS_PER_MOTOR_REV = 2240;    // we have Core Hex motors, creating a different count value
//    static final double COUNTS_PER_MOTOR_REV_ELAVATOR = 288;    // eg: TETRIX Motor Encoder
//    static final double DRIVE_GEAR_REDUCTION_ELAVATOR = 72/30;     // This is < 1.0 if geared UP
//    static final double DRIVE_GEAR_REDUCTION = 0.75;
//    static final double ENCODER_MAX = 1563;
//    static final double ELEVATION = 10.5;
//    static final double COUNTS_PER_INCH_ELEVATOR = (ENCODER_MAX/ELEVATION);
//    static final double WHEEL_DIAMETER_MM = 90;
//    static final double     COUNTS_PER_MM         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
//            (WHEEL_DIAMETER_MM * 3.1415);
//    static final double P_TURN_COEFF = 0.01;
//    static final double HEADING_THRESHOLD = 0.25;
//    static final double DRIVE_SPEED = 0.5; // higher power = faster traversal
//    static final double TURN_SPEED = 0.3; // higher power = faster traversal
//    static final double P_DRIVE_COEFF = 0.15;     // Larger is more responsive, but also less
//    //tensorflow
//    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
//    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
//    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";
//    private static final String VUFORIA_KEY = "AcWJXfv/////AAABmXTvMEsBOEYkiGqMsGZyklUBGfk5cSsLyBZx0YTUz4txj9n9lF3yHQWwQIFc+gC2pdWkKk3iWgbSza68dp0T2zqc+1sG6S5G6VAXxnNBUsH6rjRa+6p1kPIsiEQdezRl4m5VeATR5SzGECvIbIhtc3nWjjquoM/d8+R7QCMOrAPRwf9bhK6Ah2tgIuPkwVwkp+G1q4/qaFrLMGcDxRDlwNTFMZfnmTLt18Xe6Q54eDPb6Bw2MHmfUXSXbeqiahjFGQz40bh0TH3vAp47L88U3oXSo+YgV2TyT1PhQE7SUE71ucVNQ9PUM0OfIKFTtHy2MaXsE2dOsj+WAJG5J3iGO530Gaod7DtKZSCPC02SyADM";
//    private VuforiaLocalizer vuforia;
//    private TFObjectDetector tfod;
//
//
//
//
//    public void runOpMode() {
//        // get a reference to our digitalTouch object.
//        //digitalTouch = hardwareMap.get(DigitalChannel.class, "sensor_digital");
//
//        // set the digital channel to input.
//        //digitalTouch.setMode(DigitalChannel.Mode.INPUT);
//
//        initVuforia();
//
//        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
//            initTfod();
//        } else {
//            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
//        }
//
//        if (opModeIsActive()){
//            if (tfod != null) {
//                tfod.activate();
//            }
//        }
//        gyroTurn(TURN_SPEED, -20);
//        boolean tfodDone = false;
//
//        if (tfod != null) {
//            // getUpdatedRecognitions() will return null if no new information is available since
//            // the last time that call was made.
//            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
//            if (updatedRecognitions != null) {
//                telemetry.addData("# Object Detected", updatedRecognitions.size());
//
//                int goldMineralX = -1;
//
//                final int fov = 78;
//                final float d_per_pix = (float) 0.040625;
//                for (Recognition recognition : updatedRecognitions) {
//                    if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
//                        goldMineralX = (int) recognition.getLeft();
//                        telemetry.addLine("Skipper we did it, we found the gold mineral");
//                        telemetry.addData("Gold getLeft Value: ", recognition.getLeft());
//                        telemetry.addData("Gold getRight Value: ", recognition.getRight());
//                        float getleftval = (int) recognition.getLeft();
//                        float getrightval = (int) recognition.getRight();
//                        float p = (float) (0.5*(recognition.getLeft()+recognition.getRight()));
//                        telemetry.addData("Gold p Value that was calculated: ",p);
//                        float h = (float) (p-(0.5*800));
//                        telemetry.addData("h value calculated ", h);
//                        float angle = h* d_per_pix;
//                        telemetry.addData("the final angle lmao ", angle);
//                        telemetry.update();
//
//                        tfodDone = false;
//                        //turns at angle
//                        gyroTurn(TURN_SPEED, angle - 5);
//                        sleep(500);
//                        encoderDrive(DRIVE_SPEED, 350, 350, 5.0);
//
//
//                    }
//
//                }
//
//                telemetry.update();
//            }
//        }
//
//
//
//        BNO055IMU.Parameters parametersGyro = new BNO055IMU.Parameters();
//        parametersGyro.angleUnit = BNO055IMU.AngleUnit.DEGREES;
//        parametersGyro.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
//        parametersGyro.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
//        parametersGyro.loggingEnabled = true;
//        parametersGyro.loggingTag = "IMU";
//        parametersGyro.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
//        leftDrive = hardwareMap.get(DcMotor.class, "left_drive");
//        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");
//        elevatorDrive = hardwareMap.get(DcMotor.class, "elevator_drive");
//        markerServo = hardwareMap.get(Servo.class, "servo");
//        leftDrive.setDirection(DcMotor.Direction.FORWARD); // our right motor turns in the opposite direction so it has
//        rightDrive.setDirection(DcMotor.Direction.REVERSE); // to be reversed to create forward motion when told to go forward
//        elevatorDrive.setDirection(DcMotor.Direction.REVERSE);
//        gyro = hardwareMap.get(BNO055IMU.class, "imu");
//        waitForStart();
//        telemetry.addData ("heading:", getHeading());
//        telemetry.update();
////        elevatorDrive (1, 10, 8);// coming off the lander
//        gyro.initialize(parametersGyro);
//        markerServo.setPosition(1);
//        encoderDrive(DRIVE_SPEED, -100, -100, 5.0);
////        encoderDrive(DRIVE_SPEED, 1506.8, 1506.8, 60);// driving to depot
////        sleep(1500); // gives time for servo to do its thing
////        encoderDrive (DRIVE_SPEED, -100, -100, 4);
////        elevatorDrive (1, -10, 8);// coming off the lander
////        //encoderDrive(TURN_SPEED,-1000,-1000, 15);// go backwards from the depot
////        gyroTurnTo(TURN_SPEED, 45);// turning towards the
////        encoderDrive(DRIVE_SPEED,2203.2, 2203.2, 10);
//
//       /* if (digitalTouch.getState() == true) {
//            telemetry.addData("Digital Touch", "Is Not Pressed");
//        } else {
//            telemetry.addData("Digital Touch", "Is Pressed");
//        }*/
//
//        telemetry.update();
//    }
//    public void encoderDrive(double speed,
//                             double leftMM, double rightMM,
//                             double timeoutS) {
//        int newLeftTarget;
//        int newRightTarget;
//
//        // Ensure that the opmode is still active
//        if (opModeIsActive()) {
//
//            // Determine new target position, and pass to motor controller
//            newLeftTarget = leftDrive.getCurrentPosition() - (int) (leftMM * COUNTS_PER_MM);
//            newRightTarget = rightDrive.getCurrentPosition() - (int) (rightMM * COUNTS_PER_MM);
//            leftDrive.setTargetPosition(newLeftTarget);
//            rightDrive.setTargetPosition(newRightTarget);
//
//            // Turn On RUN_TO_POSITION
//            leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//            // reset the timeout time and start motion.
//            runtime.reset();
//            leftDrive.setPower(Math.abs(speed));
//            rightDrive.setPower(Math.abs(speed));
//
//            // keep looping while we are still active, and there is time left, and both motors are running.
//            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
//            // its target position, the motion will stop.  This is "safer" in the event that the robot will
//            // always end the motion as soon as possible.
//            // However, if you require that BOTH motors haveD finished their moves before the robot continues
//            // onto the next step, use (isBusy() || isBusy()) in the loop test.
//            while (opModeIsActive() &&
//                    (runtime.seconds() < timeoutS) &&
//                    (leftDrive.isBusy() && rightDrive.isBusy())) {
//
//                // Display it for the driver.
//                telemetry.addData("Path1", "Running to %7d :%7d", newLeftTarget, newRightTarget);
//                telemetry.addData("Path2", "Running at %7d :%7d",
//                        leftDrive.getCurrentPosition(),
//                        rightDrive.getCurrentPosition());
//                telemetry.update();
//            }
//
//            // Stop all motion;
//            leftDrive.setPower(0);
//            rightDrive.setPower(0);
//
//            // Turn off RUN_TO_POSITION
//            leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//            rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//
//            //  sleep(250);   // optional pause after each move
//        }
//    }
//    public void elevatorDrive(double speed, double elevatorDis, double timeoutS) {
//        int newTarget;
//
//        // Ensure that the opmode is still active
//        if (opModeIsActive()) {
//
//            // Determine new target position, and pass to motor controller
//            newTarget = elevatorDrive.getCurrentPosition() + (int) (elevatorDis * COUNTS_PER_INCH_ELEVATOR);
//            elevatorDrive.setTargetPosition(newTarget);
//
//            // Turn On RUN_TO_POSITION
//            elevatorDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//            // reset the timeout time and start motion.
//            runtime.reset();
//            elevatorDrive.setPower(Math.abs(speed));
//
//
//            // keep looping while we are still active, and there is time left, and both motors are running.
//            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
//            // its target position, the motion will stop.  This is "safer" in the event that the robot will
//            // always end the motion as soon as possible.
//            // However, if you require that BOTH motors have finished their moves before the robot continues
//            // onto the next step, use (isBusy() || isBusy()) in the loop test.
//            while (opModeIsActive() &&
//                    (runtime.seconds() < timeoutS) &&
//                    (elevatorDrive.isBusy())) {
//
//            }
//            // Stop all motion;
//            elevatorDrive.setPower(0);
//
//            // Turn off RUN_TO_POSITION
//            elevatorDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//
//        }
//    }
//    public void gyroTurnTo(double speed, double angle) {
//
//        // keep looping while we are still active, and not on heading.
//        while (opModeIsActive() && !onHeading(speed, angle, P_TURN_COEFF)) {
//            // Update telemetry & Allow time for other processes to run.
//            telemetry.update();
//        }
//    }
//    public void gyroTurn(double speed, double angle) {
//
//        // keep looping while we are still active, and not on heading.
//        double angleTarget;
//        angleTarget = getHeading() - angle;
//
//        while (opModeIsActive() && !onHeading(speed, angleTarget, P_TURN_COEFF)) {
//            // Update telemetry & Allow time for other processes to run.
//            telemetry.update();
//        }
//    }
//    boolean onHeading(double speed, double angle, double PCoeff) {
//        double error;
//        double steer;
//        boolean onTarget = false;
//        double leftSpeed;
//        double rightSpeed;
//
//        // determine turn power based on +/- error
//        error = getError(angle);
//
//        if (Math.abs(error) <= HEADING_THRESHOLD) {
//            steer = 0.0;
//            leftSpeed = 0.0;
//            rightSpeed = 0.0;
//            onTarget = true;
//        } else {
//            steer = getSteer(error, PCoeff);
//            rightSpeed = -(speed * steer);
//            leftSpeed = -rightSpeed;
//        }
//
//        // Send desired speeds to motors.
//        leftDrive.setPower(leftSpeed);
//        rightDrive.setPower(rightSpeed);
//
//        // Display it for the driver.
//        telemetry.addData("Target", "%5.2f", angle);
//        telemetry.addData("Err/St", "%5.2f/%5.2f", error, steer);
//        telemetry.addData("Speed.", "%5.2f:%5.2f", leftSpeed, rightSpeed);
//        telemetry.addData("heading", "%5.2f:%5.2f%5.2f", getHeading(),getRoll(),getPitch());
//
//        return onTarget;
//    }
//    public double getError(double targetAngle) {
//
//        double robotError;
//
//        // calculate error in -179 to +180 range  (
//        robotError = targetAngle - getHeading();
//        //while (robotError >= 180) robotError -= 360;
//        //while (robotError <= -180) robotError += 360;
//        return robotError;
//    }
//    public double getSteer(double error, double PCoeff) {
//        return Range.clip(error * PCoeff, -1, 1);
//    }
//
//    public double getHeading() {
//        Orientation angles = gyro.getAngularOrientation();
//        return angles.firstAngle+180;
//    }
//    public double getRoll() {
//        Orientation angles = gyro.getAngularOrientation();
//        return angles.secondAngle;
//    }
//    public double getPitch() {
//        Orientation angles = gyro.getAngularOrientation();
//        return angles.thirdAngle;
//    }
//
//    public double getTFAngle(){
//        telemetry.addLine("rip mr. lee");
//        return -3.14;
//    }
//    private void initTfod() {
//        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
//                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
//        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
//        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
//        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
//    }
//
//    private void initVuforia() {
//        /*
//         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
//         */
//        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
//
//        parameters.vuforiaLicenseKey = VUFORIA_KEY;
//        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");
//
//        //  Instantiate the Vuforia engine
//        vuforia = ClassFactory.getInstance().createVuforia(parameters);
//
//        // Loading trackables is not necessary for the Tensor Flow Object Detection engine.
//    }
//
//}