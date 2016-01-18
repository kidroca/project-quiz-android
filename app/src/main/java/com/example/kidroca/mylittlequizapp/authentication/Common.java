package com.example.kidroca.mylittlequizapp.authentication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Patterns;
import android.widget.TextView;

import com.example.kidroca.mylittlequizapp.R;
import com.example.kidroca.mylittlequizapp.authentication.models.user.UserView;

/**
 * Created by kidroca on 15.1.2016 г..
 */
public class Common {
    public static final int MIN_PASSWORD_LENGHT = 6;
    public static final int MAX_PASSWORD_LENGTH = 24;

    public static final String KEY_USER_PARCELABLE = "userModel";

    public static boolean validateCredentials(UserView user, Context context) {
        boolean valid = true;

        TextView email = user.getEmail();
        TextView password = user.getPassword();

        if (!isEmailValid(email)) {
            email.setError(context.getString(R.string.invalid_email_text));
            valid = false;
        } else {
            email.setError(null);
        }

        if (!isPasswordValid(password)) {
            password.setError(String.format(
                    context.getString(R.string.invalid_password_text),
                    MIN_PASSWORD_LENGHT,
                    MAX_PASSWORD_LENGTH));
            valid = false;
        } else {
            password.setError(null);
        }

        return valid;
    }

    public static boolean validateSignup(UserView user, Context context) {
        boolean valid = validateCredentials(user, context);

        TextView firstName = user.getFirstName();
        TextView lastName = user.getLastName();
        TextView password = user.getPassword();
        TextView confirmPassword = user.getConfirmPassword();

        if (!isNameValid(firstName)) {
            firstName.setError(context.getString(R.string.invalid_name_text));
            valid = false;
        } else {
            firstName.setError(null);
        }

        if (!isNameValid(lastName)) {
            lastName.setError(context.getString(R.string.invalid_name_text));
            valid = false;
        } else {
            lastName.setError(null);
        }

        if (!passwordsMatch(password, confirmPassword)) {
            confirmPassword.setError(context.getString(R.string.invalid_confirm_password_text));
            valid = false;
        } else {
            confirmPassword.setError(null);
        }

        return valid;
    }



    public static ProgressDialog createDialog(Activity context, int theme, String message) {
        ProgressDialog dialog = createDialog(context, theme);
        dialog.setMessage(message);

        return dialog;
    }

    public static ProgressDialog createDialog(Activity context, int theme) {
        ProgressDialog dialog = new ProgressDialog(context, theme);
        dialog.setIndeterminate(true);

        return dialog;
    }

    public static void focusFirstError(TextView... args) {
        for (TextView view : args) {
            if (view.getError() != null) {
                view.requestFocus();
                return;
            }
        }
    }

    private static boolean isEmailValid(TextView emailView) {
        String email = emailView.getText().toString();
        return !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private static boolean isPasswordValid(TextView passwordView) {
        String password = passwordView.getText().toString();
        return !password.isEmpty() &&
                MIN_PASSWORD_LENGHT <= password.length() && password.length() <= MAX_PASSWORD_LENGTH;
    }

    private static boolean isNameValid(TextView nameView) {
        String name = nameView.getText().toString();
        return !name.trim().isEmpty();
    }

    private static boolean passwordsMatch(TextView passwordView, TextView confirmView) {
        String password = passwordView.getText().toString();
        String confirmPassword = confirmView.getText().toString();

        return password.equals(confirmPassword);
    }
}
