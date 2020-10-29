package top.devonte.note.config.security.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import top.devonte.note.entity.NoteUserDetail;
import top.devonte.note.service.UserService;

import java.util.Collection;

/**
 * 用户名密码登录
 *
 * @author Devonte
 * @date 2020/8/21
 */
@Component
public class NoteDefaultAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        NoteUserDetail details = (NoteUserDetail) userService.loadUserByUsername(username);
        if (details == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        if (password.equals(details.getPassword())) {
            Collection<? extends GrantedAuthority> authorities = details.getAuthorities();
            return new UsernamePasswordAuthenticationToken(details.getUser(), null, authorities);
        }
        throw new UsernameNotFoundException("用户名或密码不正确！");
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UsernamePasswordAuthenticationToken.class
                .isAssignableFrom(aClass);
    }
}
