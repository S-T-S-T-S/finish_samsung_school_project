package com.example.ydmurt;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectTranslation#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectTranslation extends Fragment {
    RadioButton one, two, three, four;
    int score = 0, all = 0;
    String answer;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SelectTranslation() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SelectTranslation.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectTranslation newInstance(String param1, String param2) {
        SelectTranslation fragment = new SelectTranslation();
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
        View view = inflater.inflate(R.layout.fragment_select_translation, container, false);
        RadioGroup radioGroup = view.findViewById(R.id.radioGroup);
        ProgressBar progressBar=view.findViewById(R.id.progress);
        TextView textView = view.findViewById(R.id.translScore);
        textView.setText("Правильно"+score + "/" + all);
        progressBar.setProgress(0); // Установить 50%


        answer = upt(view);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == -1) return;

                RadioButton radioButton = view.findViewById(checkedId);

                if (radioButton == null) return;
                if (answer == radioButton.getText()) {
                        score++;
                        showToast("✓ Правильно!", Toast.LENGTH_SHORT);
                    } else {
                        showToast("✗ Неправильно! Правильный перевод: " + answer, Toast.LENGTH_LONG);
                    }

                all++;
                textView.setText("Правильно"+score + "/" + all);
                progressBar.setProgress((int) (((float) (score)/(float)(all))*100));
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    progressBar.setProgress((int) (((float) (score)/(float)(all))*100), true);
                }
                radioGroup.clearCheck();
                answer = upt(view);

            }
        });

        return view;
    }

    private String upt(View view) {
        Random random = new Random();
        TextView udm = view.findViewById(R.id.YdmurtTrans);
        ArrayList<ArrayList<ArrayList<String>>> words = new WORDS().words;
        ArrayList<ArrayList<String>> cat = words.get(random.nextInt(words.size() - 1));
        int name_id = random.nextInt(cat.size() - 1);
        ArrayList<String> name = cat.get(name_id);
        udm.setText(name.get(0));
        one = view.findViewById(R.id.radioButton);
        two = view.findViewById(R.id.radioButton2);
        three = view.findViewById(R.id.radioButton3);
        four = view.findViewById(R.id.radioButton4);
        ArrayList<RadioButton> radioButtons = new ArrayList<>(Arrays.asList(one, two, three, four));
        ArrayList<String> usedAnswers = new ArrayList<>();
        int radio_id = random.nextInt(radioButtons.size() - 1);

        RadioButton radio = radioButtons.get(radio_id);
        radioButtons.remove(radio_id);
        radio.setText(name.get(1));
        usedAnswers.add(name.get(1));
        for (RadioButton btn : radioButtons) {
            String answer;

            do {
                ArrayList<ArrayList<String>> randomCat = words.get(random.nextInt(words.size()));
                ArrayList<String> randomWord = randomCat.get(random.nextInt(randomCat.size()));
                answer = randomWord.get(1);

            } while (usedAnswers.contains(answer));

            usedAnswers.add(answer);
            btn.setText(answer);
        }
        return name.get(1);
    }
    private void showToast(String message, int duration) {
        Toast.makeText(getContext(), message, duration).show();
    }
}