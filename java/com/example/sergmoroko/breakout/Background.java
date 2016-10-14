package com.example.sergmoroko.breakout;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


public class Background extends GameObject {

    private Bitmap scaledBackground = Bitmap.createScaledBitmap(BitmapFactory.decodeResource
            (GameView.getInstance().getResources(), R.drawable.breakout_background),
            GameConstants.DISPLAY_WIDTH, GameConstants.DISPLAY_HEIGHT, false);

    public Background() {
        x = 0;
        y = 0;
        this.image = scaledBackground;
    }
}
