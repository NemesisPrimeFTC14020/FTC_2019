package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import java.util.List;
@Disabled
@Autonomous(name="Auton Tensorflow Depot Blue", group="Minibot")
public class AutonTensorFlowDepot extends LinearOpMode {
    public Methods methods = new Methods();

    public void runOpMode() {
        methods.Hardware.initHardware(this);
        methods.initVuforia(this);
        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            methods.initTfod(this);
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }
        telemetry.addLine("Press play to start.");
        telemetry.update();
        waitForStart();
        methods.variables.runtime.reset();
        methods.elevatorDrive(1, -9, 4, this);
        methods.encoderDrive(Variables.DRIVE_SPEED, 100, 100, 4, this);

        /** Wait for the game to begin */
        if (methods.variables.tfod != null) {
            methods.variables.tfod.activate();
        }
        boolean tfodDone = false;
        while (opModeIsActive() && !tfodDone) {
            if (methods.variables.tfod != null) {
                // getUpdatedRecognitions() will return null if no new information is available since
                // the last time that call was made.

                int goldMineralX = -1;
                int silverMineral1X = -1;
                int silverMineral2X = -1;
                final int fov = 78;
                final double d_per_pix = 0.040625;
                methods.gyroTurnTo(Variables.TURN_SPEED, 225, this);
                sleep(500);
                List<Recognition> updatedRecognitions = methods.variables.tfod.getUpdatedRecognitions();
                if (updatedRecognitions != null) {
                    telemetry.addData("# Object Detected", updatedRecognitions.size());
                    sleep(1500);
                    updatedRecognitions = methods.variables.tfod.getUpdatedRecognitions();
                    for (Recognition recognition : updatedRecognitions) {
                        if (recognition.getLabel().equals(Variables.LABEL_GOLD_MINERAL)) {
                            methods.Hardware.leftDrive.setPower(0);
                            methods.Hardware.rightDrive.setPower(0);
                            goldMineralX = (int) recognition.getLeft();
                            telemetry.addLine("Skipper we did it, we found the gold mineral");
                            telemetry.addData("Gold getLeft Value: ", recognition.getLeft());
                            telemetry.addData("Gold getRight Value: ", recognition.getRight());
                            double getleftval = (int) recognition.getLeft();
                            double getrightval = (int) recognition.getRight();
                            double p = (0.5 * (recognition.getLeft() + recognition.getRight()));
                            telemetry.addData("Gold p Value that was calculated: ", p);
                            double h = (p - (0.5 * 800));
                            telemetry.addData("h value calculated ", h);
                            double angle = h * d_per_pix;
                            telemetry.addData("the final angle lmao ", angle);
                            telemetry.update();
                            methods.gyroTurn(Variables.TURN_SPEED, angle,  this);
                            sleep(500);
                            methods.encoderDrive(Variables.DRIVE_SPEED, 350, 350, 100, this);
                            methods.gyroTurn(Variables.TURN_SPEED, -55, this);
                           // Hardware.markerServo.setPosition(1);
                            sleep(1500);
                            methods.encoderDrive(Variables.DRIVE_SPEED, 2000, -2000, 20, this);

                            tfodDone = true;
                        }
                    }
                    if (!tfodDone) {
                        methods. gyroTurnTo(Variables.TURN_SPEED, 135, this);
                        sleep(500);
                        updatedRecognitions = methods.variables.tfod.getUpdatedRecognitions();
                        if (updatedRecognitions != null) {
                            telemetry.addData("# Object Detected", updatedRecognitions.size());

                            for (Recognition recognition : updatedRecognitions) {
                                if (recognition.getLabel().equals(Variables.LABEL_GOLD_MINERAL)) {
                                    methods.Hardware.leftDrive.setPower(0);
                                    methods.Hardware.rightDrive.setPower(0);
                                    goldMineralX = (int) recognition.getLeft();
                                    telemetry.addLine("Skipper we did it, we found the gold mineral");
                                    telemetry.addData("Gold getLeft Value: ", recognition.getLeft());
                                    telemetry.addData("Gold getRight Value: ", recognition.getRight());
                                    float getleftval = (int) recognition.getLeft();
                                    float getrightval = (int) recognition.getRight();
                                    float p = (float) (0.5 * (recognition.getLeft() + recognition.getRight()));
                                    telemetry.addData("Gold p Value that was calculated: ", p);
                                    float h = (float) (p - (0.5 * 800));
                                    telemetry.addData("h value calculated ", h);
                                    double angle = h * d_per_pix;
                                    telemetry.addData("the final angle lmao ", angle);
                                    telemetry.update();
                                    //if (angle < 0) {
                                    //    angle = angle - 10;
                                    //}
                                    methods.gyroTurn(Variables.TURN_SPEED, angle, this);
                                    sleep(500);
                                    methods.encoderDrive(Variables.DRIVE_SPEED, 350, 350, 100, this);
                                    methods.gyroTurn(Variables.TURN_SPEED, -55, this);
                                    methods.encoderDrive(Variables.DRIVE_SPEED, 350, 350, 10, this);
                                    //methods.Hardware.markerServo.setPosition(1);
                                    sleep(1500);
                                    methods.encoderDrive(Variables.DRIVE_SPEED, -200, -200, 10, this);
                                    sleep(500);
                                    tfodDone = true;
                                }
                            }
                        }
                    }
                    if (tfodDone) {
                        methods.gyroTurnTo(Variables.TURN_SPEED, 180, this);
                        methods.encoderDrive(Variables.DRIVE_SPEED, 650, 650, 5, this);
                       // methods.Hardware.markerServo.setPosition(1);
                        sleep(1500);
                        methods.encoderDrive(Variables.DRIVE_SPEED, -650, -650, 5, this);
                    }

                    telemetry.update();
                }
            }
        }
        if (methods.variables.tfod != null) {
            methods.variables.tfod.shutdown();
        }
        telemetry.addData("Tfod", "done");
        telemetry.update();


    }
}
