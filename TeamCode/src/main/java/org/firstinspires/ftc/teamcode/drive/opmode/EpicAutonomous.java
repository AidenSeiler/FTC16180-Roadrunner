package org.firstinspires.ftc.teamcode.drive.opmode;

import org.firstinspires.ftc.teamcode.drive.HardwareConfig;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="SuperFunkyAuto", group="Linear Opmode")

public class EpicAutonomous extends LinearOpMode {
    private HardwareConfig config = new HardwareConfig();

    @Override
    public void runOpMode() {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        config.init(hardwareMap);
        config.setMotorBehave();
        config.liftEncoder();

        Pose2d startPose = new Pose2d(10, -8, Math.toRadians(90));
        drive.setPoseEstimate(startPose);

        Trajectory inlineWithSignal = drive.trajectoryBuilder(startPose)
                .strafeRight(0.5) //inlinWithCone
                .build();

        Trajectory approachCone = drive.trajectoryBuilder(inlineWithSignal.end())
                .lineTo(new Vector2d(31,-41.25)) //approachCone
                .build();

        Trajectory moveCone = drive.trajectoryBuilder(approachCone.end())
                .splineTo(new Vector2d(62, -35.25), Math.toRadians(0))//moveCone
                .build();

        Trajectory gotoPole1 = drive.trajectoryBuilder(moveCone.end())
                .lineToLinearHeading(new Pose2d(58.75, -16, Math.toRadians(90)))//reverse, reverse
                .lineToConstantHeading(new Vector2d(23.5, -16))//strafe to pole
                .build();

        Trajectory Pole1 = drive.trajectoryBuilder(gotoPole1.end())
                .lineTo(new Vector2d(23.5, -4))//approach pole and armup at the same time
                .build();

        Trajectory cone2 = drive.trajectoryBuilder(Pole1.end())
                .lineTo(new Vector2d(23.5, -16))//retracts from pole
                .lineToLinearHeading(new Pose2d(66, -11.75, Math.toRadians(0)))//cone2
                .lineToLinearHeading(new Pose2d(23.5, -16, Math.toRadians(90)))//back to pole
                .build();

        Trajectory Pole2 = drive.trajectoryBuilder(cone2.end())
                .lineTo(new Vector2d(23.5, -4))//approach pole and armup at the same time
                .build();

        Trajectory cone3 = drive.trajectoryBuilder(Pole2.end())
                .lineTo(new Vector2d(23.5, -16))//retracts from pole
                .lineToLinearHeading(new Pose2d(66, -11.75, Math.toRadians(0)))//cone2
                .lineToLinearHeading(new Pose2d(23.5, -16, Math.toRadians(90)))//back to pole
                .build();

        Trajectory Pole3 = drive.trajectoryBuilder(cone3.end())
                .lineTo(new Vector2d(23.5, -4))//approach pole and armup at the same time
                .build();

        Trajectory park1 = drive.trajectoryBuilder(Pole3.end())
                .lineTo(new Vector2d(23.5, -11.75))//back up
                .lineTo(new Vector2d(35.25, -11.75))//right
                .lineToConstantHeading(new Vector2d(35.25, -35.25))//back
                .lineToConstantHeading(new Vector2d(11.75, -35.25))//left
                .build();
        Trajectory park2 = drive.trajectoryBuilder(Pole3.end())
                .lineTo(new Vector2d(23.5, -11.75))//back
                .lineTo(new Vector2d(35.25, -11.75))//right
                .lineToConstantHeading(new Vector2d(35.25, -35.25))//back
                .build();
        Trajectory park3 = drive.trajectoryBuilder(Pole3.end())
                .lineTo(new Vector2d(23.5, -11.75))//back up
                .lineTo(new Vector2d(35.25, -11.75))//right
                .lineToConstantHeading(new Vector2d(35.25, -35.25))//back
                .lineToConstantHeading(new Vector2d(58.75, -35.25))//right
                .build();


        waitForStart();
        if (isStopRequested()) return;

        while (opModeIsActive() && !isStopRequested()) {
            //pickup first cone
            config.liftDown();
            config.closeServos();

            //goto scan and scan
            drive.followTrajectory(inlineWithSignal);
            drive.followTrajectory(approachCone);
            String coneColor = config.getColor();

            //goto first cone
            drive.followTrajectory(moveCone);
            drive.followTrajectory(gotoPole1);
            config.liftUp();
            drive.followTrajectory(Pole1);
            config.openServos();
            config.liftDown();


        }
        telemetry.addData("Lift", "target: %d; current: %d", config.lift.getTargetPosition(), config.lift.getCurrentPosition());
    }
}