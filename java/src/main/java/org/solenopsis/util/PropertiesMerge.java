package com.redhat.solenopsis.util;

import java.util.Properties;

/**
 *
 * The purpose of this class is
 *
 * @author sfloess
 *
 */
public class PropertiesMerge {
    protected static void addTo(final Properties destination, final Properties toAdd) {
        for (final String propertyName : toAdd.stringPropertyNames()) {
            final String toAddValue = toAdd.getProperty(propertyName);
            final String value = destination.getProperty(propertyName);
            
            if (value == null) {
                destination.setProperty(propertyName, toAddValue);
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(value);
                
                String[] toAddValues = toAddValue.split(" ");
                
                //Collections.
                
//                treeSet.addAll(toAddValues);
            }
        }
    }
    
    public static Properties mergeProperties(final Properties properties1, final Properties properties2) {
        final Properties retVal = new Properties();
        retVal.putAll(properties1);
        
        addTo(retVal, properties2);
        
        return retVal;
    }
}
