package com.xuptggg.detail.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.xuptggg.detail.R;
import com.xuptggg.detail.contract.IDetailContract;
import com.xuptggg.detail.databinding.ActivityDetailBinding;
import com.xuptggg.detail.model.DetailModel;
import com.xuptggg.detail.model.infos.DetailInfo;
import com.xuptggg.detail.presenter.DetailPresenter;
import com.xuptggg.detail.utils.AnimationUtils;
import com.xuptggg.detail.utils.GlideLoader;

@Route(path = "/detail/DetailActivity")
public class DetailActivity extends AppCompatActivity implements IDetailContract.IDetailView {
    private ActivityDetailBinding binding;
    private boolean isLiked = false;
    @Autowired(name =  "teaName")
    public String teaName;
    private IDetailContract.IDetailPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)   {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ARouter.getInstance().inject(this);

        binding.fabDetailLike.setOnClickListener(v -> {
            if (!isLiked) {
                AnimationUtils.performLikeAnimation(this, binding.fabDetailLike);
                isLiked = true;
            } else {
                AnimationUtils.performUnlikeAnimation(this, binding.fabDetailLike);
                isLiked = false;
            }
        });

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        startAnimation();

        setupSections();
        setPresenter(new DetailPresenter(new DetailModel(),this));
        mPresenter.getDetailInfo("西湖龙井");
    }

    private void startAnimation() {

        // 获取根视图
        View rootView = findViewById(android.R.id.content);

        // 初始时将视图缩小到一个点
        rootView.setScaleX(0f);
        rootView.setScaleY(0f);
        rootView.setPivotX(0);
        rootView.setPivotY(0);

        // 创建属性动画，从点击位置开始逐渐扩大
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(rootView, "scaleX", 0f, 1f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(rootView, "scaleY", 0f, 1f);
        scaleXAnimator.setDuration(500);
        scaleYAnimator.setDuration(500);
        scaleXAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleYAnimator.setInterpolator(new AccelerateDecelerateInterpolator());

        // 同时播放 X 和 Y 方向的缩放动画
        scaleXAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                // 动画结束后恢复视图的正常状态
                rootView.setScaleX(1f);
                rootView.setScaleY(1f);
            }
        });
        scaleXAnimator.start();
        scaleYAnimator.start();
    }

    @Override
    public void setPresenter(IDetailContract.IDetailPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showDetailInfomation(DetailInfo navigationInfos) {
        binding.ctlDetail.setTitle(navigationInfos.getTeaName());
        GlideLoader.with(this)
                .load(navigationInfos.getImageUrl())
                .into(binding.ivDetail);
        binding.tvDetailTitle.setText(navigationInfos.getTeaName());
        binding.tvContent.setText(navigationInfos.getTeaBrief());
        binding.tvGuideContent.setText(navigationInfos.getTeaGuide());
        binding.tvHistoryContent.setText(navigationInfos.getTeaHistory());
        binding.tvProcessContent.setText(navigationInfos.getTeaProcess());
    }

    @Override
    public void showError() {

    }

    private void setupSections() {
        // 设置点击监听（使用 binding 直接访问视图）
        setupSection(binding.sectionHistory, binding.tvHistoryContent, binding.ivHistoryArrow);
        setupSection(binding.sectionProcess, binding.tvProcessContent, binding.ivProcessArrow);
        setupSection(binding.sectionGuide, binding.tvGuideContent, binding.ivGuideArrow);

        // 默认展开第一个
        collapseSectionWithAnimation(binding.tvHistoryContent, binding.ivHistoryArrow);
        collapseSectionWithAnimation(binding.tvProcessContent, binding.ivProcessArrow);
        collapseSectionWithAnimation(binding.tvGuideContent, binding.ivGuideArrow);
    }

    private void setupSection(View container, final TextView content, final ImageView arrow) {
        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (content.getVisibility() == View.VISIBLE) {
                    collapseSectionWithAnimation(content, arrow);
                } else {
                    expandSectionWithAnimation(content, arrow);
                }
            }
        });
    }

    private void expandSectionWithAnimation(final TextView content, final ImageView arrow) {
        // 先测量实际内容高度
        content.measure(
                View.MeasureSpec.makeMeasureSpec(content.getWidth(), View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        );
        final int targetHeight = content.getMeasuredHeight();

        // 设置初始状态
        content.setVisibility(View.VISIBLE);
        content.getLayoutParams().height = 0;
        content.requestLayout();

        // 高度动画
        ValueAnimator heightAnim = ValueAnimator.ofInt(0, targetHeight);
        heightAnim.setInterpolator(new FastOutSlowInInterpolator());
        heightAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                content.getLayoutParams().height = (int) animation.getAnimatedValue();
                content.requestLayout();
            }
        });

        // 透明度动画（可选）
        ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(content, "alpha", 0f, 1f);
        alphaAnim.setInterpolator(new LinearInterpolator());

        // 箭头旋转动画
        ObjectAnimator rotateAnim = ObjectAnimator.ofFloat(arrow, "rotation", 0f, 180f);
        rotateAnim.setInterpolator(new AccelerateDecelerateInterpolator());

        // 组合动画
        AnimatorSet set = new AnimatorSet();
        set.playTogether(heightAnim, alphaAnim, rotateAnim);
        set.setDuration(400);
        set.start();
    }

    private void collapseSectionWithAnimation(final TextView content, final ImageView arrow) {
        final int startHeight = content.getHeight();

        // 高度动画
        ValueAnimator heightAnim = ValueAnimator.ofInt(startHeight, 0);
        heightAnim.setInterpolator(new FastOutSlowInInterpolator());
        heightAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                content.getLayoutParams().height = (int) animation.getAnimatedValue();
                content.requestLayout();
            }
        });

        // 透明度动画（可选）
        ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(content, "alpha", 1f, 0f);
        alphaAnim.setInterpolator(new LinearInterpolator());

        // 箭头旋转动画
        ObjectAnimator rotateAnim = ObjectAnimator.ofFloat(arrow, "rotation", 180f, 0f);
        rotateAnim.setInterpolator(new AccelerateDecelerateInterpolator());

        // 组合动画
        AnimatorSet set = new AnimatorSet();
        set.playTogether(heightAnim, alphaAnim, rotateAnim);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                content.setVisibility(View.GONE);
            }
        });
        set.setDuration(400);
        set.start();
    }
}