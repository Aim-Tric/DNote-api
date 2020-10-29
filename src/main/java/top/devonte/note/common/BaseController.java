package top.devonte.note.common;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import top.devonte.note.entity.NoteUser;

public abstract class BaseController {

    public NoteUser getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (NoteUser) authentication.getPrincipal();
    }

}
