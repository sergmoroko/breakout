package com.example.sergmoroko.breakout;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by ssss on 19.07.2016.
 */
public class Background {
    private Bitmap backgroundImage;
    private int x, y;

    public Background(Bitmap image){
        backgroundImage = image;
    }
    public void update(){

    }
    public void draw(Canvas canvas){

    canvas.drawBitmap(backgroundImage, x, y, null);
    }
}
