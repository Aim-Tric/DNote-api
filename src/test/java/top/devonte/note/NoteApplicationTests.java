package top.devonte.note;

import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.devonte.note.entity.NoteFile;
import top.devonte.note.service.FileService;
import top.devonte.note.util.JsonUtils;

import javax.annotation.Resource;
import java.io.IOException;

@SpringBootTest
class NoteApplicationTests {

    @Resource
    private FileService fileService;

    @Test
    void contextLoads() throws IOException {
        PageInfo<NoteFile> files = fileService.getFiles(1, 1);
        System.out.println(JsonUtils.stringify(files));
    }

}
