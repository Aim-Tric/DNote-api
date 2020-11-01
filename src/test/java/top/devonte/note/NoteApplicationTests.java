package top.devonte.note;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.devonte.note.service.FileService;

import javax.annotation.Resource;
import java.io.IOException;

@SpringBootTest
class NoteApplicationTests {

    @Resource
    private FileService fileService;

    @Test
    void contextLoads() throws IOException {
    }

}
