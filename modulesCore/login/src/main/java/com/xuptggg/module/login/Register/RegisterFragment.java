package com.xuptggg.module.login.Register;

import static com.xuptggg.module.login.LoginIn.LoginInFragment.combineAndUnderline;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuptggg.module.login.LoginIn.LoginInContract;
import com.xuptggg.module.login.R;
import com.xuptggg.module.login.databinding.FragmentLoginInBinding;
import com.xuptggg.module.login.databinding.FragmentRegisterBinding;


public class RegisterFragment extends Fragment implements RegisterContract.View {
    private FragmentRegisterBinding binding;
    private LoginInContract.Presenter mPresenter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        mPresenter.onstart();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String text_to_register_before = getString(R.string.login_have_to_register_before);
        String text_to_register_after = getString(R.string.login_have_to_register_after);
        binding.textViewToRegister.setText(combineAndUnderline(text_to_register_before, text_to_register_after));

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