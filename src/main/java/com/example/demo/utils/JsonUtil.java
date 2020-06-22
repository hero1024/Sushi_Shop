package com.example.demo.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * json 工具类
 *
 * @author songpeijiang
 * @since 2020/6/10
 */
public class JsonUtil {

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
    }

    /**
     * 根据实体获取json字符串
     *
     * @param entity 实体
     * @return 字符串
     * @throws JsonProcessingException JsonProcessingException
     */
    public static String getStrByEntity(Object entity) throws JsonProcessingException {
        return objectMapper.writeValueAsString(entity);

    }

    /**
     * @param jsonString  json字符串
     * @param targetClass 目标类
     * @param <T>         泛型
     * @return 目标类
     * @throws JsonProcessingException JsonProcessingException
     */
    public static <T> T getEntityByStr(String jsonString, Class<T> targetClass) throws JsonProcessingException {
        return objectMapper.readValue(jsonString, targetClass);
    }

    /**
     * 根据字符串获取集合
     *
     * @param jsonString  json字符串
     * @param targetClass 集合中的对象
     * @param <T>         泛型
     * @return 目标集合
     * @throws JsonProcessingException JsonProcessingException
     */
    public static <T> List<T> getListEntityByStr(String jsonString, Class<T> targetClass) throws JsonProcessingException {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, targetClass);
        return (List<T>) objectMapper.readValue(jsonString, javaType);
    }
}
