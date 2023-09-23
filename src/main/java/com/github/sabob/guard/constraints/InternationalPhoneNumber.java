package com.github.sabob.guard.constraints;

import com.github.sabob.guard.Constraint;
import com.github.sabob.guard.GuardContext;
import com.github.sabob.guard.utils.StringUtils;
import com.github.sabob.guard.utils.GuardUtils;
import com.github.sabob.guard.validators.Validators;
import com.github.sabob.guard.violation.Violation;

/**
 * Validates that a String is a valid international phone number.
 *
 * Supported types are any CharSequence (String).
 * Other data types aren't validated and isValid() will return true.
 */
public class InternationalPhoneNumber implements Constraint {

    protected static final String messageTemplate = GuardUtils.getProperties()
            .getProperty("international.phone.number.message");

    public InternationalPhoneNumber() {
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

        if (value == null) {
            return true;
        }

        String strValue = value.toString();

        boolean valid = Validators.isPhoneNumber(strValue);
        return valid;
    }

    public boolean supported(Object value) {
        if (value instanceof CharSequence) {
            return true;
        }
        return false;
    }
}

