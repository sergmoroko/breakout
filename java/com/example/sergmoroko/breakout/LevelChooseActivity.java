package com.example.sergmoroko.breakout;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class LevelChooseActivity extends Activity implements View.OnClickListener {

    private static Activity currentActivity;
    private int chosenLvl = 0;
    private boolean isSoundEnabled;
    private int highScore;
    private ImageButton soundButton;

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sp = this.getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = sp.edit();

        if (sp.contains("sound")) {
            isSoundEnabled = sp.getBoolean("sound", true);
        } else {
            editor.putBoolean("sound", true);
            editor.commit();
            isSoundEnabled = true;
        }

        if (sp.contains("highScore")) {
            highScore = sp.getInt("highScore", 0);
        } else {
            editor.putInt("highScore", 0);
            editor.commit();
            highScore = 0;
        }


        currentActivity = this;

        // Turn title off
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Set to full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_level_choose);

        soundButton = (ImageButton) findViewById(R.id.level_choose_sound_button);
        if (isSoundEnabled) {
            soundButton.setImageResource(R.drawable.ic_volume_up_white_36dp);
        } else {
            soundButton.setImageResource(R.drawable.ic_volume_off_white_36dp);
        }

        ImageButton aboutAppButton = (ImageButton) findViewById(R.id.level_choose_info_button);

        soundButton.setLayoutParams(new LinearLayout.LayoutParams(GameConstants.TOP_PANEL_BUTTONS_SIZE,
                GameConstants.TOP_PANEL_BUTTONS_SIZE));
        aboutAppButton.setLayoutParams(new LinearLayout.LayoutParams(GameConstants.TOP_PANEL_BUTTONS_SIZE,
                GameConstants.TOP_PANEL_BUTTONS_SIZE));


        LinearLayout bottomPanel = (LinearLayout) findViewById(R.id.linearLayout2);

        RelativeLayout.LayoutParams bottomPanelLayoutParams = new RelativeLayout.LayoutParams
                (GameConstants.DISPLAY_WIDTH, GameConstants.BOTTOM_PANEL_HEIGHT);
        bottomPanelLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        bottomPanel.setLayoutParams(bottomPanelLayoutParams);

        LinearLayout row1 = (LinearLayout) findViewById(R.id.level_buttons_row1);
        LinearLayout row2 = (LinearLayout) findViewById(R.id.level_buttons_row2);
        LinearLayout row3 = (LinearLayout) findViewById(R.id.level_buttons_row3);
        LinearLayout row4 = (LinearLayout) findViewById(R.id.level_buttons_row4);
        LinearLayout row5 = (LinearLayout) findViewById(R.id.level_buttons_row5);


        LinearLayout.LayoutParams buttonRowsParams = new LinearLayout.LayoutParams(GameConstants.DISPLAY_WIDTH, GameConstants.LEVEL_BUTTON_HEIGHT);

        row1.setLayoutParams(buttonRowsParams);
        row2.setLayoutParams(buttonRowsParams);
        row3.setLayoutParams(buttonRowsParams);
        row4.setLayoutParams(buttonRowsParams);
        row5.setLayoutParams(buttonRowsParams);


        FrameLayout level1Button = (FrameLayout) findViewById(R.id.level_1);
        FrameLayout level2Button = (FrameLayout) findViewById(R.id.level_2);
        FrameLayout level3Button = (FrameLayout) findViewById(R.id.level_3);
        FrameLayout level4Button = (FrameLayout) findViewById(R.id.level_4);
        FrameLayout level5Button = (FrameLayout) findViewById(R.id.level_5);
        FrameLayout level6Button = (FrameLayout) findViewById(R.id.level_6);
        FrameLayout level7Button = (FrameLayout) findViewById(R.id.level_7);
        FrameLayout level8Button = (FrameLayout) findViewById(R.id.level_8);
        FrameLayout level9Button = (FrameLayout) findViewById(R.id.level_9);
        FrameLayout level10Button = (FrameLayout) findViewById(R.id.level_10);

        // setting click listeners
        level1Button.setOnClickListener(this);
        level2Button.setOnClickListener(this);
        level3Button.setOnClickListener(this);
        level4Button.setOnClickListener(this);
        level5Button.setOnClickListener(this);
        level6Button.setOnClickListener(this);
        level7Button.setOnClickListener(this);
        level8Button.setOnClickListener(this);
        level9Button.setOnClickListener(this);
        level10Button.setOnClickListener(this);

        soundButton.setOnClickListener(this);
        aboutAppButton.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        highScore = sp.getInt(GameConstants.SP_HIGHSCORE, 0);
        TextView highScoreTextView = (TextView) findViewById(R.id.highscore_text_view);
        highScoreTextView.setText(Integer.toString(highScore));
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.level_1:
                chosenLvl = GameConstants.LEVEL_1;
                startLevel();
                break;
            case R.id.level_2:
                chosenLvl = GameConstants.LEVEL_2;
                startLevel();
                break;
            case R.id.level_3:
                chosenLvl = GameConstants.LEVEL_3;
                startLevel();
                break;
            case R.id.level_4:
                chosenLvl = GameConstants.LEVEL_4;
                startLevel();
                break;
            case R.id.level_5:
                chosenLvl = GameConstants.LEVEL_5;
                startLevel();
                break;
            case R.id.level_6:
                chosenLvl = GameConstants.LEVEL_6;
                startLevel();
                break;
            case R.id.level_7:
                chosenLvl = GameConstants.LEVEL_7;
                startLevel();
                break;
            case R.id.level_8:
                chosenLvl = GameConstants.LEVEL_8;
                startLevel();
                break;
            case R.id.level_9:
                chosenLvl = GameConstants.LEVEL_9;
                startLevel();
                break;
            case R.id.level_10:
                chosenLvl = GameConstants.LEVEL_10;
                startLevel();
                break;
            case R.id.level_choose_sound_button:
                if (!isSoundEnabled) {
                    soundButton.setImageResource(R.drawable.ic_volume_up_white_36dp);
                    editor.putBoolean(GameConstants.SP_SOUND, true);
                    editor.commit();
                    isSoundEnabled = true;
                } else {
                    soundButton.setImageResource(R.drawable.ic_volume_off_white_36dp);
                    editor.putBoolean(GameConstants.SP_SOUND, false);
                    editor.commit();
                    isSoundEnabled = false;
                }
                break;
            case R.id.level_choose_info_button:
                final AboutAppDialog aboutAppDialog = new AboutAppDialog(this, R.style.DialogWithoutTitle);
                aboutAppDialog.show();
                break;
        }
    }


    @Override
    public void onBackPressed() {

        final ExitConfirmationDialog dialog = new ExitConfirmationDialog(this, R.style.DialogWithoutTitle);
        dialog.show();

    }

    public static LevelChooseActivity getInstance() {
        return (LevelChooseActivity) currentActivity;
    }

    private void startLevel() {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(GameConstants.INTENT_LEVEL, chosenLvl);
        this.startActivity(intent);
    }

}
