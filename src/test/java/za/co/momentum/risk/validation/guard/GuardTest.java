package za.co.momentum.risk.validation.guard;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import za.co.momentum.risk.validation.guard.constraints.NotNull;
import za.co.momentum.risk.validation.guard.constraints.Required;
import za.co.momentum.risk.validation.guard.domain.Person;
import za.co.momentum.risk.validation.guard.violation.Violations;

public class GuardTest {

    private static final Logger LOGGER = LoggerFactory.getLogger( GuardTest.class );

    @Test
    public void testAddPerson() {

        Person person = new Person();
        person.setFirstname( "  " );
        person.setIdNumber( "123" );

        Guard guard = new Guard();

        Violations violations = guard.of( "firstname" )
                                     .code( "FN" )
                                     .value( person.getFirstname() )
                                     .constraint( new NotNull() )
                                     .constraint( new Required() )
                                     .validate();

        System.out.println( "firstname Violations: " + violations.getList( "firstname" ) );

        violations = guard.of( "idNumber" )
                          .value( person.getIdNumber() )
                          .constraint( new NotNull(), null, "moo" )
                          .validate();

        System.out.println( "idNumber Violations: " + violations.getList( "idNumber" ) );
        System.out.println( guard.getContext() );
    }

    @Test
    public void testToJson() throws Exception {

        Person person = new Person();
        person.setFirstname( "" );

        Guard guard = new Guard();

        Violations violations = guard.of( "firstname" )
                                     .code( "FN" )
                                     .value( person.getFirstname() )
                                     .constraint( new NotNull() )
                                     .constraint( new Required() )
                                     .validate();

        LOGGER.info( "Result {}", guard.getViolations().getList() );
        LOGGER.info( "Result {}", guard.getContext() );
    }
}
