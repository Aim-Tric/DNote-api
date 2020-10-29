package top.devonte.note.service;

import org.springframework.security.core.userdetails.UserDetails;
import top.devonte.note.entity.NoteUser;

public interface UserService {

    UserDetails loadUserByUsername(String username);

    int register(NoteUser noteUser);

    void updateInfo(NoteUser noteUser);
}
