package MOBLIMA;

import java.util.HashMap;
import java.util.Scanner;

/**
 * View for App Launcher.
 * @author	SS1 Group 2
 * @version	1.0
 * @since	2019-11-13
 */
public class MOBLIMA extends View{

    /**
     * This menu with accessLevel is not applicable as MOBLIMA does not have any specified accesslevel yet.
     * However, it is implemented due to inheritance from the abstract class View.
     * @param accessLevel the access level
     */
    public void menu(AccessLevel accessLevel){}

    /**
     * The menu of the view.
     */
    public void menu(){
        while (true) {

            clearScreen("App Launcher", AccessLevel.CUSTOMER);
            System.out.println();
            System.out.println(ConsoleColours.GREEN_BRIGHT + "╔══════════════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                                 " + "PRESS C FOR " + ConsoleColours.GREEN_BOLD + "CUSTOMER MODE" + ConsoleColours.GREEN_BRIGHT + "                                ║");
            System.out.println("╚══════════════════════════════════════════════════════════════════════════════════════════╝");
            System.out.println();
            System.out.println(ConsoleColours.RED + "╔══════════════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                                  " + "PRESS A FOR " + ConsoleColours.RED_BOLD + "ADMIN MODE" + ConsoleColours.RED + "                                  ║");
            System.out.println("╚══════════════════════════════════════════════════════════════════════════════════════════╝");
            System.out.println(ConsoleColours.RESET);
            System.out.println();
    
            Scanner sc = new Scanner(System.in);
            String input = null;
            do{
                System.out.println("Enter your choice!");
                input = sc.nextLine();
            } while (!input.equals("A") && !input.equals("C"));
            View view = null;
            AccessLevel accessLevel = null;

            clearScreen("App Launcher", AccessLevel.CUSTOMER);
            System.out.println(ConsoleColours.CYAN_BOLD + "╔══════════════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                                   " + "LOADING DATABASES..." + "                                   ║");
            System.out.println("╚══════════════════════════════════════════════════════════════════════════════════════════╝" + ConsoleColours.RESET);
            System.out.println();
            System.out.println();
            HashMap<String, MOBLIMASystem> systems = new HashMap<>();
            CineplexSystem cineplexSystem = new CineplexSystem();
            systems.put("Cineplex", cineplexSystem);
            Helper.load();
            MovieSystem movieSystem = new MovieSystem();
            systems.put("Movie", movieSystem);
            Helper.load();
            CustomerSystem customerSystem = new CustomerSystem();
            systems.put("Customer", customerSystem);
            Helper.load();
            ShowSystem showSystem = new ShowSystem();
            systems.put("Show", showSystem);
            Helper.load();
            AdminLoginSystem adminLoginSystem = new AdminLoginSystem();
            systems.put("AdminLogin", adminLoginSystem);
            Helper.load();
            HolidaySystem holidaySystem = new HolidaySystem();
            systems.put("Holiday", holidaySystem);
            Helper.load();
            Price price = new Price();
            systems.put("Price", price);
            Helper.load();
            BookingSystem bookingSystem = new BookingSystem();
            systems.put("Booking", bookingSystem);
            System.out.printf("║║║║║║║║");

            if(input.equals("A")){
                view = new ViewLogin(systems);
                accessLevel = AccessLevel.ADMINISTRATOR;

            } else {
                view = new ViewCustomerMenu(systems);
                accessLevel = AccessLevel.CUSTOMER;
            }
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            view.menu(accessLevel);
        }



    }
}