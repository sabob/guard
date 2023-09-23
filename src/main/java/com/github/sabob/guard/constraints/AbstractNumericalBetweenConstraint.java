package com.github.sabob.guard.constraints;

import com.github.sabob.guard.Constraint;
import com.github.sabob.guard.GuardContext;
import com.github.sabob.guard.utils.StringUtils;
import com.github.sabob.guard.utils.GuardUtils;
import com.github.sabob.guard.violation.Violation;

/**
 * Abstract validator that can be extended for constraints that applies a numerical max/min/between constraint.
 */
public abstract class AbstractNumericalBetweenConstraint implements Constraint {

    protected Integer max;

    protected Integer min;

    public AbstractNumericalBetweenConstraint() {
    }

    public AbstractNumericalBetweenConstraint(int min, int max) {
        this.max = max;
        this.min = min;
        initialize();
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public void initialize() {

        if (max != null && max < 0) {
            throw new IllegalStateException("the Max length of the " + getPartName() + " part cannot be less than 0");
        }
        if (min != null && min < 0) {
            throw new IllegalArgumentException("the Min length of the " + getPartName() + " part cannot be less than 0.");
        }
    }

    @Override
    public void apply(GuardContext guardContext) {

        Object value = guardContext.getValue();

        boolean valid = isValid(value);

        if (valid) {
            return;
        }

        int length = getLength(value);

        if (isBetweenValidation()) {
            addBetweenViolation(guardContext, length);
            return;
        }

        if (isMaxValidation()) {
            addMaxValidator(guardContext, length);
        }

        if (isMinValidation()) {
            addMinValidator(guardContext, length);
        }
    }

    @Override
    public boolean isValid(Object value) {

        if (!supported(value)) {
            return true;
        }

        int length = getLength(value);

        if (isMaxValidation()) {
            return !isGreaterThanMax(length);
        }

        if (isMinValidation()) {
            return !isLessThanMin(length);
        }

        if (isBetweenValidation()) {
            return isBetween(length);
        }

        return true;
    }

    public boolean supported(Object value) {
        if (value == null) {
            return false;
        }
        return true;
    }

    protected boolean isBetween(int length) {

        if (length > max || length < min) {
            return false;
        }

        return true;
    }

    protected boolean isGreaterThanMax(int length) {
        if (length > max) {
            return true;
        }
        return false;
    }

    protected boolean isLessThanMin(int length) {

        if (length < min) {
            return true;
        }
        return false;
    }

    protected boolean isMinValidation() {
        boolean isMinValidation = max == null && min != null;
        return isMinValidation;
    }

    protected boolean isMaxValidation() {
        boolean isMaxValidation = min == null && max != null;
        return isMaxValidation;
    }

    protected boolean isBetweenValidation() {
        boolean isBetweenValidation = max != null && min != null;
        return isBetweenValidation;
    }

    protected void addBetweenViolation(GuardContext guardContext, int length) {

        String label = StringUtils.messageLabel(guardContext);
        String template = getBetweenTemplate();
        Violation violation = GuardUtils.toViolationWithTemplateMessage(guardContext, template, label, length, min, max);
        guardContext.addViolation(violation);

    }

    protected void addMinValidator(GuardContext guardContext, int length) {

        String label = StringUtils.messageLabel(guardContext);
        String template = getMinTemplate();
        Violation violation = GuardUtils.toViolationWithTemplateMessage(guardContext, template, label, length, min);
        guardContext.addViolation(violation);
    }

    protected void addMaxValidator(GuardContext guardContext, int length) {

        String label = StringUtils.messageLabel(guardContext);
        String template = getMaxTemplate();
        Violation violation = GuardUtils.toViolationWithTemplateMessage(guardContext, template, label, length, max);
        guardContext.addViolation(violation);
    }

    protected abstract String getMaxTemplate();

    protected abstract String getMinTemplate();

    protected abstract String getBetweenTemplate();

    protected abstract int getLength(Object value);

    protected abstract String getPartName();
}
