package top.devonte.note.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import top.devonte.note.entity.NoteUser;
import top.devonte.note.entity.NoteUserDetail;
import top.devonte.note.mapper.UserMapper;
import top.devonte.note.service.UserService;

import javax.annotation.Resource;

/**
 * Service层
 * 用户相关业务实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) {
        NoteUser noteUser = userMapper.selectByUsername(username);
        if (noteUser != null) {
            return new NoteUserDetail(noteUser);
        }
        return null;
    }

    @Override
    public int register(NoteUser noteUser) {
        if(loadUserByUsername(noteUser.getUsername()) != null) {
            throw new RuntimeException("注册失败！用户名已存在！");
        }
        return userMapper.insert(noteUser);
    }

    @Override
    public void updateInfo(NoteUser noteUser) {
        userMapper.update(noteUser);
    }
}
