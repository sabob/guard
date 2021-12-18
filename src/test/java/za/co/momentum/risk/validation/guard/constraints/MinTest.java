package za.co.momentum.risk.validation.guard.constraints;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import za.co.momentum.risk.validation.guard.Guard;
import za.co.momentum.risk.validation.guard.domain.ObjectBean;
import za.co.momentum.risk.validation.guard.domain.PrimBean;
import za.co.momentum.risk.validation.guard.violation.Violations;

public class MinTest {

    private final Logger LOGGER = LoggerFactory.getLogger( this.getClass() );

    PrimBean bean = new PrimBean();

    private Guard guard = new Guard();

    Violations violations;

    // Negative Test: Set value < Min
    @Test
    public void setValueLessThanMinTest() {

        bean.setInt( 20 );

        violations = guard.of( "test.1" )
                          .value( bean.getInt() )
                          .constraint( new Min( 21 ) )
                          .validate();

        System.out.println( guard.getViolations().getList() );

        Assertions.assertTrue( violations.isInvalid() );
        Assertions.assertTrue( violations.getList( "test.1" ).size() == 1 );

    }

    // Positive Test: Set value == Min
    @Test
    public void setValueEqualsToMin() {

        bean.setInt( 21 );

        violations = guard.of( "test.2" )
                          .value( bean.getInt() )
                          .constraint( new Min( 21 ) )
                          .validate();

        Assertions.assertTrue( violations.getList( "test.2" ).isEmpty() );
    }

    // Positive Test: Set value > Min
    @Test
    public void setValueGreaterThanMin() {

        bean.setInt( 22 );

        violations = guard.of( "test.3" )
                          .value( bean.getInt() )
                          .constraint( new Min( 21 ) )
                          .validate();

        Assertions.assertTrue( violations.getList( "test.3" ).isEmpty() );
        Assertions.assertTrue( violations.isValid() );

    }

    @Test
    public void nullTest() {

        Violations violations;
        Guard guard = new Guard();

        ObjectBean bean = new ObjectBean();

        bean.setString( null );

        violations = guard.of( "test.4" )
                          .value( bean.getString() )
                          .constraint( new Min( 20 ) )
                          .validate();

        Assertions.assertTrue( violations.isValid() );
        Assertions.assertTrue( violations.getList( "test.4" ).isEmpty() );
    }

}
