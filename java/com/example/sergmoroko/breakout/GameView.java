package com.example.sergmoroko.breakout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by ssss on 19.07.2016.
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    public static GameView gameView;

    private static MainThread thread;
    private Background bg;
    private Paddle paddle;
    private Ball ball;
    private GameTopPanel gameTopPanel;

    private int paddleCenterX;

    private boolean gameStarted;

    private GameObject collGameobject;


    private int livesQty = 0;


    // Scaled it, because in emulator, size of drawen object is way bigger than original bitmap

    private Bitmap scaledPaddle = Bitmap.createScaledBitmap(BitmapFactory.decodeResource
            (getResources(), R.drawable.breakout_paddle),GameConstants.PADDLE_WIDTH, GameConstants.PADDLE_HEIGHT, false);
    private Bitmap scaledBackground = Bitmap.createScaledBitmap(BitmapFactory.decodeResource
            (getResources(), R.drawable.breakout_background),GameConstants.DISPLAY_WIDTH, GameConstants.DISPLAY_HEIGHT, false);
//    private Bitmap scaledBall = Bitmap.createScaledBitmap(BitmapFactory.decodeResource
//            (getResources(), R.drawable.breakout_ball),GameConstants.BALL_SIZE, GameConstants.BALL_SIZE, false);
//    private Bitmap scaledBrick = Bitmap.createScaledBitmap(BitmapFactory.decodeResource
//            (getResources(), R.drawable.breakout_brick),GameConstants.BRICK_WIDTH, GameConstants.BRICK_HEIGHT, false);
    private Bitmap scaledHeart = Bitmap.createScaledBitmap(BitmapFactory.decodeResource
            (getResources(), R.drawable.breakout_heart),GameConstants.HEART_SIZE, GameConstants.HEART_SIZE, false);
    private Bitmap scaledGameTopPanel = Bitmap.createScaledBitmap(BitmapFactory.decodeResource
            (getResources(), R.drawable.breakout_top_panel),GameConstants.DISPLAY_WIDTH, GameConstants.PANEL_HEIGHT, false);




    public GameView(Context context) {
        super(context);

        gameView = this;

        // Adds the callback to the SurfaceHolder to get events
        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        //make gamePanel focusable so it can handle events
        setFocusable(true);


    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        // Creating game objects (paddle, bricks, ball...)
        createGameObjects();


        //we can safely start the game loop
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while(retry)
        {
            try{thread.setRunning(false);
                thread.join();

            }catch(InterruptedException e){e.printStackTrace();}
            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        gameStarted = true;

        paddleCenterX = (int) event.getX() - (paddle.getWidth() / 2);

        return true;

    }

    public void update() {

        if (!gameStarted){
            paddleCenterX = (GameConstants.DISPLAY_WIDTH/2) - (paddle.getWidth()/2);
        }
        else {

            collGameobject = ball.getCollidingObject();

//            if(collGameobject == gameTopPanel){
//                ball.setDy(-ball.getDy());
//                collGameobject = null;
//            }

            if (collGameobject != null && collGameobject != paddle && collGameobject != bg && collGameobject != gameTopPanel){

                Rect intersectionRect = ball.getIntersectionRect(collGameobject);

                collGameobject.remove();

                collGameobject = null;


                // Math.abs() were used, because Rect.width() / Rect.height()
                // does not check for a valid rectangle (i.e. left <= right) so the result may be negative.
                if (Math.abs(intersectionRect.width()) < Math.abs(intersectionRect.height())) {
                    // DO HORIZONTAL COLLISION STUFF
                    ball.setDx(-ball.getDx());
                }
                else {
                    // DO VERTICAL COLLISION STUFF
                    ball.setDy(-ball.getDy());
                }





                // Game score
                Score.setScore(Score.getScore() + GameConstants.SCORE_MULTIPLIER);
                if (livesQty < GameConstants.LIVES_MAX_QTY &&
                        Score.getScore() % (GameConstants.BRICK_ROWS_QTY * GameConstants.BRICKS_IN_ROW_QTY * GameConstants.SCORE_MULTIPLIER) == 0){

                    new Heart(scaledHeart, livesQty);
                    livesQty++;
                }
            }
            paddle.update(paddleCenterX);
            ball.update(paddle.getX());

            // if ball falls down
            if (ballDown()){
                // put it on paddle
                nextTry();

            }
        }
    }


    @SuppressLint("MissingSuperCall")
    @Override
    public void draw(Canvas canvas){

        for(GameObject object: GameObject.gameObjectArrayList){
            object.draw(canvas);
        }
        for(GameObject object: Heart.heartList){
            object.draw(canvas);
        }

        Score.draw(canvas);

    }

    public static void pauseGame(){
        thread.pauseThread();
    }

    public static void resumeGame(){
        thread.resumeThread();
    }

    public void restartGame(){

        gameStarted = false;
        GameObject.removeAll();
        Heart.removeAllHearts();
        livesQty = 0;
        Score.setScore(0);
        createGameObjects();
    }

    private boolean ballDown(){
        return  (ball.getY() >= GameConstants.DISPLAY_HEIGHT - ball.getHeight());
    }

    private void nextTry(){
        gameStarted = false;
        ball.remove();
        paddle.remove();
        Heart.removeHeart();
        livesQty--;
        ball = new Ball();
        paddle = new Paddle(scaledPaddle);
    }

    private void createGameObjects(){

        bg = new Background(scaledBackground);

        gameTopPanel = new GameTopPanel(scaledGameTopPanel);

        paddle = new Paddle(scaledPaddle);

        ball = new Ball();

        for(int i = 0; i < GameConstants.BRICK_ROWS_QTY; i ++){
            int y = i * (GameConstants.BRICK_HEIGHT +
                    GameConstants.BRICKS_SEPARATOR) + GameConstants.BRICKS_Y_OFFSET;
            int x = GameConstants.BRICKS_SEPARATOR;
            for (int j = 0; j < GameConstants.BRICKS_IN_ROW_QTY; j++){


                char c = Level.getLevel()[i][j];
//                new Brick(scaledBrick, i * (GameConstants.BRICK_WIDTH + GameConstants.BRICKS_SEPARATOR) +
//                        GameConstants.BRICKS_SEPARATOR + GameConstants.BRICK_X_OFFSET, j * (GameConstants.BRICK_HEIGHT +
//                        GameConstants.BRICKS_SEPARATOR) + GameConstants.BRICKS_Y_OFFSET);
                switch (c){
                    case ' ':
                        x = x + GameConstants.BRICKS_SEPARATOR + GameConstants.BRICK_WIDTH;
                        break;
                    case 'y':
                        new Brick(GameConstants.BRICK_TYPE_YELLOW, x, y);
                        x = x + GameConstants.BRICKS_SEPARATOR + GameConstants.BRICK_WIDTH;
                        break;
                    case 'r':
                        new Brick(GameConstants.BRICK_TYPE_RED, x, y);
                        x = x + GameConstants.BRICKS_SEPARATOR + GameConstants.BRICK_WIDTH;
                        break;
                    case 'g':
                        new Brick(GameConstants.BRICK_TYPE_GREEN, x, y);
                        x = x + GameConstants.BRICKS_SEPARATOR + GameConstants.BRICK_WIDTH;
                        break;
                    case 's':
                        new Brick(GameConstants.BRICK_TYPE_STONE, x, y);
                        x = x + GameConstants.BRICKS_SEPARATOR + GameConstants.BRICK_WIDTH;
                        break;
                }
            }
        }

        for (int i = GameConstants.LIVES_START_QTY; i >= 0; i--){
            new Heart(scaledHeart, livesQty);
            livesQty++;
        }
    }

    public static GameView getInstance(){
        return gameView;
    }
}