package com.example.sergmoroko.breakout;
import android.content.res.Resources;

/**
 * Created by ssss on 20.07.2016.
 */
public class GameConstants {
    /** GAME */
    public static final int FPS = 30;

    /** SCREEN SIZE */
    public static final int DISPLAY_WIDTH = Resources.getSystem().getDisplayMetrics().widthPixels;
    public static final int DISPLAY_HEIGHT = Resources.getSystem().getDisplayMetrics().heightPixels;

    /** PADDLE */
    public static final int PADDLE_HEIGHT = (int) (DISPLAY_HEIGHT/30);
    public static final int PADDLE_WIDTH = (int) (DISPLAY_WIDTH/4);
    public static final int PADDLE_Y_COORD = (int) (DISPLAY_HEIGHT * 0.85);
    public static final int PADDLE_X_COORD = (int) ((DISPLAY_WIDTH - PADDLE_WIDTH)/2);
    public static final int PADDLE_X_VELOCITY = (int) (DISPLAY_WIDTH/10);

    /** BALL */
    public static final int BALL_SIZE = (int)(PADDLE_WIDTH/3);
    public static final int BALL_X_COORD = (int)((DISPLAY_WIDTH - BALL_SIZE)/2);
    public static final int BALL_Y_COORD = (int)(PADDLE_Y_COORD - BALL_SIZE);

    /** Minimum speed of a ball, pixels per frame */
    public static final int BALL_MIN_SPEED = 7;

    /** Maximum speed of a ball, pixels per frame */
    public static final int BALL_MAX_SPEED = 12;


    /** BRICKS */
    public static final int BRICK_ROWS_QTY = 10;
    public static final int BRICKS_IN_ROW_QTY = 10;
    public static final int BRICKS_Y_OFFSET = (int) (DISPLAY_HEIGHT * 0.1);
    public static final int BRICKS_SEPARATOR = (int) (DISPLAY_WIDTH * 0.01);
    public static final int BRICK_WIDTH = (DISPLAY_WIDTH - (BRICKS_IN_ROW_QTY + 1) * BRICKS_SEPARATOR) / BRICKS_IN_ROW_QTY;
    public static final int BRICK_HEIGHT = (int) (DISPLAY_HEIGHT * 0.015);



}

