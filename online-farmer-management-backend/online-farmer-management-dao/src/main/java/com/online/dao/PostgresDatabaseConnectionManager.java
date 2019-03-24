package com.online.dao;

import java.sql.Connection;

public class PostgresDatabaseConnectionManager implements DatabaseConnectionManager
{
    private final UserCredentials userCredentials;
    
    public PostgresDatabaseConnectionManager(UserCredentials userCredentials) throws Exception
    {
        this.userCredentials = userCredentials;
    }

    @Override
    public Connection getDatabaseConnection(String database) throws Exception
    {
        if (database == null)
            throw new IllegalArgumentException("Cannot get a connection for a null database!");

        SimpleDatabaseConnectionManager simple = new SimpleDatabaseConnectionManager(this.userCredentials);
        return simple.getDatabaseConnection(database);
        
    }
}
