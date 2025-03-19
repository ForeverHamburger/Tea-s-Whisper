package com.xuptggg.detection.card;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xuptggg.detection.R;
@Route(path = "/detection/TeaCardActivity")
public class TeaCardActivity extends AppCompatActivity {
    private Button showDialogButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tea_card);

        showDialogButton = findViewById(R.id.show_dialog_button);
        showDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFlippableDialog();
            }
        });
    }

    private void showFlippableDialog() {
        final FlippableDialog flippableDialog = new FlippableDialog(this);
        flippableDialog.show();
    }
}