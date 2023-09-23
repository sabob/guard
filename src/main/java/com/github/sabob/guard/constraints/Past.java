package com.github.sabob.guard.constraints;

import com.github.sabob.guard.GuardContext;
import com.github.sabob.guard.utils.DateUtils;
import com.github.sabob.guard.utils.StringUtils;
import com.github.sabob.guard.utils.GuardUtils;
import com.github.sabob.guard.violation.Violation;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Validate that the date value is in the past.
 * Supported types are java.util.Date, java.sql.Date, Calendar, LocalDate and LocalDateTime.
 * Other data types aren't validated and isValid() will return true.
 */
public class Past extends AbstractDateTimeConstraint {

    protected static final String messageTemplate = GuardUtils.getProperties().getProperty("past.message");

    @Override
    protected boolean isValidDateTime(LocalDateTime localDateTime) {
        boolean valid = localDateTime.isBefore(LocalDateTime.now());
        return valid;
    }

    @Override
    protected boolean isValidDate(LocalDateTime localDateTime) {
        LocalDate date = DateUtils.toLocalDate(localDateTime);
        boolean valid = date.isBefore(LocalDate.now());
        return valid;
    }

    @Override
    protected void addViolation(GuardContext guardContext) {
        String label = StringUtils.messageLabel(guardContext);
        Violation violation = GuardUtils.toViolationWithTemplateMessage(guardContext, messageTemplate, label);
        guardContext.addViolation(violation);
    }
}
