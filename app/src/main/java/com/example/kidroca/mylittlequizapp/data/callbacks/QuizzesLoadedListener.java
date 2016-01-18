package com.example.kidroca.mylittlequizapp.data.callbacks;

import com.example.kidroca.mylittlequizapp.main.quizzes.models.quiz.Quiz;

import java.util.ArrayList;

/**
 * Created by kidroca on 18.1.2016 г..
 */
public interface QuizzesLoadedListener {
    public void onQuizzesLoaded(ArrayList<Quiz> quizzes);
}
