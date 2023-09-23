package com.github.sabob.guard.constraints.parseable;

import com.github.sabob.guard.Constraint;
import com.github.sabob.guard.GuardContext;
import com.github.sabob.guard.utils.GuardUtils;
import com.github.sabob.guard.utils.StringUtils;
import com.github.sabob.guard.violation.Violation;

/**
 * Provides a constraint that is valid if a given String can be parsed to the specified target class, invalid otherwise.
 * java.util.Date and java.sql.Date are supported if a "format" is provided. A SimpleDateFormat is used to parse the string
 * to a Date.
 * <p>
 * Custom parseable can be specified through the setParseable(Parseable.class) or the fluent parseable(Parseable.class) methods.
 * Note, Parseable.class is a FunctionInterface and be used as a lambda.
 * <p>
 * Supported types are non-null values. String values (CharSequence) are applied as-is.
 * Non-string values will be converted to Strings through value.toString().
 * Null values aren't validated and isValid() will return true.
 */
public class ParseableTo implements Constraint {

    protected static final String parseableToMessageTemplate = GuardUtils.getProperties().getProperty("parseable.to.message");

    Class targetClass;

    String format;

    Parseable parseable;

    public ParseableTo(Class targetClass) {
        this.targetClass = targetClass;
    }

    public ParseableTo(Class targetClass, String format) {
        this.targetClass = targetClass;
        this.format = format;
    }

    public ParseableTo(Class targetClass, Parseable parseable) {
        this.targetClass = targetClass;
        this.parseable = parseable;
    }

    public ParseableTo(Class targetClass, String format, Parseable parseable) {
        this.targetClass = targetClass;
        this.format = format;
        this.parseable = parseable;

    }

    @Override
    public void apply(GuardContext guardContext) {

        Object value = guardContext.getValue();

        boolean valid = isValid(value);
        if (valid) return;


        String label = StringUtils.messageLabel(guardContext);
        String className = "";
        if (targetClass != null) {
            className = targetClass.getSimpleName();
        }
        Violation violation = GuardUtils.toViolationWithTemplateMessage(guardContext, parseableToMessageTemplate, label, className);
        guardContext.addViolation(violation);
    }

    public void setParseable(Parseable parseable) {
        this.parseable = parseable;
    }

    public Constraint parseable(Parseable parseable) {
        setParseable(parseable);
        return this;
    }

    @Override
    public boolean isValid(Object value) {

        if (!supported(value)) {
            return true;
        }

        boolean valid;

        String str = value.toString();

        try {

            if (parseable != null) {
                valid = parseable.parseable(str);

                // else parse as a date if format is present
            } else if ((targetClass == java.util.Date.class || targetClass == java.sql.Date.class) && StringUtils.isNotBlank(format)) {
                valid = ParseableUtils.parseableToDate(str, format);

            } else {
                // Check if targetClass is a primitive and parse
                valid = ParseableUtils.parseableToPrimitive(str, targetClass);
            }

        } catch (Exception ignore) {
            return false;
        }

        return valid;
    }

    public boolean supported(Object value) {
        if (value == null) {
            return false;
        }
        return true;
    }
}
