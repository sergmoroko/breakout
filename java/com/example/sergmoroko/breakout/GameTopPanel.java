package com.example.sergmoroko.breakout;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


public class GameTopPanel extends GameObject {

    private Bitmap scaledGameTopPanel = Bitmap.createScaledBitmap(BitmapFactory.decodeResource
            (GameView.getInstance().getResources(), R.drawable.breakout_top_panel), GameConstants.DISPLAY_WIDTH, GameConstants.PANEL_HEIGHT, false);

    public GameTopPanel() {
        width = GameConstants.DISPLAY_WIDTH;
        height = GameConstants.PANEL_HEIGHT;
        x = 0;
        y = 0;
        this.image = scaledGameTopPanel;
        gameObjectArrayList.add(this);
    }
}
