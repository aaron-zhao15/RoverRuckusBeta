package org.firstinspires.ftc.teamcode;

/**
 * Created by Aaron on 9/24/18.
 */

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp(name = "HolonomicDrive")

public class HolonomicDrive extends LinearOpMode {

    HardwareConfig robot = new HardwareConfig();
    @Override
    public void runOpMode() throws InterruptedException{
        robot.init(hardwareMap); //giving robot hardware map config

        boolean toggleRot = true;
        boolean toggleIntake = false;

        double clawOpenPos = 1;
        double clawClosePos = 0.5;

        waitForStart(); //when start is pressed opModeIsActive returns true; will return false when stopped

        //left stick controls direction, right stick controls rotation
        while(opModeIsActive()){
            double x1 = -gamepad1.left_stick_x;
            double y1 = gamepad1.left_stick_y;
            double x2 = gamepad1.right_stick_x;
            
            boolean rotYAdd = gamepad1.dpad_down;
            boolean rotYSub = gamepad1.dpad_up;
            boolean rotZAdd = gamepad1.dpad_right;
            boolean rotZSub = gamepad1.dpad_left;

            double intake = (double)gamepad1.left_trigger;

            boolean rotIntake = gamepad1.left_bumper;


            boolean liftUp = gamepad1.right_bumper;
            boolean liftDown = (gamepad1.right_trigger > 0);

            //double y2 = gamepad1.right_stick_y;
/*
            boolean claw1Open = gamepad1.left_bumper;
            boolean claw1Close = (gamepad1.left_trigger > 0);
            boolean claw2Open = gamepad1.right_bumper;
            boolean claw2Close = (gamepad1.right_trigger > 0);
*/
            boolean clawUp = gamepad1.y;
            boolean clawDown = gamepad1.a;

            double bl = y1-x1+x2;
            double br = y1+x1-x2;
            double fl = y1-x1-x2;
            double fr = y1+x1+x2;

            /*
            bl = Math.pow(bl, 3);
            br = Math.pow(br, 3);
            fl = Math.pow(fl, 3);
            fr = Math.pow(fr, 3);
            */


/*
            double max = Math.max(Math.max(Math.abs(bl), Math.abs(br)), Math.max(Math.abs(fl), Math.abs(fr)));
            if(max > 1.0){
                bl /= max;
                br /= max;
                fl /= max;
                fr /= max;
            }
            if(Math.abs(bl) < 0.5) bl = 0;
            if(Math.abs(br) < 0.5) br = 0;
            if(Math.abs(fl) < 0.5) fl = 0;
            if(Math.abs(fr) < 0.5) fr = 0;

            bl = bl>=0? Math.pow(bl,2):-Math.pow(bl,2);
            br = br>=0? Math.pow(br,2):-Math.pow(br,2);
            fl = fl>=0? Math.pow(fl,2):-Math.pow(fl,2);
            fr = fr>=0? Math.pow(fr,2):-Math.pow(fr,2);
*/


            if (clawUp) {
                robot.armBot.setPower(1);
            }
            if (clawDown) {
                robot.armBot.setPower(-1);
            }

            if (rotYAdd) {
                robot.armTop.setPower(0.3);
            }
            if (rotYSub) {
                robot.armTop.setPower(-0.3);
            }

            if (liftUp){
                robot.liftMotor.setPower(-1);
            }
            if (liftDown){
                robot.liftMotor.setPower(1);
            }


            /*
            if (rotZAdd) {
                servoZRot = servoZRot + 0.01;
                servoZRot = servoZRot>1? 1: servoZRot;
                robot.servoClawBot.setPosition(servoZRot);
                sleep(10);
            }
            if (rotZSub) {
                servoZRot = servoZRot - 0.01;
                servoZRot = servoZRot<0? 0: servoZRot;
                robot.servoClawBot.setPosition(servoZRot);
                sleep(10);
            }
            if (intake) {
                robot.intakeServo.setPosition(0.3);
                //sleep(500);
            }
            if (rotIntake){
                if (toggleRot){
                    toggleRot = !toggleRot;
                    sleep(500);
                    robot.rotationServo.setPosition(0.15);
                    sleep(100);
                }
                else if (!toggleRot){
                    toggleRot = !toggleRot;
                    sleep(500);
                    robot.rotationServo.setPosition(1);
                    sleep(100);
                }
            }
*/


            robot.armBot.setPower(0);
            robot.armTop.setPower(0);
            robot.liftMotor.setPower(0);

            robot.intakeServo.setPosition(0.5-(intake/2));

            robotDrive(bl, br, fl, fr);



/*
            telemetry.addData("servotoppos", robot.servoClawTop.getPosition());
            telemetry.addData("servobotpos", robot.servoClawBot.getPosition());
            telemetry.update();
*/


        }
    }

    public void robotDrive(double bl, double br, double fl, double fr){
        robot.mBackLeft.setPower(bl);
        robot.mBackRight.setPower(br);
        robot.mFrontLeft.setPower(fl);
        robot.mFrontRight.setPower(fr);
    }
}
