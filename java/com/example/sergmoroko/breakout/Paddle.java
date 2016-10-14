package com.example.sergmoroko.breakout;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


public class Paddle extends GameObject {

    private Bitmap scaledPaddle = Bitmap.createScaledBitmap(BitmapFactory.decodeResource
            (GameView.getInstance().getResources(), R.drawable.breakout_paddle_blue),
            GameConstants.PADDLE_WIDTH, GameConstants.PADDLE_HEIGHT, false);

    public Paddle() {

        dx = GameConstants.PADDLE_X_VELOCITY;
        x = GameConstants.PADDLE_X_COORD;
        y = GameConstants.PADDLE_Y_COORD;
        width = GameConstants.PADDLE_WIDTH;
        height = GameConstants.PADDLE_HEIGHT;
        this.image = scaledPaddle;
        gameObjectArrayList.add(this);

    }


    public void update(int x) {

        moveToX(x);

        if (getX() <= 0) {
            setX(0);
        }

        if (getX() >= GameConstants.DISPLAY_WIDTH - getWidth()) {
            setX(GameConstants.DISPLAY_WIDTH - getWidth());
        }
    }

    // Slightly movement to passed x-coordinate
    private void moveToX(int x) {
        // move right
        if (getX() + dx <= x) {
            setX(getX() + dx);
        }
        // move left
        if ((getX() - dx >= x)) {
            setX(getX() - dx);
        }

        if ((getX() < x && x - getX() < dx) || (getX() > x && getX() - x < dx)) {
            setX(x);
        }
    }

}
