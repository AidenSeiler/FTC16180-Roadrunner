package org.firstinspires.ftc.teamcode.drive.opmode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.HardwareConfig;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Autonomous(name="SuperFunkyAutoTest", group="Linear Opmode")

public class autoTest extends LinearOpMode {
    private HardwareConfig config = new HardwareConfig();
    String color = "undefined";
    @Override
    public void runOpMode() {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        config.init(hardwareMap);
        config.setMotorBehave();
        config.liftEncoder();

        Pose2d startPose = new Pose2d(31, -65, Math.toRadians(90));
        drive.setPoseEstimate(startPose);

        Trajectory toCone = drive.trajectoryBuilder(startPose)
                .lineToConstantHeading(new Vector2d(33,-45))//approach cone and then scan
                .build();
        Trajectory moveCone = drive.trajectoryBuilder(toCone.end())
                .splineTo(new Vector2d(65,-36), Math.toRadians(0))//turn right
                .splineToConstantHeading(new Vector2d(58, -36), Math.toRadians(0))//push cone
                .splineTo(new Vector2d(59, -30), Math.toRadians(90))//backwards turn
                .splineTo(new Vector2d(47, -12), Math.toRadians(180))//turn left
                .splineToSplineHeading(new Pose2d(23.5, -12, Math.toRadians(90)), Math.toRadians(180))//approach pole
                .addDisplacementMarker(() -> {config.liftUp();})
                .splineToConstantHeading(new Vector2d(23.5, -5), Math.toRadians(180))//gotoPole
                .addDisplacementMarker(() -> {config.openServos();})//drop cone 1
                .splineToConstantHeading(new Vector2d(23.5, -12), Math.toRadians(180))//goawayfromPole
                .splineToSplineHeading(new Pose2d(64, -12, Math.toRadians(0)), Math.toRadians(180))//approach pole


                .build();


        waitForStart();
        if (isStopRequested()) return;

        while (opModeIsActive() && !isStopRequested()) {
            drive.followTrajectory(toCone);
            config.closeServos();
            config.lift.setTargetPosition(100);
            String color = config.getColor();
            telemetry.addData("Color: ", color);
            drive.followTrajectory(moveCone);



        }
        telemetry.addData("Lift", "target: %d; current: %d", config.lift.getTargetPosition(), config.lift.getCurrentPosition());
    }
}