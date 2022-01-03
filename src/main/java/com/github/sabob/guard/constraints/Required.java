package com.github.sabob.guard.constraints;

import com.github.sabob.guard.Constraint;
import com.github.sabob.guard.GuardContext;
import com.github.sabob.guard.utils.GuardUtils;
import com.github.sabob.guard.utils.StringUtils;
import com.github.sabob.guard.violation.Violation;

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

        NotNull notNull = new NotNull();
        if ( notNull.isInvalid( value ) ) {
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
