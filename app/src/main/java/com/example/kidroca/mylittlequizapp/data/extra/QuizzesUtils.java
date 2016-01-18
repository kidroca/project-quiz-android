package com.example.kidroca.mylittlequizapp.data.extra;

import com.android.volley.RequestQueue;
import com.example.kidroca.mylittlequizapp.data.json.Endpoints;
import com.example.kidroca.mylittlequizapp.data.json.Parser;
import com.example.kidroca.mylittlequizapp.data.json.Requester;
import com.example.kidroca.mylittlequizapp.main.quizzes.models.quiz.Quiz;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by kidroca on 18.1.2016 г..
 */
public class QuizzesUtils {

    public static ArrayList<Quiz> loadQuizzes(RequestQueue requestQueue) {
        JSONObject res = Requester.requestQuizzesJson(requestQueue, Endpoints.URL_QUIZZES);
        ArrayList<Quiz> quizzes = Parser.parseQuizzesJson(res);
        return quizzes;
    }
}
