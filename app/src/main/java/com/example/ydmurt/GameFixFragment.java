package com.example.ydmurt;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ydmurt.data.AppDatabase;
import com.example.ydmurt.data.WORDS;

import java.util.ArrayList;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameFixFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameFixFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    Button button;
    TextView textView,textRight;
    EditText editText;
    String rightWord;
    ArrayList<ArrayList<ArrayList<String>>> wordsss;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GameFixFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GameFixFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GameFixFragment newInstance(String param1, String param2) {
        GameFixFragment fragment = new GameFixFragment();
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
        View v = inflater.inflate(R.layout.fragment_game_fix, container, false);
        button = v.findViewById(R.id.buttonAnswer);
        textView = v.findViewById(R.id.tvWrongWord);
        editText = v.findViewById(R.id.editAnswer);
        textRight=v.findViewById(R.id.tvFixRighbt);
        nextWord();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();
                if (text.equals(rightWord)) {

                    textRight.setText("✓ Правильно!");
                } else {
                    textRight.setText("✗ Неправильно! Правильный перевод: " + rightWord);
                }
                nextWord();
            }
        });
        return v;
    }

    void nextWord() {
        Random random = new Random();
        wordsss = WORDS.getPartWords(AppDatabase.getInstance(requireActivity()).userDao().getUserById(requireActivity().getSharedPreferences("auth", MODE_PRIVATE).getInt("user_id", -1)).levelEducation + 1);

        ArrayList<ArrayList<String>> wordss = (wordsss.get(random.nextInt(wordsss.size())));
        ArrayList<String> words = (wordss.get(random.nextInt(wordss.size())));
        rightWord = (words.get(0));
        System.out.println(rightWord);
        int charFixed = random.nextInt(rightWord.length() - 1);
        String letters = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяӝӟӥӧ";
        char oldChar = rightWord.charAt(charFixed);
        char randomChar;

        do {
            randomChar = letters.charAt(
                    random.nextInt(letters.length())
            );
        } while (randomChar == oldChar);

        StringBuilder sb = new StringBuilder(rightWord);
        sb.setCharAt(charFixed, randomChar);
        textView.setText(sb.toString());


    }
}