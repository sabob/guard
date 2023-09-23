package com.github.sabob.guard.constraints;

import com.github.sabob.guard.utils.NumberUtils;
import com.github.sabob.guard.utils.GuardUtils;

import java.math.BigDecimal;

/**
 * A digital validator that applies to the integral values and validate that the digit element of the integral is
 * lower than or equal to the digit specify in the parameter.
 *
 * Supported types are Number.class
 * Other data types aren't validated and isValid() will return true.
 */
public class IntegralLength extends AbstractNumericalBetweenConstraint {

    public IntegralLength(int min, int max) {
        super(min, max);
    }

    IntegralLength() {
    }

    public IntegralLength max(int max) {
        this.max = max;
        return this;
    }

    public IntegralLength min(int min) {
        this.min = min;
        return this;
    }

    public void initialize() {

        if (max != null && max < 0) {
            throw new IllegalArgumentException("Max length of the integral part cannot be less than 0.");
        }

        if (min != null && min < 0) {
            throw new IllegalArgumentException("Min length of the integral part cannot be less than 0.");
        }
    }

    protected String getMaxTemplate() {
        String template = GuardUtils.getProperties().getProperty("integral.length.max.message");
        return template;
    }

    protected String getMinTemplate() {
        String template = GuardUtils.getProperties().getProperty("integral.length.min.message");
        return template;
    }

    protected String getBetweenTemplate() {
        String template = GuardUtils.getProperties().getProperty("integral.length.between.message");
        return template;
    }

    @Override
    protected String getPartName() {
        return "integral";
    }

    @Override
    protected int getLength(Object value) {

        if (!(value instanceof Number)) {
            throw new IllegalStateException(value.getClass() + " is not a valid type for the " + this.getClass()
                    .getSimpleName() +
                    " constraint. " + this.getClass().getSimpleName() + " can only be applied to numbers.");
        }

        Number numValue = (Number) value;

        BigDecimal bd = NumberUtils.numToBigDecimal(numValue);

        int fractionLength = getLength(bd);
        return fractionLength;
    }

    protected int getLength(BigDecimal bd) {
        final int length = bd.precision() - bd.scale();
        return length;
    }

    public boolean supported(Object value) {
        if (value instanceof Number) {
            return true;
        }
        return false;
    }
}

