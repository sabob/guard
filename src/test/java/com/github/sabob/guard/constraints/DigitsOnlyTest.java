package com.github.sabob.guard.constraints;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.sabob.guard.Guard;
import com.github.sabob.guard.domain.ObjectBean;
import com.github.sabob.guard.domain.PrimBean;
import com.github.sabob.guard.violation.Violations;

public class DigitsOnlyTest {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Test
    public void ensureStringDigitsPass() {

        PrimBean bean = new PrimBean();
        bean.setString("123");

        Guard guard = new Guard();

        Violations violations = guard.of("test")
                .value(bean.getString())
                .constraint(new DigitsOnly())
                .validate();

        System.out.println(violations.getList("test"));
        Assertions.assertTrue(violations.isValid());
    }

     @Test
    public void ensureStringDigitsFail() {

        PrimBean bean = new PrimBean();
        bean.setString("abc");

        Guard guard = new Guard();

        Violations violations = guard.of("test")
                .value(bean.getString())
                .constraint(new DigitsOnly())
                .validate();

        System.out.println(violations.getList("test"));
        Assertions.assertTrue(violations.isInvalid());
    }

    @Test
    public void testIntNotSupported() {

        PrimBean bean = new PrimBean();
        bean.setInt(125);

        Guard guard = new Guard();

        Violations violations = guard.of("test")
                .value(bean.getInt())
                .constraint(new DigitsOnly())
                .validate();

        System.out.println(violations.getList("test"));
        Assertions.assertTrue(violations.isValid());

    }

    @Test
    public void testNullNotSupported() {

        ObjectBean bean = new ObjectBean();
        bean.setString(null);

        Guard guard = new Guard();
        Violations violations;

        violations = guard.of("test")
                .value(bean.getString())
                .constraint(new DigitsOnly())
                .validate();

        System.out.println(violations.getList("test"));
        Assertions.assertTrue(violations.isValid());
    }

}
