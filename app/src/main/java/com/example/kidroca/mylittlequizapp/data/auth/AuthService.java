package com.example.kidroca.mylittlequizapp.data.auth;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by kidroca on 16.1.2016 г..
 */
public class AuthService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new Authenticator(this).getIBinder();
    }

}
