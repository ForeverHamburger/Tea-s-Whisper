package com.xuptggg.module.login.LoginIn;

import static android.provider.Settings.System.getString;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.xuptggg.module.login.R;
import com.xuptggg.module.login.base.InputValidator;
import com.xuptggg.module.login.databinding.FragmentLoginInBinding;

public class LoginInFragment extends Fragment implements LoginInContract.View {
    private FragmentLoginInBinding binding;
    private LoginInContract.Presenter mPresenter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentLoginInBinding.inflate(inflater, container, false);
        mPresenter.onstart();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.tlUsername.setEndIconMode(TextInputLayout.END_ICON_CLEAR_TEXT);
        binding.tlPassword.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
        binding.tlUsername.setErrorIconDrawable(0);
        binding.tlPassword.setErrorIconDrawable(0);
        binding.textViewForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mPresenter.onForgetPasswordClick();
                Toast.makeText(getContext(), "忘记密码", Toast.LENGTH_SHORT).show();
            }
        });
        String text_forget_password = getString(R.string.login_forget_password);
        String text_to_register_before = getString(R.string.login_forget_to_register_before);
        String text_to_register_after = getString(R.string.login_forget_to_register_after);

        binding.textViewToRegister.setText(combineAndUnderline(text_to_register_before, text_to_register_after));
        binding.textViewForget.setText(combineAndUnderline("",text_forget_password));
        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = binding.editTextUsername.getText().toString();
                String password = binding.editTextPassword.getText().toString();
                if (!processLogin(username, password)) {
                    return;
                }
                mPresenter.onLoginClick(username, password);
            }
        });
        binding.editTextUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String username = binding.editTextUsername.getText().toString();
                    // 移除之前可能存在的错误提示
                    binding.tlUsername.setError(null);
                    InputValidator validator = new InputValidator();
                    if (!validator.validateAccount(username)) {
                        binding.tlUsername.setError("账号格式不正确");
                    } else if (username.isEmpty()) {
                        binding.tlUsername.setError("账号不能为空");
                    } else if (username.length() < 6) {
                        binding.tlUsername.setError("账号长度不能小于6位");
                    }
                } else {
                    // 焦点重回时清除错误信息
                    binding.tlUsername.setError(null);
                }
            }
        });
        binding.editTextPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String password = binding.editTextPassword.getText().toString();
                    // 移除之前可能存在的错误提示
                    binding.tlPassword.setError(null);
                    if (password.isEmpty()) {
                        binding.tlPassword.setError("密码不能为空");
                    } else if (password.length() < 6) {
                        binding.tlPassword.setError("密码长度不能小于6位");
                    }
                } else {
                    // 焦点重回时清除错误信息
                    binding.tlPassword.setError(null);
                }
            }
        });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
//        binding = null;
        mPresenter.unSubscribe();
        mPresenter = null;
    }

    @Override
    public void showError() {

    }

//    @Override
//    public void setStarData(musicData starData) {
//        requireActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        });
//
//    }

    @Override
    public Boolean isACtive() {
        //判断是否加入到Activity
        return isAdded();
    }

    @Override
    public void setPresenter(LoginInContract.Presenter presenter) {
        mPresenter = presenter;
    }

    //辅助方法
    public static SpannableString combineAndUnderline(String firstPart, String secondPart) {
        // 为第一个字符串创建 SpannableString 并添加下划线
        SpannableString spannableFirstPart = new SpannableString(firstPart);
        spannableFirstPart.setSpan(new UnderlineSpan(), 0, firstPart.length(), 0);

        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.BLACK);
        spannableFirstPart.setSpan(colorSpan, 0, firstPart.length(), 0);

        // 为第二个字符串创建 SpannableString 并添加下划线
        SpannableString spannableSecondPart = new SpannableString(secondPart);
        spannableSecondPart.setSpan(new UnderlineSpan(), 0, secondPart.length(), 0);
        // 拼接两个 SpannableString
        SpannableString finalSpannableString = new SpannableString(firstPart + secondPart);
        // 仅对拼接后的后半段添加下划线
        finalSpannableString.setSpan(new UnderlineSpan(), firstPart.length(), firstPart.length() + secondPart.length(), 0);

        return finalSpannableString;
    }
    private boolean processLogin(String username, String password) {
        InputValidator validator = new InputValidator();
        if (!validator.validateAccount(username)) {
            binding.tlUsername.setError("账号格式不正确");
            Toast.makeText(getContext(), "账号格式不正确", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (username.isEmpty() || password.isEmpty()) {
            if (username.isEmpty()) {
                binding.tlUsername.setError("账号不能为空");
            }
            if (password.isEmpty()) {
                binding.tlPassword.setError("密码不能为空");
            }
            Toast.makeText(getContext(), "请输入账号和密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (username.length() < 6 || password.length() < 6) {
            if (username.length() < 6) {
                binding.tlUsername.setError("账号长度不能小于6位");
            }
            if (password.length() < 6) {
                binding.tlPassword.setError("密码长度不能小于6位");
            }
            return false;
        }
        return true;
    }
}