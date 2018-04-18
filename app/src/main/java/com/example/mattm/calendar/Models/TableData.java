package com.example.mattm.calendar.Models;

import com.microsoft.windowsazure.mobileservices.table.DateTimeOffset;

import java.sql.Date;

public abstract class TableData
{
    // Private Properties
    @com.google.gson.annotations.SerializedName("update")
    private DateTimeOffset UpdatedAt;
    
    @com.google.gson.annotations.SerializedName("id")
    private String Id;
    private String Version;
    
    // Accessors
    public DateTimeOffset GetUpdatedAt() { return UpdatedAt; }
    
    public String GetId() { return Id; }
    public String GetVersion() { return Version; }
    
    // Mutators
    protected final void SetUpdatedAt(DateTimeOffset updatedAt) { UpdatedAt = updatedAt; }
    
    public final void SetId(String id) { Id = id; }
    public final void SetVersion(String version) { Version = version; }
}