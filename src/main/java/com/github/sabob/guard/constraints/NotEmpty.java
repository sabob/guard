package com.github.sabob.guard.constraints;

import com.github.sabob.guard.Constraint;
import com.github.sabob.guard.GuardContext;
import com.github.sabob.guard.utils.StringUtils;
import com.github.sabob.guard.utils.GuardUtils;
import com.github.sabob.guard.violation.Violation;

//The value of the value or values in collection must not be empty.
public class NotEmpty implements Constraint {

    protected static final String messageTemplate = GuardUtils.getProperties().getProperty("not.empty.message");

    public NotEmpty() {
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

        try {
            Size sizeConstraint = new Size().min(1);
            return sizeConstraint.isValid(value);

        } catch (IllegalStateException ex) {
            String newMsg = ex.getMessage();
            newMsg = newMsg.replace(Size.class.getSimpleName(), NotEmpty.class.getSimpleName());
            throw new IllegalStateException(newMsg);
        }
    }
}
