package ru.arkady.journal.entities.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;
@RequiredArgsConstructor
public enum Role {

    TEACHER(Set.of(Permission.READ, Permission.WRITE));

    @Getter
    private final Set<Permission> permissionSet;

    public Set<SimpleGrantedAuthority> getAuthorities(){
        return getPermissionSet().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
