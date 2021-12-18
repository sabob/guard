package za.co.momentum.risk.validation.guard.constraints;

import za.co.momentum.risk.validation.guard.GuardContext;
import za.co.momentum.risk.validation.guard.utils.DateUtils;
import za.co.momentum.risk.validation.guard.utils.StringUtils;
import za.co.momentum.risk.validation.guard.utils.GuardUtils;
import za.co.momentum.risk.validation.guard.violation.Violation;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Validate that the date value is in the past.
 * Supported types are Date, Calendar, LocalDate and LocalDateTime.
 */
public class Past extends AbstractDateTimeConstraint {

    protected static final String messageTemplate = GuardUtils.getProperties().getProperty( "past.message" );

    @Override
    protected boolean isValidDateTime( LocalDateTime localDateTime ) {
        boolean valid = localDateTime.isBefore( LocalDateTime.now() );
        return valid;
    }

    @Override
    protected boolean isValidDate( LocalDateTime localDateTime ) {
        LocalDate date = DateUtils.toLocalDate( localDateTime );
        boolean valid = date.isBefore( LocalDate.now() );
        return valid;
    }

    @Override
    protected void addViolation( GuardContext context ) {
        String name = StringUtils.capitalize( context.getName() );
        Violation violation = GuardUtils.toViolationWithTemplateMessage( context, messageTemplate, name );
        context.addViolation( violation );
    }
}
