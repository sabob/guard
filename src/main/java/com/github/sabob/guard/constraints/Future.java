package com.github.sabob.guard.constraints;

import com.github.sabob.guard.GuardContext;
import com.github.sabob.guard.utils.DateUtils;
import com.github.sabob.guard.utils.StringUtils;
import com.github.sabob.guard.utils.GuardUtils;
import com.github.sabob.guard.violation.Violation;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Validate that the date value is in the future.
 *  * Supported types are java.util.Date, java.sql.Date, Calendar, LocalDate and LocalDateTime. Other data types aren't validated and isValid() will return true.
 */
public class Future extends AbstractDateTimeConstraint {

    protected static final String messageTemplate = GuardUtils.getProperties().getProperty("future.message");

    @Override
    protected boolean isValidDateTime(LocalDateTime localDateTime) {
        boolean validFuture = localDateTime.isAfter(LocalDateTime.now());
        return validFuture;
    }

    @Override
    protected boolean isValidDate(LocalDateTime localDateTime) {
        LocalDate localDate = DateUtils.toLocalDate(localDateTime);
        boolean validFuture = localDate.isAfter(LocalDate.now());
        return validFuture;
    }

    @Override
    protected void addViolation(GuardContext guardContext) {
        String label = StringUtils.messageLabel(guardContext);
        Violation violation = GuardUtils.toViolationWithTemplateMessage(guardContext, messageTemplate, label);
        guardContext.addViolation(violation);
    }
}
