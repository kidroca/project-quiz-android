package com.example.kidroca.mylittlequizapp;

/**
 * Created by kidroca on 16.1.2016 г..
 */
public class AppConfig {
   public static final String BASE_URL = "http://superquiz.apphb.com/"; // http://localhost/
   public static final String URL_LOGIN = BASE_URL + "Token";
   public static final String URL_REGISTER = BASE_URL + "api/account/register";

   public static final String RESPONSE_ERROR_DESCRIPTION_FIELD = "error_description";
   public static final String RESPONSE_ERROR_MODEL_STATE = "modelState";
}
