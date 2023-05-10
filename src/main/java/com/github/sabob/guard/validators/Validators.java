package com.github.sabob.guard.validators;

import com.github.sabob.guard.utils.StringUtils;
import com.github.sabob.guard.validators.rsaidnumber.InvalidRSAIDLengthException;
import com.github.sabob.guard.validators.rsaidnumber.RSAIDNumberData;
import com.github.sabob.guard.validators.rsaidnumber.RSAIDNumberParser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.Year;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;

public class Validators {

    public static boolean isUUID(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        }
        boolean valid = ValidatorPatterns.UUID_PATTERN.matcher(str).matches();
        return valid;
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static boolean isBlank(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean isDigitsOnly(String str) {
        String regex = "[0-9]+";
        return str.matches(regex);
    }

    public static boolean isInDateFormat(String str, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date eventDate = null;
        try {
            eventDate = sdf.parse(str);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public static Date ifInDateFormatConvert(String str, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date eventDate = null;
        try {
            eventDate = sdf.parse(str);
        } catch (ParseException e) {
            return null;
        }
        return eventDate;
    }

    public static boolean atLeastOneNotBlank(String... values) {
        boolean oneIsNotBlank = false;
        for (String value : values) {
            if (!isBlank(value)) {
                oneIsNotBlank = true;
            }
        }
        return oneIsNotBlank;
    }

    public static <E extends Enum<E>> boolean isValidEnumValue(String str, Class<E> clazz) {
        if (str == null) return false;

        for (E en : EnumSet.allOf(clazz)) {
            if (en.name().equals(str)) {
                return true;
            }
        }
        return false;
    }

    public static <E extends Enum<E>> boolean isValidEnumValue(Enum theEnum, Class<E> clazz) {
        if (theEnum == null) return false;

        for (E en : EnumSet.allOf(clazz)) {
            if (en.equals(theEnum)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isEmail(String email) {
        java.util.regex.Matcher m = ValidatorPatterns.EMAIL_PATTERN.matcher(email);
        return m.matches();
    }

    public static boolean isPhoneNumber(String phoneNumber) {
        java.util.regex.Matcher m = ValidatorPatterns.INTERNATIONAL_PHONE_PATTERN.matcher(phoneNumber);
        return m.matches();
    }

    public static boolean isRSALandLineNumber(String phoneNumber) {
        java.util.regex.Matcher m = ValidatorPatterns.RSA_PHONE_LAND_LINE_PATTERN.matcher(phoneNumber);
        return m.matches();
    }

    public static boolean isRSAMobilePhoneNumber(String phoneNumber) {
        java.util.regex.Matcher m = ValidatorPatterns.RSA_PHONE_MOBILE_PHONE_PATTERN.matcher(phoneNumber);
        return m.matches();
    }

    public static RSAIDNumberData parseRSAIDNumber(String idNumber, int pivotYear) throws InvalidRSAIDLengthException, DateTimeException {
        RSAIDNumberParser idNumberParser = new RSAIDNumberParser();
        idNumberParser.setPivotYear(Year.of(pivotYear));
        RSAIDNumberData idNumberData = idNumberParser.parse(idNumber);
        return idNumberData;

    }

    public static boolean isRSAIDNumber(String idNumber, int pivotYear) {

        try {
            RSAIDNumberData data = parseRSAIDNumber(idNumber, pivotYear);
            return data.isValid();
        } catch (Exception ex) {
            return false;
        }
    }

    public static boolean isRSAIDNumber(String idNumber) {
        int pivotYear = Year.now().getValue() - 100;// Assume ID belongs to someone not older than 100 years
        boolean valid = isRSAIDNumber(idNumber, pivotYear);
        return valid;
    }

    public static boolean contains(List list, Object value) {
        return list.contains(value);
    }

    public static boolean instanceOf(Object value, Class clazz) {
        return clazz.isInstance(value);
    }
}
