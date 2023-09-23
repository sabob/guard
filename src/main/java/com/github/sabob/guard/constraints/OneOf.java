package com.github.sabob.guard.constraints;

import com.github.sabob.guard.Constraint;
import com.github.sabob.guard.GuardContext;
import com.github.sabob.guard.utils.GuardUtils;
import com.github.sabob.guard.utils.StringUtils;
import com.github.sabob.guard.violation.Violation;
import java.util.List;

/**
 * The value of the field or property must match one of the given constraints.
 */
public class OneOf implements Constraint {

    protected static final String messageTemplate = GuardUtils.getProperties().getProperty("one.of.message");

    private List values;

    public OneOf(Object... values) {
        this.values = GuardUtils.unwrapVarArgsToList(values);
    }

    @Override
    public void apply(GuardContext guardContext) {

        Object value = guardContext.getValue();

        boolean valid = isValid(value);
        if (valid) {
            return;
        }

        String label = StringUtils.messageLabel(guardContext);
        String valuesStr = StringUtils.joinList(values, ", ", " or ");
        valuesStr = "[" + valuesStr + "]";
        Violation violation = GuardUtils.toViolationWithTemplateMessage(guardContext, messageTemplate, label, valuesStr);
        guardContext.addViolation(violation);
    }

    @Override
    public boolean isValid(Object value) {

        if (!supported(value)) {
            return true;
        }

        for (Object obj : values) {
            if (StringUtils.equals(obj, value)) {
                return true;
            }
        }

        return false;
    }

    public boolean supported(Object value) {
        if (value == null) {
            return false;
        }
        return true;
    }
}
