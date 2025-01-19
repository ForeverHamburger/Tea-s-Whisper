package com.xuptggg.module.login.LoginIn;

import static android.provider.Settings.System.getString;

import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
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
        //绘制超链接下划线
        String text = getString(R.string.login_forget_password);
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new UnderlineSpan(), 0, text.length(), 0);
        binding.textViewForget.setText(spannableString);
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

    public void Validator(String username, String password) {

    }

}