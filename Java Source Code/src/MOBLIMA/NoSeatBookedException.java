package MOBLIMA;

/**
 * Exception raised when no seats were selection during booking.
 * @author	SS1 Group 2
 * @version	1.0
 * @since	2019-11-13
 */
public class NoSeatBookedException extends Exception {
    public NoSeatBookedException(String message){
        super(message);
    }
}
