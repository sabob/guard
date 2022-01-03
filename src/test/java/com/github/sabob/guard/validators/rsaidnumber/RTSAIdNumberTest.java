package com.github.sabob.guard.validators.rsaidnumber;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RTSAIdNumberTest {

    @Test
    public void testRSAIdValidator() throws Exception {
        RSAIDNumberParser idNumberParser = new RSAIDNumberParser();
        RSAIDNumberData idNumberData = idNumberParser.parse( "7801014800084" );
        Assertions.assertTrue( idNumberData.isValid() );

        idNumberData = idNumberParser.parse( "7701014800084" );
        Assertions.assertFalse( idNumberData.isValid() );

    }

}
