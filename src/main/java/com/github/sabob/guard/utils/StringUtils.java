package com.github.sabob.guard.utils;

import java.util.Iterator;
import java.util.List;

public class StringUtils {

    public static final String joinList(List<Object> values, String firstSeparator, String lastSeparator) {
        if (values.isEmpty()) {
            return "";

        } else if (values.size() == 1) {
            return String.valueOf(values.get(0));

        } else {
            firstSeparator = firstSeparator == null ? "" : firstSeparator;
            lastSeparator = lastSeparator == null ? firstSeparator : lastSeparator;

            final StringBuilder builder = new StringBuilder();

            final Iterator iterator = values.iterator();
            while (iterator.hasNext()) {
                final Object next = iterator.next();
                if (builder.length() > 0) {
                    if (iterator.hasNext()) {
                        builder.append(firstSeparator);
                    } else {
                        builder.append(lastSeparator);
                    }
                }
                builder.append(next);
            }

            return builder.toString();

        }
    }

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

    public static boolean equals(final Object first, final Object second) {

        if (first == null && second == null) {
            return true;
        }
        if (first == null || second == null) {
            return false;
        }
        return first.equals(second);
    }
}
