package com.xuptggg.search.view.Result;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;
import com.xuptggg.libnetwork.aword.LoadTasksCallBack;
import com.xuptggg.libnetwork.aword.aWord;
import com.xuptggg.libnetwork.aword.aWordHelper;
import com.xuptggg.search.R;
import com.xuptggg.search.base.data.Post;
import com.xuptggg.search.base.data.Tea;
import com.xuptggg.search.base.data.User;
import com.xuptggg.search.databinding.ItemPostBinding;
import com.xuptggg.search.databinding.ItemTeaBinding;
import com.xuptggg.search.databinding.ItemUserBinding;

public class MultiTypeAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_EMPTY = 0;
    private static final int TYPE_TEA = 1;
    private static final int TYPE_USER = 2;
    private static final int TYPE_POST = 3;

    private List<T> Lists = new ArrayList<>();

    @Override
    public int getItemViewType(int position) {
        if (Lists.isEmpty()) {
            return TYPE_EMPTY;
        }
        Object item = Lists.get(position);
        if (item instanceof Tea) {
            return TYPE_TEA;
        } else if (item instanceof User) {
            return TYPE_USER;
        } else if (item instanceof Post) {
            return TYPE_POST;
        }
        throw new IllegalArgumentException("Unknown item type: " + item.getClass());
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_TEA) {
            ItemTeaBinding binding = ItemTeaBinding.inflate(inflater, parent, false);
            return new TeaViewHolder(binding);
        } else if (viewType == TYPE_USER) {
            ItemUserBinding binding = ItemUserBinding.inflate(inflater, parent, false);
            return new UserViewHolder(binding);
        } else if (viewType == TYPE_POST) {
            ItemPostBinding binding = ItemPostBinding.inflate(inflater, parent, false);
            return new PostViewHolder(binding);
        } else if (viewType == TYPE_EMPTY) {
            return new EmptyViewHolder(inflater.inflate(R.layout.item_empty_history, parent, false));
        } else {
            throw new IllegalArgumentException("Unknown view type: " + viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == TYPE_TEA) {
            Tea tea = (Tea) Lists.get(position);
            ((TeaViewHolder) holder).bind(tea);
        } else if (viewType == TYPE_USER) {
            User user = (User) Lists.get(position);
            ((UserViewHolder) holder).bind(user);
        } else if (viewType == TYPE_POST) {
            Post post = (Post) Lists.get(position);
            ((PostViewHolder) holder).bind(post);
        } else if (viewType == TYPE_EMPTY) {
            ((EmptyViewHolder) holder).bindEmptyView();
        }
    }

    @Override
    public int getItemCount() {
        return Lists.isEmpty() ? 1 : Lists.size();
    }

    public void setLists(List<T> lists) {
        this.Lists.clear();
        this.Lists.addAll(lists);
        notifyDataSetChanged();
    }
    public void addAllDataList(List<T> List) {
        if (List == null || List.isEmpty()) {
            Log.e("addAllDataList", "List is null or empty");
        }
        Lists.clear();
        Lists.addAll(List);
        notifyDataSetChanged(); // 直接刷新整个 RecyclerView
    }
    private static class TeaViewHolder extends RecyclerView.ViewHolder {
        public ItemTeaBinding binding;

        public TeaViewHolder(ItemTeaBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Tea tea) {
            binding.tvTeaShowName.setText(tea.getName());
            binding.tvTeaShowDetail.setText(tea.getDetail());
            Glide.with(binding.getRoot().getContext())
                    .load(tea.getImage())
                    .into(binding.ivTeaShowImage);
            binding.btnTeaShowMore.setOnClickListener(v -> {
//                Intent intent = new Intent(binding.getRoot().getContext(), TeaDetailActivity.class);
//                intent.putExtra("tea_id", tea.getId());
//                binding.getRoot().getContext().startActivity(intent);
                Toast.makeText(binding.getRoot().getContext(), "查看详情: " + tea.getName(), Toast.LENGTH_SHORT).show();
            });
            binding.getRoot().setOnClickListener(v -> {
                Toast.makeText(binding.getRoot().getContext(), "点击了: " + tea.getName(), Toast.LENGTH_SHORT).show();
            });
        }
    }

    private static class UserViewHolder extends RecyclerView.ViewHolder {
        public ItemUserBinding binding;

        public UserViewHolder(ItemUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(User user) {
            binding.tvTeaHistoryTitle.setText(user.getUsername());
            binding.tvTeaHistoryDetail.setText(user.getIntroduction());

            Glide.with(binding.getRoot().getContext())
                    .load(user.getUrl())
                    .into(binding.ivMessageIcon);

            binding.getRoot().setOnClickListener(v -> {
                Toast.makeText(binding.getRoot().getContext(), "查看用户: " + user.getUsername(), Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(binding.getRoot().getContext(), UserProfileActivity.class);
//                intent.putExtra("user_id", user.getUserId());
//                binding.getRoot().getContext().startActivity(intent);
            });
            binding.ibFollow.setImageResource(user.isFollow() ? R.drawable.icon_love : R.drawable.ic_like);
            binding.ibFollow.setOnClickListener(v -> {
                user.setFollow(!user.isFollow());
                binding.ibFollow.setImageResource(user.isFollow() ? R.drawable.icon_love : R.drawable.ic_like);

                updateFollowStatus(user.getUserId(), user.isFollow());
            });
        }
        // 更新关注状态
        private void updateFollowStatus(String userId, boolean isFollow) {

        }
    }

    private static class PostViewHolder extends RecyclerView.ViewHolder {
        private ItemPostBinding binding;

        public PostViewHolder(ItemPostBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Post post) {
            binding.tvWaterfallTitle.setText(post.getTitle()); // 设置帖子标题
            binding.tvWaterfallUserName.setText(post.getAuthorName()); // 设置作者名字
            binding.tvWfLoveCount.setText(formatVotes(post.getVotes())); // 设置点赞数
            Glide.with(binding.getRoot().getContext())
                    .load(post.getUrl())
                    .into(binding.ivWaterfallIcon);
            Glide.with(binding.getRoot().getContext())
                    .load(post.getAuthorUrl())
                    .into(binding.ivWaterfallUserIcon);

            binding.ivWfLoveIcon.setOnClickListener(v -> {
                post.setVotes(post.getVotes() + 1);
                binding.tvWfLoveCount.setText(formatVotes(post.getVotes()));

                updateVoteStatus(post.getPostId(), post.getVotes());
            });
            binding.getRoot().setOnClickListener(v -> {
//                Intent intent = new Intent(binding.getRoot().getContext(), PostDetailActivity.class);
//                intent.putExtra("post_id", post.getPostId());
//                binding.getRoot().getContext().startActivity(intent);
            });
        }

        private String formatVotes(int votes) {
            if (votes >= 1000) {
                return (votes / 1000) + "K";
            }
            return String.valueOf(votes);
        }

        private void updateVoteStatus(long postId, int votes) {

        }
    }
    private class EmptyViewHolder extends RecyclerView.ViewHolder {
        public EmptyViewHolder(View inflate) {
            super(inflate);
        }

        public void bindEmptyView() {
            TextView emptyText = itemView.findViewById(R.id.tv_text);
            if (emptyText != null) {
                aWordHelper helper = aWordHelper.getInstance();
                helper.fetchaWord(new LoadTasksCallBack<aWord.aWordMain>() {
                    @Override
                    public void onSuccess(aWord.aWordMain data) {
                        System.out.println("最新数据: " + data);
                        if (data != null) {
                            emptyText.setText(data.getContent());
                        } else {
                            emptyText.setText("没有找到数据");
                        }
                    }

                    @Override
                    public void onFailed(String errorMessage) {
                        System.out.println("请求失败: " + errorMessage);
                        aWord.aWordMain latestData = helper.getLatestData();
                        if (latestData != null) {
                            emptyText.setText(latestData.getContent());
                        } else {
                            emptyText.setText("没有找到数据");
                        }
                    }
                });
            }
        }
    }
}