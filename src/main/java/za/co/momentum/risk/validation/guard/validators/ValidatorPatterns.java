package za.co.momentum.risk.validation.guard.validators;

import java.util.regex.Pattern;

public class ValidatorPatterns {

    public static final String UUID_REGEX = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$";

    public static final Pattern UUID_PATTERN = Pattern.compile( UUID_REGEX );

    public static final String RSA_PHONE_LAND_LINE_REGEX = "^\\+(?:[0-9] ?){6,14}[0-9]$";

    public static final Pattern RSA_PHONE_LAND_LINE_PATTERN = Pattern.compile( RSA_PHONE_LAND_LINE_REGEX );

    public static final String RSA_PHONE_MOBILE_PHONE_REGEX = "^(\\+27|0)[6-8][0-9]{8}$";

    public static final Pattern RSA_PHONE_MOBILE_PHONE_PATTERN = Pattern.compile( RSA_PHONE_MOBILE_PHONE_REGEX );

    // A general phone number regex, not too specific
    public static final String INTERNATIONAL_PHONE_REGEX = "^\\+(?:[0-9] ?){6,14}[0-9]$";

    public static final Pattern INTERNATIONAL_PHONE_PATTERN = Pattern.compile( INTERNATIONAL_PHONE_REGEX );

    public static final String EMAIL_REGEX = "^.+@.+\\..+$";

    public static final Pattern EMAIL_PATTERN = Pattern.compile( EMAIL_REGEX );
}
