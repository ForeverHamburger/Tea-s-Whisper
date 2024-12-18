package com.xuptggg.module.login.LoginIn;

import android.os.Bundle;
import android.text.SpannableString;
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
import com.xuptggg.module.login.databinding.FragmentLoginInBinding;
public class LoginInFragment extends Fragment implements LoginInContract.View{
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
        binding.tlPassword.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE );
        binding.textViewForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mPresenter.onForgetPasswordClick();
                Toast.makeText(getContext(),"忘记密码",Toast.LENGTH_SHORT).show();
            }
        });
        String text = getString(R.string.login_forget_password);
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new UnderlineSpan(), 0, text.length(), 0);
        binding.textViewForget.setText(spannableString);
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
}