package MOBLIMA;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Represents a view to manage holidays.
 * @author	SS1 Group 2
 * @version	1.0
 * @since	2019-11-13
 */
public class ViewHoliday extends ViewSystem {
    Scanner sc = new Scanner(System.in);

    /**
     * MOBLIMA's holiday system.
     */
    private HolidaySystem hs;

    /**
     * Constructor of the view.
     * It will load up the holiday system.
     * @param systems MOBLIMA's systems hash map
     */
    public ViewHoliday(HashMap<String, MOBLIMASystem> systems){
        hs = (HolidaySystem)systems.get("Holiday");
    }

    /**
     * Main menu of the holiday management view.
     * Note that this is only accessible to administrators.
     * @param accessLevel the access level, customer or administrator
     */
    @Override
    public void menu(AccessLevel accessLevel) {
        clearScreen("Directory: Home > Holiday Database", accessLevel);
        switch (accessLevel) {
            case CUSTOMER:
                System.out.println("Access denied!");
                break;
            case ADMINISTRATOR:
                int option = -1;

                while(option != 0) {
                    clearScreen("Directory: Home > Holiday Database", accessLevel);

                    System.out.println("1. Add holiday");
                    System.out.println("2. Delete holiday");
                    System.out.println("3. View holiday");
                    System.out.println("4. Reset holidays");
                    System.out.println("0. Return to Main Menu");
                    System.out.println();
                    System.out.println("Enter choice: ");

                    option = Helper.robustNextInt();
                    View view = null;

                    switch(option)
                    {
                        case 1:
                            add(accessLevel);
                            break;
                        case 2:
                            delete(accessLevel);
                            break;
                        case 3:
                            list(accessLevel);
                            break;
                        case 4:
                            reset(accessLevel);
                            break;
                        case 0:
                            break;
                        default:
                            System.out.println("Invalid input! Press enter to continue.");
                            sc.nextLine();
                    }
                    System.out.println();
                }
                break;
        }
    }

    /**
     * View to add holidays to the system.
     * Note that this is only accessible to administrators.
     * @param accessLevel the access level, customer or administrator
     */
    @Override
    public void add(AccessLevel accessLevel) {
        clearScreen("Directory: Home > Holiday Database > Add Holiday", accessLevel);
        switch (accessLevel) {
            case CUSTOMER:
                System.out.println("Access denied!");
                break;
            case ADMINISTRATOR:

                System.out.println("Enter the date to be added: (DD/MM/YYYY)");
                String holiday = sc.nextLine();

                if(hs.addHoliday(holiday)){
                    System.out.println("Holiday successfully added!");
                    System.out.println("Press enter to continue.");
                    sc.nextLine();
                }
                else {
                    System.out.println("Unable to add holiday. Press enter to continue.");
                    sc.nextLine();
                }
                break;
        }
    }

    /**
     * View to update holidays in the system.
     * Note that this is only accessible to administrators.
     * @param accessLevel the access level, customer or administrator
     */
    @Override
    public void update(AccessLevel accessLevel) {
        clearScreen("Directory: Home > Holiday Database > Update Holiday", accessLevel);
        switch (accessLevel) {
            case CUSTOMER:
                System.out.println("Access denied!");
                break;
            case ADMINISTRATOR:
                System.out.println("Access denied!");
                break;
        }
    }

    /**
     * View to delete holidays from the system.
     * Note that this is only accessible to administrators.
     * @param accessLevel the access level, customer or administrator
     */
    @Override
    public void delete(AccessLevel accessLevel) {
        clearScreen("Directory: Home > Holiday Database > Delete Holiday", accessLevel);
        switch (accessLevel) {
            case CUSTOMER:
                System.out.println("Access denied!");
                break;
            case ADMINISTRATOR:
                hs.printHoliday();
                System.out.println();
                if (hs.holidayIsEmpty())
                    System.out.println("No holiday to delete.");
                else {
                    System.out.println("Enter the date to be deleted: ");
                    int choice = Helper.robustNextInt();

                    if (hs.deleteHoliday(choice)) {
                        System.out.println("Holiday successfully deleted!");
                        System.out.println();
                        hs.printHoliday();
                        System.out.println();
                    } else {
                        System.out.println("An error has occurred!");
                    }
                }
                System.out.println("Press enter to continue.");
                sc.nextLine();
                break;
        }
    }

    /**
     * View to list holidays in the system.
     * Note that this is only accessible to administrators.
     * @param accessLevel the access level, customer or administrator
     */
    public void list(AccessLevel accessLevel) {
        clearScreen("Directory: Home > Holiday Database > List Holidays", accessLevel);
        switch (accessLevel) {
            case CUSTOMER:
                System.out.println("Access denied");
                break;
            case ADMINISTRATOR:
                hs.printHoliday();
                System.out.println();
                System.out.println("Press enter to go to continue.");
                sc.nextLine();

                break;
        }
    }

    /**
     * View to reset holidays to the system.
     * Note that this is only accessible to administrators.
     * @param accessLevel the access level, customer or administrator
     */
    @Override
    public void reset(AccessLevel accessLevel) {
        clearScreen("Directory: Home > Holiday Database > Reset Database", accessLevel);
        switch (accessLevel) {
            case CUSTOMER:
                System.out.println("Access denied!");
                break;
            case ADMINISTRATOR:
                clearScreen("Directory: Home > Holiday Database > Reset Database", accessLevel);
                System.out.println("You are going to reset the database. Confirm action? (Y/N)");
                char c = sc.next().charAt(0);
                sc.nextLine();
                if (c == 'Y') {
                    if (hs.resetDatabase()) System.out.println("Reset successful!");
                    else System.out.println("Reset unsuccessful!");
                }
                else System.out.println("Invalid input! No movie was deleted.");
                System.out.println();

                System.out.println("Press enter to go to previous page.");
                sc.nextLine();
                break;
        }
    }
}
