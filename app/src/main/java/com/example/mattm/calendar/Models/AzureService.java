package com.example.mattm.calendar.Models;

import android.content.Context;
import android.os.AsyncTask;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.table.sync.MobileServiceSyncContext;
import com.microsoft.windowsazure.mobileservices.table.sync.MobileServiceSyncTable;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.ColumnDataType;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.MobileServiceLocalStoreException;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.SQLiteLocalStore;
import com.microsoft.windowsazure.mobileservices.table.sync.synchandler.SimpleSyncHandler;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

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
    private AzureService(Context context) throws MalformedURLException, InterruptedException, ExecutionException, MobileServiceLocalStoreException
    {
        this.context = context;
        client = new MobileServiceClient(mobileBackendUrl, context);
        initialiseStore();
        
        // Initialising all tables
        // userTable = client.getSyncTable(User.class);
    }
    
    // Static Methods
    public static void Initialise(Context context) throws MalformedURLException, InterruptedException, ExecutionException, MobileServiceLocalStoreException
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
    
    // Private Methods
    private AsyncTask<Void, Void, Void> initialiseStore()
            throws MobileServiceLocalStoreException, ExecutionException, InterruptedException
    {
        /*
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>()
        {
            @Override
            protected Void doInBackground(Void... params)
            {
                try
                {
                    MobileServiceSyncContext syncContext = client.getSyncContext();
                    if (syncContext.isInitialized())
                        return null;
    
                    SQLiteLocalStore localStore = new SQLiteLocalStore(client.getContext(), "offlineStore",
                            null, 1);
                    
                    // Creating table definitions
                    Map<String, ColumnDataType> userDefinition = new HashMap<>();
                    userDefinition.put("Id", ColumnDataType.String);
                    userDefinition.put("UserName", ColumnDataType.String);
                    userDefinition.put("FirstName", ColumnDataType.String);
                    userDefinition.put("LastName", ColumnDataType.String);
                    userDefinition.put("Version", ColumnDataType.String);
                    userDefinition.put("UpdatedAt", ColumnDataType.DateTimeOffset);
                    
                    // Defining the table in the local store
                    localStore.defineTable("User", userDefinition);
                    
                    // Specifying a sync handler for conflict resolution
                    SimpleSyncHandler handler = new SimpleSyncHandler();
                    
                    // Initialising the local store
                    syncContext.initialize(localStore, handler).get();
                }
                catch (final Exception e)
                {
                    e.printStackTrace();
                }
                return null;
            }
        };
        return runAsyncTask(task);
        */
        return null;
    }
    
    // Accessors
    public MobileServiceClient GetClient()
    {
        return client;
    }
}