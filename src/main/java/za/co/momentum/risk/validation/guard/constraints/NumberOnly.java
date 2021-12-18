package za.co.momentum.risk.validation.guard.constraints;

import za.co.momentum.risk.validation.guard.Constraint;
import za.co.momentum.risk.validation.guard.GuardContext;
import za.co.momentum.risk.validation.guard.utils.NumberUtils;
import za.co.momentum.risk.validation.guard.utils.StringUtils;
import za.co.momentum.risk.validation.guard.utils.GuardUtils;
import za.co.momentum.risk.validation.guard.violation.Violation;

public class NumberOnly implements Constraint {

    protected static final String messageTemplate = GuardUtils.getProperties().getProperty( "number.only.message" );

    public NumberOnly() {
    }

    @Override
    public void apply( GuardContext guardContext ) {

        Object value = guardContext.getValue();

        boolean valid = isValid( value );

        String name = StringUtils.capitalize( guardContext.getName() );
        Violation violation = GuardUtils.toViolationWithTemplateMessage( guardContext, messageTemplate, name );
        guardContext.addViolation( violation );
    }

    @Override
    public boolean isValid( Object value ) {

        if ( value == null ) {
            return true;
        }

        if ( (value instanceof Number) ) {
            return true;
        }

        String strValue = GuardUtils.toString( Number.class.getSimpleName(), value );

        if ( NumberUtils.isCreatable( strValue ) ) {
            return true;
        }
        return false;
    }
}
