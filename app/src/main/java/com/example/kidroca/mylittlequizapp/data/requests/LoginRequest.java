package com.example.kidroca.mylittlequizapp.data.requests;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.example.kidroca.mylittlequizapp.AppConfig;
import com.example.kidroca.mylittlequizapp.authentication.models.user.UserRequestModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kidroca on 16.1.2016 г..
 */
public class LoginRequest extends BaseRequest {
    private static final String TOKEN_GRANT = "grant_type";
    private static final String TOKEN_USER = "username";
    private static final String TOKEN_PASS = "password";

    private final UserRequestModel user;
    private String tokenGrant;

    public LoginRequest(
            UserRequestModel userData,
            String tokenGrant,
            Response.Listener<String> listener,
            Response.ErrorListener errorListener) {

        super(Method.POST, AppConfig.URL_LOGIN, listener, errorListener);
        this.user = userData;
        this.tokenGrant = tokenGrant;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> params = new HashMap<>();
        params.put(TOKEN_GRANT, tokenGrant);
        params.put(TOKEN_USER, user.getEmail());
        params.put(TOKEN_PASS, user.getPassword());

        return params;
    }
}
