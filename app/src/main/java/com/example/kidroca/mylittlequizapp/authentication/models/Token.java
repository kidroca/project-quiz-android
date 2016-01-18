package com.example.kidroca.mylittlequizapp.authentication.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kidroca on 16.1.2016 г..
 */
public class Token {
    public static final String FIELD_ACCESS_TOKEN = "access_token";
    public static final String FIELD_EXPIRES_IN = "expires_in";
    public static final String FIELD_TOKEN_TYPE = "token_type";
    public static final String FIELD_USERNAME = "userName";

    private String type;
    private String access;
    private String userName;

    public Token(String type, String access, String userName) {
        this.type = type;
        this.access = access;
        this.userName = userName;
    }

    public Token(JSONObject jObj) throws JSONException {
        this.type = jObj.getString(FIELD_TOKEN_TYPE);
        this.access = jObj.getString(FIELD_ACCESS_TOKEN);
        this.userName = jObj.getString(FIELD_USERNAME);
    }

    public String getType() {
        return type;
    }

    public String getAccess() {
        return access;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public String toString() {
        return String.format("%s %s", type, access);
    }
}
