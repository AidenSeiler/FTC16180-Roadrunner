package org.firstinspires.ftc.teamcode.drive.opmode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.HardwareConfig;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Autonomous(name="SuperFunkyAutoTest", group="Linear Opmode")

public class autoTest extends LinearOpMode {
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
                .back(12) //inlinWithCone
                .build();




        waitForStart();
        if (isStopRequested()) return;

        while (opModeIsActive() && !isStopRequested()) {
            drive.followTrajectory(inlineWithSignal);
            config.closeServos();
            config.liftUp();


        }
        telemetry.addData("Lift", "target: %d; current: %d", config.lift.getTargetPosition(), config.lift.getCurrentPosition());
    }
}