package com.github.sabob.guard.utils;

import com.github.sabob.guard.domain.PrimBean;

public class NumberUtilsTest {

    public static void main( String[] args ) {

        PrimBean bean = new PrimBean();

        bean.setDouble( 0.05d );
        bean.setFloat( 0.1235478f );
        bean.setInt( 0 );

        double isNanD = bean.getNanD();
        float isNanF = bean.getNanF();
        double isDouble = bean.getDouble();
        float isFlout = bean.getFloat();
        int isInt = bean.getInt();

// TEST
        // LessThan
        boolean result = NumberUtils.isLessThan( 5, isInt );
        System.out.println( "Less than result must be false: " + result );
        result = NumberUtils.isLessThan( isDouble, isFlout );
        System.out.println( "Less than result must be true: " + result + "\n" );

        // GreaterThan
        result = NumberUtils.isGreaterThan( isFlout, isDouble );
        System.out.println( "Greater than must be true: " + result );
        result = NumberUtils.isGreaterThan( isInt, isDouble );
        System.out.println( "Greater than must be false" + result + "\n" );

        //Equal
        result = NumberUtils.equal( 0, isInt );
        System.out.println( "Equal must be true: " + result );
        result = NumberUtils.equal( 0, isNanD );
        System.out.println( "Equal must be false: " + result + "\n" );

    }

}
