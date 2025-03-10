package com.example.module.chat.communicate.recycleviewUtil;

import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.module.chat.communicate.base.ChatMessage;
import com.example.module.chat.databinding.ItemChatReceivedBinding;
import com.example.module.chat.databinding.ItemChatSentBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.noties.markwon.Markwon;

public class ChatCommunicateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ChatMessage> messages = new ArrayList<>();
    private final Markwon markwon; // Markwon 实例

    public ChatCommunicateAdapter(Markwon markwon) {
        this.markwon = markwon;
    }

    public static class SentMessageHolder extends RecyclerView.ViewHolder {
        public final ItemChatSentBinding binding;

        public SentMessageHolder(ItemChatSentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void bind(ChatMessage message, Markwon markwon) {
            if (message.getFormattedContent() != null) {
                binding.tvSent.setText(message.getFormattedContent());
            } else {
                if (markwon != null) {
                    markwon.setMarkdown(binding.tvSent, message.getContent());
                } else {
                    binding.tvSent.setText(message.getContent());
                }
            }
            binding.tvSentTime.setText(getRelativeTime(message.getTimestamp()));
            binding.tvSent.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    public static class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        public final ItemChatReceivedBinding binding;

        public ReceivedMessageHolder(ItemChatReceivedBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void bind(ChatMessage message, Markwon markwon) {
            if (message.getFormattedContent() != null) {
                binding.tvReceived.setText(message.getFormattedContent());
            } else {
                if (markwon != null) {
                    markwon.setMarkdown(binding.tvReceived, message.getContent());
                } else {
                    binding.tvReceived.setText(message.getContent());
                }
            }
            binding.tvReceivedTime.setText(getRelativeTime(message.getTimestamp()));
            binding.tvReceived.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return messages.get(position).getType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == ChatMessage.TYPE_SENT) {
            ItemChatSentBinding binding = ItemChatSentBinding.inflate(inflater, parent, false);
            return new SentMessageHolder(binding);
        } else {
            ItemChatReceivedBinding binding = ItemChatReceivedBinding.inflate(inflater, parent, false);
            return new ReceivedMessageHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatMessage message = messages.get(position);
        // 如果未缓存，先解析 Markdown
        if (message.getFormattedContent() == null) {
            CharSequence formattedContent = markwon.toMarkdown(message.getContent());
            message.setFormattedContent(formattedContent);
        }
        if (holder instanceof SentMessageHolder) {
            ((SentMessageHolder) holder).bind(message, markwon);
        } else if (holder instanceof ReceivedMessageHolder) {
            ((ReceivedMessageHolder) holder).bind(message, markwon);
        }
    }

    @Override
    public int getItemCount() {
        return messages != null ? messages.size() : 0;
    }

    public void addMessageDataList(ChatMessage message) {
        messages.add(message);
        notifyItemInserted(messages.size() - 1);
    }

    public void addAllMessageDataList(List<ChatMessage> messageList) {
        int startPosition = messages.size();
        messages.addAll(messageList);
        notifyItemRangeInserted(startPosition, messageList.size());
    }

    static String formatTime(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }
    public static String getRelativeTime(long timestamp) {
        long now = System.currentTimeMillis();
        long diff = now - timestamp;

        if (diff < 60_000) {
            return "刚刚";
        } else if (diff < 3_600_000) {
            return diff / 60_000 + "分钟前";
        } else if (diff < 86_400_000) {
            return diff / 3_600_000 + "小时前";
        } else {
            return formatTime(timestamp);
        }
    }

}