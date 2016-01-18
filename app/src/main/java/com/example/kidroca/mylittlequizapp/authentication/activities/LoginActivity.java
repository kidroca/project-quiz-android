package com.example.kidroca.mylittlequizapp.authentication.activities;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.kidroca.mylittlequizapp.R;
import com.example.kidroca.mylittlequizapp.authentication.Common;
import com.example.kidroca.mylittlequizapp.authentication.models.Token;
import com.example.kidroca.mylittlequizapp.authentication.models.user.UserRequestModel;
import com.example.kidroca.mylittlequizapp.authentication.models.user.UserView;
import com.example.kidroca.mylittlequizapp.data.VolleySingelton;
import com.example.kidroca.mylittlequizapp.data.requests.LoginRequest;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LoginActivity extends AccountAuthenticatorActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 1;

    public static final String KEY_ACCOUNT_TYPE = "AccountType";
    public static final String KEY_AUTH_TYPE = "AuthType";
    public static final String KEY_IS_ADDING_NEW_ACCOUNT = "IsAddingNewAccount";

    public static final String PARAM_ACCOUNT_TYPE = "User";


    private ProgressDialog progressDialog;

    @InjectView(R.id.input_email)
    EditText _emailText;
    @InjectView(R.id.input_password)
    EditText _passwordText;
    @InjectView(R.id.btn_login)
    Button _loginButton;
    @InjectView(R.id.link_signup)
    TextView _signupLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        progressDialog = Common.createDialog(this, R.style.AppTheme);
    }

    public void login(final UserRequestModel user) {
        showDialog(getString(R.string.login_proggress_text));

        LoginRequest req = new LoginRequest(user, "password", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.isNull("access_token");
                    if (!error) {
                        Token token = new Token(jObj);
                        String pass = user.getPassword();
                        onLoginSuccess(token, user);

                    } else {
                        String errorMessage = jObj.getString("error_msg");
                        onLoginFailed(errorMessage);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    onLoginFailed(getString(R.string.err_processing_login_response));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                onLoginFailed(error.getMessage());
            }
        });

        VolleySingelton.getInstance().addToRequestQueue(req);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK && data.hasExtra(Common.KEY_USER_PARCELABLE)) {
                UserRequestModel user = data.getParcelableExtra(Common.KEY_USER_PARCELABLE);

                login(user);
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginClick(View view) {
        Log.d(TAG, "Login");

        UserView user = new UserView(_emailText, _passwordText);

        if (!Common.validateCredentials(user, this)) {
            onLoginFailed(getString(R.string.login_onFail_toast));
            return;
        }

        _loginButton.setEnabled(false);

        login(new UserRequestModel(user));
    }

    public void onSignupClick(View view) {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivityForResult(intent, REQUEST_SIGNUP);
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

    private void onLoginSuccess(Token token, UserRequestModel user) {
        hideDialog();
        Toast.makeText(getApplicationContext(), R.string.login_success_msg, Toast.LENGTH_SHORT).show();

        _loginButton.setEnabled(true);

        AccountManager accountManager = AccountManager.get(getApplicationContext());

        Log.d(TAG, "Before bullshit");
        // Store only the token by setting the password to null, when the token
        // expires the user will be prompted to log in again
        final Account account = new Account(user.getEmail(), getString(R.string.account_type));
        // If logging in for the first time token should also be set
        if (getIntent() != null && getIntent().getBooleanExtra(KEY_IS_ADDING_NEW_ACCOUNT, false)) {
            accountManager.addAccountExplicitly(account, "", null);
            accountManager.setAuthToken(account, token.getType(), token.getAccess());
        }

        final Intent intent = new Intent();
        intent.putExtra(AccountManager.KEY_ACCOUNT_NAME, token.getUserName());
        intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, token.getType());
        intent.putExtra(AccountManager.KEY_AUTHTOKEN, token.getAccess());

        setAccountAuthenticatorResult(intent.getExtras());
        this.setResult(RESULT_OK, intent);

        finish();
    }

    private void onLoginFailed(String errorMessage) {
        hideDialog();
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();

        Common.focusFirstError(_emailText, _passwordText);

        _loginButton.setEnabled(true);
    }
}