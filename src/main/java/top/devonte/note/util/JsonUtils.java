package top.devonte.note.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.List;

public class JsonUtils {

    private static ObjectMapper mapper = new ObjectMapper();

    static {
        // 转换为格式化的json
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        // 如果json中有新增的字段并且是实体类类中不存在的，不报错
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static <T> T parse(String json, Class<T> clazz) throws JsonProcessingException {
        return mapper.readValue(json, clazz);
    }

    public static <T> List<T> parseList(String json, Class<T> clazz) throws JsonProcessingException {
        JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, clazz);
        return mapper.readValue(json, javaType);
    }



    public static String stringify(Object o) throws JsonProcessingException {
        return mapper.writeValueAsString(o);
    }

}
