package com.xuptggg.forum.publish.view;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xuptggg.forum.R;
import com.xuptggg.forum.databinding.ActivityPublishBinding;
import com.xuptggg.forum.publish.contract.IPublishContract;
import com.xuptggg.forum.publish.model.PublishInfo;
import com.xuptggg.forum.publish.model.PublishModel;
import com.xuptggg.forum.publish.presenter.PublishPresenter;
import com.xuptggg.forum.publish.utils.ThreadPoolUtil;
import com.xuptggg.forum.publish.view.adapter.ImageAdapter;
import com.xuptggg.module.libbase.eventbus.TokenManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Route(path = "/forum/PublishActivity")
public class PublishActivity extends AppCompatActivity implements IPublishContract.IPublishView{
    private ActivityResultLauncher<Intent> imagePickerLauncher;
    private ImageAdapter adapter;
    private ActivityPublishBinding binding;
    private IPublishContract.IPublishPresenter mPresenter;
    private String token = null;
    private ThreadPoolUtil threadPoolUtil;
    private List<String> imageStringList;
    private PublishDialog publishDialog;
    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityPublishBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        publishDialog = new PublishDialog(this);
        threadPoolUtil = ThreadPoolUtil.getInstance();
        setPresenter(new PublishPresenter(new PublishModel(),this));

        adapter = new ImageAdapter(this, new ImageAdapter.OnAddImageClickListener() {
            @Override
            public void onAddImageClick() {
                openImageChooser();
            }
        });
        binding.rvNineGrid.setLayoutManager(new GridLayoutManager(this, 3));
        binding.rvNineGrid.setAdapter(adapter);

        binding.tvCancelPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 注册 Activity Result Launcher
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            Intent data = result.getData();
                            List<Uri> selectedImageUris = new ArrayList<>();
                            List<File> selevtedImageFile = new ArrayList<>();
                            if (data.getClipData() != null) {
                                int count = data.getClipData().getItemCount();
                                for (int i = 0; i < count; i++) {
                                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
                                    selectedImageUris.add(imageUri);
                                }
                            } else if (data.getData() != null) {
                                Uri imageUri = data.getData();
                                selectedImageUris.add(imageUri);
                            }
                            adapter.addImages(selectedImageUris);


                            threadPoolUtil.execute(() -> {
                                try {
                                    for (Uri imageUris : selectedImageUris) {
                                        File fileFromUri = getFileFromUri(imageUris);
                                        File file = convertHeifToJpeg(fileFromUri);
                                        selevtedImageFile.add(file);
                                    }
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }

                                if (token != null) {
                                    Log.d("pic", "onActivityResult: " + token);
                                    mPresenter.getUri(selevtedImageFile,token);
                                }
                            });
                        }
                    }
                });

        binding.tvPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageStringList == null || imageStringList.size() == 0) {
                    Toast.makeText(PublishActivity.this, "请上传至少一张图片或等待图片上传成功", Toast.LENGTH_SHORT).show();
                } else {
                    if (token != null) {
                        mPresenter.publishThread(new PublishInfo(
                                binding.etPublishTitle.getText().toString(),
                                binding.etPublishContent.getText().toString(),
                                imageStringList,
                                "1"
                        ),token);
                    }
                }

            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        // 取消注册
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getToken(TokenManager tokenManager) {
        token = tokenManager.getToken();
        Log.d("pic", "getToken: " + token);
    }

    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        imagePickerLauncher.launch(intent);
    }

    @Override
    public void showMessage(List<String> strings) {
        imageStringList = strings;
        Toast.makeText(this, "图片均上传成功！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError() {

    }

    @Override
    public void showSuccess() {
        publishDialog.dismissAfterOneSecond();
        finish();
    }

    @Override
    public void showOnPublished() {
        publishDialog.showContinuously();
    }

    @Override
    public void setPresenter(IPublishContract.IPublishPresenter presenter) {
        mPresenter = presenter;
    }

    private File convertHeifToJpeg(File heifFile) throws IOException {
        Bitmap bitmap = ImageDecoder.decodeBitmap(
                ImageDecoder.createSource(getContentResolver(), Uri.fromFile(heifFile))
        );
        String randomFileName = UUID.randomUUID().toString() + ".jpg";
        File jpegFile = new File(getCacheDir(), randomFileName);
        try (FileOutputStream fos = new FileOutputStream(jpegFile)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
        }
        return jpegFile;
    }

    private File getFileFromUri(Uri uri) throws IOException {
        ContentResolver contentResolver = getContentResolver();
        // 查询文件的相关信息，如文件名
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        String displayName = null;
        if (cursor != null && cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            displayName = cursor.getString(nameIndex);
            cursor.close();
        }

        if (displayName == null) {
            displayName = "temp_file";
        }

        // 创建临时文件
        File file = new File(getCacheDir(), displayName);

        // 打开输入流读取 Uri 对应的内容
        InputStream inputStream = contentResolver.openInputStream(uri);
        if (inputStream != null) {
            FileOutputStream outputStream = new FileOutputStream(file);

            // 缓冲区大小
            byte[] buffer = new byte[4 * 1024];
            int read;
            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }

            // 关闭流
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        }

        return file;
    }
}