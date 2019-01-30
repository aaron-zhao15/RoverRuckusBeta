/* Copyright (c) 2018 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;


@Autonomous(name = "AutoAlignTestingShits")

public class AutoAlignTesting extends LinearOpMode {
    HardwareConfig          robot   = new HardwareConfig();   // Use a Pushbot's hardware
    private ElapsedTime runtime = new ElapsedTime();

    static final double     FORWARD_SPEED = 0.6;
    static final double     TURN_SPEED    = 0.5;
    static final double     CLAW_SERVO_CLOSE_POSITION = 0.1;
    static final double     CLAW_SERVO_OPEN_POSITION = 0.8;

    double jewelPos = 0;

    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";


    private static final String VUFORIA_KEY = "AcEFFIj/////AAABmSOIZr8rt0Dnsi6XmQK3QAIBsi7nd0+XZ6PfQ" +
            "TABZb9Yy4udMduTjqyXp3NosaSoevs06bzlDbNIOq2JNHffHhH2JJTMdDQM190/ILL/NdboLihbj3JGyK8LTLdM" +
            "I2jcAACH4dye2N/12RObvEbFxQCQJafMOWDmPImiDw9ffmSxKMaHyo5HXUR+9Ajfs6oKvMJDJXlisysaFK0iCDI" +
            "vytTlV365YEYAu+2CO/t3M/qqiahV4w0zUR6mIQVzikl1tj9VsBTsMahPezFnxrYR6q7om658ANSDtGsf5GulAi" +
            "xbif3LXNKSK8x6yL7qIw9me0qRa3r4wJhbhGJXef/K14UvI85kraIzpscMMWmlYi54";

        /**
         * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
         * localization engine.
         */
        private VuforiaLocalizer vuforia;

        /**
         * {@link #tfod} is the variable we will use to store our instance of the Tensor Flow Object
         * Detection engine.
         */
        private TFObjectDetector tfod;

        @Override
        public void runOpMode() throws InterruptedException{
            // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that
            // first.
            robot.init(hardwareMap);
            initVuforia();

            if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
                initTfod();
            } else {
                telemetry.addData("Sorry!", "This device is not compatible with TFOD");
            }



            waitForStart();
/*
            //face the camera toward the jewels
            rotTime(0.5, 0.9);
            sleep(1000) ;

            //move back to see the jewels
            strafeSideTime(0.3, 0.5);
            sleep(1000);
*/

            strafeForwardTime(-0.2, 0.35);
            sleep(1000);

            //shift backwards to line up with the middle

            /** Activate Tensor Flow Object Detection. */
            if (tfod != null) {
                tfod.activate();
            }

            while (getRuntime() < 6) {

                if (tfod != null) {
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                        telemetry.addData("# Object Detected", updatedRecognitions.size());

                        int goldMineralX = -1;
                        int silverMineral1X = -1;
                        int silverMineral2X = -1;

                        if (updatedRecognitions.size() == 2) {

                            for (Recognition recognition : updatedRecognitions) {
                                if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                                    goldMineralX = (int) recognition.getLeft();
                                } else if (silverMineral1X == -1) {
                                    silverMineral1X = (int) recognition.getLeft();
                                } else {
                                    silverMineral2X = (int) recognition.getLeft();
                                }
                            }
                            if (silverMineral1X != -1 && silverMineral2X != -1) {
                                jewelPos = 1;
                                telemetry.addData("Gold Mineral Position", "Left");
                                telemetry.update();
                            } else if (goldMineralX != -1 && silverMineral1X != -1) {
                                if (goldMineralX > silverMineral1X) {
                                    jewelPos = 3;
                                    telemetry.addData("Gold Mineral Position", "Right");
                                    telemetry.update();
                                } else {
                                    jewelPos = 2;
                                    telemetry.addData("Gold Mineral Position", "Center");
                                    telemetry.update();
                                }
                            } else {
                                telemetry.addData("Gold Mineral Position", "Unknown");
                            }
                        }

                        if (goldMineralX < 0) {
                            strafeForwardTime(-0.2, 0.1);
                        }
                        else if (goldMineralX > 0) {
                            strafeForwardTime(0.2, 0.1);
                        }
                        else {

                        }

                    }
                }
                telemetry.addData("Runtime: ", getRuntime());
                telemetry.update();
            }

            if (tfod != null) {
                tfod.shutdown();
            }



            if (jewelPos == 1){
                telemetry.addData("Gold Mineral Position", "Left");
                telemetry.update();
                //jewel position: left


                //back up to the left jewel
                strafeForwardTime(-0.3, 1);
                sleep(500);

                //strafe to knock the jewel off and align with the depot
                strafeSideTime(-0.3, 2.4);
                sleep(500);

                //align with the depot
                rotTime(-0.5, 0.5);
                sleep(500);

                //drop the marker in the depot
                strafeForwardTime(0.3, 1.4);
                sleep(500);

                //release
                runtime.reset();
                while (runtime.seconds() < 1) {
                    robot.markerMotor.setPower(-0.3);
                }
                robot.markerMotor.setPower(0);
                runtime.reset();

                //back into the crater
                strafeForwardTime(-0.5, 3.4);
                sleep(500);



            }
            else if (jewelPos == 3){
                telemetry.addData("Gold Mineral Position", "Right");
                telemetry.update();
                //jewel position: right


                //back up to the right jewel
                strafeForwardTime(0.3, 1.1);
                sleep(500);

                //strafe to knock the jewel off and align with the depot
                strafeSideTime(-0.3, 2.6);
                sleep(500);

                //align with the depot
                rotTime(-0.5, 0.45);
                sleep(500);

                //move into the depot
                strafeSideTime(-0.3, 1.5);
                sleep(500);

                //release
                runtime.reset();
                while (runtime.seconds() < 1) {
                    robot.markerMotor.setPower(-0.3);
                }
                robot.markerMotor.setPower(0);
                runtime.reset();

                //back into the crater
                strafeForwardTime(-0.5, 3.3);
                sleep(500);


            }
            else {
                telemetry.addData("Gold Mineral Position", "Center or unknown");
                telemetry.update();
                //jewel position: middle or unknown


                //strafe to knock the jewel off and align with the depot
                strafeSideTime(-0.3, 3.2);
                sleep(500);

                //align with the depot
                rotTime(-0.5, 0.45);
                sleep(500);

                //release marker in depot
                runtime.reset();
                while (runtime.seconds() < 1) {
                    robot.markerMotor.setPower(-0.3);
                }
                robot.markerMotor.setPower(0);
                runtime.reset();

                //back into the crater
                strafeForwardTime(-0.5, 3.3);
                sleep(500);


            }




    }


        private void strafeForwardTime(double power, double time) {
            //negative power: strafe back, positive power: strafe forward
            double y1 = -power;

            double bl = y1;
            double br = y1;
            double fl = y1;
            double fr = y1;

            runtime.reset();
            while (runtime.seconds() < time) {
                robot.mBackLeft.setPower(bl);
                robot.mBackRight.setPower(br);
                robot.mFrontLeft.setPower(fl);
                robot.mFrontRight.setPower(fr);
            }
            runtime.reset();

            robot.mBackLeft.setPower(0);
            robot.mBackRight.setPower(0);
            robot.mFrontLeft.setPower(0);
            robot.mFrontRight.setPower(0);

        }

        private void strafeSideTime(double power, double time) {

            //negative power: strafe left, positive power: strafe right
            double x2 = power;

            double bl = x2;
            double br = -x2;
            double fl = -x2;
            double fr = x2;

            runtime.reset();
            while (runtime.seconds() < time) {
                robot.mBackLeft.setPower(bl);
                robot.mBackRight.setPower(br);
                robot.mFrontLeft.setPower(fl);
                robot.mFrontRight.setPower(fr);
            }
            runtime.reset();

            robot.mBackLeft.setPower(0);
            robot.mBackRight.setPower(0);
            robot.mFrontLeft.setPower(0);
            robot.mFrontRight.setPower(0);

        }

        private void rotTime(double power, double time) {

            //negative power: rotate counterclockwise (left), positive power: rotate clockwise (right)
            double x1 = power;

            double bl = -x1;
            double br = x1;
            double fl = -x1;
            double fr = x1;

            runtime.reset();
            while (runtime.seconds() < time) {
                robot.mBackLeft.setPower(bl);
                robot.mBackRight.setPower(br);
                robot.mFrontLeft.setPower(fl);
                robot.mFrontRight.setPower(fr);
            }
            runtime.reset();

            robot.mBackLeft.setPower(0);
            robot.mBackRight.setPower(0);
            robot.mFrontLeft.setPower(0);
            robot.mFrontRight.setPower(0);

        }





    /**
     * Initialize the Vuforia localization engine.
     */
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the Tensor Flow Object Detection engine.
    }

    /**
     * Initialize the Tensor Flow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }


}
