package com.example.mattm.calendar;

import android.content.Context;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;

public class AzureService
{
    // Private Properties
    private Context context;
    
    private MobileServiceClient client;
    
    private String mobileBackendUrl = "https://myappname.azurewebsites.net";
}
