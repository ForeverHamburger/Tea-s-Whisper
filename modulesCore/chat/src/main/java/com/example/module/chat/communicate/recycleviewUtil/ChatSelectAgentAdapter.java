package com.example.module.chat.communicate.recycleviewUtil;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.module.chat.base.database.select.Agent;
import com.example.module.chat.databinding.ItemAgentBinding;

import java.util.ArrayList;
import java.util.List;

public class ChatSelectAgentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Agent> Agents = new ArrayList<>();

    ItemAgentBinding binding;

    public static class ChatAgentHolder extends RecyclerView.ViewHolder {
        public final ItemAgentBinding binding;

        public ChatAgentHolder(ItemAgentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Agent Agent) {
            binding.ivIcon.setImageResource(Agent.getIcon());
            binding.tvName.setText(Agent.getName());
            binding.tvDesc.setText(Agent.getDescription());
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
        Agent Agent = Agents.get(position);
        ((ChatSelectAgentAdapter.ChatAgentHolder) holder).bind(Agent);

    }

    @Override
    public int getItemCount() {
        return Agents == null ? 0 : Agents.size();
    }
    public void addAgentDataList(Agent agents) {
        Agents.add(agents);
        notifyItemInserted(Agents.size() - 1);
    }
    public void addAllMessageDataList(List<Agent> agentsList) {
        int startPosition = Agents.size();
        Agents.addAll(agentsList);
        notifyItemRangeInserted(startPosition, agentsList.size());
    }
}
