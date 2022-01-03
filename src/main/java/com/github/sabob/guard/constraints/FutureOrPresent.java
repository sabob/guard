package com.github.sabob.guard.constraints;

import com.github.sabob.guard.GuardContext;
import com.github.sabob.guard.utils.DateUtils;
import com.github.sabob.guard.utils.StringUtils;
import com.github.sabob.guard.utils.GuardUtils;
import com.github.sabob.guard.violation.Violation;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Validate that the date value is in the future or present.
 * Supported types are Date, Calendar, LocalDate and LocalDateTime.
 */
public class FutureOrPresent extends AbstractDateTimeConstraint {

    protected static final String messageTemplate = GuardUtils.getProperties().getProperty( "future.or.present.message" );

    @Override
    protected boolean isValidDateTime( LocalDateTime localDateTime ) {

        boolean validFuture = localDateTime.isAfter( LocalDateTime.now() );
        if ( validFuture ) return true;

        boolean validPresent = localDateTime.isEqual( LocalDateTime.now() );
        if ( validPresent ) return true;

        return false;

    }

    @Override
    protected boolean isValidDate( LocalDateTime localDateTime ) {

        LocalDate localDate = DateUtils.toLocalDate( localDateTime );

        boolean validFuture = localDate.isAfter( LocalDate.now() );
        if ( validFuture ) return true;

        boolean validPresent = localDate.isEqual( LocalDate.now() );
        if ( validPresent ) return true;

        return false;
    }

    @Override
    protected void addViolation( GuardContext context ) {
        String name = StringUtils.capitalize( context.getName() );
        Violation violation = GuardUtils.toViolationWithTemplateMessage( context, messageTemplate, name );
        context.addViolation( violation );
    }
}
