package com.online.dao;

import java.sql.Connection;

public interface DatabaseConnectionManager
{
    Connection getDatabaseConnection(String database) throws Exception;
}
