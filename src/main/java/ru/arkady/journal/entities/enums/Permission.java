package ru.arkady.journal.entities.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {
    READ("read"),
    WRITE("write");

    @Getter
    private final String permission;
}
