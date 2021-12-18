package za.co.momentum.risk.validation.guard.constraints;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import za.co.momentum.risk.validation.guard.Guard;
import za.co.momentum.risk.validation.guard.domain.DateBean;
import za.co.momentum.risk.validation.guard.violation.Violations;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class FutureOrPresentTest {

    private final Logger LOGGER = LoggerFactory.getLogger( this.getClass() );

    @Test
    public void dateSetInFutureTest() {

        Violations violations;
        Guard guard = new Guard();
        DateBean bean = new DateBean();

        bean.setLocalDate( LocalDate.of( 2024, 1, 8 ) );

        violations = guard.of( "test.1" )
                          .value( bean.getLocalDate() )
                          .constraint( new FutureOrPresent() )
                          .validate();

        System.out.println( guard.getViolations().getList() );

        Assertions.assertTrue( violations.isValid() );
        Assertions.assertTrue( violations.getList( "test.1" ).isEmpty() );

    }

    @Test
    public void dateSetInFutureIncludingTimeTest() {

        Violations violations;
        Guard guard = new Guard();
        DateBean bean = new DateBean();

        bean.setLocalDateTime( LocalDateTime.of( 2024, 1, 8, 8, 00 ) );

        violations = guard.of( "test.2" )
                          .value( bean.getLocalDateTime() )
                          .constraint( new FutureOrPresent() )
                          .validate();

        System.out.println( guard.getViolations().getList() );

        Assertions.assertTrue( violations.isValid() );
        Assertions.assertTrue( violations.getList( "test.2" ).isEmpty() );

    }

    @Test
    public void dateSetInPastTest() {

        Violations violations;
        Guard guard = new Guard();
        DateBean bean = new DateBean();

        bean.setLocalDate( LocalDate.of( 2020, 1, 8 ) );

        violations = guard.of( "test.3" )
                          .value( bean.getLocalDate() )
                          .constraint( new FutureOrPresent() )
                          .validate();

        System.out.println( guard.getViolations().getList() );

        Assertions.assertTrue( violations.isInvalid() );
        Assertions.assertTrue( violations.getList( "test.3" ).size() > 0 );

    }

    @Test
    public void dateSetInPastIncludingTimeTest() {

        Violations violations;
        Guard guard = new Guard();
        DateBean bean = new DateBean();

        bean.setLocalDateTime( LocalDateTime.of( 2020, 1, 8, 8, 00 ) );

        violations = guard.of( "test.4" )
                          .value( bean.getLocalDateTime() )
                          .constraint( new FutureOrPresent() )
                          .validate();

        System.out.println( guard.getViolations().getList() );

        Assertions.assertTrue( violations.isInvalid() );
        Assertions.assertTrue( violations.getList( "test.4" ).size() > 0 );

    }

    @Test
    public void dateSetInPresentTest() {

        Violations violations;
        Guard guard = new Guard();

        violations = guard.of( "test.5" )
                          .value( LocalDate.now() )
                          .constraint( new FutureOrPresent() )
                          .validate();

        System.out.println( guard.getViolations().getList() );

        Assertions.assertTrue( violations.isValid() );
        Assertions.assertTrue( violations.getList( "test.5" ).isEmpty() );

    }

    @Test
    public void dateSetInPresentIncludingTimeTest() {

        Violations violations;
        Guard guard = new Guard();

        violations = guard.of( "test.6" )
                          .value( LocalDateTime.now() )
                          .constraint( new FutureOrPresent() )
                          .validate();

        System.out.println( guard.getViolations().getList() );

        Assertions.assertTrue( violations.isValid() );
        Assertions.assertTrue( violations.getList( "test.6" ).isEmpty() );

    }

}
