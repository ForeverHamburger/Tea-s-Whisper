package com.xuptggg.individual.edit.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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
import com.xuptggg.module.libbase.eventbus.TokenManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;
import java.util.List;

@Route(path = "/individual/EditActivity")
public class EditActivity extends AppCompatActivity implements IEditContract.IEditView {

    private ActivityEditBinding binding;
    private static final int MAX_LENGTH = 100;
    private ActivityResultLauncher<Intent> imagePickerLauncher;
    private IEditContract.IEditPresenter mPresenter;
    private String token;
    private String url;
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

        setPresenter(new EditPresenter(new EditModel(),this));

        Spinner spinnerGender = findViewById(R.id.spinner_gender);
        List<String> genderOptions = Arrays.asList("男", "女", "奶龙", "保密");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, genderOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapter);

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
                                    .apply(new RequestOptions()
                                            .circleCrop()) // 圆形裁剪
                                    .into(binding.ivEditUserHead);
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
                        url,
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
        mPresenter.getEditInfo(tokenManager.getToken());
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
        binding.etUsername.setText(info.getUserName());
        binding.etId.setText(info.getUserId());
        binding.tvPhone.setText(info.getPhone());
        binding.tvEmail.setText(info.getEmail());
        binding.spinnerGender.setSelection(Integer.parseInt(info.getSex()));
        binding.etTeaAge.setText("1年");
        binding.tvIntroductionCount.setText(info.getIntroduction());
    }

    @Override
    public void showError() {

    }

    @Override
    public void setPresenter(IEditContract.IEditPresenter presenter) {
        mPresenter = presenter;
    }
}