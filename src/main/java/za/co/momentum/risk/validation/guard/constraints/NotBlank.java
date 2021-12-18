package za.co.momentum.risk.validation.guard.constraints;

import za.co.momentum.risk.validation.guard.Constraint;
import za.co.momentum.risk.validation.guard.GuardContext;
import za.co.momentum.risk.validation.guard.utils.StringUtils;
import za.co.momentum.risk.validation.guard.utils.GuardUtils;
import za.co.momentum.risk.validation.guard.validators.Validators;
import za.co.momentum.risk.validation.guard.violation.Violation;

//The value of the value or values inside collection must not be empty or contain only whitespace characters.
public class NotBlank implements Constraint {

    protected static final String messageTemplate = GuardUtils.getProperties().getProperty( "not.blank.message" );

    public NotBlank() {
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

        if ( value == null ) {
            return true;
        }

        if ( value instanceof String ) {
            return Validators.isNotBlank( value.toString() );
        }

        throw new IllegalStateException( value.getClass() + " is not a valid type for the " + getClass().getSimpleName() + " constraint. " +
                this.getClass().getSimpleName() + " can only be applied to Strings." );
    }
}

