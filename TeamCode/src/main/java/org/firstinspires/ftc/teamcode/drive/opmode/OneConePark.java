package org.firstinspires.ftc.teamcode.drive.opmode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.HardwareConfig;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous(name="RightOneConePark", group="Linear Opmode")

public class OneConePark extends LinearOpMode {
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

        TrajectorySequence scan = drive.trajectorySequenceBuilder(startPose)
                .addTemporalMarker(1, () -> {config.lift.setTargetPosition(100);})
                .lineToConstantHeading(new Vector2d(31.5,-46))//approach cone and then scan
                .forward(4)
                .waitSeconds(1)
                .build();
        TrajectorySequence num1 = drive.trajectorySequenceBuilder(scan.end())
                .lineToLinearHeading(new Pose2d(28,-36, Math.toRadians(0)))//turn right
                .splineTo(new Vector2d(55,-36), Math.toRadians(0))//turn right
                .waitSeconds(0.1)
                .splineToConstantHeading(new Vector2d(50, -33), Math.toRadians(0))//backup from cone
                .splineTo(new Vector2d(55,-24), Math.toRadians(90))//j turn
                .splineTo(new Vector2d(47, -15), Math.toRadians(180))//turn left
                .splineToSplineHeading(new Pose2d(20, -16, Math.toRadians(90)), Math.toRadians(180))//approach pole
                .addDisplacementMarker(() -> {config.liftUp();})
                .forward(7)
                .build();

        TrajectorySequence back = drive.trajectorySequenceBuilder(num1.end())
                .back(7)
                .build();
        TrajectorySequence red = drive.trajectorySequenceBuilder(back.end())
                .splineToConstantHeading(new Vector2d(12, -12), Math.toRadians(90))//backup from cone
                .splineToConstantHeading(new Vector2d(12, -33), Math.toRadians(90))//backup from cone
                .build();
        TrajectorySequence green = drive.trajectorySequenceBuilder(back.end())
                .splineToConstantHeading(new Vector2d(36, -12), Math.toRadians(90))//backup from cone
                .splineToConstantHeading(new Vector2d(36, -33), Math.toRadians(90))//backup from cone
                .build();
        TrajectorySequence blue = drive.trajectorySequenceBuilder(back.end())
                .splineToConstantHeading(new Vector2d(20, -24), Math.toRadians(90))//backup from cone
                .splineToConstantHeading(new Vector2d(58, -24), Math.toRadians(90))//backup from cone
                .build();
        TrajectorySequence dropCone = drive.trajectorySequenceBuilder(back.end())
                .addTemporalMarker(2,() -> {config.openServos();})
                .build();


        waitForStart();
        if (isStopRequested()) return;

        while (opModeIsActive() && !isStopRequested()) {
            config.closeServos();
            drive.followTrajectorySequence(scan);
            String color = config.getColor();
            drive.followTrajectorySequence(num1);
            drive.followTrajectorySequence(dropCone);
            drive.followTrajectorySequence(back);
            config.liftDown();
            if (color == "Red") {
                drive.followTrajectorySequence(red);
            }
            else if (color == "Green"){
                drive.followTrajectorySequence(green);
            }
            else if (color == "Blue"){
                drive.followTrajectorySequence(blue);
            }
            sleep(50000);


        }
        telemetry.addData("Lift", "target: %d; current: %d", config.lift.getTargetPosition(), config.lift.getCurrentPosition());
    }
}