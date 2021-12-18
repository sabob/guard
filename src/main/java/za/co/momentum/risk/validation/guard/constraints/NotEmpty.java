package za.co.momentum.risk.validation.guard.constraints;

import za.co.momentum.risk.validation.guard.Constraint;
import za.co.momentum.risk.validation.guard.GuardContext;
import za.co.momentum.risk.validation.guard.utils.StringUtils;
import za.co.momentum.risk.validation.guard.utils.GuardUtils;
import za.co.momentum.risk.validation.guard.violation.Violation;

//The value of the value or values in collection must not be empty.
public class NotEmpty implements Constraint {

    protected static final String messageTemplate = GuardUtils.getProperties().getProperty( "not.empty.message" );

    public NotEmpty() {
    }

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
        Size sizeConstraint = new Size().min( 1 );
        return sizeConstraint.isValid( value );
    }
}
