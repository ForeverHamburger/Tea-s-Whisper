package com.xuptggg.module.login.VerifyLogin;

import static com.xuptggg.module.login.base.ValidationUtil.validateEmail;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.goodiebag.pinview.Pinview;
import com.google.android.material.textfield.TextInputLayout;
import com.xuptggg.module.login.Forget.ForgetFragment;
import com.xuptggg.module.login.Forget.ForgetModel;
import com.xuptggg.module.login.Forget.ForgetPresenter;
import com.xuptggg.module.login.LoginIn.LoginInFragment;
import com.xuptggg.module.login.LoginIn.LoginInModel;
import com.xuptggg.module.login.LoginIn.LoginInPresenter;
import com.xuptggg.module.login.R;
import com.xuptggg.module.login.base.ValidationResult;
import com.xuptggg.module.login.base.ValidationUtil;
import com.xuptggg.module.login.databinding.FragmentVerifyLoginBinding;

public class VerifyLoginFragment extends Fragment implements VerifyLoginContract.View {
    private FragmentVerifyLoginBinding binding;
    private VerifyLoginContract.Presenter mPresenter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentVerifyLoginBinding.inflate(inflater, container, false);
        mPresenter.onstart();
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        // 监听键盘的“完成”按钮
//        binding.editTextEmailAddress.setOnEditorActionListener((v, actionId, event) -> {
//            if (actionId == EditorInfo.IME_ACTION_DONE) {
//                String email = binding.editTextEmailAddress.getText().toString().trim();
//                Log.d("VerifyLoginFragment", "Email: " + email);
//                return sentCode(email);
//            }
//            return false;
//        });

        PinView pinview = binding.codeInput;
        binding.resendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.editTextEmailAddress.getText().toString().trim();
                sentCode(email);
            }
        });
        binding.continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.editTextEmailAddress.getText().toString();
                String verificationCode = pinview.getText().toString();
                mPresenter.onVerifyLoginClick(email, verificationCode);
            }
        });
        binding.buttonGetVerificationCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.editTextEmailAddress.getText().toString().trim();
                Log.d("VerifyLoginFragment", "Email: " + email);
                sentCode(email);
            }
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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    @Override
    public void showSuccess(String data) {
//        Toast.makeText(getActivity(), "Login Success!", Toast.LENGTH_SHORT).show();
        Toast.makeText(getActivity(), data, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void showError(String error){
        Toast.makeText(getActivity(), "验证码登录失败："+error, Toast.LENGTH_SHORT).show();
    }
    public void initView() {
        binding.tlEmailAddress.setEndIconMode(TextInputLayout.END_ICON_CLEAR_TEXT);
        binding.tlEmailAddress.setErrorIconDrawable(0);
        binding.editTextEmailAddress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String email = binding.editTextEmailAddress.getText().toString();
                    binding.tlEmailAddress.setError(null);
                    ValidationResult result = ValidationUtil.validateEmail(email);
                    if (!result.isValid()) {
                        binding.tlEmailAddress.setError(result.getErrorMessage());
                    }
                } else {
                    binding.tlEmailAddress.setError(null);
                }
            }
        });
        binding.codeInput.setOnClickListener(v -> {
            if (!binding.codeInput.isEnabled()) {
                Toast.makeText(getContext(), "请先获取验证码", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void enableVerificationCodeInput(boolean enabled) {
        binding.codeInput.setEnabled(enabled);
        binding.codeInput.setFocusable(enabled);
        if (enabled) {
            binding.codeInput.requestFocus(); // 自动弹出键盘
        }
    }
    @Override
    public Boolean isACtive() {
        return isAdded();
    }

    @Override
    public void setPresenter(VerifyLoginContract.Presenter presenter) {
        mPresenter = presenter;
    }
}