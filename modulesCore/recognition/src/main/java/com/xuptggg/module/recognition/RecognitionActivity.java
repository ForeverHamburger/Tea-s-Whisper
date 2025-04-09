package com.xuptggg.module.recognition;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.eqgis.eqr.gesture.NodeGestureController;
import com.eqgis.eqr.layout.SceneViewType;
@Route(path = "/recognition/RecognitionActivity")
public class RecognitionActivity extends BaseActivity {
    private TextView tipsView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recognition);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ActivityCompat.requestPermissions(this,
                new String[]{
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        android.Manifest.permission.VIBRATE,
                        android.Manifest.permission.INTERNET,
                        android.Manifest.permission.ACCESS_WIFI_STATE,
                        android.Manifest.permission.WAKE_LOCK,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.READ_PHONE_STATE,
                        android.Manifest.permission.CAMERA,
                        android.Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.INSTALL_PACKAGES
                }, PackageManager.PERMISSION_GRANTED);

        sceneLayout = findViewById(R.id.camera_scene_layout);
        tipsView = findViewById(R.id.tips);

        //选择相机类型后，初始化控件
        sceneLayout.setSceneViewType(SceneViewType.CAMERA).init(this);
        sceneLayout.addIndirectLight("enviroments/light/lightroom_ibl.ktx",100);

        //节点手势控制器初始化
        NodeGestureController.getInstance()
                .setCamera(sceneLayout.getCamera())
                .init(this)
                .setEnabled(true);

        //加载GLTF模型
        sampleScene = new GltfSampleScene();
        sampleScene.create(getApplicationContext(),sceneLayout.getRootNode());
        sampleScene.setSceneView(sceneLayout.getSceneView());

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        View touchView = findViewById(R.id.touch_view);
        touchView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                NodeGestureController.getInstance().onTouch(motionEvent);
                return true;
            }
        });

    }
}