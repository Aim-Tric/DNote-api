package top.devonte.note;


import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileOutputStream;
import java.io.IOException;

@SpringBootTest
public class PoiTest {

    @Test
    void createFile() throws IOException {
        // TODO 测试poi创建文档
        XWPFDocument document = new XWPFDocument();

        // 添加标题
        XWPFParagraph titleParagraph = document.createParagraph();
        titleParagraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun titleParagraphRun = titleParagraph.createRun();
        titleParagraphRun.setText("标题测试");
        titleParagraphRun.setBold(true);//加粗
        titleParagraphRun.setFontSize(16);//字体大小
        titleParagraphRun.setFontFamily("黑体");
        titleParagraphRun.addBreak();

        //添加内容
        XWPFParagraph firstParagraph = document.createParagraph();
        XWPFRun run = firstParagraph.createRun();
        run.setText("测试段落   样式设置");
        run.setBold(false);
        run.setFontSize(12);//字体大小
        run.setFontFamily("宋体");
        run.addBreak();//添加一个回车空行

        FileOutputStream out = new FileOutputStream("./download/test.docx");
        document.write(out);
        out.close();
    }

}
