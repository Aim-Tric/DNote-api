package top.devonte.note.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * 日期转换配置
 * 解决@RequestAttribute、@RequestParam和@RequestBody三种类型的时间类型参数接收与转换问题
 */
@Configuration
public class DateConfiguration {

    /**
     * 默认日期时间格式
     */
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";


    /**
     * Json序列化和反序列化转换器，用于转换Post请求体中的json以及将我们的对象序列化为返回响应的json
     * 使用@RequestBody注解的对象中的Date类型将从这里被转换
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);

        SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT);
        objectMapper.setDateFormat(formatter);
        objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return objectMapper;
    }

}
