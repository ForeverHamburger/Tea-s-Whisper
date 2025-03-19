package com.example.module.chat.communicate.recycleviewUtil;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.module.chat.base.database.select.Agent;
import com.example.module.chat.communicate.base.ItemActionListener;
import com.example.module.chat.databinding.ItemAgentBinding;

import java.util.ArrayList;
import java.util.List;

public class ChatSelectAgentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Agent> Agents = new ArrayList<>();

    ItemAgentBinding binding;
    private final ItemActionListener<Agent> listener;

    public ChatSelectAgentAdapter(ItemActionListener<Agent> listener) {
        this.listener = listener;
    }

    public static class ChatAgentHolder extends RecyclerView.ViewHolder {
        public final ItemAgentBinding binding;
        private final ItemActionListener<Agent> listener;
        private Agent currentItem;

        public ChatAgentHolder(ItemAgentBinding binding, ItemActionListener<Agent> listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.listener = listener;
            itemView.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION && listener != null) {
                    listener.onItemClick(currentItem, pos);
                }
            });
        }

        public void bind(Agent Agent) {
            currentItem = Agent;
            binding.ivIcon.setImageResource(Agent.getIcon());
            binding.tvName.setText(Agent.getName());
            binding.tvDesc.setText(Agent.getDescription());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemAgentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ChatAgentHolder(binding, listener);
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
            Agents.clear();
            // 添加新数据
            Agents.addAll(agentsList);
            notifyDataSetChanged(); // 直接刷新整个 RecyclerView
    }
//    public void addAllMessageDataList(List<Agent> agentsList) {
//        if (agentsList == null || agentsList.isEmpty()) {
//            Log.e("addAllMessageDataList", "agentsList is null or empty");
//            return; // 如果数据为空，直接返回
//        }
//
//        // 清空旧数据
//        int oldSize = Agents.size();
//        Agents.clear();
//        if (oldSize > 0) {
//            notifyItemRangeRemoved(0, oldSize); // 通知 RecyclerView 移除旧数据
//        }
//
//        // 添加新数据
//        Agents.addAll(agentsList);
//        notifyItemRangeInserted(0, agentsList.size()); // 通知 RecyclerView 插入新数据
//    }
}
