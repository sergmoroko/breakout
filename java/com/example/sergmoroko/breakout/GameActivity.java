package com.example.sergmoroko.breakout;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class GameActivity extends Activity implements View.OnClickListener {

    private static Activity currentActivity;
    private static PauseDialog pauseDialog;
    private static LoseDialog loseDialog;
    private static WinDialog winDialog;

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        currentActivity = this;
        pauseDialog = new PauseDialog(this, R.style.DialogWithoutTitle);
        loseDialog = new LoseDialog(this, R.style.DialogWithoutTitle);
        winDialog = new WinDialog(this, R.style.DialogWithoutTitle);

        // receiving shared preferences
        sp = this.getSharedPreferences(GameConstants.SP_NAME, MODE_PRIVATE);
        editor = sp.edit();


        // enabling hardware volume buttons to control audio stream volume
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);


        super.onCreate(savedInstanceState);

        // Turn title off
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Set to full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        FrameLayout game = new FrameLayout(this);

        int level = getIntent().getIntExtra(GameConstants.INTENT_LEVEL, 1);

        boolean isSoundEnabled = sp.getBoolean(GameConstants.SP_SOUND, true);

        GameView gameView = new GameView(this, level, isSoundEnabled);


        game.addView(gameView);

        // to avoid ignoring layout parameters by root element, "false" condition were added
        game.addView(getLayoutInflater().inflate(R.layout.game_widgets, game, false));

        setContentView(game);

        FrameLayout pause = (FrameLayout) findViewById(R.id.pause_button);

        // Setting button size
        pause.setLayoutParams(new LinearLayout.LayoutParams(GameConstants.PAUSE_BUTTON_WIDTH, GameConstants.PANEL_HEIGHT));


        pause.setOnClickListener(this);

    }


    @Override
    protected void onPause() {
        super.onPause();
        if (!GameView.getInstance().gamePaused()) {
            GameView.pauseGame();
        }
        if (dialogsNotShowing()) {
            pauseDialog.show();
        }

        highScoreRecord();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (pauseDialog.isShowing()) {
            pauseDialog.dismiss();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (dialogsNotShowing()) {
            pauseDialog.show();
        }
    }


    @Override
    public void onClick(View v) {
        GameView.pauseGame();
        pauseDialog.show();
    }

    @Override
    public void onBackPressed() {
        GameView.pauseGame();
        if (dialogsNotShowing()) {
            pauseDialog.show();
        }
    }

    public void onLose() {

        GameView.pauseGame();

        highScoreRecord();

        Handler mHandler = new Handler(Looper.getMainLooper());
        mHandler.post(new Runnable() {
            @Override
            public void run() {

                loseDialog.show();
            }
        });
    }

    public void onWin() {
        GameView.pauseGame();

        highScoreRecord();

        Handler mHandler = new Handler(Looper.getMainLooper());
        mHandler.post(new Runnable() {
            @Override
            public void run() {

                winDialog.show();
            }
        });
    }

    public static GameActivity getInstance() {
        return (GameActivity) currentActivity;
    }


    private boolean dialogsNotShowing() {
        if (!pauseDialog.isShowing() && !winDialog.isShowing() && !loseDialog.isShowing()) {
            return true;
        } else {
            return false;
        }
    }

    private void highScoreRecord() {
        int currentScore = Score.getScore();
        int highScore = sp.getInt(GameConstants.SP_HIGHSCORE, 0);

        if (currentScore > highScore) {
            editor.putInt(GameConstants.SP_HIGHSCORE, currentScore);
            editor.commit();

        }
    }
}