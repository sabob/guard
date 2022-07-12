package com.github.sabob.guard;

import com.github.sabob.guard.constraints.Max;
import com.github.sabob.guard.constraints.NotNull;
import com.github.sabob.guard.constraints.Required;
import com.github.sabob.guard.constraints.Size;
import com.github.sabob.guard.violation.Violation;
import com.github.sabob.guard.violation.Violations;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GuardContextTest {

    private static final Logger LOGGER = LoggerFactory.getLogger( GuardContextTest.class );

    @Test
    public void testContextSwitchingHasViolationsPerName() {

        Guard guard = new Guard( "some_name" );
        Violations violations = guard
                .value("")
                .constraint( new Required() )

                .of( "other_name" )
                .value("")
                .constraint( new Required() )
                .validate();

        LOGGER.info( "some_name violations size {}", violations.getList("some_name").size() );
        Assertions.assertTrue( violations.getList("some_name").size() == 1 );

        LOGGER.info( "other_name violations size {}", violations.getList("other_name").size() );
        Assertions.assertTrue( violations.getList("other_name").size() == 1 );
    }

    @Test
    public void testGuardKeepsAllViolationsWhenContextSwitching() {

        Violations violations = new Guard("firstname")
                .value(null)
                .constraint( new NotNull() )
                .of("lastname")
                .value("Sanders")
                .constraint( new Size().max( 5 ) )
                .validate();

        LOGGER.info( "Violations size {}", violations.getList().size() );
        Assertions.assertTrue( violations.getList().size() == 2 );
    }

    @Test
    public void testContextAndGuardHasSameViolations() {

        Guard guard = new Guard( "some_name" );

        Violations violations = guard
                .constraint( new Required() )
                .validate();

        Violation guardViolation = guard.getViolations().getList( "some_name" ).get( 0 );
        Violation contextViolation = guard.getContext().getViolations().getList( "some_name" ).get( 0 );

        Assertions.assertEquals( guardViolation, contextViolation );

        LOGGER.info( "Violation message {}", guardViolation.getMessage() );
        LOGGER.info( "Context violation message {}", contextViolation.getMessage() );
    }
}
