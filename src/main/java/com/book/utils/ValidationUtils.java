package com.book.utils;

import java.util.regex.Pattern;

public class ValidationUtils {
    // 正则表达式：只允许字母、数字和下划线
    private static final String REGEX = "^\\w+$";

    public static boolean isValid(String input) {
        return Pattern.matches(REGEX, input);
    }
}