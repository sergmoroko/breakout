package com.example.sergmoroko.breakout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


/**
 * Created by ssss on 19.07.2016.
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private Background bg;
    private Paddle paddle;
    private Ball ball;
    private Brick brick;

    private int paddleCenterX;

    private boolean gameStarted;

    private GameObject collGameobject;

    // Scaled it, because in emulator, size of drawen object is way bigger than original bitmap

    private Bitmap scaledPaddle = Bitmap.createScaledBitmap(BitmapFactory.decodeResource
            (getResources(), R.drawable.breakout_paddle),GameConstants.PADDLE_WIDTH, GameConstants.PADDLE_HEIGHT, false);
    private Bitmap scaledBackground = Bitmap.createScaledBitmap(BitmapFactory.decodeResource
            (getResources(), R.drawable.breakout_background),GameConstants.DISPLAY_WIDTH, GameConstants.DISPLAY_HEIGHT, false);
    private Bitmap scaledBall = Bitmap.createScaledBitmap(BitmapFactory.decodeResource
            (getResources(), R.drawable.breakout_ball),GameConstants.BALL_SIZE, GameConstants.BALL_SIZE, false);
    private Bitmap scaledBrick = Bitmap.createScaledBitmap(BitmapFactory.decodeResource
            (getResources(), R.drawable.breakout_brick),GameConstants.BRICK_WIDTH, GameConstants.BRICK_HEIGHT, false);




    public GameView(Context context) {
        super(context);

        // Adds the callback to the SurfaceHolder to get events
        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        //make gamePanel focusable so it can handle events
        setFocusable(true);


    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        bg = new Background(scaledBackground);

        paddle = new Paddle(scaledPaddle);

        ball = new Ball(scaledBall);

        for(int i = 0; i < GameConstants.BRICK_ROWS_QTY; i ++){
            for (int j = 0; j < GameConstants.BRICKS_IN_ROW_QTY; j++){
                brick = new Brick(scaledBrick, i * (GameConstants.BRICK_WIDTH + GameConstants.BRICKS_SEPARATOR) +
                GameConstants.BRICKS_SEPARATOR, j * (GameConstants.BRICK_HEIGHT + GameConstants.BRICKS_SEPARATOR) +
                GameConstants.BRICKS_Y_OFFSET);
            }
        }


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
        paddleCenterX = (int) event.getX() - (paddle.getWidth()/2);

        return true;

    }

    public void update() {

        if (!gameStarted){
            paddleCenterX = (GameConstants.DISPLAY_WIDTH/2) - (paddle.getWidth()/2);
        }
        else {

            collGameobject = ball.getCollidingObject();

            if (collGameobject != null && collGameobject != paddle){
                collGameobject.remove();
                ball.setDy(-ball.getDy());
                collGameobject = null;
            }
            paddle.update(paddleCenterX);
            ball.update(paddle.getX());

            if (ball.getY() >= GameConstants.DISPLAY_HEIGHT - ball.getHeight()){
                gameStarted = false;
                ball.remove();
                paddle.remove();
                ball = new Ball(scaledBall);
                paddle = new Paddle(scaledPaddle);

            }
        }
    }


    @SuppressLint("MissingSuperCall")
    @Override
    public void draw(Canvas canvas){

            bg.draw(canvas);
        //paddle.draw(canvas);
        ball.draw(canvas);


        for(GameObject object: GameObject.gameObjectArrayList){
            object.draw(canvas);
        }



    }
}
