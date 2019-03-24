package com.online.farmer.api;

import javax.ws.rs.core.MediaType;


public enum ContentType {
	JSON;
	
	public static ContentType from(String type)
	{
		return from(type, ContentType.JSON);
	}
	
	public static ContentType from(String type, ContentType defaultType)
    {
		if (type == null) return defaultType;
		
        switch (type.toLowerCase())
        {
            case "json": return ContentType.JSON;
            case MediaType.APPLICATION_JSON: return ContentType.JSON;
            case "": return defaultType;
            default: throw new IllegalArgumentException("Not supported type: " + type);
        }
    }
}
