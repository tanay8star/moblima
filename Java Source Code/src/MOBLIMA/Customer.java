package MOBLIMA;

import java.io.Serializable;

/**
 * Represents a customer.
 * A customer object is required to perform a booking.
 * @author	SS1 Group 2
 * @version	1.0
 * @since	2019-11-13
 */

public class Customer implements Serializable {

    /**
     * ID of the customer.
     */
    private String customerID;

    /**
     * Name of the customer.
     */
    private String name;

    /**
     * Email of the customer.
     */
    private String email;

    /**
     * Mobile number of the customer.
     */
    private String mobileNo;

    /**
     * Age of the customer.
     */
    private String age;

    /**
     * Password of the customer account.
     */
    private String password;

    /**
     * Constructor of customer, requires details as parameters.
     * @param customerID ID of the customer
     * @param name name of the customer
     * @param email email of the customer
     * @param mobileNo mobile number of the customer
     * @param age age of the customer
     * @param password password of customer account
     */
    public Customer(String customerID, String name, String email, String mobileNo, String age, String password){
        this.customerID = customerID;
        this.name = name;
        this.email = email;
        this.mobileNo = mobileNo;
        this.age = age;
        this.password = password;
    }

    /**
     * Get the ID of the customer.
     * @return ID of the customer
     */
    public String getCustomerID() {
        return customerID;
    }

    /**
     * Get the name of the customer.
     * @return name of the customer
     */
    public String getName() {
        return name;
    }

    /**
     * Get the email of the customer
     * @return email of the customer
     */
    public String getEmail() {
        return email;
    }

    /**
     * Get the mobile number of the customer.
     * @return mobile number of the customer
     */
    public String getMobileNo() {
        return mobileNo;
    }

    /**
     * Get the age of the customer.
     * @return age of the customer
     */
    public String getAge() {
        return age;
    }

    /**
     * Get the password of the customer.
     * @return password of the customer.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the name of the customer.
     * @param name name of the customer
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Set the email of the customer.
     * @param email email of the customer
     */
    public void setEmail(String email){
        this.email = email;
    }

    /**
     * Set the mobile number of the customer.
     * @param mobileNo mobile number of the customer
     */
    public void setMobileNo(String mobileNo){
        this.mobileNo = mobileNo;
    }

    /**
     * Set the age of the customer.
     * @param age age of the customer
     */
    public void setAge(String age){
        this.age = age;
    }

    /**
     * Set the password of the user.
     * @param password password of the user
     */
    public void setPassword(String password){
        this.password = password;
    }

}