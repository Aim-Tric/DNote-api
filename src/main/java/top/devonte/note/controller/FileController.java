package top.devonte.note.controller;

import org.springframework.web.bind.annotation.*;
import top.devonte.note.common.ApiResult;
import top.devonte.note.common.BaseController;
import top.devonte.note.entity.NoteFile;
import top.devonte.note.entity.NoteUser;
import top.devonte.note.service.FileService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

@RestController
public class FileController extends BaseController {

    @Resource
    private FileService fileService;

    @GetMapping("/file/{fileId}")
    public ApiResult<NoteFile> getFile(@PathVariable Integer fileId) {
        NoteFile file = fileService.getFile(fileId);
        return ApiResult.ok(file);
    }

    @GetMapping("/file/page/{page}")
    public ApiResult<List<NoteFile>> getFiles(@PathVariable int page) {
        NoteUser noteUser = getLoginUser();
        return ApiResult.ok(fileService.getAllFiles(noteUser.getId(), page));
    }

    @GetMapping("/file/p/{folderId}")
    public ApiResult<List<NoteFile>> getPageFiles(@PathVariable int folderId) {
        NoteUser noteUser = getLoginUser();
        return ApiResult.ok(fileService.getFiles(noteUser.getId(), folderId));
    }

    @PostMapping("/file")
    public ApiResult<NoteFile> createFile(@RequestBody NoteFile file) {
        NoteUser noteUser = getLoginUser();
        NoteFile retFile = fileService.createFile(file, noteUser.getId());
        return ApiResult.ok(retFile);
    }

    @PutMapping("/file")
    public ApiResult<String> updateFile(@RequestBody NoteFile file) {
        fileService.updateFile(file);
        return ApiResult.ok("文档更新成功！");
    }

    @DeleteMapping("/file/{id}")
    public ApiResult<String> deleteFile(@PathVariable("id") Integer id) {
        NoteUser noteUser = getLoginUser();
        fileService.deleteFile(id, noteUser.getId());
        return ApiResult.ok("文档删除成功！");
    }

    @GetMapping("/folder/{folderId}/page/{page}")
    public ApiResult<List<NoteFile>> getFolderFiles(@PathVariable Integer folderId, @PathVariable int page) {
        return ApiResult.ok(fileService.getFolderFiles(folderId, page));
    }

    @GetMapping("/folders/{folderId}")
    public ApiResult<List<NoteFile>> getFolders(@PathVariable Integer folderId) {
        return ApiResult.ok(fileService.getFolder(getLoginUser().getId(), folderId));
    }

    @GetMapping("/folder/{page}")
    public ApiResult<List<NoteFile>> getFolders(@PathVariable int page) {
        NoteUser noteUser = getLoginUser();
        return ApiResult.ok(fileService.getFolders(noteUser.getId(), page));
    }

    @GetMapping("/file/download/{id}")
    public void downloadFile(HttpServletResponse response,
                             @PathVariable int id) throws IOException {
        String cachedFilePath = fileService.getCachedFilePath(id);

        File file = new File(cachedFilePath);
        String fileName = file.getName();
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);

        response.setCharacterEncoding("utf-8");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition",
                "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        response.setHeader("Accept-Ranges", "bytes");

        OutputStream os = null;
        InputStream is = new FileInputStream(file);
        response.setHeader("Content-Length", is.available() + "");
        byte[] b = new byte[is.available()];
        int i = 0;
        os = response.getOutputStream();
        while ((i = is.read(b)) != -1) {
            os.write(b, 0, i);
        }
        os.flush();
        os.close();
    }

    @GetMapping("/file/topping/{id}")
    public ApiResult<String> toppingFile(@PathVariable int id) {
        NoteFile file = new NoteFile();
        file.setId(id);
        file.setTopped(new Date());
        file.setTopping(true);
        fileService.updateFile(file);
        return ApiResult.ok("操作成功");
    }
}
