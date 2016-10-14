package com.example.sergmoroko.breakout;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;


public class ExitConfirmationDialog extends Dialog {
    public ExitConfirmationDialog(final Context context, int themeResId) {
        super(context, themeResId);
        if (context instanceof Activity) {
            setOwnerActivity((Activity) context);
        }
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.exit_alert_dialog);
        Window window = getWindow();
        window.setLayout(GameConstants.DIALOG_WIDTH, GameConstants.DIALOG_HEIGHT);
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        FrameLayout exitConfirmButton = (FrameLayout) findViewById(R.id.exit_yes_button);
        FrameLayout exitCancelButton = (FrameLayout) findViewById(R.id.exit_no_button);

        exitConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getOwnerActivity().equals(GameActivity.getInstance())) {
                    dismiss();
                    GameView.getInstance().exitGame();
                } else {

                    dismiss();
                    System.exit(0);
                }

            }
        });

        exitCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getOwnerActivity().equals(GameActivity.getInstance())) {
                    dismiss();
                    int gameState = GameView.getInstance().gameState();

                    switch (gameState) {
                        case GameConstants.GAME_STATE_LOSE:
                            final LoseDialog loseDialog = new LoseDialog(context, R.style.DialogWithoutTitle);
                            loseDialog.show();
                            break;
                        case GameConstants.GAME_STATE_WIN:
                            final WinDialog winDialog = new WinDialog(context, R.style.DialogWithoutTitle);
                            winDialog.show();
                            break;
                        default:
                            final PauseDialog pauseDialog = new PauseDialog(context, R.style.DialogWithoutTitle);
                            pauseDialog.show();
                            break;
                    }

                } else {
                    dismiss();
                }

            }
        });
    }
}
