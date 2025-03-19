package com.xuptggg.module.login.Forget;

import static com.xuptggg.module.login.base.ValidationUtil.validateEmail;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.google.android.material.textfield.TextInputLayout;
import com.xuptggg.module.login.LoginIn.LoginInFragment;
import com.xuptggg.module.login.LoginIn.LoginInModel;
import com.xuptggg.module.login.LoginIn.LoginInPresenter;
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
        super.onViewCreated(view, savedInstanceState);
        initView();

        binding.buttonGetVerificationCode.setOnClickListener(v -> {
            String email = binding.editTextEmail.getText().toString().trim();
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
            String email = binding.editTextEmail.getText().toString().trim();
            String verificationCode = binding.codeInput.getText().toString();
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

        binding.ivNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginInFragment logininFragment = new LoginInFragment();
                LoginInPresenter loginInPresenter = new LoginInPresenter(logininFragment, new LoginInModel());
                logininFragment.setPresenter(loginInPresenter);
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(
                                R.anim.slide_in_left,
                                R.anim.slide_out_right
                        )
                        .replace(R.id.fragment_container, logininFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        Typeface typeface = ResourcesCompat.getFont(getActivity(), R.font.title_font);
        binding.textViewTitle.setTypeface(typeface);
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
    public void showSuccess(String data) {
        Toast.makeText(getActivity(), data, Toast.LENGTH_SHORT).show();
        if(data.equals("密码修改成功")){
            showNewDialogConfirmation();
        }
    }
    public void showNewDialogConfirmation() {
        new AlertDialog.Builder(requireContext())
                .setTitle("即将去登录")
                .setMessage("确定要去登录吗？")
                .setPositiveButton("确定", (dialog, which) -> {
                    initFragment();
                })
                .setNegativeButton("取消", (dialog, which) -> {
                    dialog.dismiss();
                })
                .create()
                .show();
    }
    private void initFragment() {
        String email = binding.editTextEmail.getText().toString();
        String password = binding.editTextPassword.getText().toString();
        LoginInFragment loginFragment = new LoginInFragment();
        Bundle args = new Bundle();
        args.putString("email", email);
        args.putString("password", password);
        loginFragment.setArguments(args);

        LoginInPresenter loginInPresenter = new LoginInPresenter(loginFragment, new LoginInModel());
        loginFragment.setPresenter(loginInPresenter);

        requireActivity().getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(
                        R.anim.slide_in_bottom,
                        R.anim.slide_out_bottom
                )
                .replace(R.id.fragment_container, loginFragment)
                .commit();
    }
    @Override
    public void showError(String error){
        Toast.makeText(getActivity(), "密码修改失败："+error, Toast.LENGTH_SHORT).show();
    }


    public void initView() {
        binding.tlPassword.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
        binding.tlEmail.setEndIconMode(TextInputLayout.END_ICON_CLEAR_TEXT);
        binding.tlConfirmPassword.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);

        binding.tlPassword.setErrorIconDrawable(0);
        binding.tlEmail.setErrorIconDrawable(0);
        binding.tlConfirmPassword.setErrorIconDrawable(0);

        binding.editTextPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String password = binding.editTextPassword.getText().toString();
                    binding.tlPassword.setError(null);
                    ValidationResult result = ValidationUtil.validatePassword(password);
                    if (!result.isValid()) {
                        binding.tlPassword.setError(result.getErrorMessage());
                    }
                } else {
                    binding.tlPassword.setError(null);
                }
            }
        });
        binding.editTextConfirmPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String ConfirmPassword = binding.editTextConfirmPassword.getText().toString();
                    String password = binding.editTextPassword.getText().toString();
                    binding.tlConfirmPassword.setError(null);
                    ValidationResult result = ValidationUtil.validateConfirmPassword(password,ConfirmPassword);
                    if (!result.isValid()) {
                        binding.tlConfirmPassword.setError(result.getErrorMessage());
                    }
                } else {
                    binding.tlConfirmPassword.setError(null);
                }
            }
        });
        binding.editTextEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String email = binding.editTextEmail.getText().toString();
                    binding.tlEmail.setError(null);
                    ValidationResult result = ValidationUtil.validateEmail(email);
                    if (!result.isValid()) {
                        binding.tlEmail.setError(result.getErrorMessage());
                    }
                } else {
                    binding.tlEmail.setError(null);
                }
            }
        });
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