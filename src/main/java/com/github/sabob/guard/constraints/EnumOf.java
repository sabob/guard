package com.github.sabob.guard.constraints;

import com.github.sabob.guard.Constraint;
import com.github.sabob.guard.GuardContext;
import com.github.sabob.guard.utils.StringUtils;
import com.github.sabob.guard.utils.GuardUtils;
import com.github.sabob.guard.validators.Validators;
import com.github.sabob.guard.violation.Violation;

import java.util.EnumSet;
import java.util.StringJoiner;

/**
 * The value of the field or property must be an enum or convertable to an enum of the given type.
 *
 * Supported types are getClass().isEnum() or CharSequence (String).
 * Other data types aren't validated and isValid() will return true.
 */
public class EnumOf implements Constraint {

    protected static final String messageTemplate = GuardUtils.getProperties().getProperty("enum.of.message");

    private Class enumClass;

    public EnumOf(Class<? extends java.lang.Enum<?>> enumClass) {

        boolean isEnum = enumClass.isEnum();

        // Alternative check if enumClass is in fact an enum. This check is useful to check if a enumClass is a Subclass of a specific class
        //boolean isEnum = java.lang.Enum.class.isAssignableFrom(enumClass);
        if (!isEnum) {
            throw new IllegalArgumentException(enumClass + " is not an enum");
        }

        this.enumClass = enumClass;
    }

    @Override
    public void apply(GuardContext guardContext) {

        Object value = guardContext.getValue();

        boolean valid = isValid(value);
        if (valid) return;

        StringJoiner joiner = new StringJoiner(", ");
        EnumSet<?> enumSet = EnumSet.allOf(enumClass);
        enumSet.forEach(en -> joiner.add(en.name()));
        String validValues = joiner.toString();

        String label = StringUtils.messageLabel(guardContext);
        Violation violation = GuardUtils.toViolationWithTemplateMessage(guardContext, messageTemplate,
                label,
                guardContext.getValue(),
                enumClass.getSimpleName(),
                validValues);
        guardContext.addViolation(violation);
    }

    @Override
    public boolean isValid(Object value) {

        if (!supported(value)) {
            return true;
        }

        boolean valid;

        boolean isEnum = value.getClass().isEnum();

        if (isEnum) {
            Enum en = (Enum) value;
            valid = Validators.isValidEnumValue(en, enumClass);

        } else {
            String strValue = value.toString();
            valid = Validators.isValidEnumValue(strValue, enumClass);
        }

        return valid;
    }

    public boolean supported(Object value) {
        if (value == null) {
            return false;
        }

        boolean isEnum = value.getClass().isEnum();
        if (isEnum) {
            return true;
        }

        if (value instanceof CharSequence) {
            return true;
        }
        return false;
    }
}
