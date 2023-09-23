package com.github.sabob.guard.constraints;

import com.github.sabob.guard.Constraint;
import com.github.sabob.guard.GuardContext;
import com.github.sabob.guard.utils.StringUtils;
import com.github.sabob.guard.utils.GuardUtils;
import com.github.sabob.guard.violation.Violation;

/**
 * The value of the field or property must match the regular expression defined in the regexp element.
 *
 * Supported types are any CharSequence (String).
 * Other data types aren't validated and isValid() will return true.
 */
public class Pattern implements Constraint {

    protected static final String messageTemplate = GuardUtils.getProperties().getProperty("pattern.message");

    public java.util.regex.Pattern pattern = null;

    private String regularExpression = null;

    public Pattern(String regularExpression) {
        this.regularExpression = regularExpression;
        pattern = java.util.regex.Pattern.compile(regularExpression);
    }

    @Override
    public void apply(GuardContext guardContext) {

        Object value = guardContext.getValue();

        boolean valid = isValid(value);
        if (valid) return;

        String label = StringUtils.messageLabel(guardContext);
        Violation violation = GuardUtils.toViolationWithTemplateMessage(guardContext, messageTemplate, label, regularExpression);
        guardContext.addViolation(violation);
    }

    @Override
    public boolean isValid(Object value) {

        if (!supported(value)) {
            return true;
        }

        String strValue = value.toString();

        java.util.regex.Matcher m = pattern.matcher(strValue);

        if (m.matches()) {
            return true;
        }
        return false;
    }

    public boolean supported(Object value) {
        if (value instanceof CharSequence) {
            return true;
        }
        return false;
    }
}
