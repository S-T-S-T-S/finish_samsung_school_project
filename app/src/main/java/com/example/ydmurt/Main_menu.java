package com.example.ydmurt;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class Main_menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_menu);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.editPasswordReg), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SetFragment.setFragment(getSupportFragmentManager(),new Education());


        CardView education = findViewById(R.id.educCardView);
        CardView prof = findViewById(R.id.profileButton);
        CardView game = findViewById(R.id.gamesButton);

        education.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetFragment.setFragment(getSupportFragmentManager(),new Education());
            }
        });

        game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetFragment.setFragment(getSupportFragmentManager(),new game());
            }
        });

        prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetFragment.setFragment(getSupportFragmentManager(),new ProfileFragment());
            }
        });
    }


}