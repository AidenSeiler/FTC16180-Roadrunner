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
                .setConstraints(35, 50, Math.toRadians(180), Math.toRadians(180), 12)

                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(new Pose2d(30.5, -65, Math.toRadians(90)))
                        .lineToConstantHeading(new Vector2d(33,-45))//approach cone and then scan
                        .splineTo(new Vector2d(65,-36), Math.toRadians(0))//turn right
                                .splineToConstantHeading(new Vector2d(58, -36), Math.toRadians(0))//push cone
                                .splineTo(new Vector2d(59, -30), Math.toRadians(90))//backwards turn
                                .splineTo(new Vector2d(47, -12), Math.toRadians(180))//turn left
                                .splineToSplineHeading(new Pose2d(23.5, -12, Math.toRadians(90)), Math.toRadians(180))//approach pole
                                .splineToConstantHeading(new Vector2d(23.5, -5), Math.toRadians(180))//gotoPole
                                .splineToConstantHeading(new Vector2d(23.5, -12), Math.toRadians(180))//goawayfromPole
                                .splineToSplineHeading(new Pose2d(64, -12, Math.toRadians(0)), Math.toRadians(180))//approach pole







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