package com.github.sabob.guard.constraints;

import com.github.sabob.guard.Guard;
import com.github.sabob.guard.domain.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotEmptyTest {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Test
    public void testExceptionsHasNotEmptyInErrorMessage() {

        try {
            new Guard("list")
                    .value(new Person())
                    .constraint(new NotEmpty())
                    .validate();

            Assertions.fail("Exception should have been thrown above");

        } catch (IllegalStateException ex) {
            Assertions.assertTrue(ex.getMessage().contains(" NotEmpty "));
            Assertions.assertFalse(ex.getMessage().contains(" Size "));
        }
    }
}
