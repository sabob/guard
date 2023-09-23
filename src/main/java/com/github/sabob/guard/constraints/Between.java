package com.github.sabob.guard.constraints;

import com.github.sabob.guard.Constraint;
import com.github.sabob.guard.GuardContext;
import com.github.sabob.guard.utils.NumberUtils;
import com.github.sabob.guard.utils.StringUtils;
import com.github.sabob.guard.utils.GuardUtils;
import com.github.sabob.guard.violation.Violation;

public class Between implements Constraint {

    protected static final String messageTemplate = GuardUtils.getProperties().getProperty("between.message");

    private Number min;

    private Number max;

    public Between(Number min, Number max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public void apply(GuardContext guardContext) {

        Object value = guardContext.getValue();

        boolean valid = isValid(value);
        if (valid) return;


        String label = StringUtils.messageLabel(guardContext);
        Violation violation = GuardUtils.toViolationWithTemplateMessage(guardContext, messageTemplate, label, min, max);
        guardContext.addViolation(violation);
    }

    @Override
    public boolean isValid(Object value) {

        if (!supported(value)) {
            return true;
        }

        Number numberValue = (Number) value;

        boolean greaterThanMax = NumberUtils.isGreaterThan(numberValue, max);

        boolean lessThanMin = NumberUtils.isLessThan(numberValue, min);

        if (greaterThanMax || lessThanMin) {
            return false;
        }
        return true;
    }

    public boolean supported(Object value) {
        if (value instanceof Number) {
            return true;
        }
        return false;
    }
}
