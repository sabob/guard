package com.github.sabob.guard.constraints.parseable;

import com.github.sabob.guard.utils.NumberUtils;
import com.github.sabob.guard.utils.StringUtils;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Supports all primitive and their wrapper types as well as Date and Number.
 * <p>
 * Exceptions thrown from a parser must be handled by the caller.
 * <p>
 * The handle Date you must provide the format of the date.
 */
public class ParseableUtils {

    protected static Map<Class<?>, Function<String, Object>> parsers = new HashMap<>();

    static {
        parsers.put(Boolean.class, s -> s == null ? null : Boolean.parseBoolean(s));
        parsers.put(Boolean.TYPE, s -> Boolean.parseBoolean(s));
        parsers.put(Byte.class, s -> s == null ? null : Byte.parseByte(s));
        parsers.put(Byte.TYPE, s -> Byte.parseByte(s));
        parsers.put(Short.class, s -> s == null ? null : Short.parseShort(s));
        parsers.put(Short.TYPE, s -> Short.parseShort(s));
        parsers.put(Integer.class, s -> s == null ? null : Integer.parseInt(s));
        parsers.put(Integer.TYPE, s -> Integer.parseInt(s));
        parsers.put(Long.class, s -> s == null ? null : Long.parseLong(s));
        parsers.put(Long.TYPE, s -> Long.parseLong(s));
        parsers.put(Float.class, s -> s == null ? null : Float.parseFloat(s));
        parsers.put(Float.TYPE, s -> Float.parseFloat(s));
        parsers.put(Double.class, s -> s == null ? null : Double.parseDouble(s));
        parsers.put(Double.TYPE, s -> Double.parseDouble(s));
        parsers.put(String.class, s -> s);
    }

    /**
     * Null or empty objects aren't parseable. Exceptions thrown by parsers must be dealt with by the caller.
     *
     * @param obj the object to parse from a string to a primitive value.
     * @return true if the obj is parseable, false otherwise
     */
    public static boolean parseableToPrimitive(Object obj, Class targetClass) {

        if (obj == null) {
            return false;
        }

        String str = obj.toString();
        if (StringUtils.isBlank(obj.toString())) {
            return false;
        }

        Function<String, Object> fun = parsers.get(targetClass);

        if (fun != null) {
            Object ignore = fun.apply(str);
            return true;
        }

        if (targetClass == Number.class) {
            return NumberUtils.isCreatable(str);
        }

        return false;
    }

    /**
     * Uses SimpleDateFormat to parse a string to a date, using the given format.
     * <p>
     * Null objects aren't parseable to dates.
     *
     * @param obj the object to parse from a string to a Date.
     * @return true if the obj is parseable to a Date, false otherwise
     */
    public static boolean parseableToDate(Object obj, String format) {

        if (StringUtils.isBlank(format)) {
            throw new IllegalArgumentException("format cannot be empty");
        }

        if (obj == null) {
            return false;
        }

        String str = obj.toString();


        try {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            formatter.parse(str);
            return true;
        } catch (Exception ignore) {
        }
        return false;
    }
}
