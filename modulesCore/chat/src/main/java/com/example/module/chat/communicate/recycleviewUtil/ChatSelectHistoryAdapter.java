package com.example.module.chat.communicate.recycleviewUtil;
import static com.example.module.chat.communicate.recycleviewUtil.ChatCommunicateAdapter.getRelativeTime;

import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.module.chat.R;
import com.xuptggg.libnetwork.aword.aWord;
import com.xuptggg.libnetwork.aword.aWordHelper;
import com.example.module.chat.base.database.communicate.Data;
import com.example.module.chat.base.database.select.DataItem;
import com.xuptggg.libnetwork.aword.LoadTasksCallBack;
import com.example.module.chat.communicate.base.ItemActionListener;
import com.example.module.chat.databinding.ItemHistoryBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.noties.markwon.Markwon;

public class ChatSelectHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<DataItem> historys = new ArrayList<>();
    private static final int TYPE_EMPTY = 0;
    private static final int TYPE_ITEM = 1;
    private final Markwon markwon;

    private final ItemActionListener listener;

    public ChatSelectHistoryAdapter(Markwon markwon, ItemActionListener<DataItem> listener) {
        this.markwon = markwon;
        this.listener = listener;
    }

    public static class ChatHistoryHolder extends RecyclerView.ViewHolder {
        public final ItemHistoryBinding binding;
        private long lastClickTime = 0;
        Data data;
        private DataItem currentItem;
        private final ItemActionListener<DataItem> listener; // 持有监听器引用

        public ChatHistoryHolder(ItemHistoryBinding binding, ItemActionListener<DataItem> listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.listener = listener;
            // 点击防抖机制（500ms间隔）
            itemView.setOnClickListener(v -> {
                long currentTime = SystemClock.elapsedRealtime();
                if (currentTime - lastClickTime < 500) return;
                lastClickTime = currentTime;

                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION && listener != null) {
                    listener.onItemClick(currentItem, pos);
                }
            });
            // 长按事件处理
            itemView.setOnLongClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION && listener != null) {
                    listener.onLongClick(currentItem, pos);
                    return true;
                }
                return false;
            });
        }

        private void bind(DataItem history, Markwon markwon) {
            currentItem = history;
            data = history.getMsg();
            if (markwon != null) {
                markwon.setMarkdown(binding.tvContent, data.getContent());
            } else {
                binding.tvContent.setText(data.getContent());
            }
            binding.tvTime.setText(getRelativeTime(data.getTimestamp().getTime()));
            binding.tvTittle.setText(history.getTitle());
            binding.cvHistoryAvatarContainer.setCardBackgroundColor(getRandomColor(itemView));

        }

    }

    @Override
    public int getItemViewType(int position) {
        Log.d("getItemViewType", "position: " + position);
        return (historys == null || historys.isEmpty()) ? TYPE_EMPTY : TYPE_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemHistoryBinding binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        if (viewType == TYPE_EMPTY) {
            return new EmptyViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_empty_history, parent, false));
        }
        return new ChatHistoryHolder(binding, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ChatHistoryHolder) {
            DataItem history = historys.get(position);
            ((ChatHistoryHolder) holder).bind(history, markwon);
        } else if (holder instanceof EmptyViewHolder) {
            ((EmptyViewHolder) holder).bindEmptyView();
        }
    }

    @Override
    public int getItemCount() {
        Log.d("getItemCount", "historys.size(): " + historys.size());
        return (historys == null || historys.isEmpty()) ? 1 : historys.size();
    }

    public void addHistoryDataList(DataItem history) {
        historys.add(history);
        notifyItemInserted(historys.size() - 1);
    }

    public void addAllHistoryDataList(List<DataItem> historyList) {
        if (historyList == null || historyList.isEmpty()) {
            Log.e("addAllHistoryDataList", "historyList is null or empty");
        }
        // 清空旧数据
        historys.clear();
        // 添加新数据
        historys.addAll(historyList);
        notifyDataSetChanged(); // 直接刷新整个 RecyclerView
    }
    //会闪现
//    public void addAllHistoryDataList(List<DataItem> historyList) {
//        if (historyList == null || historyList.isEmpty()) {
//            Log.e("addAllHistoryDataList", "historyList is null or empty");
//            return; // 如果数据为空，直接返回
//        }
//
//        // 清空旧数据
//        int oldSize = historys.size();
//        historys.clear();
//        if (oldSize > 0) {
//            notifyItemRangeRemoved(0, oldSize); // 通知 RecyclerView 移除旧数据
//        }
//
//        // 添加新数据
//        historys.addAll(historyList);
//        notifyItemRangeInserted(0, historyList.size()); // 通知 RecyclerView 插入新数据
//    }

    public void addWithError(List<DataItem> historyList, String error) {
        if (error != null) {
            // 如果有错误信息，清空现有数据并显示错误状态
            Log.e("addWithError", "Error: " + error);
            int itemCount = historys.size();
            historys.clear(); // 清空现有数据
            if (itemCount > 0) {
                notifyItemRangeRemoved(0, itemCount); // 通知 RecyclerView 移除所有数据
            }
            return;
        }

        if (historyList == null || historyList.isEmpty()) {
            // 如果数据为空，清空现有数据
            Log.e("addWithError", "historyList is null or empty");
            int itemCount = historys.size();
            historys.clear(); // 清空现有数据
            if (itemCount > 0) {
                notifyItemRangeRemoved(0, itemCount); // 通知 RecyclerView 移除所有数据
            }
            return;
        }

        // 如果数据正常，添加新数据并通知 RecyclerView 更新
        int startPosition = historys.size();
        historys.addAll(historyList);
        notifyItemRangeInserted(startPosition, historyList.size());
    }

    private static int getRandomColor(View itemView) {
        int[] colors = {
                itemView.getResources().getColor(R.color.emerald_green),
                itemView.getResources().getColor(R.color.light_green)
        };

        // 随机选择一种颜色
        Random random = new Random();
        int randomIndex = random.nextInt(colors.length);
        int selectedColor = colors[randomIndex];
        return selectedColor;
    }

    private class EmptyViewHolder extends RecyclerView.ViewHolder {
        public EmptyViewHolder(View inflate) {
            super(inflate);
        }

        public void bindEmptyView() {
            Log.d("bindEmptyView", "bindEmptyView");
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