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
                .setConstraints(50, 100, Math.toRadians(180), Math.toRadians(180), 12)

                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(new Pose2d(30.5, -65, Math.toRadians(90)))
                        .lineToConstantHeading(new Vector2d(33,-46))//inlinWithCone
                        .splineTo(new Vector2d(34,-41), Math.toRadians(0))//inlinWithCone






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