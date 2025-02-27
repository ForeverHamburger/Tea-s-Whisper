package com.xuptggg.module.login.Register;

import static com.xuptggg.module.login.LoginActivity.setupSoftKeyboardListener;
import static com.xuptggg.module.login.LoginIn.LoginInFragment.combineAndUnderline;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.xuptggg.module.login.LoginActivity;
import com.xuptggg.module.login.LoginIn.LoginInContract;
import com.xuptggg.module.login.LoginIn.LoginInFragment;
import com.xuptggg.module.login.LoginIn.LoginInModel;
import com.xuptggg.module.login.LoginIn.LoginInPresenter;
import com.xuptggg.module.login.R;
import com.xuptggg.module.login.base.ValidationResult;
import com.xuptggg.module.login.base.ValidationUtil;
import com.xuptggg.module.login.databinding.FragmentLoginInBinding;
import com.xuptggg.module.login.databinding.FragmentRegisterBinding;


public class RegisterFragment extends Fragment implements RegisterContract.View {
    private FragmentRegisterBinding binding;
    private RegisterContract.Presenter mPresenter;
    private CountDownTimer countDownTimer;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        mPresenter.onstart();
        Log.d("fragmentHeight", "RegisterFragment" );
        binding.getRoot().post(new Runnable() {
            @Override
            public void run() {
                int fragmentHeight = binding.getRoot().findViewById(R.id.ConstraintLayout_re).getHeight();
                Log.d("fragmentHeight", "RegisterFragment: " + fragmentHeight);

                ((LoginActivity) getActivity()).adjustCardViewForFragment(fragmentHeight);
            }
        });
        return binding.getRoot();
    }
    private void adjustCardViewHeight(int fragmentHeight) {
        View cardView = requireActivity().findViewById(R.id.cardView_login);
        View fragmentContainer = requireActivity().findViewById(R.id.fragment_container);
        ConstraintLayout layout = requireActivity().findViewById(R.id.main);

        // 获取屏幕总高度（更稳定）
        int screenHeight = requireActivity().getWindow().getDecorView().getHeight();
        if (screenHeight == 0) return; // 避免除零异常

        Log.d("FragmentHeight", "当前 Register Fragment高度：" + fragmentHeight);
        float heightPercent = (float) fragmentHeight / screenHeight;
        Log.d("FragmentHeight", "调整 CardView 高度百分比：" + heightPercent);

        // 设置 CardView 的约束
        ConstraintLayout.LayoutParams cardParams = (ConstraintLayout.LayoutParams) cardView.getLayoutParams();
        cardParams.matchConstraintPercentHeight = heightPercent;
        cardView.setLayoutParams(cardParams);

        // 设置 FragmentContainerView 紧跟 CardView
        FrameLayout.LayoutParams fragmentParams = (FrameLayout.LayoutParams) fragmentContainer.getLayoutParams();
        fragmentParams.topMargin = cardView.getId();  // 让 FragmentContainerView 紧跟 CardView
        fragmentContainer.setLayoutParams(fragmentParams);
    }
    @Override
    public void onResume() {
        super.onResume();
        binding.getRoot().post(new Runnable() {
            @Override
            public void run() {
                int fragmentHeight = binding.getRoot().findViewById(R.id.ConstraintLayout_re).getHeight();
                Log.d("fragmentHeight", "onreRegisterFragment: " + fragmentHeight);
                ((LoginActivity) getActivity()).adjustCardViewForFragment(fragmentHeight);
            }
        });
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String text_to_register_before = getString(R.string.login_have_to_register_before);
        String text_to_register_after = getString(R.string.login_have_to_register_after);
        binding.textViewToLogin.setText(combineAndUnderline(text_to_register_before, text_to_register_after));
        binding.buttonRegister.setOnClickListener(v -> {
            String email = binding.editTextEmail.getText().toString();
            String password = binding.editTextPassword.getText().toString();
            String phone = binding.editTextPhone.getText().toString();
            String confirmPassword = binding.editTextConfirmPassword.getText().toString();
            String verificationCode = binding.editTextVerificationCode.getText().toString();
            // 执行所有验证
            ValidationResult[] results = {
                    ValidationUtil.validatePhone(phone),
                    ValidationUtil.validateEmail(email),
                    ValidationUtil.validatePassword(password),
                    ValidationUtil.validateConfirmPassword(password, confirmPassword),
                    ValidationUtil.validateVerificationCode(verificationCode)
            };

            // 查找第一个错误
            for (ValidationResult result : results) {
                if (!result.isValid()) {
                    Toast.makeText(getContext(), result.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            mPresenter.onRegisterClick(email, password, phone, verificationCode);
        });
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
        binding.textViewToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isACtive()) {
                    LoginInFragment logininFragment = new LoginInFragment();
                    LoginInPresenter loginInPresenter = new LoginInPresenter(logininFragment, new LoginInModel());
                    logininFragment.setPresenter(loginInPresenter);
                    requireActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, logininFragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });
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

    // 在onDestroy中释放资源

    @Override
    public void onDestroy() {
        super.onDestroy();
//        binding = null;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        mPresenter.unSubscribe();
        mPresenter = null;
    }

    @Override
    public void showError() {

    }

    @Override
    public Boolean isACtive() {
        //判断是否加入到Activity
        return isAdded();
    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {
        mPresenter = presenter;
    }
}