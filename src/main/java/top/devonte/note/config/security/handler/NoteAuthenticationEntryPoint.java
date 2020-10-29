package top.devonte.note.config.security.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import top.devonte.note.util.JsonUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 权限校验入口
 *
 * @author Devonte
 * @date 2020/8/21
 */
@Component
public class NoteAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {

        ResponseEntity<String> responseEntity = ResponseEntity
                .badRequest().body("您没有权限访问此接口！请联系管理员。错误信息: " + e.getMessage());

        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(JsonUtils.stringify(responseEntity));
    }
}
