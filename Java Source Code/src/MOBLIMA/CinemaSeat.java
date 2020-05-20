package MOBLIMA;

import java.io.Serializable;

/**
 * Represents a seat in the cinema layout.
 * @author	SS1 Group 2
 * @version	1.0
 * @since	2019-11-13
 */
public class CinemaSeat implements Serializable {
    /**
     * The unique ID of the seat.
     */
    private String seatID;

    /**
     * State of whether the seat is occupied, or not.
     */
    private boolean assigned = false;

    /**
     * The bookingID associated with the seat should the seat be assigned.
     */
    private String bookingID = "";

    /**
     * Constructor of the cinema seat.
     * @param seatID seat ID to be assigned to this seat
     */
    public CinemaSeat(String seatID){
        this.seatID = seatID;
    }

    /**
     * Get the seat ID of the seat.
     * @return seat ID of the seat
     */
    public String getSeatID(){
        return seatID;
    }

    /**
     * Get the state whether the seat is assigned.
     * @return whether the seat is assigned
     */
    public boolean isAssigned(){
        return assigned;
    }

    /**
     * Get the booking ID of the seat.
     * @return booking ID of the seat, otherwise returns empty string
     */
    public String getBookingID(){
        return bookingID;
    }

    /**
     * Unassign the given seat.
     */
    public void unassign(){
        assigned = false;
        bookingID = "";
    }

    /**
     * Assign the seat to a booking ID.
     * @param bookID booking ID the seat should be attached to
     */

    public void assign(String bookID){
        assigned = true;
        bookingID = bookID;
    }
}
