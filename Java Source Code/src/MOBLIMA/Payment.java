package MOBLIMA;

import java.io.Serializable;
import java.util.Date;
/**
 * Represents a payment object.
 * @author	SS1 Group 2
 * @version	1.0
 * @since	2019-11-13
 */
public class Payment implements Serializable {

    /**
     * The booking ID associated with the payment.
     */
    String bookingID = null;

    /**
     * The datetime payment was made.
     */
    Date datetime = null;

    /**
     * The mode of payment.
     */
    String paymentMode = null;

    /**
     * The status of the payment, which takes only the enumerated values.
     */
    PaymentStatus status;

    /**
     * The amount paid in the payment.
     */
    double amount = 0;

    /**
     * Constructor of the payment object.
     * @param amount amount paid
     * @param bookingID ID of the associated booking
     */
    public Payment(double amount, String bookingID){
        this.bookingID = bookingID;
        this.status = PaymentStatus.AWAITING_PAYMENT;
        this.amount = amount;
    }

    /**
     * Get the booking ID
     * @return the booking ID
     */
    public String getBookingID() {
        return bookingID;
    }

    /**
     * Get the datetime of payment
     * @return datetime of payment
     */
    public Date getDatetime() {
        return datetime;
    }

    /**
     * Get the payment mode
     * @return the payment mode
     */
    public String getPaymentMode() {
        return paymentMode;
    }

    /**
     * Get the status of the payment.
     * @return the status of the payment
     */
    public PaymentStatus getStatus() {
        return status;
    }

    /**
     * Get the amount paid in the payment.
     * @return amound paid in the payment
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Initiate the payment given the payment mode.
     * @param paymentMode payment mode of the payment
     * @return whether payment was successful
     */
    public boolean initiatePayment(String paymentMode){
        try {
            this.status = PaymentStatus.PAYMENT_IN_PROGRESS;
            this.datetime = new Date();
            this.paymentMode = paymentMode;
            this.status = PaymentStatus.PAYMENT_SUCCESSFUL;
            return true;
        } catch (Exception e){
            return false;
        }
    }

    /**
     * Prints the payment details.
     */
    public void print(){
        System.out.println("Booking ID:      " + bookingID);
        System.out.println("Payment Status:  " + status);
        System.out.println("Amount:          " + amount);
    }
}