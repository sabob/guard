package za.co.momentum.risk.validation.guard.constraints;

import za.co.momentum.risk.validation.guard.Constraint;
import za.co.momentum.risk.validation.guard.GuardContext;
import za.co.momentum.risk.validation.guard.utils.DateUtils;
import za.co.momentum.risk.validation.guard.utils.GuardUtils;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Abstract validator for date and time.
 * Supported types are Date, Calendar, LocalDate and LocalDateTime.
 */
public abstract class AbstractDateTimeConstraint implements Constraint {

    protected boolean includeTime = false;

    @Override
    public void apply( GuardContext guardContext ) {

        Object value = guardContext.getValue();

        boolean valid = isValid( value );

        if ( !valid ) {
            addViolation( guardContext );
        }

    }

    /**
     * Sets whether the constraint should compare the time as well, false by default.
     *
     * @param includeTime true if the time must be compared as well
     * @return Past or Past including the Present to enable fluent api
     */
    public AbstractDateTimeConstraint includeTime( boolean includeTime ) {
        this.includeTime = includeTime;
        return this;
    }

    @Override
    public boolean isValid( Object value ) {

        if ( value == null ) {
            return true;
        }

        Optional<LocalDateTime> optional = DateUtils.toLocalDateTime( value );

        LocalDateTime localDateTime = GuardUtils.toLocalDateTime( AbstractDateTimeConstraint.class.getSimpleName(), value, optional );

        boolean valid;

        if ( includeTime ) {
            valid = isValidDateTime( localDateTime );

        } else {
            valid = isValidDate( localDateTime );
        }

        return valid;
    }

    protected abstract boolean isValidDateTime( LocalDateTime localDateTime );

    protected abstract boolean isValidDate( LocalDateTime localDateTime );

    protected abstract void addViolation( GuardContext context );
}
