package MOBLIMA;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Represents a view to login into the system.
 * @author	SS1 Group 2
 * @version	1.0
 * @since	2019-11-13
 */
public class ViewLogin extends View
{
    private String username;
    private String password;
    private String input;
    private HashMap systems;
    private AdminLoginSystem als;
    private CustomerSystem cs;
    private Scanner sc = new Scanner(System.in);

    /**
     * Constructor of the view.
     * It will load up the admin login system and customer system.
     * @param systems MOBLIMA's systems hash map
     */
    public ViewLogin(HashMap<String, MOBLIMASystem> systems){
        als = (AdminLoginSystem)systems.get("AdminLogin");
        cs = (CustomerSystem)systems.get("Customer");
        this.systems = systems;
    }

    /**
     * Main menu view of the login
     * @param accessLevel the access level, customer or administrator
     * @return if customer successful login, returns the customer username
     */
    public String menuString(AccessLevel accessLevel) {
        switch(accessLevel) {

            case ADMINISTRATOR:

                clearScreen("Directory: Administrator Login", accessLevel);
                System.out.print("Enter username: ");
                input = sc.nextLine();
                username = input.toUpperCase();
                System.out.print("Enter password: ");
                password = als.getPasswordInput();

                if (!als.checkUsername(username)) {
                    System.out.println("Invalid user! Press enter to go back to home.");
                    sc.nextLine();
                } else {
                    if (!als.checkPassword(username, password)) {
                        System.out.println("Invalid password! Press enter to go back to home.");
                        sc.nextLine();
                    } else {
                        ViewAdminMenu view = new ViewAdminMenu(systems);
                        view.menuwithusername(username, AccessLevel.ADMINISTRATOR);
                        return null;
                    }
                }
                break;
            case CUSTOMER:
                int choice = -1;
                while (choice != 0){
                    clearScreen("Directory: Customer Login", accessLevel);
                    System.out.println("Please select an option below: ");
                    System.out.println();
                    System.out.println("1. Login as existing customer");
                    System.out.println("2. Create a new account");
                    System.out.println("0. Back to home");

                    choice = Helper.robustNextInt();
                    // sc.nextLine();

                    switch (choice) {
                        case 1:
                            String string = existingCustomer();
                            if(string!= null){
                                return string;
                            }
                            break;
                        case 2:
                            createCustomer();
                            break;
                        case 0:
                            return null;
                        default:
                            System.out.println("Invalid input!");
                    }
                }
                break;
        }
        return null;
    }

    /**
     * Login view for existing customers.
     * @return if customer successful login, returns the customer username
     */
    private String existingCustomer(){
        clearScreen("Directory: Customer Login > Existing Account", AccessLevel.CUSTOMER);

        System.out.print("Enter username: ");
        System.out.println();
        String username = sc.nextLine();
        if (cs.searchCustomer(username) < 0) {
            System.out.println("Invalid user! Press enter to try again.");
            sc.nextLine();
        } else {
            System.out.print("Enter password: ");
            System.out.println();
            String password = cs.getPasswordInput();
            if (!cs.checkLogin(username, password)) {
                System.out.println("Password incorrect! Press enter to go back.");
                sc.nextLine();
            } else {
                return username;
            }
        }
        return null;
    }

    /**
     * Create account view for new customers.
     */
    private void createCustomer(){
        clearScreen("Directory: Customer Login > Create Account", AccessLevel.CUSTOMER);

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
                clearScreen("Directory: Customer Login > Create Account", AccessLevel.CUSTOMER);
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
                clearScreen("Directory: Customer Login > Create Account", AccessLevel.CUSTOMER);
            }while (confirm.equals("N"));

            boolean repeat = true;

            String password;
            String passwordConfirm;

            clearScreen("Directory: Customer Login > Create Account", AccessLevel.CUSTOMER);

            while (repeat){
                clearScreen("Directory: Customer Login > Create Account", AccessLevel.CUSTOMER);
                System.out.println("Enter password for account creation:");
                password = cs.getPasswordInput();
                System.out.println("Confirm password for account creation:");
                passwordConfirm = cs.getPasswordInput();
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
                        clearScreen("Directory: Customer Login > Create Account", AccessLevel.CUSTOMER);
                        System.out.println("Account creation aborted!");
                        System.out.println("Press enter to return");
                        sc.nextLine();
                        repeat = false;
                    }
                }
            }
        }
    }

    /**
     * @deprecated since now menuString is used
     * @param accessLevel the access level, customer or administrator
     */
    @Deprecated
    @Override
    public void menu(AccessLevel accessLevel) {
        menuString(accessLevel);
    }
}
