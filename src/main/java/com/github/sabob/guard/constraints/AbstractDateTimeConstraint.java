package com.github.sabob.guard.constraints;

import com.github.sabob.guard.Constraint;
import com.github.sabob.guard.GuardContext;
import com.github.sabob.guard.utils.DateUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Optional;

/**
 * Abstract validator for date and time.
 *
 * Supported types are java.util.Date, java.sql.Date, Calendar, LocalDate and LocalDateTime. Other data types aren't
 * validated and isValid() will return true.
 */
public abstract class AbstractDateTimeConstraint implements Constraint {

    protected boolean includeTime = false;

    @Override
    public void apply(GuardContext guardContext) {

        Object value = guardContext.getValue();

        boolean valid = isValid(value);
        if (valid) return;

        addViolation(guardContext);
    }

    /**
     * Sets whether the constraint should compare the time as well, false by default.
     *
     * @param includeTime true if the time must be compared as well
     * @return Past or Past including the Present to enable fluent api
     */
    public AbstractDateTimeConstraint includeTime(boolean includeTime) {
        this.includeTime = includeTime;
        return this;
    }

    @Override
    public boolean isValid(Object value) {

        if (!supported(value)) {
            return true;
        }

        Optional<LocalDateTime> optional = DateUtils.toLocalDateTime(value);

        if (!optional.isPresent()) {
            // Skip the validation because only dates eg Date, Calendar, LocalDateTime etc are supported
            return true;
        }

        LocalDateTime localDateTime = optional.get();

        boolean valid;

        if (includeTime) {
            valid = isValidDateTime(localDateTime);

        } else {
            valid = isValidDate(localDateTime);
        }

        return valid;
    }

    public boolean supported(Object value) {

        if (value instanceof java.util.Date
                || value instanceof java.sql.Date
                || value instanceof Calendar
                || value instanceof LocalDate
                || value instanceof LocalDateTime) {
            return true;
        }

        return false;

    }

    protected abstract boolean isValidDateTime(LocalDateTime localDateTime);

    protected abstract boolean isValidDate(LocalDateTime localDateTime);

    protected abstract void addViolation(GuardContext guardContext);
}
