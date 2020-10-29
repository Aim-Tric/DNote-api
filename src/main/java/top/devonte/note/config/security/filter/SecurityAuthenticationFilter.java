package top.devonte.note.config.security.filter;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Devonte
 * @date 2020/8/22
 */
public class SecurityAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public SecurityAuthenticationFilter() {
        super(new AntPathRequestMatcher("/auth/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse httpServletResponse)
            throws AuthenticationException, IOException, ServletException {
        if (!"POST".equalsIgnoreCase(request.getMethod())) {
            throw new AuthenticationServiceException("错误校验方式: " + request.getMethod());
        } else {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            if (username == null) {
                username = "";
            }

            if (password == null) {
                password = "";
            }

            username = username.trim();
            UsernamePasswordAuthenticationToken authRequest
                    = new UsernamePasswordAuthenticationToken(username, password);
            this.setDetails(request, authRequest);
            return getAuthenticationManager().authenticate(authRequest);
        }
    }

    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }
}

