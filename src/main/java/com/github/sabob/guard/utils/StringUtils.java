package com.github.sabob.guard.utils;

public class StringUtils {

    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isNotEmpty(final CharSequence cs) {
        return !isEmpty(cs);
    }

    public static boolean isBlank(final String str) {
        if (str == null) {
            return true;
        }

        if (str.trim().isEmpty()) {
            return true;
        }
        return false;

    }

    public static boolean isNotBlank(final String str) {
        return !isBlank(str);
    }

    public static String capitalize(String value) {
        if (value == null) {
            return null;
        }

        if (isBlank(value)) {
            return value;
        }

        value = value.substring(0, 1).toUpperCase() + value.substring(1);
        return value;
    }
}
