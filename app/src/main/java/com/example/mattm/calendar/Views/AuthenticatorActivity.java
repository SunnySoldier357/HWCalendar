package com.example.mattm.calendar.Views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.auth.core.SignInStateChangeListener;
import com.amazonaws.mobile.auth.ui.SignInUI;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.example.mattm.calendar.Models.AWSConnection;
import com.example.mattm.calendar.R;

public class AuthenticatorActivity extends AppCompatActivity
{
    // Constants
    public final String LOG_TAG = "Authenticator";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticator);
        
        IdentityManager.getDefaultIdentityManager().addSignInStateChangeListener(new SignInStateChangeListener()
        {
            @Override
            public void onUserSignedIn()
            {
                // TODO: Remove when done testing
                Log.d(LOG_TAG, "User Signed In");
                
                try
                {
                    AWSConnection.getCurrentInstance(null).updateUserID().execute().get();
                }
                catch (Exception e)
                {
                    // TODO: UI - Show error message to User in a way they will understand for different error messages

                    // Temporary solution
                    e.printStackTrace();
                    Toast.makeText(AuthenticatorActivity.this, "Unable to connect to network", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onUserSignedOut()
            {
                // TODO: Do something here like return to login page?
                Toast.makeText(AuthenticatorActivity.this, "does this logout?", Toast.LENGTH_LONG).show();
            }
        });
        
        showSignIn();
    }

    // Private Method
    private void showSignIn()
    {
        // TODO: Remove when done testing
        Log.d("Authenticator", "showSignIn");

        SignInUI signin = (SignInUI) AWSMobileClient.getInstance().getClient(AuthenticatorActivity.this, SignInUI.class);
        signin.login(AuthenticatorActivity.this, MainActivity.class).execute();
    }
}