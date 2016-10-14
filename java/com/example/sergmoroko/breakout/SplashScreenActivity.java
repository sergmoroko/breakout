package com.example.sergmoroko.breakout;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;


public class SplashScreenActivity extends Activity {
    ImageView animationImageView;
    ImageView logoImageView;
    AnimationDrawable ballAnimation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Turn title off
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Set to full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.splash_screen);

        logoImageView = (ImageView) findViewById(R.id.imageView2);
        logoImageView.getLayoutParams().width = GameConstants.SPLASH_LOGO_WIDTH;

        animationImageView = (ImageView) findViewById(R.id.imageView1);
        animationImageView.getLayoutParams().width = GameConstants.SPLASH_ANIMATION_WIDTH;


        try {
            // creates animation frames from images
            BitmapDrawable frame0 = (BitmapDrawable) getResources().getDrawable(R.drawable.ball_anim_0);
            BitmapDrawable frame1 = (BitmapDrawable) getResources().getDrawable(R.drawable.ball_anim_1);
            BitmapDrawable frame2 = (BitmapDrawable) getResources().getDrawable(R.drawable.ball_anim_2);
            BitmapDrawable frame3 = (BitmapDrawable) getResources().getDrawable(R.drawable.ball_anim_3);
            BitmapDrawable frame4 = (BitmapDrawable) getResources().getDrawable(R.drawable.ball_anim_4);
            BitmapDrawable frame5 = (BitmapDrawable) getResources().getDrawable(R.drawable.ball_anim_5);


            // adds frames to animation
            ballAnimation = new AnimationDrawable();
            ballAnimation.addFrame(frame0, GameConstants.ANIMATION_FRAME_DURATION);
            ballAnimation.addFrame(frame1, GameConstants.ANIMATION_FRAME_DURATION);
            ballAnimation.addFrame(frame2, GameConstants.ANIMATION_FRAME_DURATION);
            ballAnimation.addFrame(frame3, GameConstants.ANIMATION_FRAME_DURATION);
            ballAnimation.addFrame(frame4, GameConstants.ANIMATION_FRAME_DURATION);
            ballAnimation.addFrame(frame5, GameConstants.ANIMATION_FRAME_DURATION);

            // do not repeat animation
            ballAnimation.setOneShot(true);
            animationImageView.setBackgroundDrawable(ballAnimation);



            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {

                public void run() {

                    ballAnimation.start();

                    Handler mAnimationHandler = new Handler();
                    mAnimationHandler.postDelayed(new Runnable() {

                        public void run() {
                            onAnimationEnd();
                        }
                    }, getTotalDuration(ballAnimation));
                }
            }, GameConstants.ANIMATION_DELAY);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void onAnimationEnd(){
        Intent intent = new Intent(this, LevelChooseActivity.class);
        this.startActivity(intent);
    }

    public int getTotalDuration(AnimationDrawable animation) {

        int duration = 0;

        for (int i = 0; i < animation.getNumberOfFrames(); i++) {
            duration += animation.getDuration(i);
        }

        return duration;
    }
}
