package com.example.kidroca.mylittlequizapp.data.json;

import android.util.Log;

import com.example.kidroca.mylittlequizapp.main.quizzes.models.answer.Answer;
import com.example.kidroca.mylittlequizapp.main.quizzes.models.question.Question;
import com.example.kidroca.mylittlequizapp.main.quizzes.models.quiz.Quiz;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by kidroca on 18.1.2016 г..
 */
public class Parser {

    public static final String TAG = Parser.class.getSimpleName();

    public static ArrayList<Quiz> parseQuizzesJson(JSONObject response) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList<Quiz> listQuizzes = new ArrayList<>();

        if (response != null && response.length() > 0) {
            try {
                JSONArray quizzesJson = response.getJSONArray("data");
                for (int i = 0; i < quizzesJson.length(); i++) {
                    JSONObject currentQuiz = quizzesJson.getJSONObject(i);
                    Quiz quiz = new Quiz();

                    quiz.webApiId = currentQuiz.getInt("id");
                    quiz.title = currentQuiz.getString("title");
                    quiz.category = currentQuiz.getString("category");
                    quiz.description = currentQuiz.getString("description");
                    quiz.createdBy = currentQuiz.getString("createdBy");
                    quiz.createdOn = dateFormat.parse(currentQuiz.getString("createdOn"));
                    quiz.timesSolved = currentQuiz.getInt("timesSolved");
                    quiz.rating = currentQuiz.getDouble("rating");
                    quiz.avatarUrl = currentQuiz.getString("avatarUrl");

                    quiz.questions = new ArrayList<>();

                    JSONArray questionsJson = currentQuiz.getJSONArray("questions");
                    for (int j = 0; j < questionsJson.length(); j++) {

                        JSONObject currentQuestion = questionsJson.getJSONObject(j);
                        Question question = new Question();
                        question.title = currentQuestion.getString("title");
                        question.answers = new ArrayList<>();

                        JSONArray answersJson = currentQuestion.getJSONArray("answers");

                        for (int k = 0; k < answersJson.length(); k++) {

                            JSONObject currentAnswer = answersJson.getJSONObject(k);
                            Answer answer = new Answer();
                            answer.isCorrect = currentAnswer.getBoolean("isCorrect");
                            answer.text = currentAnswer.getString("text");

                            question.answers.add(answer);
                        }

                        quiz.questions.add(question);
                    }

                    listQuizzes.add(quiz);
                }
            } catch (JSONException | ParseException e) {
                Log.d(TAG, e + "");
            }
        } else {
           listQuizzes = generateSome();
        }

        return listQuizzes;
    }

    private static ArrayList<Quiz> generateSome() {
        ArrayList<Quiz> quizzes = new ArrayList<>();

        quizzes.add(new Quiz(
                "Whisky", new Date(), 3.5F, null
        ));

        quizzes.add(new Quiz(
                "Terminator", new Date(), 7.5F, "http://vignette2.wikia.nocookie.net/uncyclopedia/images/4/49/Terminator.jpg/revision/20130522094052"
        ));

        quizzes.add(new Quiz(
                "Terminator II", new Date(), 8.5F, "https://upload.wikimedia.org/wikipedia/en/8/85/Terminator2poster.jpg"
        ));

        quizzes.add(new Quiz(
                "Star Wars", new Date(), 10F, "http://www.starwars.com/the-force-awakens/images/share_1200x627.jpg"
        ));

        quizzes.add(new Quiz(
                "Indiana Jhones", new Date(), 8.8F, "https://upload.wikimedia.org/wikipedia/en/9/97/Indiana_Jones_and_the_Infernal_Machine.jpg"
        ));

        quizzes.add(new Quiz(
                "Robocop", new Date(), 7F, "http://www.filmonpaper.com/site/media/2013/07/Robocop_B1_Poland_JakubErol-2.jpg"
        ));

        quizzes.add(new Quiz(
                "Whisky", new Date(), 3.5F, null
        ));

        quizzes.add(new Quiz(
                "Terminator", new Date(), 7.5F, "http://vignette2.wikia.nocookie.net/uncyclopedia/images/4/49/Terminator.jpg/revision/20130522094052"
        ));

        quizzes.add(new Quiz(
                "Terminator II", new Date(), 8.5F, "https://upload.wikimedia.org/wikipedia/en/8/85/Terminator2poster.jpg"
        ));

        quizzes.add(new Quiz(
                "Star Wars", new Date(), 10F, "http://www.starwars.com/the-force-awakens/images/share_1200x627.jpg"
        ));

        quizzes.add(new Quiz(
                "Indiana Jhones", new Date(), 8.8F, "https://upload.wikimedia.org/wikipedia/en/9/97/Indiana_Jones_and_the_Infernal_Machine.jpg"
        ));

        quizzes.add(new Quiz(
                "Robocop", new Date(), 7F, "http://www.filmonpaper.com/site/media/2013/07/Robocop_B1_Poland_JakubErol-2.jpg"
        ));

        quizzes.add(new Quiz(
                "Whisky", new Date(), 3.5F, null
        ));

        quizzes.add(new Quiz(
                "Terminator", new Date(), 7.5F, "http://vignette2.wikia.nocookie.net/uncyclopedia/images/4/49/Terminator.jpg/revision/20130522094052"
        ));

        quizzes.add(new Quiz(
                "Terminator II", new Date(), 8.5F, "https://upload.wikimedia.org/wikipedia/en/8/85/Terminator2poster.jpg"
        ));

        quizzes.add(new Quiz(
                "Star Wars", new Date(), 10F, "http://www.starwars.com/the-force-awakens/images/share_1200x627.jpg"
        ));

        quizzes.add(new Quiz(
                "Indiana Jhones", new Date(), 8.8F, "https://upload.wikimedia.org/wikipedia/en/9/97/Indiana_Jones_and_the_Infernal_Machine.jpg"
        ));

        quizzes.add(new Quiz(
                "Robocop", new Date(), 7F, "http://www.filmonpaper.com/site/media/2013/07/Robocop_B1_Poland_JakubErol-2.jpg"
        ));

        quizzes.add(new Quiz(
                "Whisky", new Date(), 3.5F, null
        ));

        quizzes.add(new Quiz(
                "Terminator", new Date(), 7.5F, "http://vignette2.wikia.nocookie.net/uncyclopedia/images/4/49/Terminator.jpg/revision/20130522094052"
        ));

        quizzes.add(new Quiz(
                "Terminator II", new Date(), 8.5F, "https://upload.wikimedia.org/wikipedia/en/8/85/Terminator2poster.jpg"
        ));

        quizzes.add(new Quiz(
                "Star Wars", new Date(), 10F, "http://www.starwars.com/the-force-awakens/images/share_1200x627.jpg"
        ));

        quizzes.add(new Quiz(
                "Indiana Jhones", new Date(), 8.8F, "https://upload.wikimedia.org/wikipedia/en/9/97/Indiana_Jones_and_the_Infernal_Machine.jpg"
        ));

        quizzes.add(new Quiz(
                "Robocop", new Date(), 7F, "http://www.filmonpaper.com/site/media/2013/07/Robocop_B1_Poland_JakubErol-2.jpg"
        ));

        return quizzes;
    }
}