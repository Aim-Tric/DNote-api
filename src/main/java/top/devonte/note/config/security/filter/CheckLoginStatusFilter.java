package top.devonte.note.config.security.filter;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import top.devonte.note.common.AuthConstants;
import top.devonte.note.entity.NoteUser;
import top.devonte.note.util.JsonUtils;
import top.devonte.note.util.RedisUtils;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class CheckLoginStatusFilter extends OncePerRequestFilter {

    @Resource
    private RedisUtils redisUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (AuthConstants.D_NOTE_COOKIE.equalsIgnoreCase(c.getName())) {

                    String value = c.getValue();
                    String key = AuthConstants.LOGIN_HASH_KEY + value;
                    RedisUtils.HashOps hashOps = redisUtils.getHashOps();
                    String authenticationString = (String) hashOps.get(key,
                            AuthConstants.LOGIN_KEY);
                    String user = (String) hashOps.get(key,
                            AuthConstants.AUTH_DATA_KEY);

                    if (authenticationString != null && user != null) {
                        List<GrantedAuthority> authorities = JsonUtils.parseList(authenticationString,
                                GrantedAuthority.class);

                        NoteUser noteUser = JsonUtils.parse(user, NoteUser.class);

                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                noteUser,
                                null,
                                authorities);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        break;
                    }
                }
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
