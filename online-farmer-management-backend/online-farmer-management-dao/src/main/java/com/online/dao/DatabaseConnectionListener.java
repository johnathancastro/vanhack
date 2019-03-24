package com.online.dao;

import java.sql.Connection;

public interface DatabaseConnectionListener
{
	Connection getDatabaseConnection() throws Exception;
}