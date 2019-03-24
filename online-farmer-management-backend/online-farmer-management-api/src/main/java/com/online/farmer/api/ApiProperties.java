package com.online.farmer.api;

import com.online.dao.DatabaseConnectionListener;
import com.online.dao.PropertiesManager;
import com.online.dao.UserCredentials;

public class ApiProperties
{
    private static PropertiesManager reader = new PropertiesManager("online-farmer.properties");

    public static UserCredentials credentials()
    {
        return new UserCredentials(reader.readProperty("db-user", "online"), reader.readProperty("db-password", "online"));
    }
    
    public static String server()
    {
        return reader.readProperty("config-db", "localhost/online_farmer");
    }
    
    public static DatabaseConnectionListener defaultConnectionListener()
    {
        return ApiConnectionBroker.defaultConnectionListener();
    }
}