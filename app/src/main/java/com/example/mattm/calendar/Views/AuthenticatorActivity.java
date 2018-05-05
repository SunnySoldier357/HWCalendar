package com.example.mattm.calendar.Views;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.auth.core.SignInStateChangeListener;
import com.amazonaws.mobile.auth.ui.SignInUI;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.example.mattm.calendar.Models.AWSConnection;
import com.example.mattm.calendar.R;

import java.util.concurrent.ExecutionException;

public class AuthenticatorActivity extends AppCompatActivity
{
    // Public Properties
    public String LOG_TAG = "Authenticator";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticator);

        //AWSMobileClient.getInstance().initialize(this).execute();
        IdentityManager.getDefaultIdentityManager().addSignInStateChangeListener(new SignInStateChangeListener()
        {
            @Override
            public void onUserSignedIn()
            {
                Log.d(LOG_TAG, "User Signed In");
                try {
                    AWSConnection.getCurrentInstance(null).updateUserID().execute().get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onUserSignedOut() {

            }

        });
        showSignIn();
    }

    private void showSignIn()
    {
        Log.d("Authenticator", "showSignIn");

        SignInUI signin = (SignInUI) AWSMobileClient.getInstance().getClient(AuthenticatorActivity.this, SignInUI.class);
        signin.login(AuthenticatorActivity.this, MainActivity.class).execute();
    }
}