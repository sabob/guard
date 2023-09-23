package com.github.sabob.guard.utils;

import java.math.BigDecimal;
import java.math.BigInteger;

public class NumberUtils {

    public static boolean isLessThanOrEqual(Number x, Number y) {
        return !isGreaterThan(x, y);
    }

    public static boolean isLessThan(Number x, Number y) {

        int result = compare(x, y);

        if (result == -1) {
            return true;
        }
        return false;
    }

    public static boolean isGreaterThanOrEqual(Number x, Number y) {
        return !isLessThan(x, y);
    }

    public static boolean isGreaterThan(Number x, Number y) {

        int result = compare(x, y);

        if (result == 1) {
            return true;
        }
        return false;
    }

    public static boolean equal(Number x, Number y) {

        int result = compare(x, y);

        if (result == 0) {
            return true;
        }
        return false;
    }

    public static boolean isPositive(Number x) {

        BigDecimal newBigDecimal = numToBigDecimal(x);

        int result = newBigDecimal.signum();

        if (result == 1) {
            return true;
        }
        return false;
    }

    public static boolean isPositiveOrZero(Number x) {

        BigDecimal newBigDecimal = numToBigDecimal(x);

        int result = newBigDecimal.signum();

        if (result == 1 || result == 0) {
            return true;
        }
        return false;
    }

    public static boolean isNegative(Number x) {

        BigDecimal newBigDecimal = numToBigDecimal(x);

        int result = newBigDecimal.signum();

        if (result == -1) {
            return true;
        }
        return false;
    }

    public static boolean isNegativeOrZero(Number x) {

        BigDecimal newBigDecimal = numToBigDecimal(x);

        int result = newBigDecimal.signum();

        if (result == -1 || result == 0) {
            return true;
        }
        return false;
    }

    public static boolean isZero(Number x) {

        BigDecimal newBigDecimal = numToBigDecimal(x);

        int result = newBigDecimal.signum();

        if (result == 0) {
            return true;
        }
        return false;
    }

    public static int compare(Number x, Number y) {

        if (isSpecial(x) || isSpecial(y)) {

            return Double.compare(x.doubleValue(), y.doubleValue());

        } else
            return numToBigDecimal(x).compareTo(numToBigDecimal(y));
    }

    public static boolean isSpecial(Number x) {

        boolean isNaN;
        boolean isInfinite;

        if (x instanceof Double) {

            isNaN = Double.isNaN((Double) x);
            isInfinite = Double.isInfinite((Double) x);

            if (isNaN || isInfinite) {
                return true;
            }
        }

        if (x instanceof Float) {

            isNaN = Float.isNaN((Float) x);
            isInfinite = Float.isInfinite((Float) x);

            if (isNaN || isInfinite) {
                return true;
            }
        }

        return false;
    }

    public static BigDecimal numToBigDecimal(Number number) {

        if (number instanceof BigDecimal) {
            BigDecimal newBigDecimal;
            newBigDecimal = (BigDecimal) number;
            return newBigDecimal;
        }

        if (number instanceof BigInteger) {
            return new BigDecimal((BigInteger) number);
        }

        if (number instanceof Byte ||
                number instanceof Short ||
                number instanceof Integer ||
                number instanceof Long) {
            return new BigDecimal(number.longValue());
        }

        if (number instanceof Float ||
                number instanceof Double) {
            String str = number.toString();
            return new BigDecimal(str);
        }

        try {

            return new BigDecimal(number.toString());
        } catch (final NumberFormatException ex) {
            throw new RuntimeException("The given number (\"" + number + "\" of class " + number.getClass().getName()
                    + ") does not have a parsable string representation", ex);
        }
    }

    public static BigDecimal strToBigDecimal(String str) {
        try {
            return new BigDecimal(str);
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException("The given value (\"" + str + "\" of class " + str.getClass().getName()
                    + ") does not have a parsable string representation", ex);
        }
    }

    // Copied from Apache Commons Lang

    /**
     * <p>Checks whether the String a valid Java number.</p>
     *
     * <p>Valid numbers include hexadecimal marked with the {@code 0x} or
     * {@code 0X} qualifier, octal numbers, scientific notation and
     * numbers marked with a type qualifier (e.g. 123L).</p>
     *
     * <p>Non-hexadecimal strings beginning with a leading zero are
     * treated as octal values. Thus the string {@code 09} will return
     * {@code false}, since {@code 9} is not a valid octal value.
     * However, numbers beginning with {@code 0.} are treated as decimal.</p>
     *
     * <p>{@code null} and empty/blank {@code String} will return
     * {@code false}.</p>
     *
     * @param str the {@code String} to check
     * @return {@code true} if the string is a correctly formatted number
     * @since 3.5
     */
    public static boolean isCreatable(final String str) {
        if (StringUtils.isEmpty(str)) {
            return false;
        }
        final char[] chars = str.toCharArray();
        int sz = chars.length;
        boolean hasExp = false;
        boolean hasDecPoint = false;
        boolean allowSigns = false;
        boolean foundDigit = false;
        // deal with any possible sign up front
        final int start = chars[0] == '-' || chars[0] == '+' ? 1 : 0;
        if (sz > start + 1 && chars[start] == '0' && !str.contains(".")) { // leading 0, skip if is a decimal number
            if (chars[start + 1] == 'x' || chars[start + 1] == 'X') { // leading 0x/0X
                int i = start + 2;
                if (i == sz) {
                    return false; // str == "0x"
                }
                // checking hex (it can't be anything else)
                for (; i < chars.length; i++) {
                    if ((chars[i] < '0' || chars[i] > '9')
                            && (chars[i] < 'a' || chars[i] > 'f')
                            && (chars[i] < 'A' || chars[i] > 'F')) {
                        return false;
                    }
                }
                return true;
            }
            if (Character.isDigit(chars[start + 1])) {
                // leading 0, but not hex, must be octal
                int i = start + 1;
                for (; i < chars.length; i++) {
                    if (chars[i] < '0' || chars[i] > '7') {
                        return false;
                    }
                }
                return true;
            }
        }
        sz--; // don't want to loop to the last char, check it afterwords
        // for type qualifiers
        int i = start;
        // loop to the next to last char or to the last char if we need another digit to
        // make a valid number (e.g. chars[0..5] = "1234E")
        while (i < sz || i < sz + 1 && allowSigns && !foundDigit) {
            if (chars[i] >= '0' && chars[i] <= '9') {
                foundDigit = true;
                allowSigns = false;

            } else if (chars[i] == '.') {
                if (hasDecPoint || hasExp) {
                    // two decimal points or dec in exponent
                    return false;
                }
                hasDecPoint = true;
            } else if (chars[i] == 'e' || chars[i] == 'E') {
                // we've already taken care of hex.
                if (hasExp) {
                    // two E's
                    return false;
                }
                if (!foundDigit) {
                    return false;
                }
                hasExp = true;
                allowSigns = true;
            } else if (chars[i] == '+' || chars[i] == '-') {
                if (!allowSigns) {
                    return false;
                }
                allowSigns = false;
                foundDigit = false; // we need a digit after the E
            } else {
                return false;
            }
            i++;
        }
        if (i < chars.length) {
            if (chars[i] >= '0' && chars[i] <= '9') {
                // no type qualifier, OK
                return true;
            }
            if (chars[i] == 'e' || chars[i] == 'E') {
                // can't have an E at the last byte
                return false;
            }
            if (chars[i] == '.') {
                if (hasDecPoint || hasExp) {
                    // two decimal points or dec in exponent
                    return false;
                }
                // single trailing decimal point after non-exponent is ok
                return foundDigit;
            }
            if (!allowSigns
                    && (chars[i] == 'd'
                    || chars[i] == 'D'
                    || chars[i] == 'f'
                    || chars[i] == 'F')) {
                return foundDigit;
            }
            if (chars[i] == 'l'
                    || chars[i] == 'L') {
                // not allowing L with an exponent or decimal point
                return foundDigit && !hasExp && !hasDecPoint;
            }
            // last character is illegal
            return false;
        }
        // allowSigns is true iff the val ends in 'E'
        // found digit it to make sure weird stuff like '.' and '1E-' doesn't pass
        return !allowSigns && foundDigit;
    }

}
