package za.co.momentum.risk.validation.guard.constraints;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import za.co.momentum.risk.validation.guard.Guard;
import za.co.momentum.risk.validation.guard.domain.ObjectBean;
import za.co.momentum.risk.validation.guard.violation.Violations;

public class NullOnlyTest {

    private final Logger LOGGER = LoggerFactory.getLogger( this.getClass() );

    @Test
    public void valueIsNotNull() {

        ObjectBean bean = new ObjectBean();
        Guard guard = new Guard();
        Violations violations;

        bean.setString( "abc" );

        violations = guard.of( "test.1" )
                          .value( bean.getString() )
                          .constraint( new NullOnly() )
                          .validate();

        Assertions.assertTrue( violations.isInvalid() );
        Assertions.assertTrue( violations.getList( "test.1" ).size() == 1 );

        System.out.println( guard.getViolations().getList() );

    }

    @Test
    public void isNullTest() {

        ObjectBean bean = new ObjectBean();
        Guard guard = new Guard();
        Violations violations;

        bean.setString( null );

        violations = guard.of( "test.2" )
                          .value( bean.getString() )
                          .constraint( new NullOnly() )
                          .validate();

        Assertions.assertTrue( violations.isValid() );
        Assertions.assertTrue( violations.isValid( "test.2" ) );
    }

}
