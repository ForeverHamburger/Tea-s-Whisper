package com.example.module.chat.communicate.recycleviewUtil;
import static com.example.module.chat.communicate.recycleviewUtil.ChatCommunicateAdapter.getRelativeTime;

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
import com.example.module.chat.communicate.view.CommunicateView.CommunicateFragment;
import com.example.module.chat.communicate.view.CommunicateView.CommunicateModel;
import com.example.module.chat.communicate.view.CommunicateView.CommunicatePresenter;
import com.example.module.chat.databinding.ItemHistoryBinding;

import java.util.ArrayList;
import java.util.List;

import io.noties.markwon.Markwon;

public class ChatSelectHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<DataItem> historys = new ArrayList<>();
    private static final int TYPE_EMPTY = 0;
    private static final int TYPE_ITEM = 1;
    private final Markwon markwon; // Markwon 实例
    public ChatSelectHistoryAdapter(Markwon markwon) {
        this.markwon = markwon;
    }

    public static class ChatHistoryHolder extends RecyclerView.ViewHolder {
        public final ItemHistoryBinding binding;
        Data data;

        public ChatHistoryHolder(ItemHistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void bind(DataItem history, Markwon markwon) {
            data = history.getMsg();
            if (markwon != null) {
                markwon.setMarkdown(binding.tvContent, data.getContent());
            } else {
                binding.tvContent.setText(data.getContent());
            }
            binding.tvTime.setText(getRelativeTime(data.getTimestamp().getTime()));
            binding.tvTittle.setText(history.getTitle());
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
        return new ChatSelectHistoryAdapter.ChatHistoryHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ChatHistoryHolder) {
            DataItem history = historys.get(position);
            ((ChatHistoryHolder) holder).bind(history, markwon);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 处理点击事件
                    FragmentManager fm = getSupportFragmentManager();
                    CommunicateFragment communicateFragment = (CommunicateFragment) fm.findFragmentById(R.id.fragment_container);
                    FragmentTransaction ft = fm.beginTransaction();
                    if (communicateFragment == null) {
                        communicateFragment = new CommunicateFragment();
                    }
                    CommunicatePresenter communicatePresenter = new CommunicatePresenter(communicateFragment, new CommunicateModel());
                    communicateFragment.setPresenter(communicatePresenter);
                    ft.add(R.id.fragment_container, communicateFragment);
                    ft.commit();
                }
            });
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

    private class EmptyViewHolder extends RecyclerView.ViewHolder {
        public EmptyViewHolder(View inflate) {
            super(inflate);
        }

        public void bindEmptyView() {
            Log.d("bindEmptyView", "bindEmptyView");
        }
    }
}
