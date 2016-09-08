package com.example.sergmoroko.breakout;

import android.annotation.SuppressLint;
import android.app.Activity;
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

    public static GameView gameView;

    private static MainThread thread;
    private Background bg;
    private Paddle paddle;
    private Ball ball;
    private GameTopPanel gameTopPanel;

    private int paddleCenterX;

    private boolean gameStarted;
    private Context mContext = getContext();
    int levelch;


    // Scaled it, because in emulator, size of drawen object is way bigger than original bitmap

    private Bitmap scaledPaddle = Bitmap.createScaledBitmap(BitmapFactory.decodeResource
            (getResources(), R.drawable.breakout_paddle_blue),GameConstants.PADDLE_WIDTH, GameConstants.PADDLE_HEIGHT, false);
    private Bitmap scaledBackground = Bitmap.createScaledBitmap(BitmapFactory.decodeResource
            (getResources(), R.drawable.breakout_background),GameConstants.DISPLAY_WIDTH, GameConstants.DISPLAY_HEIGHT, false);
//    private Bitmap scaledBall = Bitmap.createScaledBitmap(BitmapFactory.decodeResource
//            (getResources(), R.drawable.breakout_ball),GameConstants.BALL_SIZE, GameConstants.BALL_SIZE, false);
//    private Bitmap scaledBrick = Bitmap.createScaledBitmap(BitmapFactory.decodeResource
//            (getResources(), R.drawable.breakout_brick),GameConstants.BRICK_WIDTH, GameConstants.BRICK_HEIGHT, false);
//    private Bitmap scaledHeart = Bitmap.createScaledBitmap(BitmapFactory.decodeResource
//            (getResources(), R.drawable.breakout_heart),GameConstants.HEART_SIZE, GameConstants.HEART_SIZE, false);
    private Bitmap scaledGameTopPanel = Bitmap.createScaledBitmap(BitmapFactory.decodeResource
            (getResources(), R.drawable.breakout_top_panel),GameConstants.DISPLAY_WIDTH, GameConstants.PANEL_HEIGHT, false);




    public GameView(Context context, int level) {
        super(context);
        levelch = level;


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

            GameObject collGameObject = ball.getCollidingObject();

            if (collGameObject != null && collGameObject != ball && collGameObject != bg && collGameObject != gameTopPanel) {

                GameObject.Line collSide = ball.getCollidingSide(collGameObject);
                if (collSide != null) {


                    //if (collGameObject == paddle) {
                        // top or bottom
                        if (collSide.y1() == collSide.y2()) {
                            // top
                            if (collSide.y1() == collGameObject.getY() && ball.getDy() > 0) {
                                ball.setDy(-ball.getDy());
                            }
                            // bottom
                            if (collSide.y2() != collGameObject.getY() && ball.getDy() < 0) {
                                ball.setDy(-ball.getDy());
                            }
                        }
                        // left or right
                        if (collSide.x1() == collSide.x2()) {
                            // left
                            if (collSide.x1() == collGameObject.getX() && ball.getDx() > 0) {
                                ball.setDx(-ball.getDx());
                            }
                            // right
                            if (collSide.x1() != collGameObject.getX() && ball.getDx() < 0) {
                                ball.setDx(-ball.getDx());
                            }
                        }
                   // }


//                    // top or bottom
//                    if (collSide.y1() == collSide.y2()) {
//                        ball.setDy(-ball.getDy());
//                    }
//
//                    // right or left side
//                    if (collSide.x1() == collSide.x2()) {
//                        ball.setDx(-ball.getDx());
//                    }

                    if (collGameObject != paddle) {
                        collGameObject.remove();

                        // Game score
                        Score.increaseScore();
                        //  }
                    }
                }
                //}

            }
                paddle.update(paddleCenterX);
                ball.update(paddle.getX());

            // if ball falls down
            if (ballDown()) {
                // put it on paddle
                nextTry();

            }
            }
        }



    @SuppressLint("MissingSuperCall")
    @Override
    public void draw(Canvas canvas){

        bg.draw(canvas);

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


                char c = Level.getLevel(levelch)[i][j];
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
            new Heart();
        }
    }

    public static GameView getInstance(){
        return gameView;
    }

    public static void stopThread(){
        thread.stopThread();
    }

    public void exitGame() {
        synchronized (getHolder()) {
            //quit to main menu
            GameObject.removeAll();
            Heart.removeAllHearts();
            Score.setScore(0);
            thread.stopThread();
            ((Activity) mContext).finish();

        }
    }
}