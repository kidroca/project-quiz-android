package com.example.kidroca.mylittlequizapp.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.example.kidroca.mylittlequizapp.main.quizzes.models.quiz.Quiz;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by kidroca on 18.1.2016 г..
 */
public class DbQuizzes {
    public static final String TAG = DbQuizzes.class.getSimpleName();

    public static final int BOX_OFFICE = 0;
    public static final int UPCOMING = 1;
    private QuizzesHelper mHelper;
    private SQLiteDatabase mDatabase;

    public DbQuizzes(Context context) {
        mHelper = new QuizzesHelper(context);
        mDatabase = mHelper.getWritableDatabase();
    }

    public void insertQuizzes(int table, ArrayList<Quiz> listQuizzes, boolean clearPrevious) {
        if (clearPrevious) {
            deleteQuizzes(table);
        }

        //create a sql prepared statement
        String sql = "INSERT INTO " + (
                table == BOX_OFFICE
                        ? QuizzesHelper.TABLE_BOX_OFFICE
                        : QuizzesHelper.TABLE_UPCOMING) + " VALUES (?,?,?,?,?,?,?,?,?,?);";
        //compile the statement and start a transaction
        SQLiteStatement statement = mDatabase.compileStatement(sql);
        mDatabase.beginTransaction();
        for (int i = 0; i < listQuizzes.size(); i++) {
            Quiz currentQuiz = listQuizzes.get(i);
            statement.clearBindings();

            //for a given column index, simply bind the data to be put inside that index
            statement.bindLong(2, currentQuiz.webApiId);
            statement.bindString(3, currentQuiz.title);
            statement.bindString(4, currentQuiz.category);
            statement.bindString(5, currentQuiz.description);
            statement.bindString(6, currentQuiz.createdBy);
            statement.bindLong(7, currentQuiz.createdOn.getTime());
            statement.bindLong(8, currentQuiz.timesSolved);
            statement.bindDouble(9, currentQuiz.rating);
            // Todo: How to bind Complex Objects ???
            // statement.bindString(10, currentQuiz.questions);

            statement.execute();
        }
        //set the transaction as successful and end the transaction
        Log.d(TAG, "inserting entries " + listQuizzes.size() + new Date(System.currentTimeMillis()));
        mDatabase.setTransactionSuccessful();
        mDatabase.endTransaction();
    }

    public ArrayList<Quiz> readQuizzes(int table) {
        ArrayList<Quiz> listMovies = new ArrayList<>();

        //get a list of columns to be retrieved, we need all of them
        String[] columns = {QuizzesHelper.COLUMN_UID,
                QuizzesHelper.COLUMN_TITLE,
                QuizzesHelper.COLUMN_RELEASE_DATE,
                QuizzesHelper.COLUMN_AUDIENCE_SCORE,
                QuizzesHelper.COLUMN_SYNOPSIS,
                QuizzesHelper.COLUMN_URL_THUMBNAIL,
                QuizzesHelper.COLUMN_URL_SELF,
                QuizzesHelper.COLUMN_URL_CAST,
                QuizzesHelper.COLUMN_URL_REVIEWS,
                QuizzesHelper.COLUMN_URL_SIMILAR
        };
        Cursor cursor = mDatabase.query((table == BOX_OFFICE ? QuizzesHelper.TABLE_BOX_OFFICE : QuizzesHelper.TABLE_UPCOMING), columns, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Log.d(TAG, "loading entries " + cursor.getCount() + new Date(System.currentTimeMillis()));
            do {
                //create a new movie object and retrieve the data from the cursor to be stored in this movie object
                Quiz quiz = new Quiz();
                //each step is a 2 part process, find the index of the column first, find the data of that column using
                //that index and finally set our blank movie object to contain our data
                quiz.title = cursor.getString(cursor.getColumnIndex(QuizzesHelper.COLUMN_TITLE));
                long releaseDateMilliseconds = cursor.getLong(cursor.getColumnIndex(QuizzesHelper.COLUMN_RELEASE_DATE));
                quiz.createdOn  = new Date(releaseDateMilliseconds);
//                quiz.setAudienceScore(cursor.getInt(cursor.getColumnIndex(QuizzesHelper.COLUMN_AUDIENCE_SCORE)));
//                quiz.setSynopsis(cursor.getString(cursor.getColumnIndex(QuizzesHelper.COLUMN_SYNOPSIS)));
//                quiz.setUrlThumbnail(cursor.getString(cursor.getColumnIndex(QuizzesHelper.COLUMN_URL_THUMBNAIL)));
//                quiz.setUrlSelf(cursor.getString(cursor.getColumnIndex(QuizzesHelper.COLUMN_URL_SELF)));
//                quiz.setUrlCast(cursor.getString(cursor.getColumnIndex(QuizzesHelper.COLUMN_URL_CAST)));
//                quiz.setUrlReviews(cursor.getString(cursor.getColumnIndex(QuizzesHelper.COLUMN_URL_REVIEWS)));
//                quiz.setUrlSimilar(cursor.getString(cursor.getColumnIndex(QuizzesHelper.COLUMN_URL_SIMILAR)));
                //add the movie to the list of movie objects which we plan to return
                listMovies.add(quiz);
            }
            while (cursor.moveToNext());
        }
        return listMovies;
    }

    public void deleteQuizzes(int table) {
        mDatabase.delete((table == BOX_OFFICE ? QuizzesHelper.TABLE_BOX_OFFICE : QuizzesHelper.TABLE_UPCOMING), null, null);
    }

    private static class QuizzesHelper extends SQLiteOpenHelper {
        public static final String TABLE_UPCOMING = " movies_upcoming";
        public static final String TABLE_BOX_OFFICE = "movies_box_office";
        public static final String COLUMN_UID = "_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_AUDIENCE_SCORE = "audience_score";
        public static final String COLUMN_SYNOPSIS = "synopsis";
        public static final String COLUMN_URL_THUMBNAIL = "url_thumbnail";
        public static final String COLUMN_URL_SELF = "url_self";
        public static final String COLUMN_URL_CAST = "url_cast";
        public static final String COLUMN_URL_REVIEWS = "url_reviews";
        public static final String COLUMN_URL_SIMILAR = "url_similar";
        private static final String CREATE_TABLE_BOX_OFFICE = "CREATE TABLE " + TABLE_BOX_OFFICE + " (" +
                COLUMN_UID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_TITLE + " TEXT," +
                COLUMN_RELEASE_DATE + " INTEGER," +
                COLUMN_AUDIENCE_SCORE + " INTEGER," +
                COLUMN_SYNOPSIS + " TEXT," +
                COLUMN_URL_THUMBNAIL + " TEXT," +
                COLUMN_URL_SELF + " TEXT," +
                COLUMN_URL_CAST + " TEXT," +
                COLUMN_URL_REVIEWS + " TEXT," +
                COLUMN_URL_SIMILAR + " TEXT" +
                ");";
        private static final String CREATE_TABLE_UPCOMING = "CREATE TABLE " + TABLE_UPCOMING + " (" +
                COLUMN_UID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_TITLE + " TEXT," +
                COLUMN_RELEASE_DATE + " INTEGER," +
                COLUMN_AUDIENCE_SCORE + " INTEGER," +
                COLUMN_SYNOPSIS + " TEXT," +
                COLUMN_URL_THUMBNAIL + " TEXT," +
                COLUMN_URL_SELF + " TEXT," +
                COLUMN_URL_CAST + " TEXT," +
                COLUMN_URL_REVIEWS + " TEXT," +
                COLUMN_URL_SIMILAR + " TEXT" +
                ");";
        private static final String DB_NAME = "movies_db";
        private static final int DB_VERSION = 1;
        private Context mContext;

        public QuizzesHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
            mContext = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_TABLE_BOX_OFFICE);
                db.execSQL(CREATE_TABLE_UPCOMING);
                Log.d(TAG, "create table box office executed");
            } catch (SQLiteException exception) {
                Log.d(TAG, exception + "");
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                Log.d(TAG, "upgrade table box office executed");
                db.execSQL(" DROP TABLE " + TABLE_BOX_OFFICE + " IF EXISTS;");
                db.execSQL(" DROP TABLE " + TABLE_UPCOMING + " IF EXISTS;");
                onCreate(db);
            } catch (SQLiteException exception) {
                Log.d(TAG, exception + "");
            }
        }
    }
}
