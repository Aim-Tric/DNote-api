package top.devonte.note.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import top.devonte.note.config.security.filter.CheckLoginStatusFilter;
import top.devonte.note.config.security.filter.SecurityAuthenticationFilter;
import top.devonte.note.config.security.handler.NoteAccessDenyHandler;
import top.devonte.note.config.security.handler.NoteAuthenticationEntryPoint;
import top.devonte.note.config.security.handler.NoteAuthenticationFailureHandler;
import top.devonte.note.config.security.handler.NoteAuthenticationSuccessHandler;
import top.devonte.note.config.security.provider.NoteDefaultAuthenticationProvider;

import javax.annotation.Resource;

/**
 * 登录配置类
 *
 * @author Devonte
 * @date 2020/8/21
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Resource
    private NoteDefaultAuthenticationProvider defaultAuthenticationProvider;
    @Resource
    private NoteAuthenticationEntryPoint entryPoint;
    @Resource
    private CheckLoginStatusFilter checkLoginStatusFilter;
    @Resource
    private NoteAuthenticationSuccessHandler successHandler;
    @Resource
    private NoteAuthenticationFailureHandler failureHandler;
    @Resource
    private NoteAccessDenyHandler denyHandler;

    @Bean
    public SecurityAuthenticationFilter noteSecurityAuthenticationFilter() throws Exception {
        SecurityAuthenticationFilter noteSecurityAuthenticationFilter = new SecurityAuthenticationFilter();

        noteSecurityAuthenticationFilter.setAuthenticationManager(authenticationManager());
        noteSecurityAuthenticationFilter.setAuthenticationSuccessHandler(successHandler);
        noteSecurityAuthenticationFilter.setAuthenticationFailureHandler(failureHandler);

        return noteSecurityAuthenticationFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(defaultAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling()
                .accessDeniedHandler(denyHandler)
                .authenticationEntryPoint(entryPoint);

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/auth/login", "/auth/logout", "/auth/register")
                .permitAll()
                .anyRequest()
                .authenticated();

        http.addFilterBefore(noteSecurityAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(checkLoginStatusFilter, SecurityAuthenticationFilter.class);

        http.headers().frameOptions().disable();

        http.csrf().disable().cors();
    }

}
