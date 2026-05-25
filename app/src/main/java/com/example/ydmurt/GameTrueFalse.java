package com.example.ydmurt;

import static android.content.Context.MODE_PRIVATE;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameTrueFalse#newInstance} factory method to
 * create an instance of this fragment.
 */

import com.example.ydmurt.data.AppDatabase;
import com.example.ydmurt.data.WORDS;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.Random;

public class GameTrueFalse extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private TextView tvUdmurtWord,tvScore,tvRussianWord,textRight;
    private MaterialCardView btnTrue,btnFalse;
    private ProgressBar progressBar;


    private ArrayList<ArrayList<String>> allWords;
    private Random random;
    private int score = 0,totalQuestions = 0;
    private String currentUdmurtWord,currentCorrectTranslation,currentShownTranslation;
    private boolean isCurrentCorrect;

    public GameTrueFalse() {
        // Required empty public constructor
    }

    public static GameTrueFalse newInstance(String param1, String param2) {
        GameTrueFalse fragment = new GameTrueFalse();
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

        random = new Random();
        allWords = new ArrayList<>();

        loadAllWords();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_true_false, container, false);


        tvUdmurtWord = view.findViewById(R.id.udmurt_word);
        tvRussianWord = view.findViewById(R.id.russian_word);
        btnTrue = view.findViewById(R.id.button_true);
        btnFalse = view.findViewById(R.id.button_false);
        tvScore = view.findViewById(R.id.tv_score);
        progressBar = view.findViewById(R.id.progressBarTest);
        textRight=view.findViewById(R.id.textGameTrueFalseRight);
        progressBar.setProgress(0);
        btnTrue.setOnClickListener(v -> checkAnswer(true));
        btnFalse.setOnClickListener(v -> checkAnswer(false));

        generateNewQuestion();

        return view;
    }

    private void loadAllWords() {
        for (ArrayList<ArrayList<String>> i : WORDS.getPartWords(AppDatabase.getInstance(requireActivity()).userDao().getUserById(requireActivity().getSharedPreferences("auth", MODE_PRIVATE).getInt("user_id", -1)).levelEducation + 1)) {
            allWords.addAll(i);
        }
    }

    private void generateNewQuestion() {
        if (allWords.isEmpty()) return;

        int wordIndex = random.nextInt(allWords.size());
        ArrayList<String> selectedWord = allWords.get(wordIndex);

        currentUdmurtWord = selectedWord.get(0);
        currentCorrectTranslation = selectedWord.get(1);

        isCurrentCorrect = random.nextBoolean();

        if (isCurrentCorrect) {

            currentShownTranslation = currentCorrectTranslation;
        } else {

            currentShownTranslation = getRandomWrongTranslation(wordIndex);
        }

        tvUdmurtWord.setText(currentUdmurtWord);
        tvRussianWord.setText(currentShownTranslation);
        updateScore();
    }


    private String getRandomWrongTranslation(int excludeIndex) {
        int wrongIndex;
        do {
            wrongIndex = random.nextInt(allWords.size());
        } while (wrongIndex == excludeIndex);

        return allWords.get(wrongIndex).get(1);
    }


    private void checkAnswer(boolean userSaidTrue) {
        totalQuestions++;

        boolean correctAnswer = (userSaidTrue == isCurrentCorrect);

        if (correctAnswer) {
            score++;
            textRight.setText("✓ Правильно!");
        } else {
            textRight.setText("✗ Неправильно! Правильный перевод: " + currentCorrectTranslation);
        }
        generateNewQuestion();
    }

    private void updateScore() {
        tvScore.setText("Счёт: " + score + "/" + totalQuestions);
        progressBar.setProgress((int) (((float) (score) / (float) (totalQuestions)) * 100));
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            progressBar.setProgress((int) (((float) (score) / (float) (totalQuestions)) * 100), true);
        }
    }

}