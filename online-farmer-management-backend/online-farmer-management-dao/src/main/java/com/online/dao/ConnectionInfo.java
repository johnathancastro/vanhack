package com.online.dao;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConnectionInfo
{
	private final String driver = "jdbc:postgresql://";
	private final String server;
	private final String database;
	private final UserCredentials user;
	private final String toString;
	
	public ConnectionInfo (String serverDB, UserCredentials user) 
	{
		if (serverDB.contains("//"))
			serverDB = serverDB.substring(serverDB.indexOf("//") + 2);
		
		this.server = parseServer(serverDB);
		this.database = parseDatabase(serverDB);
		this.user = user;
		toString = server + "/" + database + "?user=" + user.getUser();
	}
	
    @Override
    public String toString()
    {
        return toString;
    }

    private String parseServer(String serverDB)
    {
	    Pattern serverPattern = Pattern.compile(".+/");   
	    Matcher matcher = serverPattern.matcher(serverDB);
	    
	    if (!matcher.find())
	    	throw new IllegalArgumentException("Invalid server address in connection info!");
	    	
	    String host = matcher.group();
	    return host.substring(0, host.length() - 1);	    
    }
    
    private String parseDatabase(String serverDB)
    {
    	Pattern databasePattern = Pattern.compile("\\/([A-Za-z0-9_]+)");
    	Matcher matcher = databasePattern.matcher(serverDB);
    	
    	if (!matcher.find())
	    	throw new IllegalArgumentException("Invalid server address in connection info!");
    	
    	return matcher.group(1);    	
    }
	
	public String getConnectionURL()
	{
		return driver + server + "/" + database + "?user=" + user.getUser() + "&password=" + user.getPassword();
	}
	
	public String getServer()
	{
		return server;
	}
	
	public String getDatabase()
	{
		return database;
	}
	
	public final UserCredentials getUserCredentials()
	{
		return user;
	}
	
	public String getUserName()
	{
	    if (user == null)
	        return null;
	    
	    return user.getUser();
	}
	
	public String getPassword()
	{
	    if (user == null)
	        return null;
	    
	    return user.getPassword();
	}
	
	public String serverDatabase()
	{
	    return server + "/" + database;
	}
}