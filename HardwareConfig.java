package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


public class HardwareConfig {

    DcMotor mFrontRight;
    DcMotor mBackRight;
    DcMotor mFrontLeft;
    DcMotor mBackLeft;
    DcMotor markerMotor;
    DcMotor armTop;
    DcMotor armBot;
    DcMotor liftMotor;

    Servo intakeServo;


    /*
    Servo servoClawBot;
    Servo servoClawTop;
    Servo servoClawOne;
    Servo servoClawTwo;*/

    public void init(HardwareMap hw) { //creating hardware map, setting up hardware map
        mFrontRight = hw.dcMotor.get("frontRight");
        mBackRight = hw.dcMotor.get("backRight");
        mFrontLeft = hw.dcMotor.get("frontLeft");
        mBackLeft = hw.dcMotor.get("backLeft");
        armTop = hw.dcMotor.get("armTop");
        armBot = hw.dcMotor.get("armBottom");
        markerMotor = hw.dcMotor.get("markerMotor");
        liftMotor = hw.dcMotor.get("liftMotor");

        //armClaw = hw.dcMotor.get("claw");
        /*servoClawBot = hw.servo.get("servoClawBottom");
        servoClawTop = hw.servo.get("servoClawTop");
        servoClawOne = hw.servo.get("servoClawOne");
        servoClawTwo = hw.servo.get("servoClawTwo");*/

        intakeServo = hw.servo.get("succ");


        //set to stopping
        mFrontRight.setPower(0.0);
        mBackRight.setPower(0.0);
        mFrontLeft.setPower(0.0);
        mBackLeft.setPower(0.0);

        //make the robot brake when there is no input
        mBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        mBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        mFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        mFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        armTop.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armBot.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //need to reverse one side so the robot can move forward
        mFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        mBackRight.setDirection(DcMotorSimple.Direction.REVERSE);

        mBackLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        mBackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        mFrontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        mFrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
}
