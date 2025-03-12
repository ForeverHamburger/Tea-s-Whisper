package com.xuptggg.forum.square;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.xuptggg.forum.R;
import com.xuptggg.forum.square.contract.IForumContract;
import com.xuptggg.forum.square.model.ForumInfo;
import com.xuptggg.forum.square.model.ForumModel;
import com.xuptggg.forum.square.presenter.ForumPresenter;

import java.util.List;

public class ForumActivity extends AppCompatActivity implements IForumContract.IForumView {
    private IForumContract.IForumPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forum);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.forum), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setPresenter(new ForumPresenter(this,new ForumModel()));
        mPresenter.getForumInfo("normal");
    }



    @Override
    public void setPresenter(IForumContract.IForumPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showForumInfomation(List<ForumInfo> forumInfos) {

    }

    @Override
    public void showError() {

    }
}