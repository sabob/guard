package com.github.sabob.guard.constraints;

import com.github.sabob.guard.Guard;
import com.github.sabob.guard.domain.Person;
import com.github.sabob.guard.violation.Violations;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotEmptyTest {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Test
    public void testNotEmptyIgnoreBean() {

        Violations violations = new Guard("list")
                .value(new Person())
                .constraint(new NotEmpty())
                .validate();

        Assertions.assertTrue(violations.isValid());
    }
}
