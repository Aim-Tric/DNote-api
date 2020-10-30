package top.devonte.note.service;

import com.github.pagehelper.PageInfo;
import top.devonte.note.entity.NoteFile;

import java.io.IOException;
import java.util.List;

public interface FileService {

    /**
     * 获取文件列表
     *
     * @param page 页数
     * @return 文件列表
     */
    List<NoteFile> getAllFiles(int userId, int page);

    /**
     * 获取文件夹内的文件
     *
     * @param folder 文件夹id
     * @param page   页数
     * @return 文件列表
     */
    List<NoteFile> getFolderFiles(Integer folder, int page);

    /**
     * 获取单个文件
     *
     * @param id 文件id
     * @return 文件内容
     */
    NoteFile getFile(Integer id);

    /**
     * 获取文件夹
     *
     * @param id 文件id
     * @return 文件内容
     */
    List<NoteFile> getFolders(int id, int page);

    /**
     * 删除文件
     *
     * @param id 文件id
     */
    void deleteFile(Integer id, int userId);

    /**
     * 创建文件/文件夹
     *
     * @param noteFile 文件实体
     * @param userId   当前用户
     * @return 新插入的文件数据
     */
    NoteFile createFile(NoteFile noteFile, int userId);

    /**
     * 更新文件/文件夹
     *
     * @param noteFile 文件实体
     */
    void updateFile(NoteFile noteFile);

    /**
     * 获取缓存文件的路径
     * 如果缓存文件不存在，则生成缓存文件，并将其路径返回
     *
     * @param id 文件id
     * @return 缓存路径
     */
    String getCachedFilePath(int id) throws IOException;

    /**
     * 分页获取文档
     * @return
     */
    PageInfo<NoteFile> getFiles(int page, int userId);

}
