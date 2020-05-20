package MOBLIMA;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;


/**
 * Represents the entity booking which can be made by a customer.
 * @author	SS1 Group 2
 * @version	1.0
 * @since	2019-11-13
 */
public class Booking implements Serializable, Cloneable {

    /**
     * A payment entity which stores the payment details of the booking.
     */
    private Payment payment = null;

    /**
     * The show associated with the booking.
     */
    private Show show = null;

    /**
     * The date booking was made.
     */
    private Date date = null;

    /**
     * Represents the transaction ID of the booking.
     */
    private String bookingID = null;

    /**
     * The customer who made the booking.
     */
    private Customer customer = null;

    /**
     * The status of the booking, which is specified in the enumeration.
     */
    private Status status = Status.PROCESSING;

    /**
     * The list of seat numbers booked by the booking.
     */
    private ArrayList<String> seats = new ArrayList<>();

    /**
     * Constructor for the booking entity.
     * @param show show associated with the booking
     * @param customer customer who made the booking
     * @param date date booking was made
     * @param bookingID transaction ID of the booking
     */
    public Booking(Show show, Customer customer, Date date, String bookingID){
        this.show = show;
        this.customer= customer;
        this.date = date;
        this.bookingID = bookingID;
    }


    /**
     * Gets the transaction ID of the booking.
     * @return transaction ID of the booking
     */
    public String getBookingID() {
        return bookingID;
    }

    /**
     * Gets the customer who made the booking.
     * @return customer who made the booking
     */
    public Customer getCustomer(){
        return customer;
    }

    /**
     * Gets the status of the booking.
     * @return status of the booking
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Gets the show associated with the booking.
     * @return show associated with the booking
     */
    public Show getShow(){return show;}

    /**
     * Gets the payment entity of the booking.
     * @return payment entity of the booking
     */
    public Payment getPayment(){return payment;}

    /**
     * Prints the booking details.
     */
    public void print(){
        System.out.println("==========================================");
        System.out.println("Booking No:      " + bookingID);
        System.out.println("Customer No:     " + customer.getCustomerID());
        System.out.println("Show Details:    ");
        show.print();
        System.out.println("Seats Booked:    "+ seats);
        System.out.println("Status:          "+ status);
        System.out.println("Payment Details: ");
        System.out.println("==========================================");
        payment.print();
    }

    /**
     * Adds a seat to the booking seat list
     * @param newSeat seat number to be added
     * @return whether adding the seat was successful
     */
    public boolean addSeat(String newSeat){
        try {
            if (seats == null){
                seats = new ArrayList<>();
            }
            seats.add(newSeat);
            return true;
        } catch(Exception e){
            System.out.println("Error");
            return false;
        }
    }

    /**
     * Initiates the payment of the booking.
     * @param bookingID transaction ID of the booking
     * @param amount amount to be paid
     * @param paymentMode how the payment is done
     * @return whether payment is successful
     */
    public boolean initiatePayment(String bookingID, double amount, String paymentMode){
        if(payment == null){
            payment = new Payment(amount, bookingID);
        }
        try{
            status = Status.AWAITING_PAYMENT;
            if(payment.initiatePayment(paymentMode)){
                status = Status.COMPLETED;
                return true;
            }
        } catch (Exception e){
            return false;
        }
        return false;
    }

    /**
     * Gets the seats of the booking
     * @return seats of the booking
     */
    public ArrayList<String> getSeats(){
        return seats;
    }




}