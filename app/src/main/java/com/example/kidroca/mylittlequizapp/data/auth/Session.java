package com.example.kidroca.mylittlequizapp.data.auth;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.kidroca.mylittlequizapp.R;
import com.example.kidroca.mylittlequizapp.authentication.models.Token;

import java.io.IOException;

/**
 * Created by kidroca on 18.1.2016 г..
 */
public class Session {

    public static final String DEFAULT_TOKEN_TYPE = "bearer";

    private static Session sInstance;

    private AccountManager mAccountManager;
    private Account mAccount;
    private Token mToken;

    private Session() {
    }

    public synchronized static Session getsInstance() {
        if (sInstance == null) {
            sInstance = new Session();
        }

        return sInstance;
    }

    public Account getAccount() {
        return this.mAccount;
    }

    public Token getToken() {
        if (mToken == null) {
            AccountManagerFuture<Bundle> managerFuture =
                    mAccountManager.getAuthToken(getAccount(), DEFAULT_TOKEN_TYPE, null, true, null, null);
            try {
                Bundle tokenBundle = managerFuture.getResult();
                String authToken = tokenBundle.get(AccountManager.KEY_AUTHTOKEN).toString();
                mToken = new Token(DEFAULT_TOKEN_TYPE, authToken, mAccount.name);
            } catch (OperationCanceledException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (AuthenticatorException e) {
                e.printStackTrace();
            }
        }

        return mToken;
    }

    public boolean setAccount(Activity fromActivity) {
        mAccountManager = AccountManager.get(fromActivity);
        Account[] appAccounts = mAccountManager.getAccountsByType(
                fromActivity.getString(R.string.account_type));

        if (appAccounts.length > 0) {
            this.mAccount = appAccounts[0];
            return true;
        } else {
            return false;
        }
    }
}
