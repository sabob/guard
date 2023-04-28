package com.github.sabob.guard.constraints;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.sabob.guard.Guard;
import com.github.sabob.guard.domain.ObjectBean;
import com.github.sabob.guard.domain.Person;
import com.github.sabob.guard.violation.Violations;

import java.util.ArrayList;
import java.util.List;

public class SizeTest {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Test
    public void maxStringTest() {

        ObjectBean objectBean = new ObjectBean();
        Guard guard = new Guard();
        Violations violations;

        objectBean.setString("FooBarBaz");

        violations = guard.of("maxStringTest")
                .value(objectBean.getString())
                .constraint(new Size(0, 2))
                .validate();

        LOGGER.info("Result {}", guard.getViolations().getList());
        Assertions.assertTrue(violations.isInvalid());

    }

    @Test
    public void minListLengthTest() {

        Guard guard = new Guard();
        Violations violations;
        Person person = new Person();

        List<Person> list = new ArrayList();
        int setSize = 2;

        for (int i = 0; i < setSize; i++) {
            list.add(new Person());
        }

        violations = guard.of("minListLengthTest")
                .value(list)
                .constraint(new Size(5, 10))
                .validate();

        LOGGER.info("Result {}", guard.getViolations().getList());
        Assertions.assertTrue(violations.isInvalid());

    }

    @Test
    public void nullTest() {

        Guard guard = new Guard();
        Violations violations;

        violations = guard.of("nullTest")
                .value(null)
                .constraint(new Size(10, 15))
                .validate();

        LOGGER.info("Result {}", guard.getViolations().getList());
        Assertions.assertTrue(violations.isValid());

    }

}
