package com.example.kidroca.mylittlequizapp.main;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.kidroca.mylittlequizapp.QuizApplication;
import com.example.kidroca.mylittlequizapp.data.auth.Session;
import com.example.kidroca.mylittlequizapp.main.navigation.NavLink;
import com.example.kidroca.mylittlequizapp.main.navigation.NavigationDrawerFragment;
import com.example.kidroca.mylittlequizapp.main.quizzes.QuizzesDrawerFragment;
import com.example.kidroca.mylittlequizapp.R;
import com.example.kidroca.mylittlequizapp.authentication.activities.LoginActivity;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity implements QuizzesDrawerFragment.OnFragmentInteractionListener {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final int PAGE_HOME = 0;
    public static final int PAGE_ADD = 1;
    public static final int PAGE_ACCOUNT = 2;
    public static final int PAGE_COUNT = 3;

    @InjectView(R.id.app_toolbar)
    Toolbar mToolbar;

    @InjectView(R.id.viewPager)
    ViewPager mPager;

    ViewPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        if (Session.getsInstance().setAccount(this)) {
            ButterKnife.inject(this);
            setpPager();
            setupDrawer();
        } else {

            AccountManager mAccountManager = AccountManager.get(this);
            mAccountManager.addAccount(
                    getString(R.string.account_type),
                    Session.DEFAULT_TOKEN_TYPE,
                    null,
                    new Bundle(),
                    this,
                    new OnAccountAddComplete(),
                    null);
        }
    }

    private void setpPager() {
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_info) {
            Toast.makeText(this, "I know what you are thinking", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        // Todo: Stuff
    }

    public void onDrawerItemClicked(int index, NavLink navLink) {
        mPager.setCurrentItem(index);
    }

    private void setupDrawer() {

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        NavigationDrawerFragment navDrawer = (NavigationDrawerFragment) getSupportFragmentManager()
                .findFragmentById(R.id.app_navigation_drawer_fragment);

        navDrawer.setUp(
                R.id.app_navigation_drawer_fragment,
                (DrawerLayout) findViewById(R.id.app_drawer_layout),
                mToolbar);
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        int icons[] = {R.drawable.ic_action_search,
                R.drawable.ic_action_trending,
                R.drawable.ic_action_upcoming};

        FragmentManager fragmentManager;

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            fragmentManager = fm;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;

            switch (position) {
                case PAGE_HOME:
                    fragment = new QuizzesDrawerFragment();
                    break;
                case PAGE_ADD:
                    fragment = new QuizzesDrawerFragment();
                    break;
                case PAGE_ACCOUNT:
                    fragment = new QuizzesDrawerFragment();
                    break;
                default:
                    fragment = new QuizzesDrawerFragment();
                    break;
            }

            return fragment;
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }
    }

    private class OnAccountAddComplete implements AccountManagerCallback<Bundle> {

        @Override
        public void run(AccountManagerFuture<Bundle> future) {
            Bundle bundle;
            try {
                bundle = future.getResult();
            } catch (OperationCanceledException e) {
                e.printStackTrace();
                return;
            } catch (AuthenticatorException e) {
                e.printStackTrace();
                return;
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            Account mAccount = new Account(
                    bundle.getString(AccountManager.KEY_ACCOUNT_NAME),
                    bundle.getString(AccountManager.KEY_ACCOUNT_TYPE)
            );
            Log.d("main", "Added account " + mAccount.name + ", fetching");
            init();
        }
    }
}
