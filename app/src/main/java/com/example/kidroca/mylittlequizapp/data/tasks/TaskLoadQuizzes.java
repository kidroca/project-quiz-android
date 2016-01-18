package com.example.kidroca.mylittlequizapp.data.tasks;

import android.os.AsyncTask;

import com.android.volley.RequestQueue;
import com.example.kidroca.mylittlequizapp.data.VolleySingelton;
import com.example.kidroca.mylittlequizapp.data.callbacks.QuizzesLoadedListener;
import com.example.kidroca.mylittlequizapp.data.extra.QuizzesUtils;
import com.example.kidroca.mylittlequizapp.main.quizzes.models.quiz.Quiz;

import java.util.ArrayList;

/**
 * Created by kidroca on 18.1.2016 г..
 */
public class TaskLoadQuizzes extends AsyncTask<Void, Void, ArrayList<Quiz>> {

    private QuizzesLoadedListener mComponent;
    private VolleySingelton volleySingelton;
    private RequestQueue requestQueue;

    public TaskLoadQuizzes(QuizzesLoadedListener mComponent) {
        this.mComponent = mComponent;
        volleySingelton = VolleySingelton.getInstance();
        requestQueue = volleySingelton.getRequestQueue();
    }

    @Override
    protected ArrayList<Quiz> doInBackground(Void... params) {
        ArrayList<Quiz> quizzes = QuizzesUtils.loadQuizzes(requestQueue);
        return quizzes;
    }

    @Override
    protected void onPostExecute(ArrayList<Quiz> quizzes) {
        if (mComponent != null) {
            mComponent.onQuizzesLoaded(quizzes);
        }
    }
}
