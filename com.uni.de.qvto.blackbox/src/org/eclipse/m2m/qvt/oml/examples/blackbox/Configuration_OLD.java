package org.eclipse.m2m.qvt.oml.examples.blackbox;

import java.util.HashMap;
import java.util.Map;

public class Configuration {
	Map<String, String> propertiesMap = new HashMap<String, String>();
 
	public void setProperty(String key, String value) {
		propertiesMap.put(key, value);
		
	}

	public String getStringProperty(String key) {
		// TODO Auto-generated method stub
		return propertiesMap.get(key);
	}

	public int getIntProperty(String key) {
		return Integer.valueOf(propertiesMap.get(key));
	}

}
