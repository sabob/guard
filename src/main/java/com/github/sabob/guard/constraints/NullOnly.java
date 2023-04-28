package com.github.sabob.guard.constraints;

import com.github.sabob.guard.Constraint;
import com.github.sabob.guard.GuardContext;
import com.github.sabob.guard.utils.StringUtils;
import com.github.sabob.guard.utils.GuardUtils;
import com.github.sabob.guard.violation.Violation;

//null.message=%s must be null!
public class NullOnly implements Constraint {

    protected static final String messageTemplate = GuardUtils.getProperties().getProperty("null.message");

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
        return value == null;
    }
}

