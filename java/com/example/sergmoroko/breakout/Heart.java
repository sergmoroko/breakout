package com.example.sergmoroko.breakout;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

/**
 * Created by ssss on 29.07.2016.
 */
public class Heart extends GameObject {

    public static ArrayList<GameObject> heartList = new ArrayList<>();
    private static int heartQty = 0;
    private Bitmap scaledHeart = Bitmap.createScaledBitmap(BitmapFactory.decodeResource
            (GameView.getInstance().getResources(), R.drawable.breakout_heart_white),GameConstants.HEART_SIZE, GameConstants.HEART_SIZE, false);

    public Heart(){

        width = GameConstants.HEART_SIZE;
        height = GameConstants.HEART_SIZE;
        x = GameConstants.DISPLAY_WIDTH - (heartQty * (width + width/2));
        y = GameConstants.HEART_Y_OFFSET - height;

        this.image = scaledHeart;
        heartQty++;

        heartList.add(this);
    }

    public static void removeHeart(){
        if(!heartList.isEmpty()) {
            heartList.remove(heartList.size() - 1);
            heartQty--;
        }
    }

    public static void removeAllHearts(){
        heartList.clear();
        heartQty = 0;
    }

    public static int getLivesQty(){
        return heartQty;
    }
}
