package com.example.sergmoroko.breakout;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by ssss on 29.07.2016.
 */
public class Heart extends GameObject {

    protected static ArrayList<GameObject> heartList = new ArrayList<>();
    //protected static int heartQty;

    public Heart(Bitmap img, int livesQty){

        width = GameConstants.HEART_SIZE;
        height = GameConstants.HEART_SIZE;
        x = GameConstants.DISPLAY_WIDTH - (livesQty * (width + width/2));
        y = GameConstants.HEART_Y_OFFSET - height;

        this.image = img;

        heartList.add(this);
    }

    public static void removeHeart(){
        if(!heartList.isEmpty()) {
            heartList.remove(heartList.size() - 1);
        }
    }

    public static void removeAllHearts(){
        heartList.clear();
    }
    public void add(){

        heartList.add(this);
    }
}
