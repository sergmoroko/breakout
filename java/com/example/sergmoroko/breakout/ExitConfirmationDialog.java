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

/**
 * Created by ssss on 13.09.2016.
 */
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
                dismiss();
                if (getOwnerActivity().equals(GameActivity.getInstance())){
                    GameView.getInstance().exitGame();
                }
                else{
                    System.exit(1);
                }

            }
        });

        exitCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getOwnerActivity().equals(GameActivity.getInstance())){
                    dismiss();
                    GameActivity.dialog.show();
                }
                else{
                    dismiss();;
                }

            }
        });
    }
}
