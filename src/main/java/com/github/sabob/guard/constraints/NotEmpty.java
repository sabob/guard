package com.github.sabob.guard.constraints;

import com.github.sabob.guard.Constraint;
import com.github.sabob.guard.GuardContext;
import com.github.sabob.guard.utils.StringUtils;
import com.github.sabob.guard.utils.GuardUtils;
import com.github.sabob.guard.violation.Violation;

/**
 * Validates that the value or values in collection must not be empty.
 *
 * Supported types are CharSequence (String), Maps, Arrays and Collections.
 * Other data types aren't validated and isValid() will return true.
 */
public class NotEmpty implements Constraint {

    protected static final String messageTemplate = GuardUtils.getProperties().getProperty("not.empty.message");

    public NotEmpty() {
    }

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

        try {
            Size sizeConstraint = new Size().min(1);
            return sizeConstraint.isValid(value);

        } catch (IllegalStateException ex) {
            String newMsg = ex.getMessage();
            newMsg = newMsg.replace(Size.class.getSimpleName(), NotEmpty.class.getSimpleName());
            throw new IllegalStateException(newMsg);
        }
    }

    public boolean supported(Object value) {
        return new Size().supported(value);
    }
}
