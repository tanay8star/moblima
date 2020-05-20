package MOBLIMA;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Represents a view to manage customers.
 * @author	SS1 Group 2
 * @version	1.0
 * @since	2019-11-13
 */
public class ViewCustomer extends ViewSystem {

    Scanner sc = new Scanner(System.in);

    /**
     * MOBLIMA's customer system
     */
    private CustomerSystem cs;

    /**
     * Constructor of the view.
     * It will load up the customer system.
     * @param systems MOBLIMA's systems hash map
     */
    public ViewCustomer(HashMap<String, MOBLIMASystem> systems){
        cs = (CustomerSystem) systems.get("Customer");
    }

    /**
     * Main menu of the customer management view.
     * Note that this is only accessible to administrators.
     * @param accessLevel the access level, customer or administrator
     */
    @Override
    public void menu(AccessLevel accessLevel) {

        switch (accessLevel) {
            case CUSTOMER:
                System.out.println("Access denied!");
                break;
            case ADMINISTRATOR:
                int choice = -1;
                do{
                    try {
                        clearScreen("Directory: Home > Customer Database", accessLevel);
                        cs.printCustomerNumber();
                        System.out.println("What do you want to do?");
                        System.out.println("1. Get customer details"); //view admin customer get customer
                        System.out.println("2. Create new customer"); //view customer add customer
                        System.out.println("3. Edit customer details"); //view admin customer edit customer
                        System.out.println("4. List all customers");
                        System.out.println("5. Reset customer database");
                        System.out.println("0. Back to home");

                        choice = Helper.robustNextInt();
                        View view = null;
                        switch (choice) {
                            case 1:
                                get(accessLevel);
                                break;
                            case 2:
                                add(accessLevel);
                                break;
                            case 3:
                                update(accessLevel);
                                break;
                            case 4:
                                list(accessLevel);
                                break;
                            case 5:
                                reset(accessLevel);
                                break;
                            case 0:
                                break;
                            default:
                                System.out.println("Invalid input! Press enter to continue.");
                                sc.nextLine();
                                break;
                        }
                        // if (view != null) view.display();

                        System.out.println();
                    } catch (Exception e) {
                        System.out.println("Invalid input! Press enter to continue.");
                        sc.nextLine();
                    }

                } while (choice != 0);
                break;
        }
    }

    /**
     * View to add customers in the system.
     * Note that this is only accessible to administrators.
     * @param accessLevel the access level, customer or administrator
     */
    @Override
    public void add(AccessLevel accessLevel) {
        clearScreen("Directory: Home > Customer Database > Add Customer", accessLevel);
        switch (accessLevel) {
            case CUSTOMER:
                System.out.println("Access denied!");
                break;
            case ADMINISTRATOR:
                System.out.println("Enter new customer ID: ");
                String customerID = sc.nextLine();

                if(cs.searchCustomer(customerID)>=0){
                    System.out.println("Error. The customer ID already exists inside the database.");
                    System.out.println("Please try again");
                    System.out.println("Press enter to return");
                    sc.nextLine();
                }
                else{
                    String confirm = "N";
                    String customerName;
                    String customerEmail;
                    String customerMobileNo;
                    String customerAge;
                    do{
                        System.out.println();
                        System.out.println("Please provide more information below to successfully create your account");
                        System.out.println("Enter customer name:");
                        customerName = sc.nextLine();
                        System.out.println("Enter customer age:");
                        do{
                            customerAge = sc.nextLine();
                            if (customerAge.length()!=2 || !Helper.isNumeric(customerAge)){
                                System.out.println("Invalid age. Please try again");
                                System.out.println("Enter customer age:");
                            }
                            else{
                                continue;
                            }
                        } while(customerAge.length()!=2 || !Helper.isNumeric(customerAge));
                        System.out.println("Enter customer email:");
                        customerEmail = sc.nextLine();
                        System.out.println("Enter customer mobile number:");
                        do{
                            customerMobileNo = sc.nextLine();
                            if (customerMobileNo.length()!=8 || !Helper.isNumeric(customerMobileNo)){
                                System.out.println("Invalid mobile number. Please try again");
                                System.out.println("Enter customer mobile number:");
                            }
                            else{
                                continue;
                            }
                        } while(customerMobileNo.length()!=8 || !Helper.isNumeric(customerMobileNo));
                        clearScreen("Directory: Home > Customer Database > Add Customer", accessLevel);
                        System.out.println();
                        System.out.println("You have provided the following information");
                        System.out.printf("Customer Name: %s \n", customerName);
                        System.out.printf("Customer Age: %s \n", customerAge);
                        System.out.printf("Customer Email: %s \n", customerEmail);
                        System.out.printf("Customer Mobile Number: %s \n", customerMobileNo);
                        System.out.println();
                        System.out.println("Are you sure the information provided is correct?");
                        System.out.println("Enter Y to proceed with account creation, N to provide information again");
                        confirm = sc.nextLine();
                        while (!confirm.equals("N") && !confirm.equals("Y")){
                            System.out.println("Invalid choice. Please try again");
                            System.out.println("Enter Y to proceed with account creation, N to provide information again");
                            confirm = sc.nextLine();
                        }
                        clearScreen("Directory: Home > Customer Database > Add Customer", accessLevel);
                    }while (confirm.equals("N"));

                    boolean repeat = true;

                    while (repeat){
                        clearScreen("Directory: Home > Customer Database > Add Customer", accessLevel);
                        System.out.println("Enter password for account creation:");
                        String password = cs.getPasswordInput();
                        System.out.println("Confirm password for account creation:");
                        String passwordConfirm = cs.getPasswordInput();
                        if (password.equals(passwordConfirm)){
                            cs.addCustomer(customerID, customerName, customerEmail, customerMobileNo, customerAge, password);
                            System.out.println("Account creation with the following details successful!");
                            System.out.println();
                            cs.printCustomerInfo(customerID);
                            System.out.println();
                            repeat = false;
                            System.out.println("Press enter to return");
                            sc.nextLine();
                        }
                        else{
                            System.out.println("Passwords do not match");
                            System.out.println("Enter Y to continue with password creation for account, N to quit account creation");
                            confirm = sc.nextLine();
                            while (!confirm.equals("N") && !confirm.equals("Y")){
                                System.out.println("Invalid choice. Please try again");
                                System.out.println("Enter Y to continue with password creation for account, N to quit account creation");
                                confirm = sc.nextLine();
                            }
                            if (confirm.equals("N")){
                                clearScreen("Directory: Home > Customer Database > Add Customer", accessLevel);
                                System.out.println("Account creation aborted!");
                                System.out.println("Press enter to return");
                                sc.nextLine();
                                repeat = false;
                            }
                        }
                    }

                }
                break;
        }
    }

    /**
     * View to update customers in the system.
     * Note that this is only accessible to administrators.
     * @param accessLevel the access level, customer or administrator
     */
    @Override
    public void update(AccessLevel accessLevel) {
        clearScreen("Directory: Home > Customer Database > Edit Customer", accessLevel);
        switch (accessLevel) {
            case CUSTOMER:
                System.out.println("Access denied!");
                break;
            case ADMINISTRATOR:
                String value;
                String valueConfirm;

                String choice = "-1";
                System.out.println("Enter customer ID: ");
                String customerID = sc.nextLine();

                if (cs.searchCustomer(customerID) < 0){
                    System.out.println("No such customer inside the database");
                    System.out.println("Press enter to return");
                    sc.nextLine();
                }
                else{
                    while(!choice.equals("0")){
                        clearScreen("Directory: Home > Customer Database > Edit Customer", accessLevel);
                        cs.deserialize();

                        System.out.println("Customer found!");
                        System.out.println();
                        cs.printCustomerInfo(customerID);
                        System.out.println();

                        System.out.println("Which info do you want to update?");
                        System.out.println("1. Edit customer name");
                        System.out.println("2. Edit customer age");
                        System.out.println("3. Edit customer email");
                        System.out.println("4. Edit customer mobile number");
                        System.out.println("5. Edit customer password");
                        System.out.println("0. Back to home");
                        System.out.println();

                        choice = sc.nextLine();

                        switch (choice) {
                            case "1":
                                System.out.println("Enter customer new name:");
                                value = sc.nextLine();
                                cs.updateCustomer(customerID, "name", value);
                                System.out.println("Name has been updated. Press enter to continue");
                                sc.nextLine();
                                break;
                            case "2":
                                System.out.println("Enter customer new age:");
                                do {
                                    value = sc.nextLine();
                                    if (value.length() != 2 || !Helper.isNumeric(value)) {
                                        System.out.println("Invalid age. Please try again");
                                        System.out.println("Enter customer age:");
                                    } else {
                                        continue;
                                    }
                                } while (value.length() != 2 || !Helper.isNumeric(value));
                                cs.updateCustomer(customerID, "age", value);
                                System.out.println("Age has been updated. Press enter to continue");
                                sc.nextLine();
                                break;
                            case "3":
                                System.out.println("Enter customer new email:");
                                value = sc.nextLine();
                                cs.updateCustomer(customerID, "email", value);
                                System.out.println("Email has been updated. Press enter to continue");
                                sc.nextLine();
                                break;
                            case "4":
                                System.out.println("Enter customer new mobile number:");
                                do {
                                    value = sc.nextLine();
                                    if (value.length() != 8 || !Helper.isNumeric(value)) {
                                        System.out.println("Invalid mobile number. Please try again");
                                        System.out.println("Enter customer mobile number:");
                                    } else {
                                        continue;
                                    }
                                } while (value.length() != 8 || !Helper.isNumeric(value));
                                cs.updateCustomer(customerID, "mobile number", value);
                                System.out.println("Mobile number has been updated. Press enter to continue");
                                sc.nextLine();
                                break;
                            case "5":
                                System.out.println("Enter customer new password:");
                                value = cs.getPasswordInput();
                                System.out.println("Confirm customer new password:");
                                valueConfirm = cs.getPasswordInput();
                                if (value.equals(valueConfirm)) {
                                    System.out.println("Password has been updated. Press enter to continue");
                                    cs.updateCustomer(customerID, "password", value);
                                    sc.nextLine();
                                } else {
                                    System.out.println("Passwords do not match");
                                }
                                break;
                            case "0":
                                break;
                            default:
                                System.out.println("Invalid input, please enter to try again");
                                sc.nextLine();
                                break;
                        }
                    }
                }
                break;
        }

    }

    /**
     * View to delete customers in the system.
     * Note that this is only accessible to administrators.
     * @param accessLevel the access level, customer or administrator
     */
    @Override
    public void delete(AccessLevel accessLevel) {
        clearScreen("Directory: Home > Customer Database > Delete Customer", accessLevel);
        System.out.println("Access denied!");

    }

    /**
     * View to reset customer system.
     * Note that this is only accessible to administrators.
     * @param accessLevel the access level, customer or administrator
     */
    @Override
    public void reset(AccessLevel accessLevel) {
        clearScreen("Directory: Home > Customer Database > Reset Database", accessLevel);
        switch (accessLevel) {
            case CUSTOMER:
                System.out.println("Access denied!");
                break;
            case ADMINISTRATOR:
                System.out.println("You are about to reset the database. Are you sure to proceed?");
                System.out.println("Press Y to reset database, N to cancel action");
                String action = sc.nextLine();
                if (action.equals("Y")){
                    cs.resetDatabase();
                    System.out.println("Database reset successful");
                    System.out.println("Press enter to return to previous screen");
                    sc.nextLine();
                }
                else{
                    System.out.println("Action aborted");
                    System.out.println("Press enter to return to previous screen");
                    sc.nextLine();
                }
                break;
        }

    }

    /**
     * View to get customers in the system.
     * Note that this is only accessible to administrators.
     * @param accessLevel the access level, customer or administrator
     */
    public void get(AccessLevel accessLevel) {
        clearScreen("Directory: Home > Customer Database > View Customer Details", accessLevel);
        switch (accessLevel) {
            case CUSTOMER:
                System.out.println("Access denied!");
                break;
            case ADMINISTRATOR:
                System.out.println("Enter customer ID: ");
                String customerID = sc.nextLine();

                if (cs.searchCustomer(customerID) < 0){
                    System.out.println("No such customer inside the database");
                    System.out.println("Press enter to return");
                    sc.nextLine();
                }
                else{
                    clearScreen("Directory: Home > Customer Database > View Customer Details", accessLevel);
                    System.out.println("Customer found!");
                    System.out.println();
                    cs.printCustomerInfo(customerID);
                    System.out.println();
                    System.out.println("Press enter to return");
                    sc.nextLine();
                }
                break;

        }
    }

    /**
     * View to list customers in the system.
     * Note that this is only accessible to administrators.
     * @param accessLevel the access level, customer or administrator
     */
    public void list(AccessLevel accessLevel) {
        clearScreen("Directory: Home > Customer Database > List Customers", accessLevel);
        switch (accessLevel) {
            case CUSTOMER:
                System.out.println("Access denied");
                break;
            case ADMINISTRATOR:
                cs.listCustomer();

                System.out.println("Press enter to return");
                sc.nextLine();
                break;
        }
    }

}
