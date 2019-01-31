package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.util.List;

@Disabled
@Autonomous(name="Auton Tfod", group="Minibot")
public class AutonTFOD extends LinearOpMode {
    public Methods methods = new Methods();
    public void runOpMode() {
        methods.initVuforia(this);
        methods.Hardware.initHardware(this);
        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            telemetry.addData("1", null);
            telemetry.update();
            methods.initTfod(this);
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }
        telemetry.addLine("Press play to start.");
        telemetry.update();
        waitForStart();
        methods.variables.runtime.reset();
        methods.elevatorDrive(1, 8.5, 4, this);
        methods.encoderDrive(Variables.DRIVE_SPEED, 100, 100, 4, this);
        methods.elevatorDrive(1, -8.5, 4, this);
        methods.encoderDrive(Variables.DRIVE_SPEED, -100, -100, 4, this);
        if (methods.variables.tfod != null) {
            methods.variables.tfod.activate();
        }
        methods.gyroTurn(Variables.TURN_SPEED, -20, this);
        boolean tFodDone = false;
        while (opModeIsActive() && tFodDone == false) {
            if (methods.variables.tfod != null) {
                // getUpdatedRecognitions() will return null if no new information is available since
                // the last time that call was made.
                List<Recognition> updatedRecognitions = methods.variables.tfod.getUpdatedRecognitions();
                methods.Hardware.leftDrive.setPower(0.075);
                methods.Hardware.rightDrive.setPower(-0.075);
                if (updatedRecognitions != null) {
                    telemetry.addData("# Object Detected", updatedRecognitions.size());

                    int goldMineralX = -1;
                    int silverMineral1X = -1;
                    int silverMineral2X = -1;
                    final int fov = 78;
                    final float d_per_pix = (float) 0.040625;
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
                            float angle = h * d_per_pix;
                            telemetry.addData("the final angle lmao ", angle);
                            telemetry.update();
                            sleep(30000);
                            //if (angle < 0) {
                            //    angle = angle - 10;
                            //}

                            methods.gyroTurn(Variables.TURN_SPEED, angle, this);
                            sleep(500);
                            methods.encoderDrive(Variables.DRIVE_SPEED, 350, 350, 100, this);
                            methods.encoderDrive(Variables.DRIVE_SPEED, -350, -350, 100, this);
                            sleep(500);
                        }
                    }
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