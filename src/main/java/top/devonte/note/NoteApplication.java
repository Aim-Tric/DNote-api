package top.devonte.note;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class NoteApplication {

    public static void main(String[] args) {
        SpringApplication.run(NoteApplication.class, args);
    }

}
