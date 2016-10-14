package com.example.sergmoroko.breakout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.media.AudioManager;
import android.media.SoundPool;
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
    private int livesQty = GameConstants.LIVES_START_QTY;
    private int brickQty;

    private int paddleCenterX;

    private boolean gameStarted;
    private Context mContext = getContext();
    int levelch;
    private boolean soundOn;
    private boolean gameLosed;
    private boolean gameWin;





    SoundPool sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);

    int brickSoundId = sp.load(this.getContext(), R.raw.sound56_s, 1);
    int paddleSoundId = sp.load(this.getContext(), R.raw.paddle_sound1, 1);
    int loseSound = sp.load(this.getContext(), R.raw.lose_sound, 1);
    int winSound = sp.load(this.getContext(), R.raw.win_sound, 1);



    public GameView(Context context, int level, boolean sound) {



        super(context);
        levelch = level;

        soundOn = sound;

        gameView = this;

        // Adds the callback to the SurfaceHolder to get events
        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        //make gameView focusable so it can handle events
        setFocusable(true);


    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        //we can safely start the game loop

        if (thread.getState() == Thread.State.TERMINATED) {
        thread = new MainThread(getHolder(), this);
            thread.setRunning(true);
            thread.start();
            thread.pauseThread();
        }
        else {
            // Creating game objects (paddle, bricks, ball...)
            createGameObjects();
            thread.setRunning(true);
            thread.start();
        }

        Score.setScore(0);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;

            while (retry) {
                try {
                    thread.setRunning(false);
                    if(thread.isPaused()){
                        thread.resumeThread();
                    }
                    thread.join();

                } catch (Exception e) {
                    e.printStackTrace();
                }
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

        if (!gameStarted) {
            paddleCenterX = (GameConstants.DISPLAY_WIDTH / 2) - (paddle.getWidth() / 2);
        } else {
            if (!gameWin && !gameLosed) {

//                paddle.update(paddleCenterX);
//                ball.update();

                GameObject collGameObject = ball.getCollidingObject();

                if (collGameObject != null && collGameObject != ball && collGameObject != bg && collGameObject != gameTopPanel) {

                    GameObject.Line collSide = ball.getCollidingSide(collGameObject);
                    if (collSide != null) {

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


                        if (collGameObject != paddle) {
                            onBrickCollision(collGameObject);
                        }
                        if (collGameObject == paddle) {
                            onPaddleCollision();
                        }
                    }

                    else{
                        System.out.println("collside = null");
                    }


                }

                paddle.update(paddleCenterX);
                ball.update();

                // if ball falls down
                if (ballDown()) {
                    onBallDown();
                }
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
        if (!thread.isPaused()) {
            thread.pauseThread();
        }
    }

    public boolean gamePaused(){
        return thread.isPaused();
    }

    public static void resumeGame(){
        thread.resumeThread();

    }

    public void restartGame(){

        gameStarted = false;
        gameWin = false;
        gameLosed = false;
        GameObject.removeAll();
        Heart.removeAllHearts();
        Score.setScore(0);
        livesQty = GameConstants.LIVES_START_QTY;
        brickQty = 0;
        createGameObjects();
    }

    public void nextLevel(){
        gameStarted = false;
        gameWin = false;
        gameLosed = false;
        GameObject.removeAll();
        Heart.removeAllHearts();
        //Score.setScore();
        levelch = getNextLevel();
        createGameObjects();
    }

    private boolean ballDown(){
        return  (ball.getY() >= GameConstants.DISPLAY_HEIGHT - ball.getHeight());
    }

    private void onBallDown() {
        // put it on paddle
        if (livesQty > 1) {
            livesQty--;
            Heart.removeHeart();
            nextTry();
        } else {

            Heart.removeHeart();

            if (soundOn) {
                sp.play(loseSound, 1, 1, 0, 0, 1);
            }

            gameLosed = true;

            GameActivity.getInstance().onLose();

        }
    }

    private void onBrickCollision(GameObject collGameObject){
        if (soundOn) {
            sp.play(brickSoundId, 1, 1, 0, 0, 1);
        }
        collGameObject.remove();

        // Game score
        Score.increaseScore();
        //  }
        brickQty--;
        if (brickQty == 0) {
            gameStarted = false;
            if (soundOn) {
                sp.play(winSound, 1, 1, 0, 0, 1);
            }

            gameWin = true;

            GameActivity.getInstance().onWin();
        }
    }

    private void onPaddleCollision(){
        if (soundOn) {
            sp.play(paddleSoundId, 1, 1, 0, 0, 1);
        }
    }

    private void nextTry(){
        gameStarted = false;
        ball.remove();
        paddle.remove();
        //Heart.removeHeart();
        ball = new Ball();
        paddle = new Paddle();

    }

    private void createGameObjects(){

        bg = new Background();

        gameTopPanel = new GameTopPanel();

        paddle = new Paddle();

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
                        brickQty++;
                        break;
                    case 'r':
                        new Brick(GameConstants.BRICK_TYPE_RED, x, y);
                        x = x + GameConstants.BRICKS_SEPARATOR + GameConstants.BRICK_WIDTH;
                        brickQty++;
                        break;
                    case 'g':
                        new Brick(GameConstants.BRICK_TYPE_GREEN, x, y);
                        x = x + GameConstants.BRICKS_SEPARATOR + GameConstants.BRICK_WIDTH;
                        brickQty++;
                        break;
                    case 's':
                        new Brick(GameConstants.BRICK_TYPE_STONE, x, y);
                        x = x + GameConstants.BRICKS_SEPARATOR + GameConstants.BRICK_WIDTH;
                        brickQty++;
                        break;
                    case 'p':
                        new Brick(GameConstants.BRICK_TYPE_PURPLE, x, y);
                        x = x + GameConstants.BRICKS_SEPARATOR + GameConstants.BRICK_WIDTH;
                        brickQty++;
                        break;
                }
            }
        }

        for (int i = livesQty; i >= 0; i--){
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
            stopThread();
            ((Activity) mContext).finish();

        }
    }

    private int getNextLevel(){
        if( levelch != GameConstants.LEVEL_NUMBER){
            return levelch + 1;
        }
        else{
            return 1;
        }
    }

    public void increaseLivesQty(){
        livesQty++;
    }


    public int gameState(){
        if(gameLosed){
            return GameConstants.GAME_STATE_LOSE;
        }
        if(gameWin){
            return GameConstants.GAME_STATE_WIN;
        }
        return 0;
    }

}