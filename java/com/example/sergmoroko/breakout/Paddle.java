package com.example.sergmoroko.breakout;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by ssss on 19.07.2016.
 */
public class Paddle extends GameObject{

    private Bitmap paddleImage;




    public Paddle(Bitmap img){

        dx = GameConstants.PADDLE_X_VELOCITY;
        x = GameConstants.PADDLE_X_COORD;
        y = GameConstants.PADDLE_Y_COORD;
        width = GameConstants.PADDLE_WIDTH;
        height = GameConstants.PADDLE_HEIGHT;
        this.image = img;
        gameObjectArrayList.add(this);

    }

//    public void draw(Canvas canvas){
//
//        canvas.drawBitmap(paddleImage, x, y, null);
//    }

    public void update(int x) {

        moveToX(x);

        if (getX()  <= 0) {
            setX(0);
        }

        if (getX() >= GameConstants.DISPLAY_WIDTH - getWidth()) {
            setX(GameConstants.DISPLAY_WIDTH - getWidth());
        }
    }

    // Slightly movement to passed x-coordinate
    private void moveToX(int x){
        if (getX() + dx <= x ){
            setX(getX() + dx);

        }
        if ((getX() - dx >= x)){
            setX(getX() - dx);
        }


        if ((getX() < x && x - getX() < dx )|| (getX() > x && getX() - x < dx)){
            setX(x);
        }

    }

}
