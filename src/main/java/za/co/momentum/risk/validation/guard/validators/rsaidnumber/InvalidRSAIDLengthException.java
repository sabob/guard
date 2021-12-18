package za.co.momentum.risk.validation.guard.validators.rsaidnumber;

public class InvalidRSAIDLengthException extends Exception {

    public InvalidRSAIDLengthException() {
        super( "ID Length invalid: ZA ID Number must be 13 digits long" );
    }

    public InvalidRSAIDLengthException( String msg ) {
        super( msg );
    }

}
