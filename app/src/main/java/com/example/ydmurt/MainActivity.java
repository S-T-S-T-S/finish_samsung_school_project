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

import org.mindrot.jbcrypt.BCrypt;

public class MainActivity extends AppCompatActivity {
    Button butonlog, toReg;
    EditText gmail, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs = getSharedPreferences("auth", MODE_PRIVATE);

        int userId = prefs.getInt("user_id", -1);

        if (userId != -1) {
            Intent intent = new Intent(MainActivity.this, Main_menu.class);
            startActivity(intent);
        }

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.editPasswordReg), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        butonlog = findViewById(R.id.buttonReg);
        toReg = findViewById(R.id.buttonToReg);
        gmail = findViewById(R.id.editGmailReg);
        password = findViewById(R.id.editTextPassword);
        butonlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(() -> {
                    String inputEmail = gmail.getText().toString().trim();
                    String inputPassword = password.getText().toString().trim();
                    runOnUiThread(() -> {
                        if (inputEmail.isEmpty() || inputPassword.isEmpty()) {
                            Toast.makeText(MainActivity.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    });

                    AppDatabase db = AppDatabase.getInstance(MainActivity.this);
                    User user = db.userDao().getUserByEmail(inputEmail);
                    runOnUiThread(() -> {
                        if (user != null) {

                            if (BCrypt.checkpw(inputPassword, user.passwordHash)) {
                                SharedPreferences prefs = getSharedPreferences("auth", MODE_PRIVATE);
                                prefs.edit()
                                        .putInt("user_id", user.id)
                                        .apply();
                                Toast.makeText(MainActivity.this, "Вход выполнен!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, Main_menu.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(MainActivity.this, "Неверный пароль", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Пользователь не зарегистрирован", Toast.LENGTH_SHORT).show();
                        }
                    });
                }).start();
            }
        });
        toReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });

    }
}