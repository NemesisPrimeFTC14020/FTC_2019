package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.util.List;
@Disabled
@TeleOp(name="TFS", group="Linear Opmode")
public class tensorflowScan extends LinearOpMode {
    public Methods methods = new Methods();

    public void runOpMode() {
        methods.Hardware.initHardware(this);
        methods.initVuforia(this);
        methods.initTfod(this);
        telemetry.addData("init", "done");
        telemetry.update();
        waitForStart();
        if (methods.variables.tfod != null) {
            methods.variables.tfod.activate();
            telemetry.addLine("tfod On");
        }
        while (opModeIsActive()) {
                    float angle = 0;
                    List<Recognition> updatedRecognitions = methods.variables.tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                        for (Recognition recognition : updatedRecognitions) {
                            if (recognition.getLabel().equals(Variables.LABEL_GOLD_MINERAL)) {
                                final int fov = 78;
                                final float d_per_pix = (float) 0.040625;
                                float getleftval = (int) recognition.getLeft();
                                float getrightval = (int) recognition.getRight();
                                float p = (float) (0.5 * (recognition.getLeft() + recognition.getRight()));
                                telemetry.addData("Gold p Value that was calculated: ", p);
                                float h = (float) (p - (0.5 * 800));
                                telemetry.addData("h value calculated ", h);
                                angle = h * d_per_pix;
                               telemetry.addData("the final angle lmao ", angle);
                                telemetry.update();
                            }
                        }
                    telemetry.update();
                }
            }
        }
    }
