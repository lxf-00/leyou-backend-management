package com.leyou.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;


import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author: HuYi.Zhang
 * @create: 2018-04-24 17:20
 **/
public class JsonUtils {

    public static final ObjectMapper mapper = new ObjectMapper();

    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    @Nullable
    public static String serialize(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj.getClass() == String.class) {
            return (String) obj;
        }
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error("json序列化出错：" + obj, e);
            return null;
        }
    }

    @Nullable
    public static <T> T parse(String json, Class<T> tClass) {
        try {
            return mapper.readValue(json, tClass);
        } catch (IOException e) {
            logger.error("json解析出错：" + json, e);
            return null;
        }
    }

    @Nullable
    public static <E> List<E> parseList(String json, Class<E> eClass) {
        try {
            return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, eClass));
        } catch (IOException e) {
            logger.error("json解析出错：" + json, e);
            return null;
        }
    }

    @Nullable
    public static <K, V> Map<K, V> parseMap(String json, Class<K> kClass, Class<V> vClass) {
        try {
            return mapper.readValue(json, mapper.getTypeFactory().constructMapType(Map.class, kClass, vClass));
        } catch (IOException e) {
            logger.error("json解析出错：" + json, e);
            return null;
        }
    }

    @Nullable
    public static <T> T nativeRead(String json, TypeReference<T> type) {
        try {
            return mapper.readValue(json, type);
        } catch (IOException e) {
            logger.error("json解析出错：" + json, e);
            return null;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class User{
         String name;
         Integer age;
    }

    public static void main(String[] args) {
        User user = new User("John", 23);

        // 测试serialize方法： 序列化为json格式
        /*String jsonStr = serialize(user);
        System.out.println("jsonStr = " + jsonStr);*/

        // 反序列化(字符串）：parse
        // User user1 = parse(jsonStr, User.class);
        // System.out.println("user1 = " + user1);

        // 反序列化（列表）： parseList
        /*jsonStr = "[3,6,2,5]";
        List<Integer> list = parseList(jsonStr, Integer.class);
        System.out.println("list = " + list);*/

        // 反序列化（集合）：parseMap
        //language=JSON
        /*String json = "{\"name\" : \"jack\", \"age\" : \"23\"}\n";
        Map<String, String> map = parseMap(json, String.class, String.class);
        System.out.println("map = " + map);*/

        // 反序列化（复杂类型）： nativeRed
        String json = "[{\"name\" : \"jack\", \"age\" : \"23\"}\n]";
        List<Map<String, String>> maps = nativeRead(json, new TypeReference<List<Map<String, String>>>() {
        });
        for (Map<String, String> map : maps) {
            System.out.println("map = " + map);
        }
    }

}

