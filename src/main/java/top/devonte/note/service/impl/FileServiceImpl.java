package top.devonte.note.service.impl;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;
import top.devonte.note.common.FileConstants;
import top.devonte.note.common.Page;
import top.devonte.note.entity.NoteFile;
import top.devonte.note.mapper.FileMapper;
import top.devonte.note.service.FileService;
import top.devonte.note.util.RedisUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Service层
 * 文件/文件夹相关操作业务实现类
 */
@Service
public class FileServiceImpl implements FileService {
    @Resource
    private FileMapper fileMapper;

    @Resource
    private RedisUtils redisUtils;

    @Override
    public List<NoteFile> getAllFiles(int userId, int page) {
        return fileMapper.page(userId, page, 20);
    }

    @Override
    public List<NoteFile> getFolderFiles(Integer folderId, int page) {
        return fileMapper.pageByFolderId(folderId, page, 20);
    }

    @Override
    public NoteFile getFile(Integer id) {
        return fileMapper.selectOne(id);
    }

    @Override
    public List<NoteFile> getFolders(int id, int page) {
        return fileMapper.selectFolders(id);
    }

    @Override
    public void deleteFile(Integer id, int userId) {
        fileMapper.delete(id);
        fileMapper.deleteRelation(userId, id);
    }

    @Override
    public NoteFile createFile(NoteFile noteFile, int userId) {
        fileMapper.insert(noteFile);
        fileMapper.insertRelation(userId, noteFile.getId());
        return noteFile;
    }

    @Override
    public void updateFile(NoteFile noteFile) {
        // DO CLEAR CACHE
        HashOperations<String, Object, Object> ops = redisUtils.getHashOps().getOps();
        int id = noteFile.getId();
        Boolean hasCache = redisUtils.getHashOps().getOps().hasKey(FileConstants.CACHE_FILE_KEY, id);
        if(hasCache) {
            ops.delete(FileConstants.CACHE_FILE_KEY, id);
        }
        fileMapper.update(noteFile);
    }

    @Override
    public String getCachedFilePath(int id) throws IOException {
        // 从缓存数据库匹配缓存文件
        Boolean hasCache = redisUtils.getHashOps().getOps().hasKey(FileConstants.CACHE_FILE_KEY, id);
        if (hasCache) {
            return (String) redisUtils.getHashOps().get(FileConstants.CACHE_FILE_KEY, id);
        }
        // 生成缓存文件并入库
        NoteFile file = getFile(id);
        // TODO 根据文档类型生成对应的文件

        String path = FileConstants.CACHE_FILE_LOCAL_PATH + id + FileConstants.CACHE_FILE_DOC_SUFFIX;
        redisUtils.getHashOps().put(FileConstants.CACHE_FILE_KEY, id, path);
        File f = new File(path);
        XWPFDocument document = new XWPFDocument();
        // 添加标题
        XWPFParagraph titleParagraph = document.createParagraph();
        titleParagraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun titleParagraphRun = titleParagraph.createRun();
        titleParagraphRun.setText(file.getTitle());
        titleParagraphRun.setBold(true);//加粗
        titleParagraphRun.setFontSize(16);//字体大小
        titleParagraphRun.setFontFamily("黑体");

        String body = file.getBody();
        for (String s : body.split("(\\[\\(br\\)])")) {
            XWPFParagraph firstParagraph = document.createParagraph();
            firstParagraph.setAlignment(ParagraphAlignment.LEFT);
            //添加内容
            XWPFRun run = firstParagraph.createRun();
            run.setText(s);
            run.setBold(false);
            run.setFontSize(12);//字体大小
            run.setFontFamily("宋体");
        }
        FileOutputStream out = new FileOutputStream(f);
        document.write(out);
        out.close();

        return path;
    }

    @Override
    public Page<NoteFile> getFiles(int page, int userId) {
        List<NoteFile> data = fileMapper.page(userId, page, 10);
        return Page.of(data, 10, fileMapper.countByUserId(userId), 10);
    }
}
