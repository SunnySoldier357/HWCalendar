package com.example.mattm.calendar.Views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.auth.core.SignInStateChangeListener;
import com.amazonaws.mobile.auth.ui.SignInUI;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.example.mattm.calendar.Models.AWSConnection;
import com.example.mattm.calendar.R;

public class AuthenticatorActivity extends AppCompatActivity
{
    // Public Properties
    public final String LOG_TAG = "Authenticator";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticator);
        
        // TODO: Move to AWSConnection class
        IdentityManager.getDefaultIdentityManager().addSignInStateChangeListener(new SignInStateChangeListener()
        {
            @Override
            public void onUserSignedIn()
            {
                Log.d(LOG_TAG, "User Signed In");
                try
                {
                    AWSConnection.GetCurrentInstance(null).UpdateUserID().execute().get();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onUserSignedOut()
            {

            }

        });
        showSignIn();
    }

    // Private Methods
    private void showSignIn()
    {
        Log.d("Authenticator", "showSignIn");

        SignInUI signin = (SignInUI) AWSMobileClient.getInstance().getClient(AuthenticatorActivity.this, SignInUI.class);
        signin.login(AuthenticatorActivity.this, MainActivity.class).execute();
    }
}