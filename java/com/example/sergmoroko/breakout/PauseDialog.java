package com.example.sergmoroko.breakout;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;


public class PauseDialog extends Dialog{
    public PauseDialog(final Context context, int themeResId) {
        super(context, themeResId);

        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.dialog_pause);

        Window window = getWindow();
        window.setLayout(GameConstants.DIALOG_WIDTH, GameConstants.DIALOG_HEIGHT);
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        FrameLayout resumeButton = (FrameLayout) findViewById(R.id.resume_fake_button);
        FrameLayout restartButton = (FrameLayout) findViewById(R.id.restart_fake_button);
        FrameLayout exitButton = (FrameLayout) findViewById(R.id.exit_fake_button);

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
                final ExitConfirmationDialog dialog = new ExitConfirmationDialog(context, R.style.DialogWithoutTitle);

                dialog.show();
            }
        });

    }

}
