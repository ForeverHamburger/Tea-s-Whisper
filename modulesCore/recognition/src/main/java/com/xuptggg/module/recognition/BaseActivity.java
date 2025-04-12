package com.xuptggg.module.recognition;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.eqgis.eqr.layout.SceneLayout;
import com.eqgis.eqr.listener.CompleteCallback;
/**
 * 基础场景
 * <p>在相关时刻调用SceneLayout的resume、pause、destroy</p>
 **/
public class BaseActivity extends AppCompatActivity {

    protected SceneLayout sceneLayout;

    /**
     * 示例场景
     */
    protected ISampleScene sampleScene;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //设置当前窗体为全屏展示
        window.setFlags(flag,flag);
        // 使用兼容性API
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

    }

    @Override
    protected void onResume() {
        super.onResume();
        sceneLayout.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sceneLayout.pause();
    }

    @Override
    protected void onDestroy() {
        if (sampleScene != null)
            sampleScene.destroy(this);
        sceneLayout.destroy();
        super.onDestroy();
    }


    /**
     * 拍照
     */
    public void capture(View view){
//        String path = getExternalCacheDir()+"/capture";
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();
        sceneLayout.captureScreen(path,true, new CompleteCallback() {
            @Override
            public void onSuccess(Object object) {
//                ThreadPools.getThreadPoolExecutor().execute(()->{
//                    try {
//                        //通知相册更新
//                        MediaStore.Images.Media.insertImage(getContentResolver(), object.toString(), "image", null);
//                    } catch (FileNotFoundException e) {
//                        Log.w(CameraActivity2.class.getSimpleName(), "onSuccess: ", e);
//                    }
//                });
                Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_SHORT).show();
                Log.i("TAG", "保存成功\n路径："+object.toString());

                String directoryPath = object.toString();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(directoryPath), "image/*");
                startActivity(intent);
            }

            @Override
            public void onFailed(String errorMessage) {
                Toast.makeText(getApplicationContext(), "出错了", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
