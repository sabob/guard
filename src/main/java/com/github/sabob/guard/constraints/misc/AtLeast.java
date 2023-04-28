package com.github.sabob.guard.constraints.misc;

import com.github.sabob.guard.Constraint;
import com.github.sabob.guard.GuardContext;
import com.github.sabob.guard.utils.GuardUtils;
import com.github.sabob.guard.violation.Violation;

import java.util.ArrayList;
import java.util.List;

public class AtLeast implements Constraint {

    private int atLeast;

    private String message;

    private List values = new ArrayList();

    private Constraint constraint;

    public AtLeast(int atLeast, String message) {
        this(atLeast, null, message);
    }

    public AtLeast(int atLeast, Constraint constraint, String message) {
        this.atLeast = atLeast;
        this.message = message;
        this.constraint = constraint;
    }

    public AtLeast constraint(Constraint constraint) {
        this.constraint = constraint;
        return this;
    }

    public AtLeast values(Object... values) {
        this.values = GuardUtils.unwrapVarArgsToList(values);
        return this;
    }

    public void values(List values) {
        this.values = values;
    }

    @Override
    public void apply(GuardContext guardContext) {

        boolean valid = isValid(values);

        if (!valid) {
            Violation violation = guardContext.toViolation();
            violation.setMessage(message);
            guardContext.getViolations().add(violation);
        }
    }

    @Override
    public boolean isValid(Object value) {

        if (constraint == null) {
            String simpleName = AtLeast.class.getSimpleName();
            throw new IllegalStateException("The " + simpleName + " constraint is not set. Usage: " +
                    simpleName + ".constraint( new NotBlank() ); ");
        }

        List values = GuardUtils.toList(value);

        int validCount = 0;

        for (Object item : values) {

            boolean valid = isValidItem(item);

            if (valid) {
                validCount++;

            } else {
                if (atLeast == 0) {
                    return false;
                }
            }
        }

        boolean atLeastValid = isAtLeastValid(validCount);
        return atLeastValid;
    }

    protected boolean isValidItem(Object value) {
        boolean valid = constraint.isValid(value);
        return valid;
    }

    protected boolean isAtLeastValid(int validCount) {

        if (validCount >= atLeast) {
            return true;
        }

        return false;
    }
}
