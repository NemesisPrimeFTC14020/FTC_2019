package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.util.List;

@Autonomous(name = "autonFull", group = "Minibot")
public class autonFull extends LinearOpMode {

    //public variables variables = new variables();
    public Methods Methods = new Methods();
    //public hardware hardware = new hardware();

    public void runOpMode() {
        Methods.initVuforia();

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            Methods.initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }

        if (opModeIsActive()) {
            if (Methods.variables.tfod != null) {
                Methods.variables.tfod.activate();
            }
        }
        Methods.gyroTurn(Variables.TURN_SPEED, -20);
        boolean tfodDone = false;

        if (Methods.variables.tfod != null) {
            // getUpdatedRecognitions() will return null if no new information is available since
            // the last time that call was made.
            List<Recognition> updatedRecognitions = Methods.variables.tfod.getUpdatedRecognitions();
            if (updatedRecognitions != null) {
                telemetry.addData("# Object Detected", updatedRecognitions.size());

                int goldMineralX = -1;

                final int fov = 78;
                final float d_per_pix = (float) 0.040625;
                for (Recognition recognition : updatedRecognitions) {
                    if (recognition.getLabel().equals(Methods.variables.LABEL_GOLD_MINERAL)) {
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
                        float angle = h * d_per_pix;
                        telemetry.addData("the final angle lmao ", angle);
                        telemetry.update();

                        tfodDone = false;
                        //turns at angle
                        Methods.gyroTurn(Methods.variables.TURN_SPEED, angle - 5);
                        sleep(500);
                        Methods.encoderDrive(Methods.variables.DRIVE_SPEED, 350, 350, 5.0);

                    }

                }

                telemetry.update();
            }
        }

        waitForStart();
        telemetry.addData("heading:", Methods.getHeading());
        telemetry.update();
        Methods.elevatorDrive(1, 10, 8);// coming off the lander
        Methods.hardware.gyro.initialize(Methods.hardware.gyro.getParameters());
        Methods.hardware.markerServo.setPosition(1);
        Methods.encoderDrive(Methods.variables.DRIVE_SPEED, -100, -100, 5.0);

    }


}
