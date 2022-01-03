package com.github.sabob.guard.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class DateBean {

    private Calendar calendar;

    private LocalDate localDate;

    private Date date;

    private LocalDateTime localDateTime;

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar( Calendar calendar ) {
        this.calendar = calendar;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate( LocalDate localDate ) {
        this.localDate = localDate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate( Date date ) {
        this.date = date;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime( LocalDateTime localDateTime ) {
        this.localDateTime = localDateTime;
    }
}
