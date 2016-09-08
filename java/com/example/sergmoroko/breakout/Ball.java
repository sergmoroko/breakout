package com.example.sergmoroko.breakout;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.Random;

/**
 * Created by ssss on 22.07.2016.
 */
public class Ball extends GameObject {

    private static Bitmap scaledBall = Bitmap.createScaledBitmap(BitmapFactory.decodeResource
            (GameView.getInstance().getResources(), R.drawable.breakout_ball_3d),GameConstants.BALL_SIZE, GameConstants.BALL_SIZE, false);


    public Ball(){

        x = GameConstants.BALL_X_COORD;
        y = GameConstants.BALL_Y_COORD - 1;
        width = GameConstants.BALL_SIZE;
        height = GameConstants.BALL_SIZE;
        gameObjectArrayList.add(this);
        this.image = scaledBall;


        // X movement direction on start
        Random rGenStart = new Random();
        dx = rGenStart.nextInt(GameConstants.BALL_MAX_SPEED + 1) + GameConstants.BALL_MIN_SPEED;
        if (rGenStart.nextBoolean())
        {
            dx = -dx;
        }

        dy =  - (rGenStart.nextInt(GameConstants.BALL_MAX_SPEED + 1) + GameConstants.BALL_MIN_SPEED);

    }



    public void update(int paddleX){



        // Increases a vertical speed of ball by 1px on each level


        // Changes a horisontal movement direction after collision with walls.
        if(getX() <= 0 && dx < 0 || getX() >= GameConstants.DISPLAY_WIDTH - GameConstants.BALL_SIZE && dx > 0)
        {
            Random rGen = new Random();

            if (dx < 0 )
            {
                dx = rGen.nextInt(GameConstants.BALL_MAX_SPEED + 1) + GameConstants.BALL_MIN_SPEED;
            }
            else
            {
                dx = - (rGen.nextInt(GameConstants.BALL_MAX_SPEED + 1) + GameConstants.BALL_MIN_SPEED);
            }
        }
        // Changes a vertical movement direction after collision with a top, or paddle
        if( getY() <= GameConstants.PANEL_HEIGHT && dy < 0 )// || getX() + GameConstants.BALL_SIZE >= paddleX && getX()
               // <= paddleX + GameConstants.PADDLE_WIDTH && getY() + GameConstants.BALL_SIZE == GameConstants.PADDLE_Y_COORD)

        {
            dy = -dy;
        }

        x = x + dx;
        y = y + dy;

    }
}
