package com.example.sergmoroko.breakout;

import android.graphics.Bitmap;

/**
 * Created by ssss on 17.08.2016.
 */
public class GameTopPanel extends GameObject{
    public GameTopPanel(Bitmap img){
        width = GameConstants.DISPLAY_WIDTH;
        height = GameConstants.PANEL_HEIGHT;
        x = 0;
        y = 0;
        this.image = img;
        gameObjectArrayList.add(this);
    }
}
