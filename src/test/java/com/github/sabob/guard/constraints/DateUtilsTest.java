package com.github.sabob.guard.constraints;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.sabob.guard.utils.DateUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

public class DateUtilsTest {

    private final Logger LOGGER = LoggerFactory.getLogger( this.getClass() );

    public static void main( String[] args ) {

        DateUtils dateUtils = new DateUtils();

        LocalDateTime localDateTime = LocalDateTime.MIN.now();
        System.out.println( "LDT = " + localDateTime );

        Optional<LocalDateTime> optional = dateUtils.toLocalDateTime( localDateTime );
        localDateTime = optional.get();

        System.out.println( "LDT to LDT = " + localDateTime );

        LocalDate localDate = dateUtils.toLocalDate( localDateTime );
        System.out.println( "LDT to Ld= " + localDate + "\n" );

//Calendar Test
        Calendar calendar = Calendar.getInstance();
        System.out.println( "CAL = " + calendar );
        optional = dateUtils.toLocalDateTime( calendar );
        localDateTime = optional.get();
        System.out.println( "CAL to LD " + localDateTime );
        localDate = dateUtils.toLocalDate( localDateTime );
        System.out.println( " CAL to LTD to LD = " + localDate + "\n" );

//date Test
        Date date = new Date( 2021, 7, 20 );
        System.out.println( "D = " + date );
        optional = dateUtils.toLocalDateTime( date );
        localDateTime = optional.get();
        System.out.println( "D to LD: " + localDate );
        localDate = dateUtils.toLocalDate( localDateTime );
        System.out.println( " D to LTD to LD = " + localDate + "\n" );

//LocalDAte
        LocalDate date3 = LocalDate.now();
        System.out.println( "LD = " + date3 );
        optional = dateUtils.toLocalDateTime( date3 );
        localDateTime = optional.get();
        System.out.println( "LD to LD: " + localDateTime );
        localDate = dateUtils.toLocalDate( localDateTime );
        System.out.println( "LD to LDT to LD = " + localDate );

//string test
//        String str = "2021/08/20";
//        System.out.println( "String = " + str );
//        localDateTime = dateUtils.toLocalDateTime( str );
//        System.out.println( "String to LocalDate = " + localDateTime );
//        localDate = dateUtils.toLocalDate( localDateTime );
//        System.out.println( "Str to LDT to LD " + localDate );

    }
}
