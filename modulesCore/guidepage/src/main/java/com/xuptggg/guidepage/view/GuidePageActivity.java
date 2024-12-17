package com.xuptggg.guidepage.view;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.jem.liquidswipe.clippathprovider.LiquidSwipeClipPathProvider;
import com.xuptggg.guidepage.R;
import com.xuptggg.guidepage.adapter.SwipePagerAdapter;
import com.xuptggg.guidepage.databinding.ActivityGuidePageBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GuidePageActivity extends AppCompatActivity {
    private ActivityGuidePageBinding binding;

    private List<Integer> backgroundColorArray;
    private List<Integer> resourceArray;
    private List<String> titleArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityGuidePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        LiquidSwipeClipPathProvider[] liquidSwipeClipPathProviders = new LiquidSwipeClipPathProvider[5];
        for (int i = 0; i < 5; i++) {
            liquidSwipeClipPathProviders[i] = new LiquidSwipeClipPathProvider();
        }

        backgroundColorArray = new ArrayList<>(Arrays.asList(
                Color.parseColor("#6200EE"),
                Color.parseColor("#9FD29D"),
                Color.parseColor("#AFE1F0"),
                Color.parseColor("#F6D336"),
                Color.parseColor("#FA796B")
        ));

        resourceArray = new ArrayList<>(Arrays.asList(
//                R.raw.mountain,
//                R.raw.map,
//                R.raw.luggage,
//                R.raw.camera,
//                R.raw.van
        ));

        titleArray = new ArrayList<>(Arrays.asList(
                "Hello fellow developer",
                "If you like this library",
                "Then do star it",
                "And check out my other libraries",
                "Cheers ^_^"
        ));

        SwipePagerAdapter swipePagerAdapter = new SwipePagerAdapter(this,liquidSwipeClipPathProviders
                ,backgroundColorArray
                ,resourceArray
                ,titleArray);

        binding.vpGuide.setAdapter(swipePagerAdapter);
        binding.vpGuide.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float waveCenterY = event.getY();
                for (LiquidSwipeClipPathProvider provider : liquidSwipeClipPathProviders) {
                    provider.setWaveCenterY(waveCenterY);
                }
                return false;
            }
        });
    }
}