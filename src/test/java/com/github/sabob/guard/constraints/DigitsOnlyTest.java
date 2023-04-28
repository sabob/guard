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
    public void ensureDigitsOnly() {

        PrimBean bean = new PrimBean();
        bean.setInt(125);

        Guard guard = new Guard();

        Assertions.assertThrows(IllegalStateException.class, () -> {

            Violations violations = guard.of("test.1")
                    .value(bean.getInt())
                    .constraint(new DigitsOnly())
                    .validate();

        });
    }

    @Test
    public void testNull() {

        ObjectBean bean = new ObjectBean();
        bean.setString(null);

        Guard guard = new Guard();
        Violations violations;

        violations = guard.of("test.2")
                .value(bean.getString())
                .constraint(new DigitsOnly())
                .validate();

        System.out.println(violations.getList("test.1"));
        Assertions.assertTrue(violations.isValid());
    }

}
