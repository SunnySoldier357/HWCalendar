package com.example.mattm.calendar.Models;

import android.content.Context;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.table.sync.MobileServiceSyncTable;

import java.net.MalformedURLException;

public class AzureService
{
    // Admin Login (calendarkennmattsan,KennyMateoSunny!)
    
    // Static Properties
    private static AzureService currentInstance = null;
    
    // Private Properties
    private Context context;
    
    private MobileServiceClient client;
    
    private MobileServiceSyncTable<User> userTable;
    
    private String mobileBackendUrl = "https://calendarkenmattsan.azurewebsites.net";
    
    // Constructor
    private AzureService(Context context) throws MalformedURLException
    {
        this.context = context;
        client = new MobileServiceClient(mobileBackendUrl, context);
        
        // Initialising all tables
        // userTable = client.getSyncTable(User.class);
    }
    
    // Static Methods
    public static void Initialise(Context context) throws MalformedURLException
    {
        if (currentInstance == null)
            currentInstance = new AzureService(context);
        else
            throw new IllegalStateException("AzureService is already initialised");
    }
    
    public static AzureService GetCurrentInstance()
    {
        if (currentInstance == null)
            throw new IllegalStateException("AzureService is not initialised");
        return currentInstance;
    }
    
    // Public Methods
    public void AddUser(User user)
    {
        //User result = userTable.insert(user).get();
    }
    
    // Accessors
    public MobileServiceClient GetClient()
    {
        return client;
    }
}