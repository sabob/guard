package com.github.sabob.guard.constraints;

import com.github.sabob.guard.Constraint;
import com.github.sabob.guard.GuardContext;
import com.github.sabob.guard.utils.NumberUtils;
import com.github.sabob.guard.utils.StringUtils;
import com.github.sabob.guard.utils.GuardUtils;
import com.github.sabob.guard.violation.Violation;

/**
 * Positive validator apply to numeric values and validate that they
 * are strictly positive values.
 *
 * Supported types are Number.class
 * Other data types aren't validated and isValid() will return true.
 */
public class Positive implements Constraint {

    protected static final String messageTemplate = GuardUtils.getProperties().getProperty("positive.message");

    @Override
    public void apply(GuardContext guardContext) {

        Object value = guardContext.getValue();

        boolean valid = isValid(value);
        if (valid) return;

        String label = StringUtils.messageLabel(guardContext);
        Violation violation = GuardUtils.toViolationWithTemplateMessage(guardContext, messageTemplate, label);
        guardContext.addViolation(violation);
    }

    @Override
    public boolean isValid(Object value) {

        if (!supported(value)) {
            return true;
        }

        Number numberValue = (Number) value;

        boolean valid = NumberUtils.isPositive(numberValue);
        return valid;
    }

    public boolean supported(Object value) {
        if (value instanceof Number) {
            return true;
        }
        return false;
    }
}
