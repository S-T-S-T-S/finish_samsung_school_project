package com.example.ydmurt;

import static android.content.Context.MODE_PRIVATE;
import static com.example.ydmurt.data.WORDS.getSelectwords;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ydmurt.data.AppDatabase;
import com.example.ydmurt.data.User;
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

    ArrayList<ArrayList<String>> selwordsYd;
    ArrayList<ArrayList<String>> selwordsRu;
    ProgressBar progressBarTest;
    Button button;
    TextView wordText, progressTextTest;
    EditText editText;
    ArrayList<Integer> usedIndexesRu = new ArrayList<>();
    ArrayList<Integer> usedIndexesYd = new ArrayList<>();
    boolean isUdmurtToRussian;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


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

        ArrayList<ArrayList<String>> selwords = getSelectwords(myId);

        countTest = selwords.size() * 2;
        count = 0;
        right = 0;

        progressBarTest.setMax(countTest);
        selwordsYd = new ArrayList<>();
        selwordsRu = new ArrayList<>();

        ArrayList<String> word;

        for (ArrayList<String> words : selwords) {
            selwordsYd.add(new ArrayList<>(words));
            selwordsRu.add(new ArrayList<>(words));
        }
        nextQuestion(selwords);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String answer = editText.getText().toString().trim().toLowerCase();

                ArrayList<String> currentWord =
                        selwords.get(currentIndex);

                String correctAnswer;
                if (isUdmurtToRussian) {
                    correctAnswer = currentWord.get(1).toLowerCase();
                } else {
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

                    progressTextTest.setText("Правильных ответов " + right + " из " + countTest);

                    button.setEnabled(false);
                    User user = AppDatabase.getInstance(requireContext()).userDao().getUserById(requireContext().getSharedPreferences("auth", MODE_PRIVATE).getInt("user_id", -1));
                    if (myId == user.levelEducation && (right * 100 / countTest) > 10) {
                        wordText.setText("Тест завершён. Открыт новый уровень");
                        user.levelEducation++;
                        AppDatabase.getInstance(requireContext()).userDao().update(user);
                    } else {
                        wordText.setText("Тест завершён. Новый уровень не разблокирован.");
                    }
                } else {


                    nextQuestion(selwords);
                }
            }
        });

        return v;
    }

    private void nextQuestion(ArrayList<ArrayList<String>> selwords) {

        isUdmurtToRussian = Math.random() < 0.5;
        ArrayList<String> word;
        if ((selwordsYd.size() + selwordsRu.size()) == 0) {
            return;
        }
        if ((isUdmurtToRussian || selwordsRu.isEmpty())&& !selwordsYd.isEmpty()) {

            currentIndex = (int) (Math.random() * selwordsYd.size());
            word = selwordsYd.remove(currentIndex);
        } else {

            currentIndex = (int) (Math.random() * selwordsRu.size());
            word = selwordsRu.remove(currentIndex);


        }


        if (isUdmurtToRussian) {
            wordText.setText(word.get(0));
            editText.setHint(String.valueOf(word.get(1).charAt(0)));
        } else {
            wordText.setText(word.get(1));
            editText.setHint(String.valueOf(word.get(0).charAt(0)));
        }

        progressBarTest.setProgress(count);

        progressTextTest.setText("Правильных ответов " + right + " из " + countTest);



    }
}