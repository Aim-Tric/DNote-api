package top.devonte.note.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NoteUserDetail implements UserDetails {

    private NoteUser user;
    private Collection<? extends GrantedAuthority> authorities;

    public NoteUserDetail(NoteUser noteUser) {
        this.user = noteUser;
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (user.getRoles() != null) {
            for (NoteRole role : user.getRoles()) {
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getName());
                authorities.add(authority);
            }
        }
        this.authorities = authorities;
    }

    public NoteUser getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
