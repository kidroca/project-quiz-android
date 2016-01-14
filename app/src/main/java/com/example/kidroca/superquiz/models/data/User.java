package com.example.kidroca.superquiz.models.data;

/**
 * Created by kidroca on 12.1.2016 г..
 */
public class User {
    private String userId;
    private String email;
    private String firstName;
    private String lastName;
    private int quizzesCreated;

    public User(
            String userId, String email, String firstName, String lastName, int quizzesCreated) {
        this.userId = userId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.quizzesCreated = quizzesCreated;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getQuizzesCreated() {
        return quizzesCreated;
    }
}
