package com.example.sergmoroko.breakout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

public class LevelChooseActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Turn title off
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Set to full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_level_choose);

        FrameLayout level1Button = (FrameLayout) findViewById(R.id.level_1);
        FrameLayout level2Button = (FrameLayout) findViewById(R.id.level_2);
        FrameLayout level3Button = (FrameLayout) findViewById(R.id.level_3);
        FrameLayout level4Button = (FrameLayout) findViewById(R.id.level_4);

        level1Button.setOnClickListener(this);
        level2Button.setOnClickListener(this);
        level3Button.setOnClickListener(this);
        level4Button.setOnClickListener(this);

            }

    @Override
    public void onClick(View v) {
        int chosenLvl = 0;

        switch (v.getId()){
            case R.id.level_1:
                chosenLvl = 1;
                break;
            case R.id.level_2:
                chosenLvl = 2;
                break;
            case R.id.level_3:
                chosenLvl = 3;
                break;
            case R.id.level_4:
                chosenLvl = 4;
                break;
        }
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("level", chosenLvl);
        this.startActivity(intent);
    }

    @Override
    public void onBackPressed() {

        final ExitConfirmationDialog dialog = new ExitConfirmationDialog(this, R.style.DialogWithoutTitle);
        dialog.show();

    }
}


