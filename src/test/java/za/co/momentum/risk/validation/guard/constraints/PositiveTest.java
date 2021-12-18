package za.co.momentum.risk.validation.guard.constraints;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import za.co.momentum.risk.validation.guard.Guard;
import za.co.momentum.risk.validation.guard.domain.PrimBean;
import za.co.momentum.risk.validation.guard.violation.Violations;

public class PositiveTest {

    private final Logger LOGGER = LoggerFactory.getLogger( this.getClass() );

    PrimBean bean = new PrimBean();

    Guard guard = new Guard();

    Violations violations;

    //Positive test: Value is Negative
    @Test
    public void valueIsNegativeTest() {

        bean.setDouble( -1.123d );

        violations = guard.of( "test.1" )
                          .value( bean.getInt() )
                          .constraint( new Positive() )
                          .validate();

        Assertions.assertTrue( violations.getList( "test.1" ).size() == 1 );
        Assertions.assertTrue( violations.isInvalid() );

        System.out.println( guard.getViolations().getList() );

    }

    // Positive test: Value is Positive
    @Test
    public void valueIsPositive() {

        bean.setDouble( 0.1235d );

        violations = guard.of( "test.2" )
                          .value( bean.getDouble() )
                          .constraint( new Positive() )
                          .validate();

        Assertions.assertTrue( violations.isValid() );
        Assertions.assertTrue( violations.getList( "test.2" ).isEmpty() );
    }

}
