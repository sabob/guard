package com.github.sabob.guard.validators.rsaidnumber;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

/**
 * Github project: https://github.com/talifhani/za-id-validator-java
 *
 * @author Tali Luvhengo <tali@semicolon.co.za>
 */
enum Nationality {
    SOUTHAFRICAN,
    NONSOUTHAFRICAN,
    REFUGEE
}

/**
 * @author Tali Luvhengo <tali@semicolon.co.za>
 */
enum Gender {
    MALE,
    FEMALE
}

/**
 * @author Tali Luvhengo <tali@semicolon.co.za>
 */
public class RSAIDNumberParser {

    private LocalDate dateOfBirth;

    private int genderNum;

    private int citizenshipNum;

    private int checkBit;

    private Year pivotYear;

    private String idNumber;

    /**
     * Creates a formatter than can convert a 2 digit year(e.g 99) to a 4 digit year (e.g 1999)
     *
     * @param pivotYear
     * @return DateTimeFormatter
     */
    public static final DateTimeFormatter getTwoYearFormatter(Year pivotYear) {
        return new DateTimeFormatterBuilder()
                .appendValueReduced(ChronoField.YEAR, 2, 2, pivotYear.getValue())
                .toFormatter();
    }

    /**
     * Breaks down id number into its meaningful parts
     * @throws DateTimeException if the birth date contains invalid values eg a dateOfMonth of 35
     */
    private void breakDownIDNumber() throws DateTimeException {
        String birthDate = idNumber.substring(0, 6);

        if (pivotYear == null) {
            pivotYear = Year.of(Year.now().getValue() - 100);// Assume ID belongs to someone not older than 100 years
        }

        int year = Year.parse(birthDate.substring(0, 2), RSAIDNumberParser.getTwoYearFormatter(pivotYear)).getValue();

        this.dateOfBirth = LocalDate.of(
                year,
                Month.of(Integer.parseInt(birthDate.substring(2, 4))),
                Integer.parseInt(birthDate.substring(4))
        );

        this.genderNum = Integer.parseInt(idNumber.substring(6, 10));
        this.citizenshipNum = Integer.parseInt(idNumber.substring(10, 11));
        this.checkBit = Integer.parseInt(idNumber.substring(12, 13));
    }

    /**
     * Returns a parsed IDNumberData object
     *
     * @param idNumber
     * @return IDNumberData
     * @throws Exception throws Exception if invalid length is provided.
     */
    public RSAIDNumberData parse(String idNumber) throws InvalidRSAIDLengthException, DateTimeException {
        if (idNumber.length() != 13) {
            throw new InvalidRSAIDLengthException();
        }

        this.idNumber = idNumber;

        this.breakDownIDNumber();

        return new RSAIDNumberData(
                idNumber,
                this.dateOfBirth,
                this.genderNum >= 5000 ? Gender.MALE : Gender.FEMALE,
                this.citizenshipNum == 0 ? Nationality.SOUTHAFRICAN : (this.citizenshipNum == 1 ? Nationality.NONSOUTHAFRICAN : Nationality.REFUGEE),
                this.checkBit == this.calculateCheckBit()
        );
    }

    /**
     * Calculates Checksum bit using Luhn Algorithm
     *
     * @return int
     */
    private int calculateCheckBit() {
        String withoutChecksum = idNumber.substring(0, idNumber.length() - 1);
        return Luhn.generate(withoutChecksum);
    }

    /**
     * The first 2 digits of the South African ID number are the year.
     * The problem with parsing this is we dont know if 19 means 2019 or 1919.
     * So the parse by default assumes the ID Number belongs to someone not older than 100 years.
     * So if this year is 2020, any years less than 20 will be considered from this century.
     * e.g 15 and 19 will translate to 2015 and 2019 respectively.
     * While 20, 25 will translate to 1920, 1925 respectively. This is the concept of a pivotYear.
     *
     * @param pivotYear
     * @return IDNumberParser
     */
    public RSAIDNumberParser setPivotYear(Year pivotYear) {
        this.pivotYear = pivotYear;

        return this;
    }
}
