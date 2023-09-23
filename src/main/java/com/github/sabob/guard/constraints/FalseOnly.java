package com.github.sabob.guard.constraints;

import com.github.sabob.guard.Constraint;
import com.github.sabob.guard.GuardContext;
import com.github.sabob.guard.utils.StringUtils;
import com.github.sabob.guard.utils.GuardUtils;
import com.github.sabob.guard.violation.Violation;

/**
 * Assert whether the value is False.
 * Null is not allowed.
 */

public class FalseOnly implements Constraint {

    protected static final String messageTemplate = GuardUtils.getProperties().getProperty("false.message");

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

        boolean bool = (Boolean) value;

        if (bool) {
            return false;

        } else {
            return true;
        }
    }

    public boolean supported(Object value) {
        if (value instanceof Boolean) {
            return true;
        }
        return false;
    }
}
