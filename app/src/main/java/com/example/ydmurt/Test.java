package com.example.ydmurt;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ydmurt.data.WORDS;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Test#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Test extends Fragment {
    int myId, countTest, count, right, currentIndex;


    ProgressBar progressBarTest;
    Button button;
    TextView wordText, progressTextTest;
    EditText editText;
    ArrayList<Integer> usedIndexesRu = new ArrayList<>();
    ArrayList<Integer> usedIndexesYd = new ArrayList<>();
    boolean isUdmurtToRussian;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Test() {

    }

    public static Test newInstance(String param1, String param2) {
        Test fragment = new Test();
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

        View v = inflater.inflate(R.layout.fragment_test, container, false);

        progressBarTest = v.findViewById(R.id.progressBarTest);
        progressTextTest = v.findViewById(R.id.tvQuestion);
        wordText = v.findViewById(R.id.tvWordTest);
        button = v.findViewById(R.id.buttonTest);
        editText = v.findViewById(R.id.editTest);

        Bundle bundle = this.getArguments();
        myId = bundle.getInt("id");

        ArrayList<ArrayList<String>> selwords =
                new WORDS().getSelectwords(myId);

        countTest = selwords.size() * 2;
        count = 0;
        right = 0;

        progressBarTest.setMax(countTest);

        nextQuestion(selwords);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String answer = editText.getText()
                        .toString()
                        .trim()
                        .toLowerCase();

                ArrayList<String> currentWord =
                        selwords.get(currentIndex);

                String correctAnswer;

                // если показываем удмуртское слово
                // надо ответить по русски
                if (isUdmurtToRussian) {
                    correctAnswer = currentWord.get(1).toLowerCase();
                } else {
                    // иначе наоборот
                    correctAnswer = currentWord.get(0).toLowerCase();
                }
                ArrayList<String> words =
                        new ArrayList<>(Arrays.asList(correctAnswer.split(", ")));
                if (words.contains(answer) || answer.equals(correctAnswer)) {
                    right++;
                }
                System.out.println(answer);
                System.out.println(correctAnswer);
                System.out.println(answer.contains(correctAnswer));
                System.out.println("-----------------");

                count++;

                if (count >= countTest) {

                    wordText.setText("Тест завершён");

                    progressTextTest.setText(
                            "Правильных ответов "
                                    + right + " из " + countTest
                    );

                    button.setEnabled(false);

                } else {

                    nextQuestion(selwords);
                }
            }
        });

        return v;
    }

    private void nextQuestion(ArrayList<ArrayList<String>> selwords) {

        if ((usedIndexesYd.size() + usedIndexesRu.size()) >= selwords.size() * 2) {
            return;
        }
        do {
            currentIndex = (int) (Math.random() * selwords.size());
        }
        while (usedIndexesYd.contains(currentIndex) && usedIndexesRu.contains(currentIndex));

        isUdmurtToRussian = Math.random() < 0.5;
        if (isUdmurtToRussian) {

            usedIndexesYd.add(currentIndex);
        } else {
            usedIndexesRu.add(currentIndex);
        }


        ArrayList<String> word = selwords.get(currentIndex);

        if (isUdmurtToRussian) {

            wordText.setText(word.get(0));

        } else {


            wordText.setText(word.get(1));
        }

        progressBarTest.setProgress(count);

        progressTextTest.setText(
                "Правильных ответов "
                        + right + " из " + countTest
        );

        editText.setText("");
    }
}