package com.example.sergmoroko.breakout;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

/**
 * Created by ssss on 15.08.2016.
 */
public class Score{

    private static int score =0;


    public static void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize((float) (GameConstants.PT_TEXT_SIZE));


        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText(Integer.toString(score), GameConstants.SCORE_Y_OFFSET, GameConstants.SCORE_Y_OFFSET, paint);
    }

    public static int getScore(){
        return score;
    }
    public static void setScore(int score){
        Score.score = score;
    }


}
