package de.jawb.restapi.template.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import de.jawb.restapi.template.controller.api.Mapping;

public class ReflectionUtil {
    
    public static Map<String, Object> getConstants(Class<?> c) {
        
        Map<String, Object> map = new HashMap<>();
        
        for (Field f : c.getDeclaredFields()) {
            int mod = f.getModifiers();
            if (Modifier.isStatic(mod) && Modifier.isPublic(mod) && Modifier.isFinal(mod)) {
                try {
                    map.put(f.getName(), f.get(null));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        
        return map;
    }
    
    public static void main(String[] args) {
        
        System.out.println(getConstants(Mapping.V1.class));
        
    }
    
}
