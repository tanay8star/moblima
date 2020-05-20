package MOBLIMA;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Represents a view to manage a cineplexes.
 * @author	SS1 Group 2
 * @version	1.0
 * @since	2019-11-13
 */
public class ViewCineplex extends ViewSystem {

    Scanner sc = new Scanner(System.in);

    /**
     * MOBLIMA's cineplex system
     */
    CineplexSystem cs;

    /**
     * Constructor of the view.
     * It will load up cineplex system.
     * @param systems MOBLIMA's systems hash map
     */
    public ViewCineplex (HashMap<String, MOBLIMASystem> systems){
        cs = (CineplexSystem)systems.get("Cineplex");
    }

    /**
     * Main menu of the cineplex management view.
     * Note that it is only accessible to administrators.
     * @param accessLevel the access level, customer or administrator
     */
    @Override
    public void menu(AccessLevel accessLevel) {
        clearScreen("Directory: Home > Cineplex Database", accessLevel);
        switch (accessLevel) {
            case CUSTOMER:
                System.out.println("Access denied!");
                break;
            case ADMINISTRATOR:
                int i;
                int choice = -1;

                while (choice != 0) {
                    try {
                        clearScreen("Directory: Home > Cineplex Database", accessLevel);
                        cs.listCineplex();

                        System.out.println("What do you want to do?");
                        System.out.println("1. Get cineplex details");
                        System.out.println("2. Add cinema hall to cineplex");
                        System.out.println("3. Delete cinema hall from cineplex");
                        System.out.println("4. Reset cineplex database (not available yet)");
                        System.out.println("0. Back to home");

                        choice = Helper.robustNextInt();

                        View view = null;
                        switch (choice) {
                            case 1:
                                viewDetails(accessLevel);
                                break;
                            case 2:
                                add(accessLevel);
                                break;
                            case 3:
                                delete(accessLevel);
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

                        //if (view != null) view.display();

                        System.out.println();
                    } catch (Exception e) {
                        System.out.println("Invalid input! Press enter to continue.");
                        sc.nextLine();
                    }

                }
                break;

        }

    }

    /**
     * View to add cineplex to database.
     * Note that it is only accessible to administrators.
     * @param accessLevel the access level, customer or administrator
     */
    @Override
    public void add(AccessLevel accessLevel) {
        clearScreen("Directory: Home > Cineplex Database > Add Cineplex", accessLevel);
        switch (accessLevel) {
            case CUSTOMER:
                System.out.println("Access denied!");
                break;
            case ADMINISTRATOR:
                Cineplex cineplex = cs.selectCineplex();
                if (cineplex != null) {
                    System.out.println(ConsoleColours.WHITE_BOLD + "Enter the cinema type:" + ConsoleColours.RESET);
                    System.out.println("1. Regular");
                    System.out.println("2. Golden Suite");

                    int statChoice = Helper.robustNextInt();
                    switch (statChoice) {
                        case 1:
                            String hallID = cineplex.getCineplexID() + (cineplex.getCinemaHalls().size() + 1);
                            CinemaHall cinemaHall = new CinemaHall("Regular", hallID);
                            cineplex.addCinemaHall(cinemaHall);
                            break;
                        case 2:
                            String hallID2 = cineplex.getCineplexID() + (cineplex.getCinemaHalls().size() + 1);
                            CinemaHall cinemaHall2 = new CinemaHall("Golden Suite", hallID2);
                            cineplex.addCinemaHall(cinemaHall2);
                            break;
                        default:
                            System.out.println("Invalid input!");
                            break;
                    }
                    cs.serialize();
                    System.out.println();
                } else {
                    System.out.println("Invalid Input!");
                }
                System.out.println("Press enter to go to previous page.");
                sc.nextLine();
                break;

        }

    }

    /**
     * View to update cineplex in database.
     * Note that it is only accessible to administrators.
     * @param accessLevel the access level, customer or administrator
     */
    @Override
    public void update(AccessLevel accessLevel) {
        clearScreen("Directory: Home > Cineplex Database > Edit Cineplex", accessLevel);
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
     * View to delete cineplex from database.
     * Note that it is only accessible to administrators.
     * @param accessLevel the access level, customer or administrator
     */
    @Override
    public void delete(AccessLevel accessLevel) {
        clearScreen("Directory: Home > Cineplex Database > Delete Cineplex", accessLevel);
        switch (accessLevel) {
            case CUSTOMER:
                System.out.println("Access denied!");
                break;
            case ADMINISTRATOR:
                cs.listCineplex();
                System.out.println("Enter cineplex number to delete: ");
                int i = Helper.robustNextInt();
                Cineplex cineplex = cs.getCineplex(i);

                cineplex.listCinemaHall();
                System.out.println("Enter cinema hall number to delete: ");
                i = Helper.robustNextInt();
                CinemaHall cinemaHall = cineplex.getCinemaHall(i);
                System.out.println("You are going to remove " + cinemaHall.getHallID() + ". Confirm deletion? (Y/N)");
                char c = sc.next().charAt(0);
                sc.nextLine();
                if (c == 'Y') {
                    if (cineplex.removeCinemaHall(cinemaHall)) {System.out.println("Deletion successful!"); cs.serialize();}
                    else System.out.println("Deletion unsuccessful!");
                }
                else System.out.println("No Cinema Hall was deleted.");

                System.out.println();

                System.out.println("Press enter to go to previous page.");
                sc.nextLine();
                break;

        }

    }

    /**
     * View to list cineplexes in database.
     * Note that it is only accessible to administrators.
     * @param accessLevel the access level, customer or administrator
     */
    @Override
    public void list(AccessLevel accessLevel) {
        clearScreen("Directory: Home > Cineplex Database > List Cineplexes", accessLevel);
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
     * View to reset cineplex database.
     * Note that it is only accessible to administrators.
     * @param accessLevel the access level, customer or administrator
     */
    @Override
    public void reset(AccessLevel accessLevel) {
        clearScreen("Directory: Home > Cineplex Database > Reset Database", accessLevel);
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
     * View to get cineplex details from database.
     * Note that it is only accessible to administrators.
     * @param accessLevel the access level, customer or administrator
     */
    public void viewDetails(AccessLevel accessLevel) {
        clearScreen("Directory: Home > Cineplex Database > View Cineplex Details", accessLevel);
        switch (accessLevel) {
            case CUSTOMER:
                System.out.println("Access denied!");
                break;
            case ADMINISTRATOR:

                cs.listCineplex();

                System.out.println("Enter cineplex number to view: ");
                int i = Helper.robustNextInt();
                Cineplex cineplex = cs.getCineplex(i);
                System.out.println();

                System.out.println("Name:");
                System.out.println(cineplex.getName());
                System.out.println();


                cineplex.listCinemaHall();

                System.out.println();

                System.out.println("Press enter to go to previous page.");
                sc.nextLine();
                break;


        }
    }
}
