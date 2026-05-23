package com.example.ydmurt.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ydmurt.R;
import com.example.ydmurt.data.WORDS;

import java.util.ArrayList;

public class wordAdapter extends RecyclerView.Adapter<wordAdapter.ViewHolder> {

    ArrayList<ArrayList<ArrayList<String>>> words = new WORDS().getWords();

    private Integer id;

    public wordAdapter(Integer id) {
        this.id = id;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice, start, finish;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            start = itemView.findViewById(R.id.tvStart);
            finish = itemView.findViewById(R.id.tvFin);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_word, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ArrayList<ArrayList<String>> word = words.get(id);
        ArrayList<String> w = word.get(position);
        holder.tvName.setText(w.get(0));
        holder.tvPrice.setText(String.valueOf(w.get(1)));
        holder.start.setText(String.valueOf(w.get(2)));
        holder.finish.setText(String.valueOf(w.get(3)));
    }

    @Override
    public int getItemCount() {
        return words.get(id).size();
    }

}