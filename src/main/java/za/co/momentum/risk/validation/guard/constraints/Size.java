package za.co.momentum.risk.validation.guard.constraints;

import za.co.momentum.risk.validation.guard.utils.GuardUtils;

import java.util.Collection;
import java.util.Map;

public class Size extends AbstractNumericalBetweenConstraint {

    public Size() {
    }

    public Size( int min, int max ) {
        super( min, max );
    }

    public Size max( int max ) {
        this.max = max;
        return this;
    }

    public Size min( int min ) {
        this.min = min;
        return this;
    }

    @Override
    protected String getMaxTemplate() {
        String template = GuardUtils.getProperties().getProperty( "size.max.message" );
        return template;
    }

    @Override

    protected String getMinTemplate() {
        String template = GuardUtils.getProperties().getProperty( "size.min.message" );
        return template;
    }

    @Override
    protected String getBetweenTemplate() {
        String template = GuardUtils.getProperties().getProperty( "size.between.message" );
        return template;
    }

    protected String getPartName() {
        return "size";
    }

    @Override
    protected int getLength( Object value ) {

        if ( value instanceof Collection ) {
            Collection collection = ( Collection ) value;
            return collection.size();

        }

        if ( value instanceof Map ) {
            Map map = ( Map ) value;
            return map.size();
        }

        if ( value.getClass().isArray() ) {
            Object[] array = ( Object[] ) value;
            return array.length;
        }

        if ( value instanceof String ) {
            String str = ( String ) value;
            return str.length();
        }

        throw new IllegalStateException( value.getClass() + " is not a valid type for the " +
                this.getClass().getSimpleName() +
                " constraint. " + this.getClass().getSimpleName() +
                " can only be applied to Strings, Maps, Arrays and Collections." );
    }
}