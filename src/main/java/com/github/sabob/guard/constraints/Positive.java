package com.github.sabob.guard.constraints;

import com.github.sabob.guard.Constraint;
import com.github.sabob.guard.GuardContext;
import com.github.sabob.guard.utils.NumberUtils;
import com.github.sabob.guard.utils.StringUtils;
import com.github.sabob.guard.utils.GuardUtils;
import com.github.sabob.guard.violation.Violation;

/**
 * Positive validator apply to numeric values and validate that they
 * are strictly positive values
 */
public class Positive implements Constraint {

    protected static final String messageTemplate = GuardUtils.getProperties().getProperty("positive.message");

    @Override
    public void apply(GuardContext guardContext) {

        Object value = guardContext.getValue();

        boolean valid = isValid(value);

        if (!valid) {
            String name = StringUtils.capitalize(guardContext.getName());
            Violation violation = GuardUtils.toViolationWithTemplateMessage(guardContext, messageTemplate, name);
            guardContext.addViolation(violation);
        }
    }

    @Override
    public boolean isValid(Object value) {

        if (value == null) {
            return true;
        }

        if (!(value instanceof Number)) {
            throw new IllegalStateException(value.getClass() + " is not a valid type for the " + this.getClass()
                    .getSimpleName() +
                    " constraint. " + this.getClass().getSimpleName() + " can only be applied to numbers.");

        }

        Number numberValue = (Number) value;

        boolean valid = NumberUtils.isPositive(numberValue);
        return valid;
    }
}
