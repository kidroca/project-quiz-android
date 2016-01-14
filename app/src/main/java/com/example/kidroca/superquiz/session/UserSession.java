package com.example.kidroca.superquiz.session;

import com.example.kidroca.superquiz.models.data.User;
import com.example.kidroca.superquiz.models.request.UserRequestModel;

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
