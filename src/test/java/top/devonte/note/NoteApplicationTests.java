package top.devonte.note;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.devonte.note.common.Page;
import top.devonte.note.entity.NoteFile;
import top.devonte.note.service.FileService;

import javax.annotation.Resource;
import java.io.IOException;

@SpringBootTest
class NoteApplicationTests {

    @Resource
    private FileService fileService;

    @Test
    void contextLoads() throws IOException {
        Page<NoteFile> cachedFilePath = fileService.getFiles(1, 1);
        System.out.println(cachedFilePath.getTotal());
    }

}
