package MOBLIMA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Represents a view to manage a customer account.
 * @author	SS1 Group 2
 * @version	1.0
 * @since	2019-11-13
 */
public class ViewAccount extends View {

    /**
     * MOBLIMA's customer system.
     */
    private CustomerSystem cs;

    /**
     * MOBLIMA's booking system.
     */
    private BookingSystem bs;
    Scanner sc = new Scanner(System.in);

    /**
     * Constructor of the view.
     * It will load up customer system and booking system.
     * @param systems MOBLIMA's systems hash map
     */
    public ViewAccount(HashMap systems) {
        cs = (CustomerSystem) systems.get("Customer");
        bs = (BookingSystem) systems.get("Booking");
    }

    /**
     * Main account management view and the customerID shall be passed as parameter.
     * The view displayed will be based on the customerID.
     * @param accessLevel the access level
     * @param customerID the customer ID
     */
    public void menuID(AccessLevel accessLevel, String customerID) {
        clearScreen("Directory : Home > Manage Account", accessLevel);
        cs.printCustomerInfo(customerID);
        int choice = -1;

        System.out.println();
        do {
            System.out.println("1. View Past Bookings");
            System.out.println("2. Edit Details");
            System.out.println("0. Back to Main Menu.");
            System.out.println();
            System.out.println("Enter your choice:");
            choice = Helper.robustNextInt();

            switch(choice){
                case 1:
                    ArrayList<Booking> result = bs.getBookingHistory(cs.getCustomer(customerID));
                    if (result != null){
                        if(result.size() >= 1) {
                            System.out.println("The following bookings are found: ");
                            bs.printArrayList(result);
                        } else {
                            System.out.println("No booking found!");
                        }
                    } else {
                        System.out.println("No booking found!");
                    }
                    System.out.println("Press Enter to Continue");
                    sc.nextLine();
                    break;
                case 2:
                    edit(accessLevel, customerID);
                    break;
                default:
                    break;
            }
        } while(choice != 0);
    }

    /**
     * Editing view for account management.
     * The view will differ based on the customer ID passed in.
     * @param accessLevel the access level
     * @param customerID the customer ID
     */
    public void edit(AccessLevel accessLevel, String customerID) {
        clearScreen("Directory: Home > Manage Account > Edit Details", accessLevel);
        Scanner sc = new Scanner(System.in);
        String choice;
        do {
            cs.printCustomerInfo(customerID);
            System.out.println();

            System.out.println("Which info do you want to update?");
            System.out.println("1. Edit customer name");
            System.out.println("2. Edit customer age");
            System.out.println("3. Edit customer email");
            System.out.println("4. Edit customer mobile number");
            System.out.println("5. Edit customer password");
            System.out.println("0. Done editing");
            System.out.println();

            choice = sc.nextLine();

            switch (choice) {
                case "1": {
                    System.out.println("Enter customer new name:");
                    String value = sc.nextLine();
                    cs.updateCustomer(customerID, "name", value);
                    System.out.println("Name has been updated. Press enter to continue");
                    sc.nextLine();
                    break;
                }
                case "2": {
                    System.out.println("Enter customer new age:");
                    String value;
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
                }
                case "3": {
                    System.out.println("Enter customer new email:");
                    String value = sc.nextLine();
                    cs.updateCustomer(customerID, "email", value);
                    System.out.println("Email has been updated. Press enter to continue");
                    sc.nextLine();
                    break;
                }
                case "4": {
                    System.out.println("Enter customer new mobile number:");
                    String value;
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
                }
                case "5": {
                    System.out.println("Enter customer new password:");
                    String value = cs.getPasswordInput();
                    System.out.println("Confirm customer new password:");
                    String valueConfirm = cs.getPasswordInput();
                    if (value.equals(valueConfirm)) {
                        System.out.println("Password has been updated. Press enter to continue");
                        cs.updateCustomer(customerID, "password", value);
                        sc.nextLine();
                    } else {
                        System.out.println("Passwords do not match");
                    }
                    break;
                }
                case "0":
                    break;
                default:
                    System.out.println("Invalid input, please try again");
                    clearScreen("Directory: Home > Manage Account > Edit Details", accessLevel);
                    break;
            }
        } while (!choice.equals("0"));
    }


    /**
     * @deprecated unused view as all views require customer ID
     * @param accessLevel the access level, customer or administrator
     */
    @Deprecated
    @Override
    public void menu(AccessLevel accessLevel) {}

}
