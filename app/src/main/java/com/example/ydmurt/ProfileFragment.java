package com.example.ydmurt;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ydmurt.data.AppDatabase;
import com.example.ydmurt.data.initFacts;

import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Random random = new Random();

        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        TextView interest = v.findViewById(R.id.fact_content);
        TextView tit_interest = v.findViewById(R.id.tv_fact_title);
        initFacts facts = new initFacts();
        interest.setText(facts.getFacts()[0]);
        Button button = v.findViewById(R.id.btn_next_fact);
        Button button1 = v.findViewById(R.id.btn_settings);
        tit_interest.setText(facts.getFactTitles()[0]);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = random.nextInt(facts.getFacts().length - 1);
                interest.setText(facts.getFacts()[i]);
                tit_interest.setText(facts.getFactTitles()[i]);
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = requireContext().getSharedPreferences("auth", MODE_PRIVATE);
                prefs.edit().clear().apply();
                Intent intent = new Intent(requireActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        return v;
    }


}