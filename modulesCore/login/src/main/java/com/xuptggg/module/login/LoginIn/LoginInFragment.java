package com.xuptggg.module.login.LoginIn;

import static android.provider.Settings.System.getString;

import static com.xuptggg.module.login.base.ValidationUtil.PASSWORD_REGEX;
import static com.xuptggg.module.login.base.ValidationUtil.PHONE_REGEX_CN;
import static com.xuptggg.module.login.base.ValidationUtil.validateEmail;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.xuptggg.module.login.Forget.ForgetFragment;
import com.xuptggg.module.login.Forget.ForgetModel;
import com.xuptggg.module.login.Forget.ForgetPresenter;
import com.xuptggg.module.login.LoginActivity;
import com.xuptggg.module.login.R;
import com.xuptggg.module.login.Register.RegisterFragment;
import com.xuptggg.module.login.Register.RegisterModel;
import com.xuptggg.module.login.Register.RegisterPresenter;
import com.xuptggg.module.login.VerifyLogin.VerifyLoginFragment;
import com.xuptggg.module.login.VerifyLogin.VerifyLoginModel;
import com.xuptggg.module.login.VerifyLogin.VerifyLoginPresenter;
import com.xuptggg.module.login.base.InputValidator;
import com.xuptggg.module.login.base.ValidationResult;
import com.xuptggg.module.login.databinding.FragmentLoginInBinding;

import java.util.regex.Pattern;

public class LoginInFragment extends Fragment implements LoginInContract.View {
    private FragmentLoginInBinding binding;
    private LoginInContract.Presenter mPresenter;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentLoginInBinding.inflate(inflater, container, false);
        mPresenter.onstart();
        binding.getRoot().post(new Runnable() {
            @Override
            public void run() {
                int fragmentHeight = binding.getRoot().findViewById(R.id.ConstraintLayout_login).getHeight();
                Log.d("fragmentHeight", "LoginInFragment: " + fragmentHeight);
                ((LoginActivity) getActivity()).adjustCardViewForFragment(fragmentHeight);
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.tlUsername.setEndIconMode(TextInputLayout.END_ICON_CLEAR_TEXT);
        binding.tlPassword.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
        binding.tlUsername.setErrorIconDrawable(0);
        binding.tlPassword.setErrorIconDrawable(0);
        String text_forget_password = getString(R.string.login_forget_password);
        String text_to_register_before = getString(R.string.login_forget_to_register_before);
        String text_to_register_after = getString(R.string.login_forget_to_register_after);

        binding.textViewToRegister.setText(combineAndUnderline(text_to_register_before, text_to_register_after));
        binding.textViewForget.setText(combineAndUnderline("",text_forget_password));
        binding.buttonLogin.setOnClickListener(v -> {
            String phoneoremail = binding.editTextUsername.getText().toString();
            String password = binding.editTextPassword.getText().toString();
            if (!processLogin(phoneoremail, password)) {
                return;
            }
            mPresenter.onLoginClick(phoneoremail, password);
        });
        // 账号输入框焦点改变监听
        binding.editTextUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String phoneoremail = binding.editTextUsername.getText().toString().trim();
                    // 移除之前可能存在的错误提示
                    binding.tlUsername.setError(null);
                    if (phoneoremail.isEmpty() || phoneoremail.trim().isEmpty()) {
                        binding.tlUsername.setError("账号不能为空");
                    }
                    if (!Pattern.matches(PHONE_REGEX_CN, phoneoremail)&&!Patterns.EMAIL_ADDRESS.matcher(phoneoremail).matches()) {
                        binding.tlUsername.setError("账号格式错误");
                    }
                } else {
                    binding.tlUsername.setError(null);
                }
            }
        });
        binding.editTextPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String password = binding.editTextPassword.getText().toString();
                    binding.tlPassword.setError(null);
                    if (password == null || password.isEmpty()) {
                        binding.tlPassword.setError("密码不能为空");
                    }
                    if (password.length() < 8) {
                        binding.tlPassword.setError("密码至少需要8位");
                    }
                    if (!Pattern.matches(PASSWORD_REGEX, password)) {
                        binding.tlPassword.setError("需包含字母和数字");
                    }
                } else {
                    binding.tlPassword.setError(null);
                }
            }
        });
        binding.textViewToRegister.setOnClickListener(v -> {
            RegisterFragment registerFragment = new RegisterFragment();

            RegisterPresenter registerPresenter = new RegisterPresenter(registerFragment, new RegisterModel());
            registerFragment.setPresenter(registerPresenter);

            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, registerFragment)
                    .addToBackStack(null)
                    .commit();
        });
        binding.textViewLoginWithVerificationCode.setOnClickListener(v -> {
            VerifyLoginFragment verifyLoginFragment = new VerifyLoginFragment();

            VerifyLoginPresenter verifyLoginPresenter = new VerifyLoginPresenter(verifyLoginFragment, new VerifyLoginModel());
            verifyLoginFragment.setPresenter(verifyLoginPresenter);

            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, verifyLoginFragment)
                    .addToBackStack(null)
                    .commit();
        });
        binding.textViewForget.setOnClickListener(v -> {
            ForgetFragment forgetFragment = new ForgetFragment();

            ForgetPresenter ForgetPresenter = new ForgetPresenter(forgetFragment, new ForgetModel());
            forgetFragment.setPresenter(ForgetPresenter);

            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, forgetFragment)
                    .addToBackStack(null)
                    .commit();
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
    public void loginSuccess() {
        Log.d("test", "loginSuccess: 111");
        Toast.makeText(getActivity(), "Login Success!", Toast.LENGTH_SHORT).show();
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