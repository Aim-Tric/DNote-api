package top.devonte.note.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface AnalyticMapper {

    Map<String, Object> analyticUserData(@Param("userId") int userId);

}
