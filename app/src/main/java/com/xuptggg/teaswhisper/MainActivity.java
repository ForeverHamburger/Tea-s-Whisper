package com.xuptggg.teaswhisper;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.tencent.mmkv.MMKV;
import com.xuptggg.teaswhisper.databinding.ActivityMainBinding;
public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        MMKV.initialize(this);

        //ifFirstLaunch();

        ARouter.getInstance().build("/guidepage/GuidePageActivity").navigation();

    }

    private void ifFirstLaunch() {
        MMKV mmkv = MMKV.mmkvWithID("Tea's Whisper");

        if(mmkv.getBoolean("first_enter", false)){
            mmkv.putBoolean("first_enter", false);
        } else {
            mmkv.putBoolean("first_enter", true);
            ARouter.getInstance().build("/guidepage/GuidePageActivity").navigation();
        }
    }
}