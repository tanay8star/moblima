package MOBLIMA;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Represents a view to manage shows.
 * @author	SS1 Group 2
 * @version	1.0
 * @since	2019-11-13
 */

public class ViewShow extends ViewSystem {

    private Scanner sc = new Scanner(System.in);

    /**
     * MOBLIMA's movie system.
     */
    private MovieSystem movieSystem;

    /**
     * MOBLIMA's cineplex system.
     */
    private CineplexSystem cineplexSystem;

    /**
     * MOBLIMA's show system.
     */
    private ShowSystem showSystem;

    /**
     * Constructor of the view.
     * It will load up the relevant systems.
     * @param systems MOBLIMA's systems hash map
     */
    public ViewShow(HashMap<String, MOBLIMASystem> systems){
        showSystem = (ShowSystem)systems.get("Show");
        movieSystem = (MovieSystem)systems.get("Movie");
        cineplexSystem = (CineplexSystem)systems.get("Cineplex");
    }

    /**
     * Main menu of the show management view.
     * Note that view differs based on access level.
     * @param accessLevel the access level, customer or administrator
     */
    @Override
    public void menu(AccessLevel accessLevel) {
        int choice = -1;
        switch (accessLevel) {
            case ADMINISTRATOR:
                do {
                    clearScreen("Directory: Home > Show Database", accessLevel);

                    System.out.println("Current active shows held: " + showSystem.getNumberOfShows());
                    System.out.println();

                    System.out.println("What do you want to do?");
                    System.out.println("1. List All Shows");
                    System.out.println("2. Query Show");
                    System.out.println("3. Add Show");
                    System.out.println("4. Remove Show");
                    System.out.println("5. Edit Show");
                    System.out.println("6. Reset Database");
                    System.out.println("0. Back to home");

                    choice = Helper.robustNextInt();

                    switch (choice) {
                        case 1:
                            list(accessLevel);
                            break;
                        case 2:
                            query(accessLevel);
                            break;
                        case 3:
                            add(accessLevel);
                            break;
                        case 4:
                            delete(accessLevel);
                            break;
                        case 5:
                            update(accessLevel);
                            break;
                        case 6:
                            reset(accessLevel);
                            break;
                        case 0:
                            break;
                        default:
                            System.out.println("Invalid input! Press enter to continue.");
                            sc.nextLine();
                            break;
                    }
                } while (choice != 0);
                break;
            case CUSTOMER:
                do {
                    clearScreen("Directory: Home > Show", accessLevel);

                    System.out.println("Current active shows held: " + showSystem.getNumberOfShows());
                    System.out.println();

                    System.out.println("What do you want to do?");
                    System.out.println("1. List All Shows");
                    System.out.println("2. Query Show and Seating Plan");
                    System.out.println("0. Back to home");

                    choice = Helper.robustNextInt();

                    switch (choice) {
                        case 1:
                            list(accessLevel);
                            break;
                        case 2:
                            query(accessLevel);
                            break;
                        case 0:
                            break;
                        default:
                            System.out.println("Invalid Input. Press enter to continue.");
                            sc.nextLine();
                            break;
                    }
                } while (choice != 0);
                break;
        }
    }

    /**
     * View to reset show database.
     * Note that this is only accessible to administrators.
     * @param accessLevel the access level, customer or administrator
     */
    @Override
    public void reset( AccessLevel accessLevel) {
        clearScreen("Directory: Home > Show Database > Reset Database", accessLevel);
        Scanner sc = new Scanner(System.in);
        System.out.println("You are going to reset the database. Confirm action? (Y/N)");
        char c = sc.next().charAt(0);
        sc.nextLine();
        if (c == 'Y') {
            if (showSystem.resetDatabase()) System.out.println("Reset successful!");
            else System.out.println("Reset unsuccessful!");
        }
        else System.out.println("Invalid input! No change was made.");
        System.out.println();

        System.out.println("Press enter to go to previous page.");
        sc.nextLine();
    }

    /**
     * View to add show to database.
     * Note that this is only accessible to administrators.
     * @param accessLevel the access level, customer or administrator
     */
    @Override
    public void add(AccessLevel accessLevel) {
        clearScreen("Directory: Home > Show Database > Add Show", accessLevel);
        Cineplex cineplex;
        do {
           cineplex = cineplexSystem.selectCineplex();
        } while (cineplex == null);
        System.out.println();

        CinemaHall cinemaHall;
        do {
            cinemaHall = cineplex.selectCinemaHall();
        } while (cinemaHall == null);
        System.out.println();

        Movie movie = movieSystem.selectShowingMovie();
        System.out.println();

        System.out.println("Enter date (DD/MM/YYYY): ");
        String date1 = sc.nextLine();
        System.out.println();
        System.out.println("Enter starting time of the show (24hr) (hh:mm):");
        String time1 = sc.nextLine();
        Date datetime = Helper.convertDateTime(date1 + " " + time1);

        Show show = showSystem.checkDuplicate(cineplex, cinemaHall, movie, datetime);
        if(show == null){
            showSystem.addShow(cineplex, cinemaHall, datetime, movie);
            showSystem.serialize();
            System.out.println("Show added successfully! Press enter to return to previous screen.");
        } else {
            System.out.println("Show Addition Failed. There was a clash with the following show.");
            show.print();
            System.out.println();
            System.out.println("Please check the details entered and try again.");
        }

        sc.nextLine();
        return;
    }

    /**
     * View to update show in database.
     * Note that this is only accessible to administrators.
     * @param accessLevel the access level, customer or administrator
     */
    @Override
    public void update(AccessLevel accessLevel) {
        clearScreen("Directory: Home > Show Database > Edit Show", accessLevel);
        Cineplex cineplex;
        do {
            cineplex = cineplexSystem.selectCineplex();
        } while (cineplex == null);
        System.out.println();

        Movie movie;
        do {
            movie = movieSystem.selectShowingMovie();
        } while (movie == null);
        System.out.println();

        System.out.println("Enter date (DD/MM/YYYY): ");
        String date1 = sc.nextLine();
        System.out.println();
        if (showSystem.queryShow(movie, cineplex, Helper.convertDate(date1)).size() != 0) showSystem.listShow(showSystem.queryShow(movie, cineplex, Helper.convertDate(date1)));
        else {
            System.out.println("No show found! Please try another date. Press enter to continue.");
            sc.nextLine();
            return;
        }
        System.out.println();
        System.out.println("Enter starting time of the show (24hr) (hh:mm):");
        String time1 = sc.nextLine();

        //I dont quite get the results here, if multiple shows, the query fails
        ArrayList<Show> result = null;
        // ArrayList<Show> result = showSystem.queryShow(cineplex1, movie1, datetime);
        if(time1.equals("")){
            Date date = Helper.convertDate(date1);
            result = showSystem.queryShow(movie, cineplex, date, false);
        } else {
            Date datetime = Helper.convertDateTime(date1 + " " + time1);
            result = showSystem.queryShow(movie, cineplex, datetime, true);
        }

        if (result.size() == 0) {
            System.out.println("No show found! Press enter to continue.");
            sc.nextLine();
            return;
        }
        Show show = null;
        if (result.size() > 1) {
            show = showSystem.selectShow(result);
        } else {
            show = result.get(0);
        }



        if (show != null){
            int input = -1;
            do{
                clearScreen("Directory: Home > Show Database > Edit Show", accessLevel);
                show.print();
                System.out.println();
                System.out.println("=========================================");
                System.out.println("What would you like to edit?");
                System.out.println("1. Edit Cineplex and Cinema Hall");
                System.out.println("2. Edit Date and Time");
                System.out.println("3. Edit Movie");
                System.out.println("0. Exit");

                input = Helper.robustNextInt();
                int index = -1;
                try {
                    switch (input) {
                        case 1:
                            Cineplex cineplex1 = cineplexSystem.selectCineplex();
                            System.out.println();
                            CinemaHall cinemaHall = cineplex1.selectCinemaHall();
                            System.out.println();
                            index = showSystem.getIndex(show);
                            show.setCineplex(cineplex1, cinemaHall);
                            if (showSystem.checkDuplicate(show.getCineplex(), show.getCinemaHall(), show.getMovie(), show.getDate()) == null) {
                                showSystem.deleteShow(index);
                                showSystem.addShow(show);
                                showSystem.serialize();
                            } else {
                                System.out.println("Database Update Failed.");
                            }
                            System.out.println("Press Enter to Return.");
                            sc.nextLine();
                            break;
                        case 2:
                            System.out.println("Enter date (DD/MM/YYYY): ");
                            String date2 = sc.nextLine();
                            System.out.println("Enter starting time of the show (24hr) (hh:mm):");
                            String time2 = sc.nextLine();
                            Date datetime1;
                            try {
                                datetime1 = Helper.convertDateTime(date2 + " " + time2);
                            } catch (Exception e){
                                System.out.println("An Error has occured. Press Enter to Continue.");
                                return;
                            }
                            index = showSystem.getIndex(show);
                            show.setDate(datetime1);
                            if (showSystem.checkDuplicate(show.getCineplex(), show.getCinemaHall(), show.getMovie(), show.getDate()) == null) {
                                showSystem.deleteShow(index);
                                showSystem.addShow(show);
                                showSystem.serialize();
                                System.out.println("Database update successful! ");
                            } else {
                                System.out.println("Database update failed due to show clash.");
                            }
                            System.out.println("Press Enter to Return.");
                            sc.nextLine();
                            break;
                        case 3:
                            index = showSystem.getIndex(show);
                            show.setMovie(movieSystem.selectShowingMovie());
                            System.out.println();

                            if (showSystem.checkDuplicate(show.getCineplex(), show.getCinemaHall(), show.getMovie(), show.getDate()) == null) {
                                showSystem.deleteShow(index);
                                showSystem.addShow(show);
                                showSystem.serialize();
                            } else {
                                System.out.println("Database Update Failed.");
                            }
                            System.out.println("Press Enter to Return.");
                            sc.nextLine();
                            break;
                    }
                } catch (Exception e){
                    System.out.println("An Error has occurred. Press Enter to Continue.");
                    sc.nextLine();
                    break;
                }
            } while (input != 0);
        }
        return;
    }

    /**
     * View to delete show from database.
     * Note that this is only accessible to administrators.
     * @param accessLevel the access level, customer or administrator
     */
    @Override
    public void delete( AccessLevel accessLevel) {
        clearScreen("Directory: Home > Show Database > Delete Show", accessLevel);
        System.out.println();
        System.out.println("Please key in the details of the show you want to edit. ");
        Cineplex cineplex = cineplexSystem.selectCineplex();
        System.out.println();
        Movie movie = movieSystem.selectShowingMovie();
        String date1 = null;
        while (true) {
            try {
                System.out.println("Enter date (DD/MM/YYYY): ");
                date1 = sc.nextLine();
                System.out.println();
                if (showSystem.queryShow(movie, cineplex, Helper.convertDate(date1)).size() != 0) {
                    clearScreen("Directory: Home > Show Database > Query Show", accessLevel);
                    showSystem.listShow(showSystem.queryShow(movie, cineplex, Helper.convertDate(date1)));
                } else {
                    System.out.println("No show found! Please try another date. Press enter to continue.");
                    sc.nextLine();
                    return;
                }
                break;
            } catch (Exception e) {
                System.out.println("Invalid date given!");
            }
        }
        System.out.println("Enter starting time of the show (24hr) (hh:mm):");
        String time1 = sc.nextLine();
        Date datetime;
        try {
            datetime = Helper.convertDateTime(date1 + " " + time1);
        } catch (Exception e){
            System.out.println("An Error Occurred. Confirm Details and try again. Press Enter to continue.");
            sc.nextLine();
            return;
        }
        ArrayList<Show> result = showSystem.queryShow(movie, cineplex, datetime, true);

        if (result.size() == 0){
            System.out.println("No Show found with those details. Please try again.");
            sc.nextLine();
        } else {
            Show show = showSystem.selectShow(result);
            System.out.println("The following show will be deleted. Please confirm [Y/N]");
            char c = sc.next().charAt(0);
            sc.nextLine();
            if (c == 'Y') {
                showSystem.deleteShow(showSystem.getIndex(show) + 1);
                System.out.println("Show Successfully Deleted!");
            } else System.out.println("Invalid input! No change was made.");
            System.out.println();

            System.out.println("Press enter to go to previous page.");
            sc.nextLine();
        }
        return;
    }

    /**
     * View to query a show in database.
     * @param accessLevel the access level, customer or administrator
     */

    public void query(AccessLevel accessLevel) {
        Cineplex cineplex;
        do {
            cineplex = cineplexSystem.selectCineplex();
        } while (cineplex == null);
        System.out.println();

        Movie movie;
        do {
            movie = movieSystem.selectShowingMovie();
        } while (movie == null);
        System.out.println();

        String date1;
        while (true) {
            try {
                System.out.println("Enter date (DD/MM/YYYY): ");
                date1 = sc.nextLine();
                System.out.println();
                Date date = Helper.convertDate(date1);
                if (date == null){
                    System.out.println("Invalid date given!");
                    continue;
                }
                else if (showSystem.queryShow(movie, cineplex, date).size() != 0) {
                    switch (accessLevel){
                        case ADMINISTRATOR:
                            clearScreen("Directory: Home > Show Database > Query Show", accessLevel);
                        case CUSTOMER:
                            clearScreen("Directory: Home > Show > Query Show", accessLevel);
                    }
                    showSystem.listShow(showSystem.queryShow(movie, cineplex, Helper.convertDate(date1)));
                }
                else {
                    System.out.println("No show found! Please try another date. Press enter to continue.");
                    sc.nextLine();
                    return;
                }
                break;
            } catch (Exception e) {
                System.out.println("Invalid date given!");
            }
        }

        Show show;

        while (true) {
            try {
                System.out.println();
                System.out.println("Enter starting time of the show (24hr) (hh:mm):");
                String time1 = sc.nextLine();

                show = showSystem.getShow(movie, cineplex, Helper.convertDateTime(date1 + " " + time1));
                break;
            } catch (Exception e) {
                System.out.println("Invalid time given!");
            }
        }
        if (show != null) {
            show.print();
            show.getCinemaLayout().printLayout();
        }
        System.out.println();
        System.out.println("Press enter to return to previous screen");
        sc.nextLine();


    }

    /**
     * View to list shows in database.
     * @param accessLevel the access level, customer or administrator
     */
    @Override
    public void list(AccessLevel accessLevel){
        showSystem.listShow();
        System.out.println("Press enter to return to previous screen");
        sc.nextLine();
    }

}
