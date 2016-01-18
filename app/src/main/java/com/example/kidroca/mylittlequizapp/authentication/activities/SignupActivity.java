package com.example.kidroca.mylittlequizapp.authentication.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.kidroca.mylittlequizapp.R;
import com.example.kidroca.mylittlequizapp.authentication.Common;
import com.example.kidroca.mylittlequizapp.authentication.models.user.UserRequestModel;
import com.example.kidroca.mylittlequizapp.authentication.models.user.UserView;
import com.example.kidroca.mylittlequizapp.data.VolleySingelton;
import com.example.kidroca.mylittlequizapp.data.requests.RegisterRequest;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    @InjectView(R.id.input_fname) EditText _firstNameText;
    @InjectView(R.id.input_lname) EditText _lastNameText;
    @InjectView(R.id.input_email) EditText _emailText;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.input_confirm_password) EditText _confirmPassword;
    @InjectView(R.id.btn_signup) Button _signupButton;

    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.inject(this);

        progressDialog = Common.createDialog(this, R.style.AppTheme);
    }

    public void signup(final UserRequestModel user) {
        RegisterRequest req = new RegisterRequest(user, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Signup Response: " + response.toString());

                onSignupSuccess(user);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Signup Error: " + error.getMessage());
                onSignupFailed(error.getMessage());
            }
        });

        VolleySingelton.getInstance().addToRequestQueue(req);
    }

    public void

    onSignupSuccess(UserRequestModel userRequestModel) {
        _signupButton.setEnabled(true);
        Intent result = new Intent();
        result.putExtra(Common.KEY_USER_PARCELABLE, userRequestModel);

        setResult(RESULT_OK, result);
        finish();
    }

    public void onSignupFailed(String errorMessage) {
        hideDialog();

        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();

        Common.focusFirstError(_firstNameText, _lastNameText, _emailText, _passwordText);

        _signupButton.setEnabled(true);
    }

    public void onSignupClick(View view) {
        Log.d(TAG, "Signup");

        UserView user = new UserView(
                _firstNameText, _lastNameText, _emailText, _passwordText, _confirmPassword);

        if (!Common.validateSignup(user, this)) {
            onSignupFailed(getString(R.string.signup_onFail_toast));
            return;
        }

        _signupButton.setEnabled(false);

        showDialog(getString(R.string.signup_progress_text));

        signup(new UserRequestModel(user));
    }

    public void onLoginClick(View view) {
        // Finish the registration screen and return to the Login activity
        finish();
    }

    private void showDialog(String message) {
        if (!progressDialog.isShowing()) {
            progressDialog.setMessage(message);
            progressDialog.show();
        }
    }

    private void hideDialog() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}