package top.devonte.note;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.devonte.note.mapper.AnalyticMapper;
import top.devonte.note.service.FileService;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

@SpringBootTest
class NoteApplicationTests {

    @Resource
    private FileService fileService;

    @Resource
    private AnalyticMapper mapper;

    @Test
    void contextLoads() throws IOException {
    }

    @Test
    void analyticMapperTest() {
        Map<String, Object> stringObjectMap = mapper.analyticUserData(1);
        for (String key : stringObjectMap.keySet()) {
            System.out.println("key : " + key + " val " + stringObjectMap.get(key));
        }
    }

}
