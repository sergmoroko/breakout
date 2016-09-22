package com.example.sergmoroko.breakout;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by ssss on 19.07.2016.
 */
public class MainThread extends Thread {

    //private int FPS = 30;
    private double averageFPS;
    private final SurfaceHolder surfaceHolder;
    private GameView gameView;
    private boolean runFlag;
    public static Canvas canvas;

    private final Object mPauseLock;
    private boolean mPaused;

    public MainThread(SurfaceHolder surfaceHolder, GameView gameView){
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
        long totalTime = 0;
        int frameCount =0;
        long targetTime = 1000/GameConstants.FPS;

        while(runFlag) {
            startTime = System.nanoTime();
            canvas = null;

            //try locking the canvas for pixel editing
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.gameView.update();
                    this.gameView.draw(canvas);
                }
            } catch (Exception e) {
            }
            finally{
                if(canvas!=null)
                {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                    catch(Exception e){e.printStackTrace();}
                }
            }




            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime-timeMillis;

            try{
                sleep(waitTime);
            }catch(Exception e){}

            totalTime += System.nanoTime()-startTime;
            frameCount++;
            if(frameCount == GameConstants.FPS)
            {
                averageFPS = 1000/((totalTime/frameCount)/1000000);
                frameCount =0;
                totalTime = 0;
                System.out.println(averageFPS);
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
    public void setRunning(boolean b)
    {
        runFlag=b;
    }

    public void pauseThread(){
        synchronized (mPauseLock) {
            mPaused = true;
        }
    }

    public void resumeThread(){

        synchronized (mPauseLock) {
            mPaused = false;
            mPauseLock.notifyAll();
        }
    }

    public void stopThread(){
        synchronized (mPauseLock) {
            mPaused = false;
            mPauseLock.notifyAll();
        }
        interrupt();
    }
    public boolean isPaused(){
        return mPaused;
    }


}
