package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Disabled
@Autonomous(name = "methods", group = "Concept")
public class Methods  extends LinearOpMode {
    public Variables variables = new Variables();
    public Hardware hardware = new Hardware();
    public void runOpMode() {
        hardware.hardwareMap(this);
    }
        void initVuforia () {
            VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
            parameters.vuforiaLicenseKey = variables.VUFORIA_KEY;
            parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");
            variables.vuforia = ClassFactory.getInstance().createVuforia(parameters);
        }

        void initTfod () {
            int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                    "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
            TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
            variables.tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, variables.vuforia);
            variables.tfod.loadModelFromAsset(variables.TFOD_MODEL_ASSET, variables.LABEL_GOLD_MINERAL, variables.LABEL_SILVER_MINERAL);
        }

        public void gyroTurn ( double speed, double angle){
            double angleTarget;
            angleTarget = getHeading() - angle;
            while (opModeIsActive() && !onHeading(speed, angleTarget, variables.P_TURN_COEFF)) {
                telemetry.update();
            }
        }

        public void gyroTurnTo ( double speed, double angle){
            while (opModeIsActive() && !onHeading(speed, angle, variables.P_TURN_COEFF)) {
                telemetry.update();
            }
        }

        public void gyroHold ( double speed, double angle, double holdTime){
            ElapsedTime holdTimer = new ElapsedTime();
            holdTimer.reset();
            while (opModeIsActive() && (holdTimer.time() < holdTime)) {
                onHeading(speed, angle, variables.P_TURN_COEFF);
                telemetry.update();
            }
            hardware.leftDrive.setPower(0);
            hardware.rightDrive.setPower(0);
        }

        boolean onHeading ( double speed, double angle, double PCoeff){
            double error;
            double steer;
            boolean onTarget = false;
            double leftSpeed;
            double rightSpeed;
            error = getError(angle);
            if (Math.abs(error) <= variables.HEADING_THRESHOLD) {
                steer = 0.0;
                leftSpeed = 0.0;
                rightSpeed = 0.0;
                onTarget = true;
            } else {
                steer = getSteer(error, PCoeff);
                double signMultiplier = 0;
                if (steer > 0) {
                    signMultiplier = 1;
                } else if (steer < 0) {
                    signMultiplier = -1;
                }
                rightSpeed = signMultiplier * Range.clip(Math.abs(speed * steer), 0.2, 1);
                leftSpeed = -rightSpeed;
            }
            hardware.leftDrive.setPower(leftSpeed);
            hardware.rightDrive.setPower(rightSpeed);
            telemetry.addData("Target", "%5.2f", angle);
            telemetry.addData("Err/St", "%5.2f/%5.2f", error, steer);
            telemetry.addData("Speed.", "%5.2f:%5.2f", leftSpeed, rightSpeed);
            telemetry.addData("heading", "%5.1f,", getHeading());
            telemetry.update();
            return onTarget;
        }

        public double getError ( double targetAngle){
            double robotError;
            robotError = targetAngle - getHeading();
            return robotError;
        }

        public double getSteer ( double error, double PCoeff){
            return Range.clip(error * PCoeff, -1, 1);
        }

        public double getHeading () {
            Orientation angles = hardware.gyro.getAngularOrientation();
            return angles.firstAngle + 180;
        }

        public void encoderDrive ( double speed, double leftMM, double rightMM, double timeoutS){
            int newLeftTarget;
            int newRightTarget;
            if (opModeIsActive()) {
                newLeftTarget = hardware.leftDrive.getCurrentPosition() - (int) (leftMM * variables.COUNTS_PER_MM);
                newRightTarget = hardware.rightDrive.getCurrentPosition() - (int) (rightMM * variables.COUNTS_PER_MM);
                hardware.leftDrive.setTargetPosition(newLeftTarget);
                hardware.rightDrive.setTargetPosition(newRightTarget);
                hardware.leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                hardware. rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                variables.runtime.reset();
                hardware.leftDrive.setPower(Math.abs(speed));
                hardware. rightDrive.setPower(Math.abs(speed));
                while (opModeIsActive() &&
                        (variables.runtime.seconds() < timeoutS) &&
                        (hardware.leftDrive.isBusy() && hardware.rightDrive.isBusy())) {
                    telemetry.addData("Path1", "Running to %7d :%7d", newLeftTarget, newRightTarget);
                    telemetry.addData("Path2", "Running at %7d :%7d",
                            hardware.leftDrive.getCurrentPosition(),
                            hardware.rightDrive.getCurrentPosition());
                    telemetry.update();
                }
                hardware.leftDrive.setPower(0);
                hardware.rightDrive.setPower(0);
                hardware.leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                hardware.rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }
        }
    public void teleopInput(double drive, double turn, double speedLimiter, DcMotor leftDrive, DcMotor rightDrive) {
        double leftPower = 0;
        double rightPower = 0;
        leftPower = Range.clip(drive + turn, -speedLimiter, speedLimiter);
        rightPower = Range.clip(drive - turn, -speedLimiter, speedLimiter);
        leftDrive.setPower(leftPower);
        rightDrive.setPower(rightPower);
    }
    public void elavatorMove(double speed, double inches, double timeoutS) {
        int newTarget;
        if (opModeIsActive()) {
            newTarget = hardware.elevatorDrive.getCurrentPosition() + (int) (inches * variables.COUNTS_PER_INCH_ELEVATOR);
            hardware.elevatorDrive.setTargetPosition(newTarget);
            hardware.elevatorDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            hardware.elevatorDrive.setPower(Math.abs(1));
            variables.runtime.reset();
            while (opModeIsActive() &&
                    (variables.runtime.seconds() < timeoutS) &&
                    (hardware.elevatorDrive.isBusy())) {
                telemetry.addData("Path1", "Running to %7d,", newTarget);
                telemetry.update();
            }

            hardware.leftDrive.setPower(0);
            hardware. rightDrive.setPower(0);
            hardware. elevatorDrive.setPower(0);
            hardware.  elevatorDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }
    public void elevatorDrive(double speed, double elevatorDis, double timeoutS) {
        int newTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newTarget = hardware.elevatorDrive.getCurrentPosition() + (int) (elevatorDis * variables.COUNTS_PER_INCH_ELEVATOR);
            hardware.elevatorDrive.setTargetPosition(newTarget);

            // Turn On RUN_TO_POSITION
            hardware.elevatorDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            variables.runtime.reset();
            hardware.elevatorDrive.setPower(Math.abs(speed));


            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (variables.runtime.seconds() < timeoutS) &&
                    (hardware.elevatorDrive.isBusy())) {
                //if (digitalTouch.getState()) {
                //  elevatorDrive.setPower(0);
                //}
            }
            // Stop all motion;
            hardware.elevatorDrive.setPower(0);

            // Turn off RUN_TO_POSITION
            hardware.elevatorDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        }
    }
    void driveTrainControl (double drive, double turn, DcMotor leftDrive, DcMotor rightDrive) {
        double leftPower = 0;
        double rightPower = 0;
        leftPower = Math.pow(Range.clip(drive + turn, -1, 1),3);
        rightPower = Math.pow(Range.clip(drive - turn, -1, 1),3);
        leftDrive.setPower(leftPower);
        rightDrive.setPower(rightPower);
    }
}
