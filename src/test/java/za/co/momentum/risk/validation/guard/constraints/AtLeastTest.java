package za.co.momentum.risk.validation.guard.constraints;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import za.co.momentum.risk.validation.guard.Guard;
import za.co.momentum.risk.validation.guard.constraints.misc.AtLeast;
import za.co.momentum.risk.validation.guard.domain.Person;
import za.co.momentum.risk.validation.guard.violation.Violations;

public class AtLeastTest {

    private final Logger LOGGER = LoggerFactory.getLogger( this.getClass() );

    @Test
    public void atLeastTest() {

        Person person = new Person();

        person.setLastname( "Foo" );

        Guard guard = new Guard();
        guard.of( "lastname" );

        AtLeast atLeast = new AtLeast( 2, new NotEmpty(), "At least 2 values are required!" );
        atLeast.values( "one" );
        guard.constraint( atLeast );
        //atLeast.constraint( new NotEmpty2(), guard.context() );

        Violations violations = guard.validate();

        System.out.println( violations.getList( "lastname" ) );
        System.out.println( "Is valid? " + violations.isValid( "lastname" ) );
    }
}
