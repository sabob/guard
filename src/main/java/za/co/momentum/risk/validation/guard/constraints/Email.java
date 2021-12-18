package za.co.momentum.risk.validation.guard.constraints;

import za.co.momentum.risk.validation.guard.Constraint;
import za.co.momentum.risk.validation.guard.GuardContext;
import za.co.momentum.risk.validation.guard.utils.StringUtils;
import za.co.momentum.risk.validation.guard.utils.GuardUtils;
import za.co.momentum.risk.validation.guard.validators.Validators;
import za.co.momentum.risk.validation.guard.violation.Violation;

public class Email implements Constraint {

    protected static final String messageTemplate = GuardUtils.getProperties().getProperty( "email.message" );

    public Email() {
    }

    @Override
    public void apply( GuardContext guardContext ) {

        Object value = guardContext.getValue();

        boolean valid = isValid( value );

        if ( valid ) {
            return;
        }

        String name = StringUtils.capitalize( guardContext.getName() );
        Violation violation = GuardUtils.toViolationWithTemplateMessage( guardContext, messageTemplate, name );
        guardContext.addViolation( violation );
    }

    @Override
    public boolean isValid( Object value ) {

        if ( value == null ) {
            return true;
        }

        String strValue = GuardUtils.toString( Email.class.getSimpleName(), value );

        boolean valid = Validators.isEmail( strValue );
        return valid;
    }
}

