package ru.arkady.journal.security;

import org.springframework.security.core.context.SecurityContextHolder;

public final class UserInfo {
    public static String getUsername(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
