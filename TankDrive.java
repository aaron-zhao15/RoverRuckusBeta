package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "TankDrive")
@Disabled
public class TankDrive extends LinearOpMode {

    HardwareConfig robot = new HardwareConfig();
    @Override
    public void runOpMode() throws InterruptedException{
        robot.init(hardwareMap); //giving robot hardware map config
        waitForStart(); //when start is pressed opModeIsActive returns true; will return false when stopped

        //left stick controls direction, right stick controls rotation
        while(opModeIsActive()){
            double x = gamepad1.left_stick_x;
            double y = gamepad1.left_stick_y;
            //double r = gamepad1.right_stick_x;
            boolean up = gamepad1.a;
            boolean down = gamepad1.y;
            boolean open = gamepad1.x;
            boolean close = gamepad1.b;
            boolean sensorUp = gamepad1.dpad_up;
            boolean sensorDown = gamepad1.dpad_down;

            double frbr = x-y;
            double flbl = -x-y;

            double max = Math.max(Math.abs(frbr), Math.abs(flbl));
            if(max > 1.0){
                frbr /= max;
                flbl /= max;
            }
            if(Math.abs(frbr) < 0.5) frbr = 0;
            if(Math.abs(flbl) < 0.5) flbl = 0;

            frbr = frbr>=0? Math.pow(frbr,2):-Math.pow(frbr,2);
            flbl = flbl>=0? Math.pow(flbl,2):-Math.pow(flbl,2);

            while(open){
                //robot.armClaw.setPower(0.2);
//                robot.servoClawBot.setPosition(0.6);
//                robot.servoClawTop.setPosition(0.4);
                robot.armBot.setPower(0);
                robot.mBackLeft.setPower(flbl);
                robot.mBackRight.setPower(frbr);
                robot.mFrontLeft.setPower(flbl);
                robot.mFrontRight.setPower(frbr);
                open = gamepad1.x;
            }
            while(close){
                //robot.armClaw.setPower(-0.2);
//                robot.servoClawBot.setPosition(0.9);
//                robot.servoClawTop.setPosition(0.1);
                robot.armBot.setPower(0);
                robot.mBackLeft.setPower(flbl);
                robot.mBackRight.setPower(frbr);
                robot.mFrontLeft.setPower(flbl);
                robot.mFrontRight.setPower(frbr);
                close = gamepad1.b;
            }
            while(up){
                robot.armBot.setPower(0.2);
                robot.mBackLeft.setPower(flbl);
                robot.mBackRight.setPower(frbr);
                robot.mFrontLeft.setPower(flbl);
                robot.mFrontRight.setPower(frbr);
                up = gamepad1.a;
                //telemetry.addData("Up: ", up);
                //telemetry.update();
            }
            while(down){
                robot.armBot.setPower(-0.4);
                robot.mBackLeft.setPower(flbl);
                robot.mBackRight.setPower(frbr);
                robot.mFrontLeft.setPower(flbl);
                robot.mFrontRight.setPower(frbr);
                down = gamepad1.y;
            }
            while(sensorUp){
                //robot.armClaw.setPower(-0.2);
                robot.armBot.setPower(0);
                robot.mBackLeft.setPower(flbl);
                robot.mBackRight.setPower(frbr);
                robot.mFrontLeft.setPower(flbl);
                robot.mFrontRight.setPower(frbr);
                sensorUp = gamepad1.dpad_up;
            }
            while(sensorDown) {
                //robot.armClaw.setPower(-0.2);
                robot.armBot.setPower(0);
                robot.mBackLeft.setPower(flbl);
                robot.mBackRight.setPower(frbr);
                robot.mFrontLeft.setPower(flbl);
                robot.mFrontRight.setPower(frbr);
                sensorDown = gamepad1.dpad_down;
            }
            //robot.armClaw.setPower(0);
            robot.armBot.setPower(0);
            robot.mBackLeft.setPower(flbl);
            robot.mBackRight.setPower(frbr);
            robot.mFrontLeft.setPower(flbl);
            robot.mFrontRight.setPower(frbr);

        }
    }

}
