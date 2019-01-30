package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp(name = "debug")

public class Debug extends LinearOpMode {
    HardwareConfig robot = new HardwareConfig();
    @Override
    public void runOpMode() throws InterruptedException{
        robot.init(hardwareMap); //giving robot hardware map config
        waitForStart(); //when start is pressed opModeIsActive returns true; will return false when stopped

        //left stick controls direction, right stick controls rotation
        while(opModeIsActive()){

            if (gamepad1.y) {
                robot.armBot.setPower(1);
            }
            if (gamepad1.a) {
                robot.armBot.setPower(-1);
            }
            if (gamepad1.dpad_up) {
                robot.armTop.setPower(0.4);
            }
            if (gamepad1.dpad_down) {
                robot.armTop.setPower(-0.4);
            }


        }
    }
}

