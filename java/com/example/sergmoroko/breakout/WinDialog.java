package com.example.sergmoroko.breakout;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;


public class WinDialog extends Dialog {
    public WinDialog(final Context context, int themeResId) {
        super(context, themeResId);

        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.dialog_win);

        Window window = getWindow();
        window.setLayout(GameConstants.DIALOG_WIDTH, GameConstants.DIALOG_HEIGHT);
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        FrameLayout nextOrRestartButton = (FrameLayout) findViewById(R.id.lose_try_again_button);
        FrameLayout exitButton = (FrameLayout) findViewById(R.id.lose_exit_button);


        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                final ExitConfirmationDialog confirmDialog = new ExitConfirmationDialog(context, R.style.DialogWithoutTitle);
                confirmDialog.show();
            }

        });

        nextOrRestartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
                GameView.getInstance().nextLevel();
                GameView.resumeGame();
            }

        });
    }
}
