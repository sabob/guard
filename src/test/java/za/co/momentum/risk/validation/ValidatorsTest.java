package za.co.momentum.risk.validation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import za.co.momentum.risk.validation.guard.validators.rsaidnumber.RSAIDNumberData;
import za.co.momentum.risk.validation.guard.validators.rsaidnumber.RSAIDNumberParser;

public class ValidatorsTest {

    @Test
    public void testRSAIdValidator() throws Exception {
        RSAIDNumberParser idNumberParser = new RSAIDNumberParser();
        RSAIDNumberData idNumberData = idNumberParser.parse( "7801014800084" );
        Assertions.assertTrue( idNumberData.isValid() );

        idNumberData = idNumberParser.parse( "7701014800084" );
        Assertions.assertFalse( idNumberData.isValid() );

    }

}
