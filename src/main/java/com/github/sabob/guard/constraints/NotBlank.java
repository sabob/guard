package com.github.sabob.guard.constraints;

import com.github.sabob.guard.Constraint;
import com.github.sabob.guard.GuardContext;
import com.github.sabob.guard.utils.StringUtils;
import com.github.sabob.guard.utils.GuardUtils;
import com.github.sabob.guard.validators.Validators;
import com.github.sabob.guard.violation.Violation;

//The value of the value or values inside collection must not be empty or contain only whitespace characters.
public class NotBlank implements Constraint {

    protected static final String messageTemplate = GuardUtils.getProperties().getProperty("not.blank.message");

    public NotBlank() {
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

        if (value instanceof String) {
            return Validators.isNotBlank(value.toString());
        }

        throw new IllegalStateException(value.getClass() + " is not a valid type for the " + getClass().getSimpleName() + " constraint. " +
                this.getClass().getSimpleName() + " can only be applied to Strings.");
    }
}

