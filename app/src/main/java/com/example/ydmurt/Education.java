package com.example.ydmurt;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ydmurt.adapter.lessonAdapter;
import com.example.ydmurt.adapter.wordAdapter;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Education#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Education extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    int id;
    private String mParam1;
    private String mParam2;
    lessonAdapter adapter;
    RecyclerView recyclerView;

    public Education() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Education.
     */
    // TODO: Rename and change types and number of parameters
    public static Education newInstance(String param1, String param2) {
        Education fragment = new Education();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_education, container, false);
        recyclerView = view.findViewById(R.id.receclerViewlesson);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        ArrayList<Lesson> lessons = new ArrayList<>();
        lessons.add(new Lesson("Вежливые обращения", R.mipmap.ladoshka));
        lessons.add(new Lesson("Числа", R.mipmap.numbers));
        lessons.add(new Lesson("Дикие животные", R.mipmap.dikiy_animal));
        lessons.add(new Lesson("Домашние животные", R.mipmap.home_animal));
        lessons.add(new Lesson("Еда", R.mipmap.eat));
        lessons.add(new Lesson("Одежда", R.mipmap.cloths));
        lessons.add(new Lesson("Моя семья", R.mipmap.family));
        lessons.add(new Lesson("Овощи", R.mipmap.vegetabels));
        lessons.add(new Lesson("Цвета", R.mipmap.colors));
        lessons.add(new Lesson("Ягоды", R.mipmap.jagoda));
        lessons.add(new Lesson("Фразы", R.mipmap.phrases));
        lessonAdapter adapter = new lessonAdapter(lessons, getParentFragmentManager(),requireContext());
        recyclerView.setAdapter(adapter);


        return view;
    }

}