package org.firstinspires.ftc.teamcode.drive.opmode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.HardwareConfig;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

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

        Trajectory scan = drive.trajectoryBuilder(startPose)
                .lineToConstantHeading(new Vector2d(35,-42.5))//approach cone and then scan
                .build();
        TrajectorySequence EpicAuto = drive.trajectorySequenceBuilder(new Pose2d(22, -45, Math.toRadians(90)))
                .splineTo(new Vector2d(55,-36), Math.toRadians(0))//turn right
                .splineToConstantHeading(new Vector2d(50, -36), Math.toRadians(0))//backup from cone
                .splineTo(new Vector2d(55,-24), Math.toRadians(90))//turn left
                .splineTo(new Vector2d(47, -12), Math.toRadians(180))//turn left
                .splineToSplineHeading(new Pose2d(19, -15, Math.toRadians(90)), Math.toRadians(180))//approach pole
                .addDisplacementMarker(() -> {config.liftUp();})
                .forward(4)
                .waitSeconds(2)
                .addDisplacementMarker(() -> {config.openServos();})//drop cone 1
                .addDisplacementMarker(() -> {config.liftDown();})
                .back(7)
                .lineToLinearHeading(new Pose2d(58, -15, Math.toRadians(0)))//approach pole
                .build();


        waitForStart();
        if (isStopRequested()) return;

        while (opModeIsActive() && !isStopRequested()) {
            config.closeServos();
            sleep(100);
            config.lift.setTargetPosition(100);
            drive.followTrajectory(scan);
            String color = config.getColor();
            telemetry.addData("Color: ", color);
            drive.followTrajectorySequence(EpicAuto);
            sleep(50000);


        }
        telemetry.addData("Lift", "target: %d; current: %d", config.lift.getTargetPosition(), config.lift.getCurrentPosition());
    }
}