package com.online.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class SimpleDatabaseConnectionManager implements DatabaseConnectionManager
{
    private final UserCredentials userCredentials;
    
    public SimpleDatabaseConnectionManager(UserCredentials user)
    {
        this.userCredentials = user;
    }

    @Override
    public Connection getDatabaseConnection(String database) throws Exception
    {
        ConnectionInfo connInfo = new ConnectionInfo(database, userCredentials);
        Connection conn = DriverManager.getConnection(connInfo.getConnectionURL());
        return conn;
    }
}