package com.example.ydmurt.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    public String email;

    public String passwordHash;

    public User(@NonNull String email, String passwordHash) {
        this.email = email;
        this.passwordHash = passwordHash;
    }
}
