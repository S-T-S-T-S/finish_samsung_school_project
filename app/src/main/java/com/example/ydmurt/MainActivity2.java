package com.example.ydmurt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ydmurt.data.AppDatabase;
import com.example.ydmurt.data.User;
import com.google.android.material.button.MaterialButton;

import org.mindrot.jbcrypt.BCrypt;

public class MainActivity2 extends AppCompatActivity {
    Button reg;
    MaterialButton toLog;
    EditText etEmail, etPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.editPasswordReg), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        reg = findViewById(R.id.buttonReg);
        etEmail = findViewById(R.id.editGmailReg);

        etPassword = findViewById(R.id.editTextTextPassword);
        toLog = findViewById(R.id.buttrett);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();


                String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity2.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
                    return;
                }

                new Thread(() -> {
                    AppDatabase db = AppDatabase.getInstance(MainActivity2.this);


                    User existingUser = db.userDao().getUserByEmail(email);

                    if (existingUser != null) {
                        runOnUiThread(() -> {
                            Toast.makeText(MainActivity2.this, "Этот логин уже занят", Toast.LENGTH_SHORT).show();
                        });
                    } else {
                        User newUser = new User(email, hashed, 0);
                        db.userDao().insert(newUser);
                        runOnUiThread(() -> {
                            SharedPreferences prefs = getSharedPreferences("auth", MODE_PRIVATE);

                            prefs.edit()
                                    .putInt("user_id", db.userDao().getUserByEmail(email).id)
                                    .apply();

                            Toast.makeText(MainActivity2.this, "Регистрация успешна!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity2.this, Main_menu.class);
                            startActivity(intent);
                            etEmail.setText("");
                            etPassword.setText("");
                        });
                    }
                }).start();
            }
        });
        toLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}