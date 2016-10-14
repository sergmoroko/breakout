package com.example.sergmoroko.breakout;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread {

    private final SurfaceHolder surfaceHolder;
    private GameView gameView;
    private boolean runFlag;
    public static Canvas canvas;

    private final Object mPauseLock;
    private boolean mPaused;

    public MainThread(SurfaceHolder surfaceHolder, GameView gameView) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
        mPauseLock = new Object();
        mPaused = false;
        runFlag = false;
    }

    @Override
    public void run() {
        long startTime;
        long timeMillis;
        long waitTime;

        long targetTime = 1000 / GameConstants.FPS;

        while (runFlag) {
            startTime = System.nanoTime();
            canvas = null;

            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.gameView.update();
                    this.gameView.draw(canvas);
                }
            } catch (Exception e) {
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            timeMillis = (System.nanoTime() - startTime) / GameConstants.SECONDS_DIVIDER;

            waitTime = targetTime - timeMillis;

            try {
                if (waitTime > 0) {
                    sleep(waitTime);
                }
                else{
                    sleep(1);
                }
            } catch (Exception e) {
                e.printStackTrace();

            }


            synchronized (mPauseLock) {
                while (mPaused) {
                    try {
                        mPauseLock.wait();
                    } catch (InterruptedException e) {
                    }
                }
            }
        }
    }

    public void setRunning(boolean b) {
        runFlag = b;
    }

    public void pauseThread() {
        synchronized (mPauseLock) {
            mPaused = true;
        }
    }

    public void resumeThread() {

        synchronized (mPauseLock) {
            mPaused = false;
            mPauseLock.notifyAll();
        }
    }

    public void stopThread() {
        synchronized (mPauseLock) {
            mPaused = false;
            mPauseLock.notifyAll();
        }
        interrupt();
    }

    public boolean isPaused() {
        return mPaused;
    }

}
