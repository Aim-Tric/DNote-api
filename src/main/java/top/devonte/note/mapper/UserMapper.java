package top.devonte.note.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.devonte.note.entity.NoteUser;

@Mapper
public interface UserMapper {

    NoteUser selectByUsername(@Param("username") String username);

    int insert(@Param("noteUser") NoteUser noteUser);

    void update(@Param("noteUser") NoteUser noteUser);

}
