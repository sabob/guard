package za.co.momentum.risk.validation.guard.constraints;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import za.co.momentum.risk.validation.guard.Guard;
import za.co.momentum.risk.validation.guard.domain.ObjectBean;
import za.co.momentum.risk.validation.guard.domain.PrimBean;
import za.co.momentum.risk.validation.guard.violation.Violations;

public class InternationalPhoneNumberTest {

    private final Logger LOGGER = LoggerFactory.getLogger( this.getClass() );

    @Test
    public void notAPhoneNumberTest() {

        Guard guard = new Guard();

        PrimBean primBean = new PrimBean();
        ObjectBean objBean = new ObjectBean();

        String propertyName = "test.1";

        objBean.setString( "foo" );

        Violations violations;
        violations = guard.of( propertyName )
                          .value( objBean.getString() )
                          .constraint( new InternationalPhoneNumber() )
                          .validate();

        Assertions.assertTrue( violations.isInvalid() );
        Assertions.assertTrue( violations.getList( propertyName ).size() > 0 );

        System.out.println( guard.getViolations().getList() );

    }

    @Test
    public void validPhoneNumberTest() {

        Guard guard = new Guard();

        PrimBean primBean = new PrimBean();
        ObjectBean objBean = new ObjectBean();

        String propertyName = "test.2";

        objBean.setString( "+2712345" );

        Violations violations;
        violations = guard.of( propertyName )
                          .value( objBean.getString() )
                          .constraint( new InternationalPhoneNumber() )
                          .validate();

        Assertions.assertTrue( violations.isValid() );
        Assertions.assertTrue( violations.getList( propertyName ).isEmpty() );
    }

}



