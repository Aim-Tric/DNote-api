package top.devonte.note.config.security.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import top.devonte.note.util.JsonUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class NoteAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {

        ResponseEntity<String> responseEntity = ResponseEntity.badRequest().body("登录失败: " + e.getMessage());

        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(JsonUtils.stringify(responseEntity));
    }
}
