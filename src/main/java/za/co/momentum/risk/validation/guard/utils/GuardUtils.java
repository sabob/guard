package za.co.momentum.risk.validation.guard.utils;

import za.co.momentum.risk.validation.guard.GuardContext;
import za.co.momentum.risk.validation.guard.violation.Violation;
import za.co.momentum.risk.validation.guard.violation.Violations;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class GuardUtils {

    private static Map<String, Properties> propertiesMap = new ConcurrentHashMap();

    public static Violation toViolationWithTemplateMessage( GuardContext guardContext, String messageTemplate, Object... args ) {
        Violation violation = guardContext.toViolation();
        if ( violation.getMessage() == null ) {

            String message = createMessage( messageTemplate, args );
            violation.setMessage( message );
        }
        return violation;
    }

    public static String createMessage( String messageTemplate, Object... args ) {
        String message = String.format( messageTemplate, args );
        return message;
    }

    public static Properties getProperties() {
        return getProperties( "guard.properties" );
    }

    public static Properties getProperties( String filename ) {

        Properties properties = propertiesMap.get( filename );

        if ( properties == null ) {
            properties = loadProperties( filename );
            propertiesMap.put( filename, properties );
        }
        return properties;
    }

    public static Properties loadProperties( String filename ) {

        Properties properties = new Properties();

        InputStream input = null;

        try {
            input = GuardUtils.class.getClassLoader().getResourceAsStream( filename );

            if ( input == null ) {
                input = Thread.currentThread().getContextClassLoader().getResourceAsStream( filename );
            }

            if ( input == null ) {
                throw new RuntimeException( filename + " could not be found on the classpath!" );
            }

            properties.load( input );

        } catch ( IOException ex ) {
            throw new RuntimeException( ex );

        } finally {
            closeQuietly( input );
        }

        return properties;
    }

    public static void closeQuietly( Closeable closeable ) {
        if ( closeable == null ) {
            return;
        }

        try {
            closeable.close();
        } catch ( Exception ex ) {
            throw new RuntimeException( ex );
        }
    }

    public static Object[] prepend( Object first, Object[] array ) {
        Object[] copy = new Object[array.length + 1];
        copy[0] = first;
        System.arraycopy( array, 0, copy, 1, array.length );
        return copy;
    }

    public static List<BasicViolationMessage> toBasicViolationMessages( Violations violations ) {
        return toBasicViolationMessages( violations.getList() );
    }

    public static List<BasicViolationMessage> toBasicViolationMessages( List<Violation> violationList ) {

        List<BasicViolationMessage> messages = new ArrayList<>();

        for ( Violation violation : violationList ) {
            BasicViolationMessage message = new BasicViolationMessage();
            message.setName( violation.getName() );
            message.setMessage( violation.getMessage() );
            message.setCode( violation.getCode() );
            messages.add( message );
        }

        return messages;
    }

    public static LocalDateTime toLocalDateTime( String constraintName, Object value, Optional<LocalDateTime> optional ) {

        if ( !optional.isPresent() ) {
            throw new IllegalStateException( constraintName + " constraint can only be applied to Date, Calendar, LocalDate and LocalDateTime. " +
                    "The type of the given value is: " + value.getClass() );
        }

        return optional.get();
    }

    public static String toString( String constraintName, Object value ) {

        if ( value == null ) {
            throw new IllegalArgumentException( "value is required!" );
        }

        if ( !(value instanceof String) ) {
            throw new IllegalStateException( constraintName + " constraint can only be applied to String.class. The type of the given value is: " + value.getClass() );
        }

        return String.valueOf( value );
    }

    public static int getIterableSize( Object value ) {
        if ( value == null ) {
            return 0;
        }

        if ( (value instanceof Collection) ) {
            Collection col = ( Collection ) value;
            return col.size();
        }

        if ( value.getClass().isArray() ) {
            Object[] array = ( Object[] ) value;

            return array.length;
        }

        if ( value instanceof Map ) {
            Map map = ( Map ) value;
            return map.size();
        }
        return 0;
    }

    public static boolean isIterable( Object obj ) {

        if ( obj == null ) {
            return false;
        }

        if ( obj instanceof Iterable ) {
            return true;
        } else if ( obj instanceof Map ) {
            return true;
        } else if ( obj.getClass().isArray() ) {
            return true;
        }

        return false;
    }

    public static List toList( Object value ) {

        if ( value instanceof List ) {
            return ( List ) value;
        }

        if ( value instanceof Collection ) {

            Collection col = ( Collection ) value;
            return new ArrayList( col );
        }

        if ( value != null && value.getClass().isArray() ) {
            Object[] array = ( Object[] ) value;
            return Arrays.asList( array );
        }

        List list = new ArrayList();
        list.add( value );
        return list;
    }

    public Object unwrapVarArgs( Object value ) {

        Object newValue = value;

        if ( value != null && value.getClass().isArray() ) {
            Object[] array = ( Object[] ) value;
            if ( array.length == 1 ) {
                newValue = array[0];
            } else {
                newValue = Arrays.asList( array );
            }
        }

        return newValue;
    }

    public static List unwrapVarArgsToList( Object value ) {

        Object newValue = value;

        if ( value != null && value.getClass().isArray() ) {
            Object[] array = ( Object[] ) value;
            if ( array.length == 1 ) {
                newValue = array[0];
            }
        }
        return toList( newValue );
    }

}
