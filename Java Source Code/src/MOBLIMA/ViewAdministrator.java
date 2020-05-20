package MOBLIMA;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Represents a view to manage administrators in MOBLIMA.
 * @author	SS1 Group 2
 * @version	1.0
 * @since	2019-11-13
 */
public class ViewAdministrator extends ViewSystem {

    Scanner sc = new Scanner(System.in);
    /**
     * MOBLIMA's admin login system.
     */
    private AdminLoginSystem als;

    /**
     * Constructor of the view.
     * It will load up admin login system.
     * @param systems MOBLIMA's systems hash map
     */
    public ViewAdministrator(HashMap<String, MOBLIMASystem> systems){
        als = (AdminLoginSystem) systems.get("AdminLogin");
    }

    /**
     * Main view of administrator management.
     * Note that this is only accessible if access level is administrator.
     * @param accessLevel the access level, customer or administrator
     */
    @Override
    public void menu(AccessLevel accessLevel) {

        switch (accessLevel) {
            case CUSTOMER:
                clearScreen("Directory: Home > Administrator Database", accessLevel);
                System.out.println("Access denied!");
                break;
            case ADMINISTRATOR:
                int choice = -1;

                do{
                    clearScreen("Directory: Home > Administrator Database", accessLevel);
                    als.printAdminNumber();
                    System.out.println("What do you want to do?");
                    System.out.println("1. Add admin"); //view admin administrator add admin
                    System.out.println("2. Delete admin"); //view admin adminstrator delete admin
                    System.out.println("3. Update admin password"); // view admin adminstrator update password
                    System.out.println("4. List all admins");
                    System.out.println("5. Reset admin database"); // view admi administartor reset database
                    System.out.println("0. Back to home");

                    choice = Helper.robustNextInt();
                    switch (choice) {
                        case 1:
                            add(accessLevel);
                            break;
                        case 2:
                            delete(accessLevel);
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

                    System.out.println();
                }while (choice != 0);
                break;
        }

    }

    /**
     * View to add administrators in the system.
     * Note that this is only accessible if access level is administrator.
     * @param accessLevel the access level, customer or administrator
     */
    @Override
    public void add(AccessLevel accessLevel) {
        clearScreen("Directory: Home > Administrator Database > Add Administrator", accessLevel);
        switch (accessLevel) {
            case CUSTOMER:
                System.out.println("Access denied!");
                break;
            case ADMINISTRATOR:
                String adminName;
                String input;
                System.out.println("Input admin username to be added: ");
                input = sc.nextLine();
                adminName = input.toUpperCase();
                if (als.checkUsername(adminName)){
                    System.out.println("Admin username is already taken");
                    System.out.println("Please try again");
                    System.out.println("Press enter to return to previous screen");
                    sc.nextLine();
                }
                else{
                    System.out.println("Password: ");
                    String adminPassword = als.getPasswordInput();
                    System.out.println("Confirm password: ");
                    String adminPasswordConfirm = als.getPasswordInput();
                    if (adminPassword.equals(adminPasswordConfirm)){
                        als.addAdmin(adminName, adminPassword);
                        System.out.println("Admin successfully added");
                        System.out.println("Press enter to return to previous screen");
                        sc.nextLine();
                    }
                    else{
                        System.out.println("Password did not match");
                        System.out.println("Please try again");
                        System.out.println("Press enter to return to previous screen");
                        sc.nextLine();
                    }
                }
                break;

        }
    }

    /**
     * View to update administrators in the system.
     * Note that this is only accessible if access level is administrator.
     * @param accessLevel the access level, customer or administrator
     */
    @Override
    public void update(AccessLevel accessLevel) {
        clearScreen("Directory: Home > Administrator Database > Edit Administrator", accessLevel);
        switch (accessLevel) {
            case CUSTOMER:
                System.out.println("Access denied!");
                break;
            case ADMINISTRATOR:
                String adminName;
                String input;
                System.out.println("Input admin username: ");
                input = sc.nextLine();
                adminName = input.toUpperCase();
                if (!als.checkUsername(adminName)){
                    System.out.println("No such admin in database");
                    System.out.println("Press enter to return to previous screen");
                    sc.nextLine();
                }
                else{
                    System.out.println("Old password: ");
                    String adminOldPassword = als.getPasswordInput();
                    if(als.checkPassword(adminName, adminOldPassword)){
                        System.out.println("New password: ");
                        String adminNewPassword = als.getPasswordInput();
                        if (als.updateAdmin(adminName, adminNewPassword)){
                            System.out.println("Confirm new password: ");
                            String adminNewPasswordConfirm = als.getPasswordInput();
                            if (adminNewPassword.equals(adminNewPasswordConfirm)){
                                System.out.println("Admin password successfully updated");
                                System.out.println("Press enter to return to previous screen");
                                sc.nextLine();
                            }
                            else{
                                System.out.println("Password did not match");
                                System.out.println("Please try again");
                                System.out.println("Press enter to return to previous screen");
                                sc.nextLine();
                            }
                        }
                        else{
                            System.out.println("New password cannot be same as old password");
                            System.out.println("Please try again");
                            System.out.println("Press enter to return to previous screen");
                            sc.nextLine();
                        }
                    }
                    else{
                        System.out.println("Old password is wrong");
                        System.out.println("Please try again");
                        System.out.println("Press enter to return to previous screen");
                        sc.nextLine();
                    }
                }
                break;
        }
    }

    /**
     * View to delete administrators in the system.
     * Note that this is only accessible if access level is administrator.
     * @param accessLevel the access level, customer or administrator
     */
    @Override
    public void delete(AccessLevel accessLevel) {
        clearScreen("Directory: Home > Administrator Database > Delete Administrator", accessLevel);
        switch (accessLevel) {
            case CUSTOMER:
                System.out.println("Access denied!");
                break;
            case ADMINISTRATOR:
                String adminName;
                String input;
                System.out.println("Input admin username to be deleted: ");
                input = sc.nextLine();
                adminName = input.toUpperCase();
                if (!als.checkUsername(adminName)){
                    System.out.println("No such admin in database");
                    System.out.println("Press enter to return to previous screen");
                    sc.nextLine();
                }
                else{
                    if (als.getAdminNumber()<=1){
                        System.out.println("Unable to remove admin, there must be at least one admin in database");
                        System.out.println("Press enter to return to previous screen");
                        sc.nextLine();
                    }
                    else{
                        System.out.println("You are about to delete the admin. Are you sure to proceed?");
                        System.out.println("Press Y to delete admin, N to cancel action");
                        String action = sc.nextLine();

                        if(action.equals("Y")){
                            als.removeAdmin(adminName);
                            System.out.println("Admin successfully removed");
                            System.out.println("Press enter to return to previous screen");
                            sc.nextLine();
                        }
                        else{
                            System.out.println("Action aborted");
                            System.out.println("Press enter to return to previous screen");
                            sc.nextLine();
                        }
                    }
                }
                break;
        }
    }

    /**
     * View to reset the administrator database.
     * Note that this is only accessible if access level is administrator.
     * @param accessLevel the access level, customer or administrator
     */
    @Override
    public void reset(AccessLevel accessLevel) {
        clearScreen("Directory: Home > Administrator Database > Reset Database", accessLevel);
        switch (accessLevel) {
            case CUSTOMER:
                System.out.println("Access denied!");
                break;
            case ADMINISTRATOR:
                System.out.println("You are about to reset the database. Are you sure to proceed?");
                System.out.println("Press Y to reset database, N to cancel action");
                String action = sc.nextLine();
                if (action.equals("Y")){
                    als.resetDatabase();
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
     * View to list all administrators in the system.
     * Note that this is only accessible if access level is administrator.
     * @param accessLevel the access level, customer or administrator
     */
    public void list(AccessLevel accessLevel) {
        clearScreen("Directory: Home > Administrator Database > List Administrators", accessLevel);
        switch (accessLevel) {
            case CUSTOMER:
                System.out.println("Access denied!");
                break;
            case ADMINISTRATOR:
                als.listAdmin();
                System.out.println("Press enter to return");
                sc.nextLine();
                break;
        }

    }
}
