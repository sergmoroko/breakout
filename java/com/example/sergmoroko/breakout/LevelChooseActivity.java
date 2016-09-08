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
        int choosenLvl = 0;

        switch (v.getId()){
            case R.id.level_1:
                choosenLvl = 1;
                break;
            case R.id.level_2:
                choosenLvl = 2;
                break;
            case R.id.level_3:
                choosenLvl = 3;
                break;
            case R.id.level_4:
                choosenLvl = 4;
                break;
        }
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("level", choosenLvl);
        this.startActivity(intent);
    }
}


