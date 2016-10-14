package com.example.sergmoroko.breakout;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;


public class Ball extends GameObject {

    private static Bitmap scaledBall = Bitmap.createScaledBitmap(BitmapFactory.decodeResource
                    (GameView.getInstance().getResources(), R.drawable.breakout_ball_3d),
            GameConstants.BALL_SIZE, GameConstants.BALL_SIZE, false);


    public Ball() {

        x = GameConstants.BALL_X_COORD;
        y = GameConstants.BALL_Y_COORD - 1;
        width = GameConstants.BALL_SIZE;
        height = GameConstants.BALL_SIZE;
        gameObjectArrayList.add(this);
        this.image = scaledBall;


        // X movement direction on start
        Random rGenStart = new Random();
        dx = rGenStart.nextInt(GameConstants.BALL_MAX_SPEED + 1) + GameConstants.BALL_MIN_SPEED;
        if (rGenStart.nextBoolean()) {
            dx = -dx;
        }

        dy = -(rGenStart.nextInt(GameConstants.BALL_MAX_SPEED + 1) + GameConstants.BALL_MIN_SPEED);

    }


    public void update() {


        // Changes a horizontal movement direction after collision with walls.
        if (getX() <= 0 && dx < 0 || getX() >= GameConstants.DISPLAY_WIDTH - GameConstants.BALL_SIZE && dx > 0) {
            Random rGen = new Random();

            if (dx < 0) {
                dx = rGen.nextInt(GameConstants.BALL_MAX_SPEED + 1) + GameConstants.BALL_MIN_SPEED;
            } else {
                dx = -(rGen.nextInt(GameConstants.BALL_MAX_SPEED + 1) + GameConstants.BALL_MIN_SPEED);
            }
        }

        // Changes a vertical movement direction after collision with a top, or paddle
        if (getY() <= GameConstants.PANEL_HEIGHT && dy < 0)

        {
            dy = -dy;
        }

        x = x + dx;
        y = y + dy;

    }
}