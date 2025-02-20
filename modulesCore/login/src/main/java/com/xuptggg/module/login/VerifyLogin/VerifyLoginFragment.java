package com.xuptggg.module.login.VerifyLogin;

import static com.xuptggg.module.login.base.ValidationUtil.validateEmail;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import com.goodiebag.pinview.Pinview;
import com.xuptggg.module.login.R;
import com.xuptggg.module.login.base.ValidationResult;
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
//        EditText emailAddress = binding.emailAddress;
        // 监听键盘的“完成”按钮
        binding.tlEmailAddress.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                String email = binding.tlEmailAddress.getText().toString().trim();
                return sentCode(email);
            }
            return false;
        });

        Pinview pinview = binding.codeInput;
        pinview.setPinViewEventListener((pinview1, fromUser) -> {
            // 当用户输入完成时触发
            if (pinview.getValue().length() == 6) {
                String email = binding.tlEmailAddress.getText().toString();
                String verificationCode = pinview1.getValue();
                mPresenter.onVerifyLoginClick(email, verificationCode);
                Toast.makeText(getContext(), "Entered: " + verificationCode, Toast.LENGTH_SHORT).show();
            }
        });
        binding.resendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.tlEmailAddress.getText().toString().trim();
                sentCode(email);
            }
        });
        binding.continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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
    public void showError() {

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