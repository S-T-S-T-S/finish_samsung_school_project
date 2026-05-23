package com.example.ydmurt.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ydmurt.Lesson;
import com.example.ydmurt.LvlTraining;
import com.example.ydmurt.R;
import com.example.ydmurt.SetFragment;
import com.example.ydmurt.Test;


import java.util.ArrayList;

public class lessonAdapter extends RecyclerView.Adapter<lessonAdapter.ViewHolder> {


    ArrayList<Lesson> lessons;
    int id;
    Context context;

    FragmentManager fragmentManager;
    public lessonAdapter(ArrayList<Lesson> lessons,
                         FragmentManager fragmentManager, Context context) {

        this.lessons = lessons;
        this.fragmentManager = fragmentManager;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView photo;
        TextView text, num;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.photoLesson);
            text = itemView.findViewById(R.id.text_les);
            num = itemView.findViewById(R.id.numLesson);
            cardView = itemView.findViewById(R.id.b1);

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
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);

                    builder.setTitle("Учить или Тест");

                    builder.setPositiveButton("Тест", (dialog, which) -> {
                        id = position;
                        Test test = new Test();
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", id);
                        test.setArguments(bundle);

                        SetFragment.setFragment(fragmentManager, test);
                    });

                    builder.setNegativeButton("Учить", (dialog, which) -> {
                        id = position;
                        LvlTraining lvlTraining = new LvlTraining();
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", id);
                        lvlTraining.setArguments(bundle);

                        SetFragment.setFragment(fragmentManager, lvlTraining);
                    });

                    builder.show();

                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return lessons.size();
    }
}
