package com.example.sergmoroko.breakout;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;

/**
 * Created by ssss on 05.08.2016.
 */
public class PauseDialog extends Dialog{
    public PauseDialog(final Context context, int themeResId) {
        super(context, themeResId);

        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.custom);
        setTitle(R.string.dialog_title);

        Window window = getWindow();
        window.setLayout(GameConstants.DIALOG_WIDTH, GameConstants.DIALOG_HEIGHT);
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        FrameLayout resumeButton = (FrameLayout) findViewById(R.id.resume_fake_button);
        FrameLayout restartButton = (FrameLayout) findViewById(R.id.restart_fake_button);
        FrameLayout exitButton = (FrameLayout) findViewById(R.id.exit_fake_button);
        //FrameLayout testButton = (FrameLayout) findViewById(R.id.test_button1);
        // if button is clicked, close the custom dialog
        resumeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    GameView.resumeGame();
                }

        });
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameView.getInstance().restartGame();
                dismiss();
                GameView.resumeGame();
            }
        });
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
                GameView.getInstance().exitGame();
            }
        });
//        testButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                dismiss();
//                GameView.getInstance().exitGame();
//                Intent intent = new Intent(v.getContext(), LevelChooseActivity.class);
//                v.getContext().startActivity(intent);
//            }
//        });

    }

}
