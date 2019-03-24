package com.online.farmer.api;

import java.sql.Connection;

import com.online.dao.CatalogType;
import com.online.dao.DatabaseConnectionListener;
import com.online.dao.DatabaseConnectionManager;

public class ApiConnectionBroker
{
    private static DatabaseConnectionManager connection;
    
    public static void configure(DatabaseConnectionManager connection)
    {
        ApiConnectionBroker.connection = connection;
    }
    
    public static DatabaseConnectionManager getConnection(){
    	return ApiConnectionBroker.connection;
    }
    
    private static Connection connectionFor(CatalogType catalogType) throws Exception
    {
        switch (catalogType) {
			case ONLINE:
				return connection.getDatabaseConnection(ApiProperties.server());
			default: 
				return null;
		}
    }
    
    public static DatabaseConnectionListener defaultConnectionListener()
    {
    	return defaultConnectionListener(CatalogType.ONLINE);
    }
    
    private static DatabaseConnectionListener defaultConnectionListener(final CatalogType catalogType)
    {
        return new DatabaseConnectionListener()
        {
            @Override
            public Connection getDatabaseConnection() throws Exception
            {
                return connectionFor(catalogType);
            }
        };
    }

}
