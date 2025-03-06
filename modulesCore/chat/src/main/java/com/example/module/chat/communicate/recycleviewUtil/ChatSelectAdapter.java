package com.example.module.chat.communicate.recycleviewUtil;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.module.chat.communicate.base.ChatHistory;
import com.example.module.chat.communicate.base.ChatMessage;

import java.util.ArrayList;
import java.util.List;

public class ChatSelectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<ChatHistory> history = new ArrayList<>();
    private List<ChatMessage> messageList = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return 0;
    }
}
