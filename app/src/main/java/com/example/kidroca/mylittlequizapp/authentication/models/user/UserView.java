package com.example.kidroca.mylittlequizapp.authentication.models.user;

import android.widget.TextView;

/**
 * Created by kidroca on 15.1.2016 г..
 */
public class UserView {
    private TextView firstName;
    private TextView lastName;
    private TextView email;
    private TextView password;
    private TextView confirmPassword;

    public UserView(TextView email, TextView password) {
        this.email = email;
        this.password = password;
    }

    public UserView(TextView firstName,
                    TextView lastName,
                    TextView email,
                    TextView password,
                    TextView confirmPassword) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public TextView getFirstName() {
        return firstName;
    }

    public TextView getLastName() {
        return lastName;
    }

    public TextView getEmail() {
        return email;
    }

    public TextView getPassword() {
        return password;
    }

    public TextView getConfirmPassword() {
        return confirmPassword;
    }
}
