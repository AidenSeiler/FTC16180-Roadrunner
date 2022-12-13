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
                .lineToConstantHeading(new Vector2d(33,-45))//inlinWithCone
                .build();
        Trajectory moveCone = drive.trajectoryBuilder(toCone.end())
                .splineTo(new Vector2d(65,-33), Math.toRadians(350))//inlinWithCone
                .splineTo(new Vector2d(58.75,-33), Math.toRadians(90))//inlinWithCone
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