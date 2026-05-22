package com.example.ydmurt;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
    CardView b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11;
    // TODO: Rename and change types of parameters
    int id;
    private String mParam1;
    private String mParam2;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_education, container, false);


        b1 = view.findViewById(R.id.b1);
        b2 = view.findViewById(R.id.b2);
        b3 = view.findViewById(R.id.b3);
        b4 = view.findViewById(R.id.b4);
        b5 = view.findViewById(R.id.b5);
        b6 = view.findViewById(R.id.b6);
        b7 = view.findViewById(R.id.b7);
        b8 = view.findViewById(R.id.b8);
        b9 = view.findViewById(R.id.b9);
        b10 = view.findViewById(R.id.b10);
        b11 = view.findViewById(R.id.b11);
        ArrayList<CardView> cardViews = new ArrayList<CardView>(Arrays.asList(b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11));
        for (CardView i : cardViews) {
            i.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = getResources().getResourceEntryName(i.getId());
                    if (name.length() == 3) {
                         id = Character.getNumericValue(name.charAt(name.length() - 2)) * 10 + Character.getNumericValue(name.charAt(name.length() - 1));

                    } else {
                         id = Character.getNumericValue(name.charAt(name.length() - 1));
                    }
                    LvlTraining lvlTraining = new LvlTraining();
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", id - 1);
                    lvlTraining.setArguments(bundle);

                    setfragment(lvlTraining);
                }
            });
        }

        return view;
    }

    private void setfragment(Fragment fragment) {
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        ft.replace(R.id.mainframe, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}