package top.devonte.note.config.security.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import top.devonte.note.common.ApiResult;
import top.devonte.note.common.AuthConstants;
import top.devonte.note.entity.NoteUser;
import top.devonte.note.util.JsonUtils;
import top.devonte.note.util.JwtUtils;
import top.devonte.note.util.RedisUtils;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class NoteAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Resource
    private RedisUtils redisUtils;
    @Resource
    private JwtUtils jwtUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        NoteUser noteUser = (NoteUser) authentication.getPrincipal();
        String token = jwtUtils.generateToken(noteUser.getUsername());

        response.setHeader("Access-Control-Allow-Credentials", "true");
        Cookie c = new Cookie(AuthConstants.D_NOTE_COOKIE, token);
        c.setMaxAge(3600);
        c.setPath("/");
        response.addCookie(c);
        String key = AuthConstants.LOGIN_HASH_KEY + token;
        RedisUtils.HashOps hashOps = redisUtils.getHashOps();
        hashOps.putIfAbsentWithExpire(key,
                AuthConstants.LOGIN_KEY,
                JsonUtils.stringify(authentication.getAuthorities()),
                AuthConstants.AN_HOUR);
        hashOps.putIfAbsentWithExpire(key,
                AuthConstants.AUTH_DATA_KEY,
                JsonUtils.stringify(noteUser),
                AuthConstants.AN_HOUR);

        ApiResult<String> responseEntity = ApiResult.ok("登录成功！");

        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(JsonUtils.stringify(responseEntity));
    }
}
