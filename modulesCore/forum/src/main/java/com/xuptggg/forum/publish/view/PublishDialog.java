package com.xuptggg.forum.publish.view;


import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.xuptggg.forum.R;



public class PublishDialog extends Dialog {
    private ConstraintLayout dialogLayout;
    private Handler handler;
    private TextView textView;

    public PublishDialog(Context context) {
        super(context, R.style.dialog);
        setContentView(R.layout.dialog_publish);

        dialogLayout = findViewById(R.id.dialog_container);
        handler = new Handler();
        textView = findViewById(R.id.tv_dialog_publish);
    }

    /**
     * 持续展示对话框
     */
    public void showContinuously() {
        textView.setText("发 布 中...");
        handler.postDelayed(() -> {
        }, 1000);
        show();
    }

    /**
     * 一秒后关闭对话框
     */
    public void dismissAfterOneSecond() {
        show();
        textView.setText("发 布 成 功！");
        handler.postDelayed(() -> {
            if (isShowing()) {
                dismiss();
            }
        }, 500);
    }
}