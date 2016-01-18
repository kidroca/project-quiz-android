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
public class RegisterRequest extends BaseRequest {
    public static final String EMAIL_FIELD = "email";
    public static final String PASSWORD_FIELD = "password";
    public static final String CONFIRM_PASSWORD_FIELD = "confirmPassword";
    public static final String FIRST_NAME_FIELD = "firstName";
    public static final String LAST_NAME_FIELD = "lastName";

    private final UserRequestModel user;

    public RegisterRequest(
            UserRequestModel userData,
            Response.Listener<String> listener,
            Response.ErrorListener errorListener) {

        super(Method.POST, AppConfig.URL_REGISTER, listener, errorListener);
        user = userData;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> params = new HashMap<>();
        params.put(EMAIL_FIELD, user.getEmail());
        params.put(PASSWORD_FIELD, user.getPassword());
        params.put(CONFIRM_PASSWORD_FIELD, user.getConfirmPassword());
        params.put(FIRST_NAME_FIELD, user.getFirstName());
        params.put(LAST_NAME_FIELD, user.getLastName());

        return params;
    }
}
