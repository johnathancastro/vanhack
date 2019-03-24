package com.online.model.json;

import java.text.ParseException;
import java.util.Date;
import java.util.InputMismatchException;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JsonHandler {
	private final JsonObject obj;
    public JsonObject getObj() {
    	return this.obj;
    }
    
    public JsonHandler(JsonElement ele)
    {
        this(ele != null ? ele.getAsJsonObject() : null);
    }

    public JsonHandler(JsonObject obj)
    {
        this.obj = obj;
    }
    
    public String extractString(String name)
    {
    	return extractString(name, "");
    }
    
    public String extractString(String name, String defaultValue)
    {
    	String value = defaultValue;
        if (obj == null)
            return value;
        
        JsonElement ele = obj.get(name);
        if (isEmptyElement(ele))
            return value;
        
        return ele.getAsString() != null ? ele.getAsString().trim() : ele.getAsString();
    }
    
    public Integer extractInteger(String name)
    {
    	return extractInteger(name, null);
    }
    
    public Integer extractInteger(String name, Integer defaultValue)
    {
    	Integer value = defaultValue;
        if (obj == null)
            return value;
        
        JsonElement ele = obj.get(name);
        
        if (isEmptyElement(ele))
            return value;
        
    	try {
            return ele.getAsInt();
    	} catch(NumberFormatException e) {
    		throw new NumberFormatException(formatNumberExceptionMsg(ele.getAsString()));
    	}
    }
    
    private boolean isEmptyElement(JsonElement ele) {
    	return ele == null || ele.isJsonNull() || ele.getAsString().equals("");
    }
    
    public Boolean extractBoolean(String name)
    {
    	return extractBoolean(name, null);
    }
    
    public Boolean extractBoolean(String name, Boolean defaultValue)
    {
    	Boolean value = defaultValue;
        if (obj == null)
            return value;
        
        JsonElement ele = obj.get(name);
        if (isEmptyElement(ele))
            return value;
        
        return ele.getAsBoolean();
    }
    
    public Double extractDouble(String name)
    {
    	return extractDouble(name, null);
    }
    
    public Double extractDouble(String name, Double defaultValue)
    {
    	Double value = defaultValue;
        if (obj == null)
            return value;
        
        JsonElement ele = obj.get(name);
        if (isEmptyElement(ele))
            return value;
        
        try {
        	return ele.getAsDouble();
    	} catch(NumberFormatException e) {
    		throw new NumberFormatException(formatNumberExceptionMsg(ele.getAsString()));
    	}
    }
    
    private String formatNumberExceptionMsg(String value) {
    	return String.format("Invalid Number: <%s>.", value);
    }
    
    public JsonElement get(String name)
    {
    	JsonElement value = null;
        if (obj == null)
            return value;
        
        JsonElement ele = obj.get(name);
        if (ele == null || ele.isJsonNull())
            return value;
        
        return ele;
    }

}