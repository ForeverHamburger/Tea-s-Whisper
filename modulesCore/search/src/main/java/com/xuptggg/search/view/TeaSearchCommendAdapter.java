package com.xuptggg.search.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xuptggg.search.R;
import com.xuptggg.search.model.TeaShowInfo;

import java.util.List;

public class TeaSearchCommendAdapter extends RecyclerView.Adapter<TeaSearchCommendAdapter.TeaSearchCommendViewHolder> {

    private List<String> teaSearchCommend;

    public TeaSearchCommendAdapter(List<String> teaSearchCommend) {
        this.teaSearchCommend = teaSearchCommend;
    }
    @NonNull
    @Override
    public TeaSearchCommendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tea_search_commend, parent, false);
        return new TeaSearchCommendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeaSearchCommendViewHolder holder, int position) {
        String string = teaSearchCommend.get(position);
        holder.teaSearchCommend.setText(string);
    }

    @Override
    public int getItemCount() {
        return teaSearchCommend.size();
    }

    public static class TeaSearchCommendViewHolder extends RecyclerView.ViewHolder {
        TextView teaSearchCommend;

        public TeaSearchCommendViewHolder(@NonNull View itemView) {
            super(itemView);
            teaSearchCommend = itemView.findViewById(R.id.tv_tea_search_commend);
        }
    }
}