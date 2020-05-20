package MOBLIMA;

import java.util.HashMap;

/**
 * Represents a view for administrator main menu.
 * @author	SS1 Group 2
 * @version	1.0
 * @since	2019-11-13
 */
public class ViewAdminMenu extends View {
    /**
     * Hash map of MOBLIMA's systems.
     */
    private HashMap<String, MOBLIMASystem> systems;

    /**
     * Constructor of the view.
     * It will save MOBLIMA's systems.
     * @param systems MOBLIMA's systems hash map
     */
    public ViewAdminMenu(HashMap<String, MOBLIMASystem> systems){
        this.systems = systems;
    }

    /**
     * @deprecated unused view, as view requires administrator username
     * @param accessLevel the access level, customer or administrator
     */
    @Deprecated
    public void menu(AccessLevel accessLevel){}

    /**
     * Administrator main menu.
     * Note that this is only accessible if access level is administrator.
     * @param username the administrator username
     * @param accessLevel the access level, customer or administrator
     */
    public void menuwithusername(String username, AccessLevel accessLevel) {
        switch (accessLevel) {
            case CUSTOMER:
                clearScreen("Directory: Home", accessLevel);
                System.out.println("Access denied!");
                break;
            case ADMINISTRATOR:
                int i;
                int choice = -1;

                while (choice != 0) {
                    clearScreen("Directory: Home", accessLevel);

                    System.out.println("Welcome back, " + username + "!");
                    System.out.println();
                    System.out.println("What do you want to do?");
                    System.out.println("1. Configure cineplexes");
                    System.out.println("2. Configure movies");
                    System.out.println("3. Configure shows");
                    System.out.println("4. Configure bookings");
                    System.out.println("5. Configure prices");
                    System.out.println("6. Configure holidays");
                    System.out.println("7. Configure administrators");
                    System.out.println("8. Configure customer");
                    System.out.println("0. Back to home");

                    choice = Helper.robustNextInt();

                    View view = null;
                    switch (choice) {
                        case 1:
                            view = new ViewCineplex(systems);
                            break;
                        case 2:
                            view = new ViewMovie(systems);
                            break;
                        case 3:
                            view = new ViewShow(systems);
                            break;
                        case 4:
                            view = new ViewBooking(systems);
                            break;
                        case 5:
                            view = new ViewPrice(systems);
                            break;
                        case 6:
                            view = new ViewHoliday(systems);
                            break;
                        case 7:
                            view = new ViewAdministrator(systems);
                            break;
                        case 8:
                            view = new ViewCustomer(systems);
                            break;
                        case 0:
                            break;
                        default:
                            System.out.println("Invalid input!");
                    }

                    if (view != null) view.menu(AccessLevel.ADMINISTRATOR);
                    System.out.println();
                }
                break;
        }
    }
}
