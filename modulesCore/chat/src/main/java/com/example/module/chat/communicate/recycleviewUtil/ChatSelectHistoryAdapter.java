package com.example.module.chat.communicate.recycleviewUtil;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.module.chat.communicate.base.ChatHistory;
import com.example.module.chat.communicate.base.ChatMessage;
import com.example.module.chat.databinding.ItemChatReceivedBinding;
import com.example.module.chat.databinding.ItemHistoryBinding;

import java.util.ArrayList;
import java.util.List;

public class ChatSelectHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<ChatHistory> historys = new ArrayList<>();
    public static class ChatHistoryHolder extends RecyclerView.ViewHolder{
        public final ItemHistoryBinding binding;
        public ChatHistoryHolder(ItemHistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(ChatHistory history){

        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemHistoryBinding binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ChatSelectHistoryAdapter.ChatHistoryHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatHistory history = historys.get(position);
    }


    @Override
    public int getItemCount() {
        return historys != null ? historys.size() : 0;
    }
    public void addHistoryDataList(ChatHistory history) {
        historys.add(history);
        notifyItemInserted(historys.size() - 1);
    }
    public void addAllHistoryDataList(List<ChatHistory> historyList) {
        int startPosition = historys.size();
        historys.addAll(historyList);
        notifyItemRangeInserted(startPosition, historyList.size());
    }
}
