package com.example.ydmurt;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link game#newInstance} factory method to
 * create an instance of this fragment.
 */
public class game extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public game() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment game.
     */

    public static game newInstance(String param1, String param2) {
        game fragment = new game();
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
        View v = inflater.inflate(R.layout.fragment_game, container, false);

        CardView button = v.findViewById(R.id.buttonSelectTranslation);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetFragment.setFragment(getParentFragmentManager(),new SelectTranslation());
            }
        });
        CardView game_true = v.findViewById(R.id.buttonTrueFalse);
        game_true.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetFragment.setFragment(getParentFragmentManager(),new GameTrueFalse());
            }
        });
        CardView game_Fix = v.findViewById(R.id.GameFixButton);
        game_Fix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetFragment.setFragment(getParentFragmentManager(),new GameFixFragment());
            }
        });
        return v;
    }


}