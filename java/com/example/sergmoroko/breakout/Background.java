package com.example.sergmoroko.breakout;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by ssss on 19.07.2016.
 */
public class Background extends GameObject{

    public Background(Bitmap img){
        x = 0;
        y = 0;
        this.image = img;
        gameObjectArrayList.add(this);
    }
//    public void update(){
//
//    }
//    public void draw(Canvas canvas){
//
//    canvas.drawBitmap(backgroundImage, x, y, null);
//    }
}
