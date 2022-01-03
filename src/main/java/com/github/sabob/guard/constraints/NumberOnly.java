package com.github.sabob.guard.constraints;

import com.github.sabob.guard.Constraint;
import com.github.sabob.guard.GuardContext;
import com.github.sabob.guard.utils.NumberUtils;
import com.github.sabob.guard.utils.StringUtils;
import com.github.sabob.guard.utils.GuardUtils;
import com.github.sabob.guard.violation.Violation;

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
