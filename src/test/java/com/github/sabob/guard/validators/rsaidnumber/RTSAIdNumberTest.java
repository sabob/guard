package com.github.sabob.guard.validators.rsaidnumber;

import com.github.sabob.guard.validators.Validators;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.DateTimeException;

public class RTSAIdNumberTest {

    @Test
    public void testParserForValidNumber() throws Exception {
        RSAIDNumberParser idNumberParser = new RSAIDNumberParser();
        RSAIDNumberData idNumberData = idNumberParser.parse("7801014800084");
        Assertions.assertTrue(idNumberData.isValid());
    }

    @Test
    public void testParserForInvalidNumber() throws Exception {
        RSAIDNumberParser idNumberParser = new RSAIDNumberParser();
        RSAIDNumberData idNumberData = idNumberParser.parse("7701014800084");
        Assertions.assertFalse(idNumberData.isValid());
    }

    @Test
    public void testParserFprInvalidBirtDay() throws Exception {
        RSAIDNumberParser idNumberParser = new RSAIDNumberParser();

        try {
            RSAIDNumberData idNumberData = idNumberParser.parse("1234567895465");
            Assertions.assertFalse(idNumberData.isValid());
            Assertions.fail("Validation should fail because day of month (34) is invalid");

        } catch ( DateTimeException expected) {
        }
    }

    @Test
    public void testInvalidBirtDay() {
        boolean valid = Validators.isRSAIDNumber("1234567895465");
        Assertions.assertFalse(valid);
    }
}
