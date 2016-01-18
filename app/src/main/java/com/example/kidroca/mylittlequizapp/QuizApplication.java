package com.example.kidroca.mylittlequizapp;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.kidroca.mylittlequizapp.authentication.activities.LoginActivity;
import com.example.kidroca.mylittlequizapp.authentication.models.Token;
import com.example.kidroca.mylittlequizapp.data.DbQuizzes;
import com.example.kidroca.mylittlequizapp.data.auth.AuthService;
import com.example.kidroca.mylittlequizapp.data.auth.Authenticator;

import java.io.IOException;

/**
 * Created by kidroca on 18.1.2016 г..
 */
public class QuizApplication extends Application {
    public static final String PREF_FILE_NAME = "userPreferences";

    private static QuizApplication sInstance;
    private static DbQuizzes mDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static QuizApplication getsInstance() {
        return sInstance;
    }

    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }

    public synchronized static DbQuizzes getWritableDatabase() {
        if (mDatabase == null) {
            mDatabase = new DbQuizzes(getAppContext());
        }

        return mDatabase;
    }

    public static void saveToPreferences(Context context, String preferenceName, String preferenceValue) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();
    }

    public static String readFromPreference(Context context, String preferenceName, String defaultValue) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE);

        return sharedPref.getString(preferenceName, defaultValue);
    }
}
