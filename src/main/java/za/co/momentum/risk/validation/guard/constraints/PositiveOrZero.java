package za.co.momentum.risk.validation.guard.constraints;

import za.co.momentum.risk.validation.guard.Constraint;
import za.co.momentum.risk.validation.guard.GuardContext;
import za.co.momentum.risk.validation.guard.utils.NumberUtils;
import za.co.momentum.risk.validation.guard.utils.StringUtils;
import za.co.momentum.risk.validation.guard.utils.GuardUtils;
import za.co.momentum.risk.validation.guard.violation.Violation;

//PositiverOrZero apply to numeric values and validate that they are strictly negative, or negative including 0.
public class PositiveOrZero implements Constraint {

    protected static final String messageTemplate = GuardUtils.getProperties().getProperty( "positive.or.zero.message" );

    @Override
    public void apply( GuardContext guardContext ) {

        Object value = guardContext.getValue();

        boolean valid = isValid( value );

        if ( !valid ) {
            String name = StringUtils.capitalize( guardContext.getName() );
            Violation violation = GuardUtils.toViolationWithTemplateMessage( guardContext, messageTemplate, name );
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

        Number numberValue = ( Number ) value;

        boolean valid = NumberUtils.isPositiveOrZero( numberValue );
        return valid;
    }
}
