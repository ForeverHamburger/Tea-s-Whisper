package com.xuptggg.search.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xuptggg.libnetwork.aword.LoadTasksCallBack;
import com.xuptggg.libnetwork.aword.aWord;
import com.xuptggg.libnetwork.aword.aWordHelper;
import com.xuptggg.search.R;

import java.util.List;

public class TeaSearchCommendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> teaSearchCommend;
    private OnItemClickListener listener;
    private static final int TYPE_EMPTY = 0;
    private static final int TYPE_ITEM = 1;

    public TeaSearchCommendAdapter(List<String> teaSearchCommend, OnItemClickListener listener) {
        this.teaSearchCommend = teaSearchCommend;
        this.listener = listener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tea_search_commend, parent, false);
        if (viewType == TYPE_EMPTY) {
            return new EmptyViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_empty_history, parent, false));
        }
        return new TeaSearchCommendViewHolder(view,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TeaSearchCommendViewHolder) {
            String string = teaSearchCommend.get(position);
            ((TeaSearchCommendViewHolder) holder).tvteaSearchCommend.setText(string);
        }
        if (holder instanceof EmptyViewHolder) {
            ((EmptyViewHolder) holder).bindEmptyView();
        }
    }

    @Override
    public int getItemCount() {
        return (teaSearchCommend == null || teaSearchCommend.isEmpty()) ? 1 : teaSearchCommend.size();
    }

    @Override
    public int getItemViewType(int position) {
        Log.d("getItemViewType", "position: " + position);
        return (teaSearchCommend == null || teaSearchCommend.isEmpty()) ? TYPE_EMPTY : TYPE_ITEM;
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




    public static class TeaSearchCommendViewHolder extends RecyclerView.ViewHolder {
        TextView tvteaSearchCommend;
        public TeaSearchCommendViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            tvteaSearchCommend = itemView.findViewById(R.id.tv_tea_search_commend);
            tvteaSearchCommend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!= null) {
                        int position = getAdapterPosition(); // 获取点击的位置
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position, tvteaSearchCommend.getText().toString()); // 通知 Activity
                        }
                    }
                }
            });
        }
    }
}