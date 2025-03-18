package com.example.module.chat.communicate.recycleviewUtil;
import static com.example.module.chat.communicate.recycleviewUtil.ChatCommunicateAdapter.getRelativeTime;

import android.graphics.Color;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.module.chat.R;
import com.example.module.chat.base.database.communicate.Data;
import com.example.module.chat.base.database.select.DataItem;
import com.example.module.chat.communicate.base.ItemActionListener;
import com.example.module.chat.communicate.view.CommunicateView.CommunicateFragment;
import com.example.module.chat.communicate.view.CommunicateView.CommunicateModel;
import com.example.module.chat.communicate.view.CommunicateView.CommunicatePresenter;
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
        public ChatHistoryHolder(ItemHistoryBinding binding,ItemActionListener<DataItem> listener ) {
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
        return historys.isEmpty() ? TYPE_EMPTY : TYPE_ITEM;
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
            // 无需绑定数据，或设置空状态提示
            ((EmptyViewHolder) holder).bindEmptyView();
        }
    }


    @Override
    public int getItemCount() {
        return historys != null ? historys.size() : 0;
    }

    public void addHistoryDataList(DataItem history) {
        historys.add(history);
        notifyItemInserted(historys.size() - 1);
    }

    public void addAllHistoryDataList(List<DataItem> historyList) {
        int startPosition = historys.size();
        historys.addAll(historyList);
        notifyItemRangeInserted(startPosition, historyList.size());
    }

    public void addWithError(List<DataItem> historyList, String error) {
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
        }
    }
}
