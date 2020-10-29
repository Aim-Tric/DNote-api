package top.devonte.note.config.security.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import top.devonte.note.util.JsonUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class NoteAccessDenyHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        System.out.println(request.getRequestURI());
        ResponseEntity<String> responseEntity = ResponseEntity.badRequest().body("访问被拒绝: " + e.getMessage());

        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(JsonUtils.stringify(responseEntity));
    }
}
