package za.co.momentum.risk.validation.guard.constraints;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import za.co.momentum.risk.validation.guard.Guard;
import za.co.momentum.risk.validation.guard.domain.Person;
import za.co.momentum.risk.validation.guard.violation.Violations;

public class BetweenTest {

    private final Logger LOGGER = LoggerFactory.getLogger( this.getClass() );

    @Test
    public void testBetweenSuccess() {

        Person person = new Person();
        person.setAge( 10 );

        Guard guard = new Guard();

        Violations violations = guard.of( "age" )
                                     .value( person.getAge() )
                                     .constraint( new NotNull() )
                                     .constraint( new Between( 10, 20 ) )
                                     .validate();

        Assertions.assertTrue( violations.isValid( "age" ) );

        violations = guard.of( "age" )
                          .value( person.getAge() )
                          .constraint( new NotNull() )
                          .constraint( new Between( 1, 10 ) )
                          .validate();

        Assertions.assertTrue( violations.isValid( "age" ) );

        violations = guard.of( "age" )
                          .value( person.getAge() )
                          .constraint( new NotNull() )
                          .constraint( new Between( 11, 20 ) )
                          .validate();

        Assertions.assertFalse( violations.isValid( "age" ) );
    }

    @Test
    public void toJsonTest() throws Exception {

        Person person = new Person();
        person.setFirstname( "" );

        Guard guard = new Guard();

        Violations violations = guard.of( "firstname" )
                                     .code( "FN" )
                                     .value( person.getFirstname() )
                                     .constraint( new NotNull() )
                                     .constraint( new Required() )
                                     .validate();

        System.out.println( guard.getViolations().getList() );
        System.out.println( guard.getContext() );
    }
}
