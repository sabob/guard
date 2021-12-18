package za.co.momentum.risk.validation.guard.constraints;

import za.co.momentum.risk.validation.guard.Constraint;
import za.co.momentum.risk.validation.guard.GuardContext;
import za.co.momentum.risk.validation.guard.utils.NumberUtils;
import za.co.momentum.risk.validation.guard.utils.StringUtils;
import za.co.momentum.risk.validation.guard.utils.GuardUtils;
import za.co.momentum.risk.validation.guard.violation.Violation;

/**
 * Max validator apply to numeric values and validate that the values are
 * lower than or equal to the number specify in parameter.
 */
public class Max implements Constraint {

    protected static final String messageTemplate = GuardUtils.getProperties().getProperty( "max.message" );

    private Number max;

    public Max( Number max ) {
        this.max = max;

    }

    @Override
    public void apply( GuardContext guardContext ) {

        Object value = guardContext.getValue();

        boolean valid = isValid( value );

        if ( !valid ) {
            String name = StringUtils.capitalize( guardContext.getName() );
            Violation violation = GuardUtils.toViolationWithTemplateMessage( guardContext, messageTemplate, name, max );
            guardContext.addViolation( violation );
        }

    }

    @Override
    public boolean isValid( Object value ) {

        if ( value == null ) {
            return true;
        }
        if ( !(value instanceof Number) ) {
            throw new IllegalStateException( value.getClass() + " is not a valid type for the " + this.getClass().getSimpleName() +
                    " constraint. " + this.getClass().getSimpleName() + " can only be applied to numbers." );
        }

        Number number = ( Number ) value;

        boolean valid = NumberUtils.isLessThanOrEqual( number, max );
        return valid;
    }

}

