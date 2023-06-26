package com.github.lory24.brainfrick.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum LexerOutCodes {
    // Parenthesis errors
    MISSING_OPEN_PARENTHESIS("An extra loop close parenthesis has been found! Fix it boomer"),
    MISSING_CLOSED_PARENTHESIS("A loop seems to have not been closed properly... Cry about it or just fix it. Fast!"),

    // Success out code
    OK("Success! Yay"),
    ;

    @Getter
    private final String message;
}
