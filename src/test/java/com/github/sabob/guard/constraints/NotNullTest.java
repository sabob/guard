package com.github.sabob.guard.constraints;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.sabob.guard.Guard;
import com.github.sabob.guard.domain.ObjectBean;
import com.github.sabob.guard.domain.PrimBean;
import com.github.sabob.guard.violation.Violations;

public class NotNullTest {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Test
    public void valueIsNotNullTest() {

        PrimBean bean = new PrimBean();
        Violations violations;
        Guard guard = new Guard();

        bean.setInt(123);

        violations = guard.of("test.1")
                .value(bean.getInt())
                .constraint(new NotNull())
                .validate();

        Assertions.assertTrue(violations.isValid());
        Assertions.assertTrue(violations.getList("test.1").isEmpty());
    }

    @Test
    public void nullTest() {

        ObjectBean bean = new ObjectBean();
        Violations violations;
        Guard guard = new Guard();

        bean.setString(null);

        violations = guard.of("test.2")
                .value(bean.getString())
                .constraint(new NotNull())
                .validate();

        System.out.println(guard.getViolations().getList());

        Assertions.assertTrue(violations.isInvalid());
        Assertions.assertTrue(violations.getList("test.2").size() > 0);
    }

}
