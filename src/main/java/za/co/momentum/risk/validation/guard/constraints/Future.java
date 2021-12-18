package za.co.momentum.risk.validation.guard.constraints;

import za.co.momentum.risk.validation.guard.GuardContext;
import za.co.momentum.risk.validation.guard.utils.DateUtils;
import za.co.momentum.risk.validation.guard.utils.StringUtils;
import za.co.momentum.risk.validation.guard.utils.GuardUtils;
import za.co.momentum.risk.validation.guard.violation.Violation;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Validate that the date value is in the future.
 * Supported types are Date, Calendar, LocalDate and LocalDateTime.
 */
public class Future extends AbstractDateTimeConstraint {

    protected static final String messageTemplate = GuardUtils.getProperties().getProperty( "future.message" );

    @Override
    protected boolean isValidDateTime( LocalDateTime localDateTime ) {
        boolean validFuture = localDateTime.isAfter( LocalDateTime.now() );
        return validFuture;
    }

    @Override
    protected boolean isValidDate( LocalDateTime localDateTime ) {
        LocalDate localDate = DateUtils.toLocalDate( localDateTime );
        boolean validFuture = localDate.isAfter( LocalDate.now() );
        return validFuture;
    }

    @Override
    protected void addViolation( GuardContext context ) {
        String name = StringUtils.capitalize( context.getName() );
        Violation violation = GuardUtils.toViolationWithTemplateMessage( context, messageTemplate, name );
        context.addViolation( violation );
    }
}
