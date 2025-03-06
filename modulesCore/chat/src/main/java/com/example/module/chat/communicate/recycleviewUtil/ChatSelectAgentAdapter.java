package com.example.module.chat.communicate.recycleviewUtil;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.module.chat.communicate.base.ChatAgent;
import com.example.module.chat.communicate.base.ChatMessage;
import com.example.module.chat.databinding.ItemAgentBinding;

import java.util.ArrayList;
import java.util.List;

public class ChatSelectAgentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<ChatAgent> Agents = new ArrayList<>();
    ItemAgentBinding binding;

    public static class ChatAgentHolder extends RecyclerView.ViewHolder {
        public final ItemAgentBinding binding;

        public ChatAgentHolder(ItemAgentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ChatAgent Agent) {

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemAgentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new ChatAgentHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChatAgent Agent = Agents.get(position);

    }

    @Override
    public int getItemCount() {
        return Agents == null ? 0 : Agents.size();
    }
    public void addAgentDataList(ChatAgent agents) {
        Agents.add(agents);
        notifyItemInserted(Agents.size() - 1);
    }
    public void addAllMessageDataList(List<ChatAgent> agentsList) {
        int startPosition = Agents.size();
        Agents.addAll(agentsList);
        notifyItemRangeInserted(startPosition, agentsList.size());
    }
}
