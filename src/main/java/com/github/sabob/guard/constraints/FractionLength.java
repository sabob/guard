package com.github.sabob.guard.constraints;

import com.github.sabob.guard.utils.NumberUtils;
import com.github.sabob.guard.utils.GuardUtils;

import java.math.BigDecimal;

/**
 * A digital validator that applies to the fractional part of numbers and validate that the length of the fraction is
 * lower than or equal to the digit specify in the parameter.
 */
public class FractionLength extends AbstractNumericalBetweenConstraint {

    public FractionLength(int min, int max) {
        super(min, max);
    }

    public FractionLength() {
    }

    public FractionLength max(int max) {
        this.max = max;
        return this;
    }

    public FractionLength min(int min) {
        this.min = min;
        return this;
    }

    @Override
    protected String getMaxTemplate() {
        String template = GuardUtils.getProperties().getProperty("fraction.length.max.message");
        return template;
    }

    @Override

    protected String getMinTemplate() {
        String template = GuardUtils.getProperties().getProperty("fraction.length.min.message");
        return template;
    }

    @Override
    protected String getBetweenTemplate() {
        String template = GuardUtils.getProperties().getProperty("fraction.length.between.message");
        return template;
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
        int length = bd.scale() < 0 ? 0 : bd.scale();
        return length;
    }

    protected String getPartName() {
        return "fractional";
    }
}
