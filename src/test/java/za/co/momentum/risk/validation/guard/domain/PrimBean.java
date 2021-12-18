package za.co.momentum.risk.validation.guard.domain;

public class PrimBean {

    private boolean booleanPrim;

    private int intPrim;

    private double doublePrim;

    private float floatPrim;

    private final double nanD = Double.NaN;

    private final float nanF = Float.NaN;

    public int getInt() {
        return intPrim;
    }

    public void setInt( int intPrim ) {
        this.intPrim = intPrim;
    }

    public double getDouble() {
        return doublePrim;
    }

    public void setDouble( double doublePrim ) {
        this.doublePrim = doublePrim;
    }

    public float getFloat() {
        return floatPrim;
    }

    public void setFloat( float floatPrim ) {
        this.floatPrim = floatPrim;
    }

    public boolean isBoolean() {
        return booleanPrim;
    }

    public void setBoolean( boolean booleanPrim ) {
        this.booleanPrim = booleanPrim;
    }

    public double getNanD() {
        return nanD;
    }

    public float getNanF() {
        return nanF;
    }
}
