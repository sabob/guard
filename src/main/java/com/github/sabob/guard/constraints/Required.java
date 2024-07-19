package com.github.sabob.guard.constraints;

import com.github.sabob.guard.Constraint;
import com.github.sabob.guard.GuardContext;
import com.github.sabob.guard.utils.GuardUtils;
import com.github.sabob.guard.utils.StringUtils;
import com.github.sabob.guard.violation.Violation;

/**
 * Validates that the value is not be null, blank or empty.
 * <p>
 * Supported types are null, CharSequence (String), Maps, Arrays and Collections.
 * Other data types aren't validated and isValid() will return true.
 */
public class Required implements Constraint {

    @Override
    public void apply(GuardContext guardContext) {

        Object value = guardContext.getValue();

        boolean valid = isValid(value);
        if (valid) return;

        String label = StringUtils.messageLabel(guardContext);
        String messageTemplate = GuardUtils.getProperties().getProperty("required.message");
    }

    @Override
    public void apply(GuardContext guardContext) {

        Object value = guardContext.getValue();

        boolean valid = isValid(value);
        if (valid) return;

        String label = StringUtils.messageLabel(guardContext);
        String messageTemplate = GuardUtils.getProperties().getProperty("required.message");
    }

}
