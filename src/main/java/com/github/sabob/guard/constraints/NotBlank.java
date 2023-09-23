package com.github.sabob.guard.constraints;

import com.github.sabob.guard.Constraint;
import com.github.sabob.guard.GuardContext;
import com.github.sabob.guard.utils.StringUtils;
import com.github.sabob.guard.utils.GuardUtils;
import com.github.sabob.guard.validators.Validators;
import com.github.sabob.guard.violation.Violation;

/**
 ** The value must not be empty or contain only whitespace characters.
 *
 * Supported types are any CharSequence (String).
 * Other data types aren't validated and isValid() will return true.
 */
public class NotBlank implements Constraint {

    protected static final String messageTemplate = GuardUtils.getProperties().getProperty("not.blank.message");

    public NotBlank() {
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

        return Validators.isNotBlank(value.toString());
    }

    public boolean supported(Object value) {
        if (value instanceof CharSequence) {
            return true;
        }
        return false;
    }
}

