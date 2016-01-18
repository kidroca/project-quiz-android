package com.example.kidroca.mylittlequizapp.main.quizzes.models.quiz;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.kidroca.mylittlequizapp.main.quizzes.models.question.Question;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by kidroca on 17.1.2016 г..
 */
public class Quiz implements Parcelable {
    public int webApiId;
    public String title;
    public String category;
    public String description;
    public String createdBy;
    public Date createdOn;
    public int timesSolved;
    public double rating;
    public String avatarUrl;
    public List<Question> questions;

    public Quiz() {

    }

    public Quiz(String title, Date createdOn, double rating, String avatarUrl) {
        this.title = title;
        this.createdOn = createdOn;
        this.rating = rating;
        this.avatarUrl = avatarUrl;
    }

    protected Quiz(Parcel in) {
        webApiId = in.readInt();
        title = in.readString();
        category = in.readString();
        description = in.readString();
        createdBy = in.readString();
        long tmpCreatedOn = in.readLong();
        createdOn = tmpCreatedOn != -1 ? new Date(tmpCreatedOn) : null;
        timesSolved = in.readInt();
        rating = in.readDouble();
        avatarUrl = in.readString();
        if (in.readByte() == 0x01) {
            questions = new ArrayList<Question>();
            in.readList(questions, Question.class.getClassLoader());
        } else {
            questions = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(webApiId);
        dest.writeString(title);
        dest.writeString(category);
        dest.writeString(description);
        dest.writeString(createdBy);
        dest.writeLong(createdOn != null ? createdOn.getTime() : -1L);
        dest.writeInt(timesSolved);
        dest.writeDouble(rating);
        dest.writeString(avatarUrl);
        if (questions == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(questions);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Quiz> CREATOR = new Parcelable.Creator<Quiz>() {
        @Override
        public Quiz createFromParcel(Parcel in) {
            return new Quiz(in);
        }

        @Override
        public Quiz[] newArray(int size) {
            return new Quiz[size];
        }
    };
}