package com.example.sergmoroko.breakout;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;


public class Score{

    private static int score = 0;


    public static void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize((float) (GameConstants.PT_TEXT_SIZE));


        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText(Integer.toString(score), GameConstants.SCORE_Y_OFFSET, GameConstants.SCORE_Y_OFFSET, paint);
    }

    public static void increaseScore(){
        setScore(getScore() + GameConstants.SCORE_MULTIPLIER);
        if (Heart.getLivesQty() < GameConstants.LIVES_MAX_QTY &&
                getScore() % (GameConstants.BONUS_HEART_SCORE) == 0) {

            new Heart();
            GameView.getInstance().increaseLivesQty();
        }
    }

    public static int getScore(){
        return score;
    }
    public static void setScore(int score){
        Score.score = score;
    }


}
