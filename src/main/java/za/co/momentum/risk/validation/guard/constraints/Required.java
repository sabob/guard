package za.co.momentum.risk.validation.guard.constraints;

import za.co.momentum.risk.validation.guard.Constraint;
import za.co.momentum.risk.validation.guard.GuardContext;
import za.co.momentum.risk.validation.guard.utils.GuardUtils;
import za.co.momentum.risk.validation.guard.utils.StringUtils;
import za.co.momentum.risk.validation.guard.violation.Violation;

public class Required implements Constraint {

    @Override
    public void apply( GuardContext guardContext ) {

        Object value = guardContext.getValue();

        boolean valid = isValid( value );

        if ( !valid ) {

            String name = StringUtils.capitalize( guardContext.getName() );
            String messageTemplate = GuardUtils.getProperties().getProperty( "required.message" );
            Violation violation = GuardUtils.toViolationWithTemplateMessage( guardContext, messageTemplate, name );
            guardContext.addViolation( violation );
        }
    }

    @Override
    public boolean isValid( Object value ) {

        if ( value == null ) {
            return false;
        }

        if ( value instanceof String ) {
            // For strings, we use NotBlank
            NotBlank notBlank = new NotBlank();
            if ( notBlank.isValid( value ) ) {
                return true;
            }
        } else {

            // For everything else, we use NotEmpty
            NotEmpty notEmpty = new NotEmpty();
            if ( notEmpty.isValid( value ) ) {
                return true;
            }
        }

        return false;
    }
}
