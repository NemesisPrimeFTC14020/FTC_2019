package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class Methods {
    public Variables variables = new Variables();
    public Hardware Hardware = new Hardware();

    double leftPower;
    double rightPower;
    double leftPowerteleop;
    double rightPowerteleop;

        void initVuforia (LinearOpMode myOpMode) {
            VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
            parameters.vuforiaLicenseKey = variables.VUFORIA_KEY;
            parameters.cameraName = myOpMode.hardwareMap.get(WebcamName.class, "Webcam 1");
            variables.vuforia = ClassFactory.getInstance().createVuforia(parameters);
        }

        void initTfod (LinearOpMode myOpMode) {
            int tfodMonitorViewId = myOpMode.hardwareMap.appContext.getResources().getIdentifier(
                    "tfodMonitorViewId", "id", myOpMode.hardwareMap.appContext.getPackageName());
            TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
            variables.tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, variables.vuforia);
            variables.tfod.loadModelFromAsset(variables.TFOD_MODEL_ASSET, variables.LABEL_GOLD_MINERAL, variables.LABEL_SILVER_MINERAL);
        }

        public void gyroTurn ( double speed, double angle, LinearOpMode myOpMode){
            double angleTarget;
            angleTarget = getHeading(myOpMode) - angle;
            while (myOpMode.opModeIsActive() && !onHeading(speed, angleTarget, variables.P_TURN_COEFF, myOpMode)) {
                myOpMode.telemetry.update();
            }
        }

        public void gyroTurnTo ( double speed, double angle, LinearOpMode myOpMode){
            while (myOpMode.opModeIsActive() && !onHeading(speed, angle, variables.P_TURN_COEFF, myOpMode)) {
                myOpMode.telemetry.update();
            }
        }

        public void gyroHold ( double speed, double angle, double holdTime, LinearOpMode myOpMode){
            ElapsedTime holdTimer = new ElapsedTime();
            holdTimer.reset();
            while (myOpMode.opModeIsActive() && (holdTimer.time() < holdTime)) {
                onHeading(speed, angle, variables.P_TURN_COEFF, myOpMode);
                myOpMode.telemetry.update();
            }
            Hardware.leftDrive.setPower(0);
            Hardware.rightDrive.setPower(0);
        }

        boolean onHeading ( double speed, double angle, double PCoeff, LinearOpMode myOpMode){
            double error;
            double steer;
            boolean onTarget = false;
            double leftSpeed;
            double rightSpeed;
            error = getError(angle, myOpMode);
            if (Math.abs(error) <= variables.HEADING_THRESHOLD) {
                steer = 0.0;
                leftSpeed = 0.0;
                rightSpeed = 0.0;
                onTarget = true;
            } else {
                steer = getSteer(error, PCoeff, myOpMode);
                double signMultiplier = 0;
                if (steer > 0) {
                    signMultiplier = 1;
                } else if (steer < 0) {
                    signMultiplier = -1;
                }
                rightSpeed = signMultiplier * Range.clip(Math.abs(speed * steer), 0.2, 1);
                leftSpeed = -rightSpeed;
            }
            Hardware.leftDrive.setPower(leftSpeed);
            Hardware.rightDrive.setPower(rightSpeed);
            myOpMode.telemetry.addData("Target", "%5.2f", angle);
            myOpMode.telemetry.addData("Err/St", "%5.2f/%5.2f", error, steer);
            myOpMode.telemetry.addData("Speed.", "%5.2f:%5.2f", leftSpeed, rightSpeed);
            myOpMode.telemetry.addData("heading", "%5.1f,", getHeading(myOpMode));
            myOpMode.telemetry.update();
            return onTarget;
        }

        public double getError ( double targetAngle, LinearOpMode myOpMode){
            double robotError;
            robotError = targetAngle - getHeading(myOpMode);
            return robotError;
        }

        public double getSteer ( double error, double PCoeff, LinearOpMode myOpMode){
            return Range.clip(error * PCoeff, -1, 1);
        }

        public double getHeading (LinearOpMode myOpMode) {
            Orientation angles = Hardware.gyro.getAngularOrientation();
            return angles.firstAngle + 180;
        }

        public void encoderDrive (double speed, double leftMM, double rightMM, double timeoutS, LinearOpMode myOpMode){
            int newLeftTarget;
            int newRightTarget;
            if (myOpMode.opModeIsActive()) {
                newLeftTarget = Hardware.leftDrive.getCurrentPosition() - (int) (leftMM * variables.COUNTS_PER_MM);
                newRightTarget = Hardware.rightDrive.getCurrentPosition() - (int) (rightMM * variables.COUNTS_PER_MM);
                Hardware.leftDrive.setTargetPosition(newLeftTarget);
                Hardware.rightDrive.setTargetPosition(newRightTarget);
                Hardware.leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                Hardware.rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                variables.runtime.reset();
                Hardware.leftDrive.setPower(Math.abs(speed));
                Hardware. rightDrive.setPower(Math.abs(speed));
                while (myOpMode.opModeIsActive() &&
                        (variables.runtime.seconds() < timeoutS) &&
                        (Hardware.leftDrive.isBusy() && Hardware.rightDrive.isBusy())) {
                    myOpMode.telemetry.addData("Path1", "Running to %7d :%7d", newLeftTarget, newRightTarget);
                    myOpMode.telemetry.addData("Path2", "Running at %7d :%7d",
                            Hardware.leftDrive.getCurrentPosition(),
                            Hardware.rightDrive.getCurrentPosition());
                    myOpMode.telemetry.update();
                }
                Hardware.leftDrive.setPower(0);
                Hardware.rightDrive.setPower(0);
                Hardware.leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                Hardware.rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }
        }
    public void teleopInput(double drive, double turn, DcMotor leftDrive, DcMotor rightDrive, LinearOpMode myOpMode) {
        leftPowerteleop = Range.clip(drive + turn, -1, 1);
        rightPowerteleop = Range.clip(drive - turn, -1, 1);

        while (leftPowerteleop != 0 || rightPowerteleop != 0) {
            leftDrive.setPower(leftPowerteleop);
            rightDrive.setPower(rightPowerteleop);

            if (leftPowerteleop > 0) {
                leftPowerteleop = leftPowerteleop - 0.001;
            }
            else if (leftPowerteleop < 0) {
                leftPowerteleop = leftPowerteleop + 0.001;
            }
            if (rightPowerteleop > 0) {
                rightPowerteleop = rightPowerteleop - 0.001;
            }
            else if (rightPowerteleop < 0) {
                rightPowerteleop = rightPowerteleop + 0.001;
            }
        }
    }
    public void elevatorMove(double speed, double inches, double timeoutS, LinearOpMode myOpMode) {
        int newTarget;
        if (myOpMode.opModeIsActive()) {
            newTarget = Hardware.elevatorDrive.getCurrentPosition() + (int) (inches * variables.COUNTS_PER_INCH_ELEVATOR);
            Hardware.elevatorDrive.setTargetPosition(newTarget);
            Hardware.elevatorDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            Hardware.elevatorDrive.setPower(Math.abs(1));
            variables.runtime.reset();
            while (myOpMode.opModeIsActive() &&
                    (variables.runtime.seconds() < timeoutS) &&
                    (Hardware.elevatorDrive.isBusy())) {
                myOpMode.telemetry.addData("Path1", "Running to %7d,", newTarget);
                myOpMode.telemetry.update();
            }
            Hardware.leftDrive.setPower(0);
            Hardware.rightDrive.setPower(0);
            Hardware.elevatorDrive.setPower(0);
            Hardware.elevatorDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }
    public void elevatorDrive(double speed, double elevatorDis, double timeoutS, LinearOpMode myOpMode) {
        int newTarget;

        // Ensure that the opmode is still active
        if (myOpMode.opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newTarget = Hardware.elevatorDrive.getCurrentPosition() + (int) (elevatorDis * variables.COUNTS_PER_INCH_ELEVATOR);
            Hardware.elevatorDrive.setTargetPosition(newTarget);

            // Turn On RUN_TO_POSITION
            Hardware.elevatorDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            variables.runtime.reset();
            Hardware.elevatorDrive.setPower(Math.abs(speed));


            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (myOpMode.opModeIsActive() &&
                    (variables.runtime.seconds() < timeoutS) &&
                    (Hardware.elevatorDrive.isBusy())) {
                //if (digitalTouch.getState()) {
                //  elevatorDrive.setPower(0);
                //}
            }
            // Stop all motion;
            Hardware.elevatorDrive.setPower(0);

            // Turn off RUN_TO_POSITION
            Hardware.elevatorDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        }
    }
    void driveTrainControl (double drive, double turn, DcMotor leftDrive, DcMotor rightDrive, LinearOpMode myOpMode) {
        double leftPower = 0;
        double rightPower = 0;
        leftPower = Math.pow(Range.clip(drive + turn, -1, 1),3);
        rightPower = Math.pow(Range.clip(drive - turn, -1, 1),3);
        leftDrive.setPower(leftPower);
        rightDrive.setPower(rightPower);
    }
}
