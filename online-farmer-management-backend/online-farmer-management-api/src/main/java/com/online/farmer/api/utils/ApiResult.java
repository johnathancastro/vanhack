package com.online.farmer.api.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.online.farmer.api.ContentType;

public class ApiResult
{
	private final JsonObject json = new JsonObject();
    
    private ApiResult(String message, JsonElement element)
    {
        this(message == null || message.isEmpty(), message, element);
    }

    private ApiResult(boolean success, String message, JsonElement element)
    {
        json.addProperty("success", success);
        json.addProperty("message", message);
        json.add("data", element);
    }

    public static  ApiResult from(Object data)
    {
        //this is more expensive than I would prefer, look into a more direct translation
        JsonParser parser = new JsonParser();
        JsonElement dataElement = parser.parse(JsonTranslator.toJson(data));
        return from(dataElement);
    }
 
    public static ApiResult from(Object data, ContentType type) throws Exception
    {    	
    	switch(type){
    		case JSON:
    			return from(data);
    		default:
    			return from(data);
    	}
    }

	public static ApiResult from(String attr, Object value)
    {
    	JsonObject innerObject = new JsonObject();
    	innerObject.addProperty(attr != null ? attr : "", value != null ? value.toString() : "");
    	
        return from(innerObject);
    }
    
    public static ApiResult from(JsonElement data)
    {
        return from("", data);
    }

    public static ApiResult from(String message, JsonElement data)
    {
        return new ApiResult(message, data);
    }
    
    public static ApiResult decode(String json)
    {
        //this is more expensive than I would prefer, look into a more direct translation
        JsonParser parser = new JsonParser();
        JsonElement dataElement = parser.parse(json);
        return from(dataElement);
    }
    
    @Override
    public String toString()
    {
        return json.toString();
    }
    
    public String toString(ContentType type){
    	switch(type){
		case JSON:
			return json.toString();
		default:
			return json.toString();
    	}
    }
    
    public String toJson()
    {
        return json.toString();
    }
}
