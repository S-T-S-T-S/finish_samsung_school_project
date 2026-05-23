package com.example.ydmurt.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ydmurt.Lesson;
import com.example.ydmurt.R;


import java.util.ArrayList;

public class lessonAdapter extends RecyclerView.Adapter<lessonAdapter.ViewHolder> {


    ArrayList<Lesson> lessons;

    public lessonAdapter(ArrayList<Lesson> lessons) {
        this.lessons = lessons;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView photo;
        TextView text, num;

        public ViewHolder(View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.photoLesson);
            text = itemView.findViewById(R.id.text_les);
            num = itemView.findViewById(R.id.numLesson);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lesson, parent, false);
        return new lessonAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.text.setText(lessons.get(position).getTitle());

        holder.photo.setImageResource(
                lessons.get(position).getImage()
        );
        holder.num.setText(String.valueOf(position + 1));
    }


    @Override
    public int getItemCount() {
        return lessons.size();
    }
}
