package com.github.sabob.guard.utils;

import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

public class DateUtils {

    public static LocalDate toLocalDate(LocalDateTime date) {
        if (date == null) {
            throw new IllegalArgumentException("supported types are Date, Calendar, LocalDate and LocalDateTime");
        }
        return date.toLocalDate();
    }

    public static Optional<LocalDateTime> toLocalDateTime(Object value) {

        if (value instanceof LocalDateTime) {
            LocalDateTime localDateTime = (LocalDateTime) value;
            return Optional.of(localDateTime);
        }

        if (value instanceof Date) {
            Date date = (Date) value;
            Instant instant = date.toInstant();
            ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
            LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();
            return Optional.of(localDateTime);
        }

        if (value instanceof java.sql.Date) {
            java.sql.Date date = (java.sql.Date) value;
            Instant instant = date.toInstant();
            ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
            LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();
            return Optional.of(localDateTime);
        }

        if (value instanceof Calendar) {
            Calendar calendar = ((Calendar) value);
            Instant instant = calendar.toInstant();
            ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
            LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();
            return Optional.of(localDateTime);

        }

        if (value instanceof LocalDate) {
            LocalDate localDate = (LocalDate) value;
            LocalDateTime localDateTime = localDate.atTime(0, 0, 0, 0);
            return Optional.of(localDateTime);
        }

        return Optional.empty();
    }

    public static Optional<Date> parse(String str, SimpleDateFormat formatter) {

        try {
            Date date = formatter.parse(str);
            return Optional.of(date);

        } catch (Exception ex) {
            return Optional.empty();
        }
    }
}
