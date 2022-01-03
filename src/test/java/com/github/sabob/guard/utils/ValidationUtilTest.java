package com.github.sabob.guard.utils;

import org.junit.jupiter.api.Test;
import com.github.sabob.guard.GuardContext;
import com.github.sabob.guard.violation.Violation;

public class ValidationUtilTest {

    @Test
    public void testCreateMessage() throws Exception {

        String template = "%s must be between %d and %d";

        String name = StringUtils.capitalize( "age" );
        String message = GuardUtils.createMessage( template, name, 1, 2 );
        System.out.println( "Message: " + message );
    }

    @Test
    public void testMessageFormat() throws Exception {

        String template = "%s must be between %d and %d";

        GuardContext guardContext = new GuardContext();
        guardContext.setName( "age" );
        guardContext.setValue( 1 );

        String name = StringUtils.capitalize( guardContext.getName() );
        Violation violation = GuardUtils.toViolationWithTemplateMessage( guardContext, template, name, 10, 20 );
        System.out.println( "violation: " + violation );

    }
}
