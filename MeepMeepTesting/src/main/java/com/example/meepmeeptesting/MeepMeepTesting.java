package com.example.meepmeeptesting;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                .setConstraints(35, 100, Math.toRadians(180), Math.toRadians(180), 12)

                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(new Pose2d(30.5, -65, Math.toRadians(90)))
                        .lineToConstantHeading(new Vector2d(32,-43))//approach cone and then scan
                        .splineToSplineHeading(new Pose2d(28,-36, Math.toRadians(0)), Math.toRadians(0))//turn right

                        .splineTo(new Vector2d(57,-33), Math.toRadians(0))//turn right
                        .splineToConstantHeading(new Vector2d(50, -33), Math.toRadians(0))//backup from cone
                        .splineTo(new Vector2d(55,-24), Math.toRadians(90))//j turn
                        .splineTo(new Vector2d(47, -15), Math.toRadians(180))//turn left
                        .splineToSplineHeading(new Pose2d(17, -15, Math.toRadians(90)), Math.toRadians(180))//approach pole
                        .forward(4)
                        .waitSeconds(1.5)
                        .back(7)
                        .lineToLinearHeading(new Pose2d(58, -17, Math.toRadians(0)))//approach cone2











                        //MAKE A TRAJ TO GO TO THE POLE AND REPEAT IT
                        //servoopen
                        .build()

                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}