package org.firstinspires.ftc.teamcode;

/**
 * Created by Aaron on 9/24/18.
 */

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp(name = "ServoTest")

public class ServoTesting extends LinearOpMode {

    HardwareConfig robot = new HardwareConfig();
    @Override
    public void runOpMode() throws InterruptedException{
        robot.init(hardwareMap); //giving robot hardware map config
        waitForStart(); //when start is pressed opModeIsActive returns true; will return false when stopped

        //left stick controls direction, right stick controls rotation
        while(opModeIsActive()){

            double servo1 = gamepad1.left_stick_x;
            double servo2 = gamepad1.left_stick_y;

            boolean servoRotYAdd = (servo1 > 0);
            boolean servoRotYSub = (servo1 < 0);

            boolean servoRotZAdd = (servo2 > 0);
            boolean servoRotZSub = (servo2 < 0);


            boolean clawUp = gamepad1.dpad_up;
            boolean clawDown = gamepad1.dpad_down;


            double powerUp = 0.4;
            double powerDown = -0.2;
/*
            double servoYRot = robot.servoClawBot.getPosition();
            double servoZRot = robot.servoClawTop.getPosition();

            while (servoRotYAdd) {
                servoYRot = servoYRot + 0.01;
                servoYRot = servoYRot>1? 1: servoYRot;
                robot.servoClawBot.setPosition(servoYRot);
                Thread.sleep(10);
                servo1 = gamepad1.left_stick_x;
                servoRotYAdd = (servo1 > 0);
            }
            while (servoRotYSub) {
                servoYRot = servoYRot - 0.01;
                servoYRot = servoYRot<0? 0: servoYRot;
                robot.servoClawBot.setPosition(servoYRot);
                Thread.sleep(10);
                servo1 = gamepad1.left_stick_x;
                servoRotYSub = (servo1 < 0);
            }

            while (servoRotZAdd) {
                servoZRot = servoZRot + 0.01;
                servoZRot = servoZRot>1? 1: servoZRot;
                robot.servoClawTop.setPosition(servoZRot);
                Thread.sleep(10);
                servo2 = gamepad1.left_stick_y;
                servoRotZAdd = (servo2 > 0);
            }
            while (servoRotZSub) {
                servoZRot = servoZRot - 0.01;
                servoZRot = servoZRot<0? 0: servoZRot;
                robot.servoClawTop.setPosition(servoZRot);
                Thread.sleep(10);
                servo2 = gamepad1.left_stick_y;
                servoRotZSub = (servo2 < 0);
            }

*/
            /*
            double servoYRot = (servo1 + 1)/2;
            double servoZRot = (servo2 + 1)/2;


            robot.servoClawBot.setPosition(servoYRot);
            Thread.sleep(10);
            robot.servoClawTop.setPosition(servoZRot);
            Thread.sleep(10);


            telemetry.addData("Y rot input:", servoYRot);
            telemetry.addData("Z rot input:", servoZRot);

            telemetry.addData("Y rotation rad:", robot.servoClawBot.getPosition());
            telemetry.addData("Z rotation rad:", robot.servoClawTop.getPosition());
            telemetry.update();

*/



/*
            while(clawUp){
                robot.clawMotor.setPower(powerUp);

                robot.servoClawBot.setPosition(servoYRot);
                robot.servoClawTop.setPosition(servoZRot);
                clawUp = gamepad1.dpad_up;
            }

            while(clawDown){
                robot.clawMotor.setPower(powerDown);

                robot.servoClawBot.setPosition(servoYRot);
                robot.servoClawTop.setPosition(servoZRot);
                clawDown = gamepad1.dpad_down;
            }

            robot.clawMotor.setPower(0);

            robot.servoClawBot.setPosition(0.5);
            robot.servoClawTop.setPosition(0.5);
*/
        }
    }
}
