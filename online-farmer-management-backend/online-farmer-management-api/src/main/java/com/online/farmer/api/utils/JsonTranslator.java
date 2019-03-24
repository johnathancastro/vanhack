package com.online.farmer.api.utils;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class JsonTranslator
{
	private static final String datePattern = "yyyy-MM-dd kk:mm:ssXX";
    private static final int  timezoneLength = 4;
    private static final SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
    private static final FieldNamingPolicy fnp = FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES;    
    private static final Pattern pattern = Pattern.compile("\\.\\d+");
    private static final Pattern patternTimezone = Pattern.compile("^(.*:.*)[-+](.*)$");
    private static final Pattern patternTimeSeparator = Pattern.compile("^.*-.*(T).*:.*$");
    private static final Pattern patternDigit = Pattern.compile("\\d+");
    private static final JsonDeserializer<Date> dateDeserializer = new JsonDeserializer<Date>()            
    {
        @Override
        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
        {
            return parseDateTime(json.getAsString());
        }
    };
    private static final JsonSerializer<Date> dateSerializer = new JsonSerializer<Date>()
    {
        @Override
        public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context)
        {
            return new JsonPrimitive(sdf.format(src));
        }
    };
    public static final Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, dateSerializer)
                                                      .registerTypeAdapter(Date.class, dateDeserializer)
                                                      .setFieldNamingPolicy(fnp)
                                                      .create();
    
    private static Date parseDateTime(String jsonDate)
    {
        try
        {
        	jsonDate = timeZonePadding(removeTimeSeparator(removeMilliseconds(jsonDate)));
            	
            synchronized (sdf) 
            {
                return sdf.parse(jsonDate);
            }
        }
        catch (ParseException e)
        {
            return parseDefaultDateFormat(jsonDate, e);
        }
    }
    
    private static String removeMilliseconds (String jsonDate)
    {
    	return pattern.matcher(jsonDate).replaceFirst("");
    }
    
    private static String removeTimeSeparator (String jsonDate)
    {
    	Matcher m = patternTimeSeparator.matcher(jsonDate);
    	if (m.matches())
    		return jsonDate.replace("T", " ");
    	
    	return jsonDate;
    }
    
    private static String timeZonePadding (String jsonDate)
    {
    	Matcher m = patternTimezone.matcher(jsonDate);
        if (m.matches() && m.groupCount() == 2) 
        {
        	String tz = m.group(2);
        	if (tz.length() < timezoneLength && patternDigit.matcher(tz).matches())
        		for (int i = 0 ; i < timezoneLength - tz.length() ; ++i)
        			jsonDate += "0";
        }
        
        return jsonDate;
    }

    private static Date parseDefaultDateFormat(String jsonDate, ParseException e)
    {
        try
        {
            return new SimpleDateFormat().parse(jsonDate);
        }
        catch (ParseException e1)
        {
            throw new JsonParseException(e);
        }
    }

    public static <T> T parseJson(String json, Class<T> klass)
    {
        return gson.fromJson(json, klass);
    }
    
    public static <T> T parseJson(String json, Type type)
    {        
        return gson.fromJson(json, type);
    }

    public static String toJson(Object obj)
    {
        return gson.toJson(obj);
    }
    
    public static String toJson(Object obj, Type type)
    {
        return gson.toJson(obj, type);
    }
    
}