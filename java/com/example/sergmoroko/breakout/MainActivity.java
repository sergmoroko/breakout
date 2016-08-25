package com.example.sergmoroko.breakout;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MainActivity extends Activity implements View.OnClickListener {

    static Activity currentActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        currentActivity = this;

        super.onCreate(savedInstanceState);

        // Turn title off
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Set to full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        FrameLayout game = new FrameLayout(this);
        GameView gameView = new GameView (this);


        game.addView(gameView);

        // to avoid ignoring layout parameters by root element, "false" condition were added
        game.addView(getLayoutInflater().inflate(R.layout.game_widgets, game, false));

        //game.addView(View.inflate(this, R.layout.game_widgets, null));




        //setContentView(new GameView(this));

        setContentView(game);

        ImageButton pause = (ImageButton) findViewById(R.id.pause_button);

        // Setting button size
        pause.setLayoutParams(new LinearLayout.LayoutParams(GameConstants.PAUSE_BUTTON_WIDTH, GameConstants.PANEL_HEIGHT));


        pause.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        GameView.pauseGame();
        final PauseDialog dialog = new PauseDialog(this, R.style.DialogWithoutTitle);

        dialog.show();
    }

    public static MainActivity getInstance(){
    return (MainActivity) currentActivity;
    }
}