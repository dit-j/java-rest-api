package de.jawb.restapi.template.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.jawb.restapi.template.base.exceptions.BaseAppException;

public class JsonUtil {
    
    /**
     * @param jsonString
     * @return
     */
    public static <K, V> Map<K, V> toMap(String jsonString) {
        
        ObjectMapper m = new ObjectMapper();
        TypeReference<HashMap<K, V>> typeRef = new TypeReference<HashMap<K, V>>() {
        };
        Map<K, V> map = null;
        try {
            map = m.readValue(jsonString, typeRef);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return map;
    }
    
    public static String toJsonString(Object o) {
        try {
            return new ObjectMapper().writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new BaseAppException(e);
        }
    }
    
    public static <T> T parse(String json, Class<T> clazz) {
        try {
            return new ObjectMapper().readValue(json, clazz);
        } catch (Exception e) {
            throw new BaseAppException(e);
        }
    }
    
    public static <T> T parse(File src, Class<T> clazz) {
        try {
            return new ObjectMapper().readValue(src, clazz);
        } catch (Exception e) {
            throw new BaseAppException(e);
        }
    }
}
