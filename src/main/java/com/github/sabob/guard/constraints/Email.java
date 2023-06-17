package com.github.sabob.guard.constraints;

import com.github.sabob.guard.Constraint;
import com.github.sabob.guard.GuardContext;
import com.github.sabob.guard.utils.StringUtils;
import com.github.sabob.guard.utils.GuardUtils;
import com.github.sabob.guard.validators.Validators;
import com.github.sabob.guard.violation.Violation;

public class Email implements Constraint {

    protected static final String messageTemplate = GuardUtils.getProperties().getProperty("email.message");

    public Email() {
    }

    @Override
    public void apply(GuardContext guardContext) {

        Object value = guardContext.getValue();

        boolean valid = isValid(value);
        if (valid) return;

        String name = StringUtils.capitalize(guardContext.getName());
        Violation violation = GuardUtils.toViolationWithTemplateMessage(guardContext, messageTemplate, name);
        guardContext.addViolation(violation);
    }

    @Override
    public boolean isValid(Object value) {

        if (value == null) {
            return true;
        }

        String strValue = GuardUtils.ensureValueIsString(Email.class.getSimpleName(), value);

        boolean valid = Validators.isEmail(strValue);
        return valid;
    }
}

