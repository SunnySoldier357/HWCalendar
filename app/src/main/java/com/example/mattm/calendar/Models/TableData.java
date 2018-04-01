package com.example.mattm.calendar.Models;

import com.microsoft.windowsazure.mobileservices.table.DateTimeOffset;

import java.sql.Date;

public abstract class TableData
{
    // Private Properties
    private DateTimeOffset updatedAt;
    
    private String id;
    private String version;
    
    // Accessors
    public DateTimeOffset getUpdatedAt() { return updatedAt; }
    
    public String getId() { return id; }
    public String getVersion() { return version; }
    
    // Mutators
    protected final void setUpdatedAt(DateTimeOffset updatedAt) { this.updatedAt = updatedAt; }
    
    public final void setId(String id) { this.id = id; }
    public final void setVersion(String version) { this.version = version; }
}
