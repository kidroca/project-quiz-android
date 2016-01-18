package com.example.kidroca.mylittlequizapp.data.json;

import static com.example.kidroca.mylittlequizapp.AppConfig.BASE_URL;

/**
 * Created by kidroca on 18.1.2016 г..
 */
public class Endpoints {
    public static final String URL_QUIZZES = BASE_URL + "api/quizzes";
    public static final String URL_CATEGORIES = URL_QUIZZES + "/categories";
    public static final String URL_SOLVE = URL_QUIZZES + "/solve";
    public static final String URL_ACCOUNT = BASE_URL + "api/Account/UserInfo";
}
