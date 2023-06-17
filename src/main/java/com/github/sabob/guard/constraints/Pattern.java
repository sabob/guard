package com.github.sabob.guard.constraints;

import com.github.sabob.guard.Constraint;
import com.github.sabob.guard.GuardContext;
import com.github.sabob.guard.utils.StringUtils;
import com.github.sabob.guard.utils.GuardUtils;
import com.github.sabob.guard.violation.Violation;

//The value of the field or property must match the regular expression defined in the regexp element.
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

        String name = StringUtils.capitalize(guardContext.getName());
        Violation violation = GuardUtils.toViolationWithTemplateMessage(guardContext, messageTemplate, name, regularExpression);
        guardContext.addViolation(violation);
    }

    @Override
    public boolean isValid(Object value) {

        if (value == null) {
            return true;
        }

        String strValue = GuardUtils.ensureValueIsString(Pattern.class.getSimpleName(), value);

        java.util.regex.Matcher m = pattern.matcher(strValue);

        if (m.matches()) {
            return true;
        }
        return false;
    }
}
