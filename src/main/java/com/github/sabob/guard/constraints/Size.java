package com.github.sabob.guard.constraints;

import com.github.sabob.guard.utils.GuardUtils;

import java.util.Collection;
import java.util.Map;

/**
 * Supported types are CharSequence (String), Maps, Arrays and Collections. Other data types aren't validated and isValid() will return true.
 */
public class Size extends AbstractNumericalBetweenConstraint {

    public Size() {
    }

    public Size(int min, int max) {
        super(min, max);
    }

    public Size max(int max) {
        this.max = max;
        return this;
    }

    public Size min(int min) {
        this.min = min;
        return this;
    }

    @Override
    protected String getMaxTemplate() {
        String template = GuardUtils.getProperties().getProperty("size.max.message");
        return template;
    }

    @Override

    protected String getMinTemplate() {
        String template = GuardUtils.getProperties().getProperty("size.min.message");
        return template;
    }

    @Override
    protected String getBetweenTemplate() {
        String template = GuardUtils.getProperties().getProperty("size.between.message");
        return template;
    }

    protected String getPartName() {
        return "size";
    }

    @Override
    protected int getLength(Object value) {

        if (value instanceof Collection) {
            Collection collection = (Collection) value;
            return collection.size();

        }

        if (value instanceof Map) {
            Map map = (Map) value;
            return map.size();
        }

        if (value.getClass().isArray()) {
            Object[] array = (Object[]) value;
            return array.length;
        }

        String str = value.toString();
        return str.length();
    }

    public boolean supported(Object value) {
        if (value == null) {
            return false;
        }

        if (value instanceof Collection
                || value instanceof Map
                || value.getClass().isArray()
                || value instanceof CharSequence) {
            return true;
        }
        return false;
    }
}
