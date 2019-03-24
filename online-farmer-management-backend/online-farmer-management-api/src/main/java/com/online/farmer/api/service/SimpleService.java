package com.online.farmer.api.service;

import java.sql.Connection;

import com.online.dao.DatabaseConnectionListener;
import com.online.dao.PostgresDatabaseConnectionManager;
import com.online.farmer.api.ApiProperties;

public class SimpleService implements AutoCloseable
{
    protected final Connection conn;

    public SimpleService(DatabaseConnectionListener listener) throws Exception
    {
    	Class.forName("org.postgresql.Driver");
    	this.conn = new PostgresDatabaseConnectionManager(ApiProperties.credentials()).getDatabaseConnection(ApiProperties.server());
//        this.conn = listener.getDatabaseConnection();
    }

    @Override
    public void close() throws Exception
    {
        conn.close();
    }
    
    

}
