package com.example.sergmoroko.breakout;

import android.content.res.Resources;

public class GameConstants {
    /** GAME */
    public static final int GAME_STATE_LOSE = 1;
    public static final int GAME_STATE_WIN = 2;

    /** THREAD */
    public static final int FPS = 60;
    public static final int SECONDS_DIVIDER = 1000000;


    /** SCREEN SIZE */
    public static final int DISPLAY_WIDTH = Resources.getSystem().getDisplayMetrics().widthPixels;
    public static final int DISPLAY_HEIGHT = Resources.getSystem().getDisplayMetrics().heightPixels;

    /** DIALOGS*/
    public static final int DIALOG_WIDTH = (int) (DISPLAY_WIDTH*0.65);
    public static final int ABOUT_DIALOG_WIDTH = (int) (DISPLAY_WIDTH*0.9);
    public static final int DIALOG_HEIGHT = DISPLAY_HEIGHT/2;


    /** PADDLE */
    public static final int PADDLE_HEIGHT = (int) (DISPLAY_HEIGHT/30);
    public static final int PADDLE_WIDTH = (int) (DISPLAY_WIDTH/4);
    public static final int PADDLE_Y_COORD = (int) (DISPLAY_HEIGHT * 0.85);
    public static final int PADDLE_X_COORD = (int) ((DISPLAY_WIDTH - PADDLE_WIDTH)/2);
    public static final int PADDLE_X_VELOCITY = (int) (DISPLAY_WIDTH/5);

    /** BALL */
    public static final int BALL_SIZE = (int)(PADDLE_WIDTH/5);
    public static final int BALL_X_COORD = (int)((DISPLAY_WIDTH - BALL_SIZE)/2);
    public static final int BALL_Y_COORD = (int)(PADDLE_Y_COORD - BALL_SIZE);

    /** Minimum speed of a ball, pixels per frame */
    public static final int BALL_MIN_SPEED = 4;

    /** Maximum speed of a ball, pixels per frame */
    public static final int BALL_MAX_SPEED = 6;


    /** BRICKS */
    public static final int BRICK_ROWS_QTY = 10;
    public static final int BRICKS_IN_ROW_QTY = 10;

    public static final int BRICKS_Y_OFFSET = (int) (DISPLAY_HEIGHT * 0.15);
    public static final int BRICKS_SEPARATOR = (int) (DISPLAY_WIDTH * 0.01);
    public static final int BRICK_WIDTH = (DISPLAY_WIDTH - (BRICKS_IN_ROW_QTY + 1) * BRICKS_SEPARATOR) / BRICKS_IN_ROW_QTY;
    public static final int BRICK_HEIGHT = (int) (DISPLAY_HEIGHT * 0.03);
    public static final int BRICK_X_OFFSET = (int) (DISPLAY_WIDTH -((BRICK_WIDTH * BRICKS_IN_ROW_QTY) +
            (BRICKS_SEPARATOR * (BRICKS_IN_ROW_QTY + 1)))) / 2;
    public static final String BRICK_TYPE_YELLOW = "yellow";
    public static final String BRICK_TYPE_RED = "red";
    public static final String BRICK_TYPE_GREEN = "green";
    public static final String BRICK_TYPE_STONE = "stone";
    public static final String BRICK_TYPE_PURPLE = "purple";

    /** LIVES AND SCORE */

    public static final int TEXT_SIZE = DISPLAY_HEIGHT / 50;

    // Convert the pixels to points, because 1 point in text size equals 0.75 of pixel
    public static final int PT_TEXT_SIZE = (int) (TEXT_SIZE / 0.75);


    public static final int SCORE_Y_OFFSET = 30;
    public static final int HEART_Y_OFFSET = SCORE_Y_OFFSET;
    public static final int HEART_SIZE = TEXT_SIZE;
    public static final int LIVES_START_QTY = 3;
    public static final int LIVES_MAX_QTY = 5;
    public static final int SCORE_MULTIPLIER = 10;
    public static final int PANEL_HEIGHT = SCORE_Y_OFFSET + (SCORE_Y_OFFSET - TEXT_SIZE);
    public static final int PAUSE_BUTTON_WIDTH = DISPLAY_WIDTH/8;
    public static final int BONUS_HEART_SCORE = SCORE_MULTIPLIER * 50;

    /** SPLASH SCREEN */

    public static final int SPLASH_LOGO_WIDTH = (int) (DISPLAY_WIDTH * 0.95);
    public static final int SPLASH_ANIMATION_WIDTH = (int) (DISPLAY_WIDTH * 0.5);
    public static final int ANIMATION_FRAME_DURATION = 200;
    public static final int ANIMATION_DELAY = 500;

    /** LEVELS AND LEVEL CHOOSING ACTIVITY*/
    public static final int LEVEL_NUMBER = 10;
    public static final int TOP_PANEL_BUTTONS_SIZE = (int) (DISPLAY_HEIGHT * 0.07);
    public static final int BOTTOM_PANEL_HEIGHT = TOP_PANEL_BUTTONS_SIZE / 2;
    public static final int LEVEL_BUTTON_HEIGHT = DISPLAY_WIDTH / 2;

    public static final int LEVEL_1 = 1;
    public static final int LEVEL_2 = 2;
    public static final int LEVEL_3 = 3;
    public static final int LEVEL_4 = 4;
    public static final int LEVEL_5 = 5;
    public static final int LEVEL_6 = 6;
    public static final int LEVEL_7 = 7;
    public static final int LEVEL_8 = 8;
    public static final int LEVEL_9 = 9;
    public static final int LEVEL_10 = 10;

    /** INTENT EXTRAS AND SHARED PREFERENCES STRINGS */

    public static final String SP_NAME = "MyPref";
    public static final String SP_HIGHSCORE = "highScore";
    public static final String SP_SOUND = "sound";
    public static final String INTENT_LEVEL = "level";


}