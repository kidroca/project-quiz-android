package com.example.kidroca.mylittlequizapp.data.auth;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Created by kidroca on 17.1.2016 г..
 */
public class AuthPreferences {
    private static final String KEY_USER = "user";

    private SharedPreferences preferences;

    public AuthPreferences(Context context) {
        preferences = context
                .getSharedPreferences("auth", Context.MODE_PRIVATE);
    }

    public void setUser(String user) {
        Editor editor = preferences.edit();
        editor.putString(KEY_USER, user);
        editor.commit();
    }

    public String getUser() {
        return preferences.getString(KEY_USER, null);
    }
}
