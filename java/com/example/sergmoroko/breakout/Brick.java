package com.example.sergmoroko.breakout;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by ssss on 23.07.2016.
 */
public class Brick extends GameObject {

private Bitmap brickImage;

    public Brick(Bitmap img, int x, int y){
        this.x = x;
        this.y = y;
        width = GameConstants.BRICK_WIDTH;
        height = GameConstants.BRICK_HEIGHT;
        this.image = img;
        gameObjectArrayList.add(this);
    }

//    public void draw(Canvas canvas){
//
//        canvas.drawBitmap(brickImage, x, y, null);
//    }

    public void update(int x, int y){

    }
}
