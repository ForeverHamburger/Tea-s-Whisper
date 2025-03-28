package com.xuptggg.home.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.xuptggg.home.R;
import com.xuptggg.home.contract.IHomeContract;
import com.xuptggg.home.databinding.FragmentHomeBinding;
import com.xuptggg.home.model.HomeModel;
import com.xuptggg.home.model.infos.TeaHistoryInfo;
import com.xuptggg.home.model.infos.TeaInfo;
import com.xuptggg.home.model.infos.TeaMakeInfo;
import com.xuptggg.home.presenter.HomePresenter;
import com.xuptggg.home.view.adapter.TeaCardAdapter;
import com.xuptggg.home.view.adapter.TeaHistoryAdapter;
import com.xuptggg.home.view.adapter.TeaMakeAdapter;
import com.xuptggg.module.libbase.eventbus.TokenManager;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

@Route(path = "/home/HomeFragment")
public class HomeFragment extends Fragment implements IHomeContract.IHomeView {
    private final static String TAG  = "HomeFragment";
    private IHomeContract.IHomePresenter mPresenter;
    private FragmentHomeBinding binding;
    @Autowired
    int containerId;
    private String token = null;

    public HomeFragment() {

    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater);
        // 初始化轮播图
        initBanner(binding.getRoot());

        // 初始化 RecyclerView
        initRecyclerView(binding.getRoot());

        ARouter.getInstance().inject(this);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setPresenter(new HomePresenter(new HomeModel(),this));

        binding.etSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Fragment fragment = (Fragment) ARouter.getInstance().build("/search/SearchFragment").navigation();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_right,R.anim.slide_out_left)
                        .replace(containerId,fragment)
                        .commit();
            }
        });

        binding.etSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/search/SearchActivity").navigation();
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getToken(TokenManager tokenManager) {
        Log.d(TAG, "getToken: " + token);
        mPresenter.getHomeInfo(tokenManager.getToken());
        Log.d(TAG, "getToken: " + token+"\\\\"+tokenManager.getToken());
    }

    private void initBanner(View view) {
        List<Integer> bannerImages = new ArrayList<>();
        bannerImages.add(R.drawable.tea_posters1);
        bannerImages.add(R.drawable.tea_posters2);
        bannerImages.add(R.drawable.tea_posters3);

        binding.banner.setAdapter(new BannerImageAdapter<Integer>(bannerImages) {
                    @Override
                    public void onBindView(BannerImageHolder holder, Integer data, int position, int size) {
                        holder.imageView.setImageResource(data);
                    }
                })
                .addBannerLifecycleObserver(this)
                .setIndicator(new CircleIndicator(requireContext()));
    }

    private void initRecyclerView(View view) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.rvTeaCard.setLayoutManager(layoutManager);

        List<TeaInfo> teaInfoList = new ArrayList<>();
        teaInfoList.add(new TeaInfo("龙井茶叶", "浙江杭州", R.drawable.tea_item1));
        teaInfoList.add(new TeaInfo("碧螺春", "江苏苏州", R.drawable.tea_item2));
        teaInfoList.add(new TeaInfo("铁观音", "福建安溪", R.drawable.tea_item3));
        teaInfoList.add(new TeaInfo("龙井茶叶", "浙江杭州", R.drawable.tea_item4));
        teaInfoList.add(new TeaInfo("碧螺春", "江苏苏州", R.drawable.tea_item2));

        TeaCardAdapter teaCardAdapter = new TeaCardAdapter(teaInfoList);
        binding.rvTeaCard.setAdapter(teaCardAdapter);

        binding.rvTeaCard.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                setScaleEffect(recyclerView);
            }
        });

        // 手动触发一次缩放效果设置，让初始状态就有中间大两边小的效果
        binding.rvTeaCard.post(() -> setScaleEffect(binding.rvTeaCard));

        // 使用 LinearSnapHelper 实现阻尼和居中效果
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(binding.rvTeaCard);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false);
        binding.rvTeaHistory.setLayoutManager(layoutManager2);

        List<TeaHistoryInfo> teaHistoryInfos = new ArrayList<>();
        teaHistoryInfos.add(new TeaHistoryInfo("陆羽问泉","茶圣陆羽为寻煮茶真味，踏遍三十州辨天下水质，著成《茶经》时笑叹：“好水如镜，照见茶魂。”",R.drawable.tea_history_item1));
        teaHistoryInfos.add(new TeaHistoryInfo("禅茶一味","唐代赵州和尚一句“吃茶去”，让三字成千年公案，禅院石阶至今留着被求道者踏出凹痕的茶碗印记。",R.drawable.tea_history_item));
        teaHistoryInfos.add(new TeaHistoryInfo("万里茶笺","徽商江氏兄弟在茶箱夹层藏家书，竟让武夷岩茶沿着中俄茶路成了游子们的“液体乡愁”。",R.drawable.tea_history_item2));

        TeaHistoryAdapter teaHistoryAdapter = new TeaHistoryAdapter(teaHistoryInfos);
        binding.rvTeaHistory.setAdapter(teaHistoryAdapter);


        LinearLayoutManager layoutManager1 = new LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false);
        binding.rvTeaMake.setLayoutManager(layoutManager1);

        List<TeaMakeInfo> teaMakeInfos = new ArrayList<>();
        teaMakeInfos.add(new TeaMakeInfo("杀青","高温钝化酶活性，锁住鲜绿与清香",R.drawable.tea_item1));
        teaMakeInfos.add(new TeaMakeInfo("萎凋","自然失水激发毫香，形成\"不炒不揉\"的纯净风味",R.drawable.tea_item2));
        teaMakeInfos.add(new TeaMakeInfo("渥堆","微生物主导的固态发酵，孕育陈香与醇厚",R.drawable.tea_item2));
        teaMakeInfos.add(new TeaMakeInfo("窨制","茶坯与鲜花层层叠合，淬炼\"茶引花香\"的灵动",R.drawable.tea_item1));

        TeaMakeAdapter teaMakeAdapter = new TeaMakeAdapter(teaMakeInfos);
        binding.rvTeaMake.setAdapter(teaMakeAdapter);

    }

    private void setScaleEffect(RecyclerView recyclerView) {
        int childCount = recyclerView.getChildCount();
        int recyclerViewCenter = recyclerView.getLeft() + recyclerView.getWidth() / 2;

        for (int j = 0; j < childCount; j++) {
            View v = recyclerView.getChildAt(j);
            int itemCenter = v.getLeft() + v.getWidth() / 2;
            float distanceFromCenter = Math.abs(recyclerViewCenter - itemCenter);
            float maxDistance = recyclerView.getWidth() / 2;
            float rate = distanceFromCenter / maxDistance;

            v.setScaleX(1 - rate * 0.3f);
            v.setScaleY(1 - rate * 0.3f);
        }
    }

    @Override
    public void setPresenter(IHomeContract.IHomePresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showHomeInfomation(List<TeaInfo> individualInfos) {

    }

    @Override
    public void showError() {

    }
}