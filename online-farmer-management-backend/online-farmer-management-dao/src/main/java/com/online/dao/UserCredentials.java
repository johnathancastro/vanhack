package com.online.dao;

public class UserCredentials
{
	private final String user;
	private final String password;
	
	public UserCredentials(String user, String password)
	{
		this.user = user;
		this.password = password;
	}

	public String getUser()
	{
		return user;
	}
	
	public String getPassword()
	{
		return password;
	}
}