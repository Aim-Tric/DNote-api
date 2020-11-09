package top.devonte.note.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.devonte.note.entity.NoteFile;

import java.util.List;

@Mapper
public interface FileMapper {

    List<NoteFile> page(@Param("userId") int userId, @Param("page") int page, @Param("size") int size);

    List<NoteFile> list(@Param("userId") int userId, @Param("folderId") int folderId);

    NoteFile selectOne(Integer id);

    Integer insert(@Param("noteFile") NoteFile noteFile);

    void insertRelation(@Param("userId") int userId, @Param("id") Integer id);

    void delete(@Param("id") Integer id);

    void deleteRelation(@Param("userId") int userId, @Param("id") Integer id);

    List<NoteFile> selectFolders(@Param("userId") int userId);

    List<NoteFile> pageByFolderId(@Param("folderId") int folderId, @Param("page") int page, @Param("size") int size);

    List<NoteFile> pageByType(@Param("userId") int userId, @Param("page") int page, @Param("size") int size);

    void update(@Param("noteFile") NoteFile noteFile);

    List<NoteFile> listFolder(@Param("userId") int userId ,@Param("folderId") int folderId);
}
