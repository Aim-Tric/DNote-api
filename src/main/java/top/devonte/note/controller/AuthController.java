package top.devonte.note.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import top.devonte.note.common.ApiResult;
import top.devonte.note.common.AuthConstants;
import top.devonte.note.entity.NoteUser;
import top.devonte.note.service.UserService;
import top.devonte.note.util.JsonUtils;
import top.devonte.note.util.RedisUtils;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@RestController
public class AuthController {

    @Resource
    private UserService userService;
    @Resource
    private RedisUtils redisUtils;

    @PostMapping("/auth/register")
    public ApiResult<String> register(@RequestBody NoteUser noteUser) {
        userService.register(noteUser);
        return ApiResult.ok("注册成功");
    }

    @GetMapping("/auth/logout")
    public ApiResult<String> logout(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (AuthConstants.D_NOTE_COOKIE.equals(cookie.getName())) {
                String token = cookie.getValue();
                RedisUtils.HashOps hashOps = redisUtils.getHashOps();
                hashOps.getOps().delete(AuthConstants.LOGIN_HASH_KEY + token, AuthConstants.LOGIN_KEY, AuthConstants.AUTH_DATA_KEY);

            }
        }
        return ApiResult.ok("用户已退出登录!");
    }

    @GetMapping("/auth/info")
    public ApiResult<NoteUser> info(HttpServletRequest request) throws JsonProcessingException {
        Cookie[] cookies = request.getCookies();
        NoteUser user = null;
        for (Cookie cookie : cookies) {
            if (AuthConstants.D_NOTE_COOKIE.equals(cookie.getName())) {
                String token = cookie.getValue();
                RedisUtils.HashOps hashOps = redisUtils.getHashOps();
                String json = (String) hashOps.getOps().get(AuthConstants.LOGIN_HASH_KEY + token, AuthConstants.AUTH_DATA_KEY);
                user = JsonUtils.parse(json, NoteUser.class);
            }
        }
        return ApiResult.ok(user);
    }

    @PostMapping("/auth/update")
    public ApiResult<String> updateUserInfo(@RequestBody NoteUser user) {
        userService.updateInfo(user);
        return ApiResult.ok("修改完成!");
    }
}
