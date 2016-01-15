package com.example.kidroca.projectquizandroid.session;

import com.example.kidroca.projectquizandroid.data.models.data.User;
import com.example.kidroca.projectquizandroid.data.models.request.UserRequestModel;

/**
 * Created by kidroca on 12.1.2016 г..
 */
public class UserSession {
    private String token;
    private User currentUser;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void logIn(UserRequestModel req) {

    }

    public void registerUser(UserRequestModel req) {

    }
}
