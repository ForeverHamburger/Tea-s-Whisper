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
        EditText emailAddress = binding.emailAddress;
        // 监听键盘的“完成”按钮
        emailAddress.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                String email = emailAddress.getText().toString().trim();
                getVerificationCode(email);
                return true;
            }
            return false;
        });

        Pinview pinview = binding.codeInput;
        pinview.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean fromUser) {
                // 当用户输入完成时触发
                String email = binding.emailAddress.getText().toString();
                String verificationCode = pinview.getValue();
                mPresenter.onVerifyLoginClick(email,verificationCode);
                Toast.makeText(getContext(), "Entered: " + verificationCode, Toast.LENGTH_SHORT).show();
            }
        });
        binding.resendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailAddress.getText().toString().trim();
                getVerificationCode(email);
            }
        });
        binding.continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    public void getVerificationCode(String email) {
        ValidationResult result = validateEmail(email);
        // 调用后端发送验证码
        if (result.isValid()) {
            mPresenter.getVerificationCode(email);
        } else {
            Toast.makeText(getContext(), result.getErrorMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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