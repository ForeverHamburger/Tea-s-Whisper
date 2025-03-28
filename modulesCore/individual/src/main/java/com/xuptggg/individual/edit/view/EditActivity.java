package com.xuptggg.individual.edit.view;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.xuptggg.individual.R;
import com.xuptggg.individual.databinding.ActivityEditBinding;
import com.xuptggg.individual.databinding.ActivityIndividualBinding;
import com.xuptggg.individual.edit.contract.IEditContract;
import com.xuptggg.individual.edit.model.BaseIndividualInfo;
import com.xuptggg.individual.edit.model.EditModel;
import com.xuptggg.individual.edit.presenter.EditPresenter;
import com.xuptggg.individual.personal.model.IndividualInfo;
import com.xuptggg.individual.utils.ThreadPoolUtil;
import com.xuptggg.module.libbase.eventbus.TokenManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Route(path = "/individual/EditActivity")
public class EditActivity extends AppCompatActivity implements IEditContract.IEditView {

    private ActivityEditBinding binding;
    private static final int MAX_LENGTH = 100;
    private ActivityResultLauncher<Intent> imagePickerLauncher;
    private IEditContract.IEditPresenter mPresenter;
    private String token;
    private String imageUrl;
    private File imageFile;
    private ThreadPoolUtil threadPoolUtil;
    private static final String KEY_IMAGE_URL = "image_url";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_SEX = "sex";
    private static final String KEY_INTRODUCTION = "introduction";
    private boolean dataRequested = false;
    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        threadPoolUtil = ThreadPoolUtil.getInstance();
        setPresenter(new EditPresenter(new EditModel(),this));

        Spinner spinnerGender = findViewById(R.id.spinner_gender);
        List<String> genderOptions = Arrays.asList("男", "女", "奶龙", "保密");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, genderOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapter);
        if (savedInstanceState != null) {
            imageUrl = savedInstanceState.getString(KEY_IMAGE_URL);
            String username = savedInstanceState.getString(KEY_USERNAME);
            String sex = savedInstanceState.getString(KEY_SEX);
            String introduction = savedInstanceState.getString(KEY_INTRODUCTION);

            if (imageUrl != null) {
                Glide.with(this)
                        .load(imageUrl)
                        .into(binding.ivEditUserHead);
            }
            binding.etUsername.setText(username);
            binding.spinnerGender.setSelection(Integer.parseInt(sex));
            binding.etIntroduction.setText(introduction);
        }

        binding.tvEditCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 设置输入的最大长度
        binding.etIntroduction.setFilters(new android.text.InputFilter[]{new android.text.InputFilter.LengthFilter(MAX_LENGTH)});

        binding.etIntroduction.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = s.length();
                binding.tvIntroductionCount.setText(length + "/" + MAX_LENGTH);
            }
        });

        // 注册图片选择的 Result Launcher
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null && data.getData() != null) {
                            Uri imageUri = data.getData(); // 获取选中的图片 Uri
                            // 使用 Glide 加载图片到 ImageView
                            Glide.with(this)
                                    .load(imageUri)
                                    .into(binding.ivEditUserHead);

                            threadPoolUtil.execute(() -> {
                                try {
                                    File fileFromUri = getFileFromUri(imageUri);
                                    imageFile = convertHeifToJpeg(fileFromUri);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }

                                if (token != null) {
                                    mPresenter.getUri(imageFile,token);
                                }
                            });
                        }
                    }
                }
        );

        binding.ivEditCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();
            }
        });

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseIndividualInfo baseIndividualInfo = new BaseIndividualInfo(
                        binding.etUsername.getText().toString(),
                        String.valueOf(binding.spinnerGender.getSelectedItemPosition()),
                        imageUrl,
                        binding.etIntroduction.getText().toString()
                );

                mPresenter.postEditInfo(baseIndividualInfo,token);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getToken(TokenManager tokenManager) {
        Log.d("TAG", "getTokenIndividual: " + tokenManager.getToken());
        token = tokenManager.getToken();
        if (!dataRequested) {
            mPresenter.getEditInfo(tokenManager.getToken());
            dataRequested = true;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_IMAGE_URL, imageUrl);
        outState.putString(KEY_USERNAME, binding.etUsername.getText().toString());
        outState.putString(KEY_SEX, String.valueOf(binding.spinnerGender.getSelectedItemPosition()));
        outState.putString(KEY_INTRODUCTION, binding.etIntroduction.getText().toString());
    }

    @Override
    public void onStop() {
        super.onStop();
        // 取消注册
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*"); // 限制只选图片
        imagePickerLauncher.launch(intent); // 替代 startActivityForResult
    }

    @Override
    public void showMessage(IndividualInfo info) {
        Glide.with(this)
                .load(info.getUrl())
                .into(binding.ivEditUserHead);
        binding.etUsername.setText(info.getUserName());
        binding.etId.setText(info.getUserId());
        binding.tvPhone.setText(info.getPhone());
        binding.tvEmail.setText(info.getEmail());
        binding.spinnerGender.setSelection(Integer.parseInt(info.getSex()));
        binding.etTeaAge.setText("1年");
        binding.etIntroduction.setText(info.getIntroduction());
    }

    @Override
    public void showSuccessMessage(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setImageUri(String imageUri) {
        this.imageUrl = imageUri;
        Toast.makeText(this, "图片上传成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError() {

    }

    @Override
    public void setPresenter(IEditContract.IEditPresenter presenter) {
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