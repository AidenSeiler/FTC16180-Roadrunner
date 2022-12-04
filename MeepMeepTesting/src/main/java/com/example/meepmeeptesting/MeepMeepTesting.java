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
                        //Detect Color
                        .strafeRight(0.5) //inlinWithCone
                        .lineTo(new Vector2d(31,-41.25)) //approachCone

                        //Get to first cone
                        .splineTo(new Vector2d(62, -35.25), Math.toRadians(0))//moveCone
                        .lineToLinearHeading(new Pose2d(58.75, -16, Math.toRadians(90)))//reverse, reverse
                        .lineToConstantHeading(new Vector2d(23.5, -16))//strafe to pole
                        .lineTo(new Vector2d(23.5, -4))//approach pole and armup at the same time
                        //armup
                        .lineTo(new Vector2d(23.5, -16))//retracts from pole
                        //armdown

                        //Second Cone
                        .lineToLinearHeading(new Pose2d(66, -11.75, Math.toRadians(0)))//cone2
                        .lineToLinearHeading(new Pose2d(23.5, -16, Math.toRadians(90)))//back to pole
                        .lineTo(new Vector2d(23.5, -4))//approach pole and armup at the same time
                        .lineTo(new Vector2d(23.5, -16))//retracts from pole

                        //Third Cone
                        .lineToLinearHeading(new Pose2d(66, -11.75, Math.toRadians(0)))//cone3
                        .lineToLinearHeading(new Pose2d(23.5, -16, Math.toRadians(90)))//back to pole
                        .lineTo(new Vector2d(23.5, -4))//approach pole and armup at the same time
                        .lineTo(new Vector2d(23.5, -16))//retracts from pole

                        //Fourth Cone
                        .lineToLinearHeading(new Pose2d(66, -11.75, Math.toRadians(0)))//cone4
                        .lineToLinearHeading(new Pose2d(23.5, -16, Math.toRadians(90)))//back to pole
                        .lineTo(new Vector2d(23.5, -4))//approach pole and armup at the same time

                        //ColorPark
                        .lineTo(new Vector2d(23.5, -11.75))//approach pole and armup at the same time
                        .lineTo(new Vector2d(35.25, -11.75))//approach pole and armup at the same time
                        .lineToConstantHeading(new Vector2d(35.25, -35.25))//approach pole and armup at the same time
                        .lineToConstantHeading(new Vector2d(11.75, -35.25))//approach pole and armup at the same time






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