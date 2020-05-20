package MOBLIMA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Represents a view for customer main menu.
 * @author	SS1 Group 2
 * @version	1.0
 * @since	2019-11-13
 */
public class ViewCustomerMenu extends View {

    /**
     * Hash map of MOBLIMA's systems.
     */
    private HashMap<String, MOBLIMASystem> systems;

    /**
     * Constructor of the view.
     * It will save MOBLIMA's systems.
     * @param systems MOBLIMA's systems hash map
     */
    public ViewCustomerMenu(HashMap systems) {
        this.systems = systems;
    }

    /**
     * Customer main menu.
     * @param accessLevel the access level, customer or administrator
     */
    public void menu(AccessLevel accessLevel) {
        Scanner sc = new Scanner(System.in);
        int i;
        int choice = -1;

        while (choice != 0) {
            clearScreen("Directory: Home", accessLevel);
            System.out.println(ConsoleColours.CYAN + "╔══════════════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                               " + ConsoleColours.WHITE_BOLD + "    HOT MOVIES RIGHT NOW    " + ConsoleColours.CYAN + "                               ║");
            System.out.println("╚══════════════════════════════════════════════════════════════════════════════════════════╝" + ConsoleColours.RESET);
            MovieSystem ms = new MovieSystem();
            HashMap<Integer, ArrayList<Object>> results = ms.rankMoviebyBooking();
            int count = -1;
            String spaces1 = null;
            String spaces2 = null;
            System.out.println(ConsoleColours.CYAN + "┌────────────────────────────┐ ┌────────────────────────────┐ ┌────────────────────────────┐ ");
            String movie1 = ((Movie)results.get(1).get(0)).getTitle();
            count = 15 - movie1.length() / 2;
            spaces1 = String.format("%" + (count-1) +"s", "");
            spaces2 = String.format("%" + (30 - count - movie1.length()-1) +"s", "");
            System.out.print("║" + ConsoleColours.WHITE_BOLD + spaces1 + movie1 + spaces2 + ConsoleColours.CYAN +  "║ ");
            String movie2 = ((Movie)results.get(2).get(0)).getTitle();
            count = 15 - movie2.length() / 2;
            spaces1 = String.format("%" + (count-1) +"s", "");
            spaces2 = String.format("%" + (30 - count - movie2.length()-1) +"s", "");
            System.out.print("║" + ConsoleColours.WHITE_BOLD + spaces1 + movie2 + spaces2 + ConsoleColours.CYAN +  "║ ");
            String movie3 = ((Movie)results.get(3).get(0)).getTitle();
            count = 15 - movie3.length() / 2;
            spaces1 = String.format("%" + (count-1) +"s", "");
            spaces2 = String.format("%" + (30 - count - movie3.length()-1) +"s", "");
            System.out.println("║" + ConsoleColours.WHITE_BOLD + spaces1 + movie3 + spaces2 + ConsoleColours.CYAN +  "║ ");
            System.out.println(ConsoleColours.CYAN + "└────────────────────────────┘ └────────────────────────────┘ └────────────────────────────┘" + ConsoleColours.RESET);
            System.out.println();

            // Finding the EXCELLENT MOVIES

            System.out.println(ConsoleColours.BLUE + "╔══════════════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                               " + ConsoleColours.WHITE_BOLD + "    TOP MOVIES RIGHT NOW    " + ConsoleColours.BLUE + "                               ║");
            System.out.println("╚══════════════════════════════════════════════════════════════════════════════════════════╝" + ConsoleColours.RESET);
            ArrayList<Movie> results2 = ms.rankMoviebyRatings();
            System.out.println(ConsoleColours.BLUE + "┌────────────────────────────┐ ┌────────────────────────────┐ ┌────────────────────────────┐ ");
            movie1 = (results2.get(0)).getTitle();
            count = 15 - movie1.length() / 2;
            spaces1 = String.format("%" + (count-1) +"s", "");
            spaces2 = String.format("%" + (30 - count - movie1.length()-1) +"s", "");
            System.out.print("║" + ConsoleColours.WHITE_BOLD + spaces1 + movie1 + spaces2 + ConsoleColours.BLUE +  "║ ");
            movie2 = (results2.get(1)).getTitle();
            count = 15 - movie2.length() / 2;
            spaces1 = String.format("%" + (count-1) +"s", "");
            spaces2 = String.format("%" + (30 - count - movie2.length()-1) +"s", "");
            System.out.print("║" + ConsoleColours.WHITE_BOLD + spaces1 + movie2 + spaces2 + ConsoleColours.BLUE +  "║ ");
            movie3 = (results2.get(2)).getTitle();
            count = 15 - movie3.length() / 2;
            spaces1 = String.format("%" + (count-1) +"s", "");
            spaces2 = String.format("%" + (30 - count - movie3.length()-1) +"s", "");
            System.out.println("║" + ConsoleColours.WHITE_BOLD + spaces1 + movie3 + spaces2 + ConsoleColours.BLUE +  "║ ");
            System.out.println(ConsoleColours.BLUE + "└────────────────────────────┘ └────────────────────────────┘ └────────────────────────────┘" + ConsoleColours.RESET);
            System.out.println();
            System.out.println(ConsoleColours.WHITE_BOLD + "Welcome to Golden Town Cineplexes!" + ConsoleColours.RESET);
            System.out.println("1. Search Movies");
            System.out.println("2. Search Shows and Seating Plan");
            System.out.println("3. Make a Booking");
            System.out.println("4. Manage Account");
            System.out.println("0. Exit");
            System.out.println();
            System.out.println("Enter your choice:");
            choice = Helper.robustNextInt();

            switch (choice) {
                case 1:
                    View view = new ViewMovie(systems);
                    view.menu(AccessLevel.CUSTOMER);
                    break;
                case 2:
                    View view2 = new ViewShow(systems);
                    view2.menu(AccessLevel.CUSTOMER);
                    break;
                case 3:
                    View view3 = new ViewBooking(systems);
                    view3.menu(AccessLevel.CUSTOMER);
                    break;
                case 4:
                    ViewLogin view4 = new ViewLogin(systems);
                    String result = view4.menuString(accessLevel);
                    if(result != null) {
                        ViewAccount view5 = new ViewAccount(systems);
                        view5.menuID(AccessLevel.CUSTOMER, result);
                    }
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid input! Press enter to proceed.");
                    sc.nextLine();
            }

            System.out.println();
        }
    }
}
