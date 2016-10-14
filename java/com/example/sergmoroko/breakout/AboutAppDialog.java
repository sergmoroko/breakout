package com.example.sergmoroko.breakout;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.Window;
import android.widget.TextView;


public class AboutAppDialog extends Dialog {

    public AboutAppDialog(final Context context, int themeResId) {
        super(context, themeResId);

        setCanceledOnTouchOutside(true);
        setContentView(R.layout.dialog_about_app);

        Window window = getWindow();
        window.setLayout(GameConstants.ABOUT_DIALOG_WIDTH, GameConstants.DIALOG_HEIGHT);
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // link to GitHub
        TextView githubLink = (TextView) findViewById(R.id.about_github_link);
        githubLink.setMovementMethod(LinkMovementMethod.getInstance());

    }
}