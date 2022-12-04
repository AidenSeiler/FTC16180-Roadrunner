package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class HardwareConfig {
    public DcMotorEx lift = null;
    public DcMotorEx fLeft = null;
    public DcMotorEx fRight = null;
    public DcMotorEx bLeft = null;
    public DcMotorEx bRight = null;
    public Servo leftServo = null;
    public Servo rightServo = null;
    public ColorSensor colorSensor = null;
    public DcMotor perpEncoder = null;
    public DcMotor parallelEncoder = null;

    public static double clamp(double val, double min, double max) {
        return  val;
    }

    public HardwareConfig() {
        super();
    }

    public void init(HardwareMap hardwareMap) {
        lift = hardwareMap.get(DcMotorEx.class, "pulley_motor");
        fLeft = hardwareMap.get(DcMotorEx.class, "front_left_motor");
        fRight = hardwareMap.get(DcMotorEx.class, "front_right_motor");
        bLeft = hardwareMap.get(DcMotorEx.class, "back_left_motor");
        bRight = hardwareMap.get(DcMotorEx.class, "back_right_motor");
        leftServo = hardwareMap.get(Servo.class, "left_servo");
        rightServo = hardwareMap.get(Servo.class, "right_servo");
        colorSensor = hardwareMap.get(ColorSensor.class, "color_sensor");
        perpEncoder = hardwareMap.get(DcMotor.class, "perpEncoder");
        parallelEncoder = hardwareMap.get(DcMotor.class, "parallelEncoder");

    }
    public void Auton(){
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setTargetPosition(0);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.setPower(1.0);
    }
    public void adjustMotors(double lF, double rF, double lB, double rB) {
        int velo = 5000;
        int pos = lift.getCurrentPosition();
        if (pos>2400){
            velo = 1000;
        }
        else if (pos>1500){
            velo = 1250;
        }
        fLeft.setVelocity(velo*clamp(lF, -1.0, 1.0));
        fRight.setVelocity(velo*clamp(rF, -1.0, 1.0));
        bLeft.setVelocity(velo*clamp(lB, -1.0, 1.0));
        bRight.setVelocity(velo*clamp(rB, -1.0, 1.0));
    }

    public void moveDistance(int liftA) throws InterruptedException{
        lift.setTargetPosition(liftA);
    }

    public void setMotorBehave(){
        fLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void liftEncoder(){
        lift.setTargetPosition(0);
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.setPower(1.0);

        perpEncoder.setTargetPosition(0);
        perpEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        perpEncoder.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        perpEncoder.setPower(1.0);

        parallelEncoder.setTargetPosition(0);
        parallelEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        parallelEncoder.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        parallelEncoder.setPower(1.0);


    }

    public void liftUp(){
        lift.setTargetPosition(2735);
    }

    public void liftAdjust(double adjustment){
        lift.setTargetPosition((int)lift.getCurrentPosition()+(int)adjustment*100);
    }

    public void liftDown(){
        lift.setTargetPosition(100);
    }

    public void smallLift(){
        lift.setTargetPosition(1250);
    }

    public void mediumLift(){
        lift.setTargetPosition(2000);
    }

    public void closeServos(){
        leftServo.setPosition(0.4);
        rightServo.setPosition(0.15);
    }
    public void openServos(){
        leftServo.setPosition(0.32);
        rightServo.setPosition(0.24);
    }
    public String getColor(){
        String coneColor = "undefined";
        int r = colorSensor.red();
        int g = colorSensor.green();
        int b = colorSensor.blue();
        if (r > b && r > g){
            coneColor = "Red";
        }
        if (g > b && g > r){
            coneColor = "Green";
        }
        if (b > r && b > g){
            coneColor = "Blue";
        }
        return coneColor;
    }

}