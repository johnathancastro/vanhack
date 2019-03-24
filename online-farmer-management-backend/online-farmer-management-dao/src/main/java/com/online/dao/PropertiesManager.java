package com.online.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesManager {
	
	private Properties prop = new Properties();
	private final String propertyFile;

	public PropertiesManager(String propertyFile) {
		this.propertyFile = propertyFile;
		loadFile();
	}
	
	private void loadFile() {
		try (InputStream input = new FileInputStream(new File(getClass().getClassLoader().getResource(propertyFile).getFile()))){
			this.prop.load(input); 
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public String readProperty(String prop, String defaultValue)  {
		String value;
		try {
			value = this.getProperty(prop);
		} catch (Exception e) {
			value = defaultValue;
		}
		return value;
	}
	
	private String getProperty(String prop) throws Exception {
		String value = this.prop.getProperty(prop);
		if (value != null && !value.isEmpty()) return value;
		
		throw new Exception("Properties " + prop + " was not found !");
	}
}