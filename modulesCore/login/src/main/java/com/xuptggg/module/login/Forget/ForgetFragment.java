package com.xuptggg.module.login.Forget;

import static com.xuptggg.module.login.base.ValidationUtil.validateEmail;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.xuptggg.module.login.R;
import com.xuptggg.module.login.base.ValidationResult;
import com.xuptggg.module.login.base.ValidationUtil;
import com.xuptggg.module.login.databinding.FragmentForgetBinding;

public class ForgetFragment extends Fragment implements ForgetContract.View {
    FragmentForgetBinding binding;
    private ForgetContract.Presenter mPresenter;
    private CountDownTimer countDownTimer;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentForgetBinding.inflate(inflater, container, false);
        mPresenter.onstart();
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.buttonGetVerificationCode.setOnClickListener(v -> {
            String email = binding.editTextVerificationCode.getText().toString().trim();
//            sentCode(email);
            ValidationResult result = ValidationUtil.validateEmail(email);
            if (!result.isValid()) {
                Toast.makeText(getContext(), result.getErrorMessage(), Toast.LENGTH_SHORT).show();
                return;
            }
            mPresenter.getVerificationCode(email);
            startCountDown();
        });

        // 确认按钮点击事件
        binding.confirmButton.setOnClickListener(v -> {
            String email = binding.editTextVerificationCode.getText().toString().trim();
            String verificationCode = binding.codeview.getValue();
            String newPassword = binding.editTextPassword.getText().toString().trim();
            String confirmPassword = binding.editTextConfirmPassword.getText().toString().trim();
            ValidationResult[] results = {
                    ValidationUtil.validateEmail(email),
                    ValidationUtil.validatePassword(newPassword),
                    ValidationUtil.validateConfirmPassword(newPassword, confirmPassword),
                    ValidationUtil.validateVerificationCode(verificationCode)
            };
            for (ValidationResult result : results) {
                if (!result.isValid()) {
                    Toast.makeText(getContext(), result.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            mPresenter.onForgetClick(email, verificationCode,newPassword, confirmPassword);
        });
    }
    public boolean sentCode(String email) {
        if (!validateEmail(email).isValid()) {
            Toast.makeText(getContext(), "请输入正确的邮箱地址", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Toast.makeText(getContext(), "验证码发送中", Toast.LENGTH_SHORT).show();
            ValidationResult result = validateEmail(email);
            if (result.isValid()) {
//            Toast.makeText(getContext(), "验证码发送中", Toast.LENGTH_SHORT).show();
                mPresenter.getVerificationCode(email);
            } else {
                Toast.makeText(getContext(), result.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    }
    private void startCountDown() {
        binding.buttonGetVerificationCode.setEnabled(false);

        countDownTimer = new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                String text = String.format(getString(R.string.resend_countdown), millisUntilFinished / 1000);
                binding.buttonGetVerificationCode.setText(text);
            }

            public void onFinish() {
                resetButton();
            }
        }.start();
    }
    private void resetButton() {
        binding.buttonGetVerificationCode.setEnabled(true);
        binding.buttonGetVerificationCode.setText(R.string.get_verification_code);
    }
    @Override
    public void showError() {

    }

    @Override
    public Boolean isACtive() {
        return isAdded();
    }

    @Override
    public void setPresenter(ForgetContract.Presenter presenter) {
        mPresenter = presenter;
    }
}