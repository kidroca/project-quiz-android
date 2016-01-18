package com.example.kidroca.mylittlequizapp.data;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.kidroca.mylittlequizapp.authentication.models.user.UserView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kidroca on 16.1.2016 г..
 */
public class webApi {

    private static final String TOKEN_URL = "http://superquiz.apphb.com/Token"; // "http://localhost/Token"
    private static final String TOKEN_GRANT = "grant_type";
    private static final String TOKEN_USER = "username";
    private static final String TOKEN_PASS = "password";

    public static void postLogin(final UserView user, final String tokenGrant) {

        StringRequest postRequest = new StringRequest(
                Request.Method.POST, TOKEN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(TOKEN_GRANT, tokenGrant);
                params.put(TOKEN_USER, user.getEmail().getText().toString());
                params.put(TOKEN_PASS, user.getPassword().getText().toString());

                return params;
            }
        };
    }
}
