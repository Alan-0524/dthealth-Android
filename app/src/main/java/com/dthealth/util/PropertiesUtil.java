package com.dthealth.util;

import android.util.Log;

import java.util.Properties;

public class PropertiesUtil {
    private static Properties properties=null;
    public static synchronized Properties getProperties() {
        if(properties == null){
            properties = new Properties();
            try {
                properties.load(PropertiesUtil.class.getResourceAsStream("/assets/appConfig.properties"));
            } catch (Exception e) {
                Log.e("PropertiesUtil",e.getMessage());
            }
        }
        return properties;
    }
}
