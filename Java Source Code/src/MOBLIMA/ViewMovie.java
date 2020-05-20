package MOBLIMA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Represents a view to manage movies.
 * @author	SS1 Group 2
 * @version	1.0
 * @since	2019-11-13
 */
public class ViewMovie extends ViewSystem {
    /**
     * MOBLIMA's movie system.
     */
    private MovieSystem ms;
    Scanner sc = new Scanner(System.in);

    /**
     * Constructor of the view.
     * It will load up the movie system.
     * @param systems MOBLIMA's systems hash map
     */
    public ViewMovie(HashMap<String, MOBLIMASystem> systems){
        ms = (MovieSystem) systems.get("Movie");
    }

    /**
     * Main menu of the movie management view.
     * Note that view differs based on access level.
     * @param accessLevel the access level, customer or administrator
     */
    @Override
    public void menu(AccessLevel accessLevel) {

        Movie movie;
        int i;
        int choice = -1;


        clearScreen("Directory: Home > Movies", accessLevel);
        switch (accessLevel) {
            case CUSTOMER:
                while (choice != 0) {
                    clearScreen("Directory: Home > Movies", accessLevel);
                    ms.listShowingMovies();

                    System.out.println("What do you want to do?");
                    System.out.println("1. View movie list");
                    System.out.println("2. Search movie");
                    System.out.println("3. Add review to movie");
                    System.out.println("4. Hot movies right now");
                    System.out.println("5. Top movies right now");
                    System.out.println("0. Back to home");

                    choice = Helper.robustNextInt();

                    View view = null;
                    switch (choice) {
                        case 1:
                            list(accessLevel);
                            break;
                        case 2:
                            search(accessLevel);
                            break;
                        case 3:
                            addReview(accessLevel);
                            break;
                        case 4:
                            rankByBooking(accessLevel);
                            break;
                        case 5:
                            rankByRating(accessLevel);
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

            case ADMINISTRATOR:

                while (choice != 0) {
                    clearScreen("Directory: Home > Movie Database", accessLevel);
                    ms.listShowingMovies();

                    System.out.println("What do you want to do?");
                    System.out.println("1. Get movie details");
                    System.out.println("2. Add movie");
                    System.out.println("3. Edit movie");
                    System.out.println("4. Delete movie");
                    System.out.println("5. Reset movie database");
                    System.out.println("6. Add review to movie");
                    System.out.println("7. Delete review of movie");
                    System.out.println("8. Print review of movie");
                    System.out.println("9. Print ranking of movie by bookings");
                    System.out.println("10. Print ranking of movie by ratings");
                    System.out.println("0. Back to home");

                    choice = Helper.robustNextInt();

                    View view = null;
                    switch (choice) {
                        case 1:
                            list(accessLevel);
                            break;
                        case 2:
                            add(accessLevel);
                            break;
                        case 3:
                            update(accessLevel);
                            break;
                        case 4:
                            delete(accessLevel);
                            break;
                        case 5:
                            reset(accessLevel);
                            break;
                        case 6:
                            addReview(accessLevel);
                            break;
                        case 7:
                            deleteReview(accessLevel);
                            break;
                        case 8:
                            viewReviews(accessLevel);
                            break;
                        case 9:
                            rankByBooking(accessLevel);
                            break;
                        case 10:
                            rankByRating(accessLevel);
                            break;
                        case 0:
                            break;
                        default:
                            System.out.println("Invalid input!");
                    }

                    System.out.println();

                }
        }
    }

    /**
     * View to add movies to movie database.
     * This is only accessible to administrators.
     * @param accessLevel the access level, customer or administrator
     */
    @Override
    public void add(AccessLevel accessLevel) {
        clearScreen("Directory: Home > Movie Database > Add Movie", accessLevel);
        switch (accessLevel) {
            case CUSTOMER:
                System.out.println("Access denied!");
                break;
            case ADMINISTRATOR:
                Movie movie = new Movie();

                System.out.println("Enter the title:");
                String title = sc.nextLine();
                movie.setTitle(title);
                System.out.println();

                System.out.println("Enter the synopsis:");
                String synopsis = sc.nextLine();
                movie.setSynopsis(synopsis);
                System.out.println();

                System.out.println("Enter the director:");
                String director = sc.nextLine();
                movie.setDirector(director);
                System.out.println();

                System.out.println("Enter the casts: (Enter all the casts separated by an enter, and input # to indicate end of input)");
                while (true) {
                    String cast = sc.nextLine();
                    if (cast.equals("#")) break;
                    movie.addCast(cast);
                }
                System.out.println();

                System.out.println("Enter the movie runtime in minutes:");
                int showtime;
                do{
                    showtime = Helper.robustNextInt();
                    if (showtime==-1){
                        System.out.println("Invalid input! Please enter a number");
                    }
                }while (showtime==-1);
                movie.setShowtime(showtime);
                System.out.println();

                System.out.println("Enter the showing status:");
                System.out.println("1. Coming Soon");
                System.out.println("2. Preview");
                System.out.println("3. Now Showing");
                System.out.println("4. End of Showing");

                try {
                    int statChoice = Helper.robustNextInt();
                    switch (statChoice) {
                        case 1:
                            movie.setStatus(MovieStatus.COMING_SOON);
                            break;
                        case 2:
                            movie.setStatus(MovieStatus.PREVIEW);
                            break;
                        case 3:
                            movie.setStatus(MovieStatus.NOW_SHOWING);
                            break;
                        case 4:
                            movie.setStatus(MovieStatus.END_OF_SHOWING);
                            break;
                        default:
                            System.out.println("Invalid input! It will automatically be set to not available.");
                            break;
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input! It will automatically be set to not available.");
                }

                System.out.println();
                try {
                    System.out.println("Is it a blockbuster?");
                    System.out.println("1. Yes");
                    System.out.println("2. No");

                    int bbChoice = Helper.robustNextInt();
                    switch (bbChoice) {
                        case 1:
                            movie.setBlockbuster(true);
                            break;
                        case 2:
                            movie.setBlockbuster(false);
                            break;
                        default:
                            System.out.println("Invalid input! It will automatically be set to false.");
                            break;
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input! It will automatically be set to false.");
                }

                System.out.println();
                try {
                    System.out.println("What is the movie format?");
                    System.out.println("1. 2D");
                    System.out.println("2. 3D");

                    int fChoice = Helper.robustNextInt();
                    switch (fChoice) {
                        case 1:
                            movie.setFormat("2D");
                            break;
                        case 2:
                            movie.setFormat("3D");
                            break;
                        default:
                            System.out.println("Invalid input! It will automatically be set to false.");
                            break;
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input! It will automatically be set to false.");
                }

                System.out.println();
                try {
                    System.out.println("What is the category?");
                    System.out.println("1. G");
                    System.out.println("2. PG");
                    System.out.println("3. PG-13");
                    System.out.println("4. NC-16");
                    System.out.println("5. R-21");

                    int cChoice = Helper.robustNextInt();
                    switch (cChoice) {
                        case 1:
                            movie.setCategory(MovieCategory.G);
                            break;
                        case 2:
                            movie.setCategory(MovieCategory.PG);
                            break;
                        case 3:
                            movie.setCategory(MovieCategory.PG_13);
                            break;
                        case 4:
                            movie.setCategory(MovieCategory.NC_16);
                            break;
                        case 5:
                            movie.setCategory(MovieCategory.R_21);
                            break;
                        default:
                            System.out.println("Invalid input! It will automatically be set to G.");
                            break;
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input! It will automatically be set to G.");
                }


                ms.addMovie(movie);
                ms.serialize();
                System.out.println();

                System.out.println("Press enter to go to previous page.");
                sc.nextLine();
                break;
        }
    }

    /**
     * View to update movies in movie database.
     * This is only accessible to administrators.
     * @param accessLevel the access level, customer or administrator
     */
    @Override
    public void update(AccessLevel accessLevel) {
        clearScreen("Directory: Home > Movie Database > Edit Movie", accessLevel);
        switch (accessLevel) {
            case CUSTOMER:
                System.out.println("Access denied!");
                break;
            case ADMINISTRATOR:
                String cast;
                int choice = -1;

                clearScreen("Directory: Home > Movie Database > Edit Movie", accessLevel);
                Movie movie = ms.selectShowingMovie();

                if (movie != null) {
                    System.out.println();

                    while (choice != 0) {
                        clearScreen("Directory: Home > Movie Database > Edit Movie", accessLevel);
                        System.out.println("You are editing '" + movie.getTitle() + "'.");
                        System.out.println();
                        System.out.println("What do you want to edit?");
                        System.out.println("1. Change title");
                        System.out.println("2. Change synopsis");
                        System.out.println("3. Change director");
                        System.out.println("4. Add cast");
                        System.out.println("5. Remove cast");
                        System.out.println("6. Change showing status");
                        System.out.println("7. Change blockbuster status");
                        System.out.println("8. Change category");
                        System.out.println("0. Back to previous menu");

                        choice = Helper.robustNextInt();

                        switch (choice) {
                            case 1:
                                System.out.println("Current title: " + movie.getTitle());
                                System.out.println("Enter new title:");
                                String title = sc.nextLine();
                                if (movie.setTitle(title)) System.out.println("Change successful! Press enter to proceed.");
                                else System.out.println("Change unsuccessful! Press enter to proceed.");
                                sc.nextLine();
                                System.out.println();
                                break;
                            case 2:
                                System.out.println("Current synopsis: " + movie.getSynopsis());
                                System.out.println("Enter new synopsis:");
                                String synopsis = sc.nextLine();
                                if (movie.setSynopsis(synopsis)) System.out.println("Change successful! Press enter to proceed.");
                                else System.out.println("Change unsuccessful! Press enter to proceed.");
                                sc.nextLine();
                                System.out.println();
                                break;
                            case 3:
                                System.out.println("Current director: " + movie.getDirector());
                                System.out.println("Enter new director:");
                                String director = sc.nextLine();
                                if (movie.setDirector(director)) System.out.println("Change successful! Press enter to proceed.");
                                else System.out.println("Change unsuccessful! Press enter to proceed.");
                                sc.nextLine();
                                System.out.println();
                                break;
                            case 4:
                                System.out.println("Enter the cast name to add:");
                                cast = sc.nextLine();
                                if (movie.addCast(cast)) System.out.println("Change successful! Press enter to proceed.");
                                else System.out.println("Change unsuccessful! Press enter to proceed.");
                                sc.nextLine();
                                System.out.println();
                                break;
                            case 5:
                                System.out.println("Current casts:");
                                for (int i = 0; i < movie.getCasts().size(); i++) {
                                    System.out.print(movie.getCasts().get(i));
                                    System.out.println();
                                }

                                System.out.println("Enter the cast name to remove:");
                                cast = sc.nextLine();
                                if (movie.removeCast(cast)) System.out.println("Change successful! Press enter to proceed.");
                                else System.out.println("Change unsuccessful! Press enter to proceed.");
                                sc.nextLine();
                                System.out.println();
                                break;
                            case 6:
                                System.out.print("Current showing status: ");
                                switch (movie.getStatus()) {
                                    case COMING_SOON:
                                        System.out.print("Coming Soon");
                                        break;
                                    case PREVIEW:
                                        System.out.print("Preview");
                                        break;
                                    case NOW_SHOWING:
                                        System.out.print("Now Showing");
                                        break;
                                    case END_OF_SHOWING:
                                        System.out.print("End of Showing");
                                }
                                System.out.println();
                                System.out.println("Enter new showing status:");

                                System.out.println("1. Coming Soon");
                                System.out.println("2. Preview");
                                System.out.println("3. Now Showing");
                                System.out.println("4. End of Showing");

                                try {
                                    int statChoice = Helper.robustNextInt();

                                    switch (statChoice) {
                                        case 1:
                                            if (movie.setStatus(MovieStatus.COMING_SOON)) System.out.println("Change successful. Press enter to proceed.");
                                            else System.out.println("Change unsuccessful! Press enter to proceed.");
                                            break;
                                        case 2:
                                            if (movie.setStatus(MovieStatus.PREVIEW)) System.out.println("Change successful. Press enter to proceed.");
                                            else System.out.println("Change unsuccessful! Press enter to proceed.");
                                            break;
                                        case 3:
                                            if (movie.setStatus(MovieStatus.NOW_SHOWING)) System.out.println("Change successful. Press enter to proceed.");
                                            else System.out.println("Change unsuccessful! Press enter to proceed.");
                                            break;
                                        case 4:
                                            if (movie.setStatus(MovieStatus.END_OF_SHOWING)) {
                                                System.out.println("Change successful. Press enter to proceed.");
                                                ShowSystem ss = new ShowSystem();
                                                ss.removeMovie(movie);
                                            }
                                            else System.out.println("Change unsuccessful! Press enter to proceed.");
                                            break;
                                        default:
                                            System.out.println("Invalid input! Change unsuccessful. Press enter to proceed.");
                                            break;
                                    }

                                } catch (Exception e) {
                                    //deleted exception
                                }
                                sc.nextLine();
                                System.out.println();
                                break;

                            case 7:
                                //TODO current blockbuster
                                System.out.println("Is it a blockbuster?");

                                System.out.println("1. Yes");
                                System.out.println("2. No");


                                try {
                                    int bbChoice = Helper.robustNextInt();
                                    switch (bbChoice) {
                                        case 1:
                                            if (movie.setBlockbuster(true)) System.out.println("Change successful. Press enter to proceed.");
                                            else System.out.println("Change unsuccessful! Press enter to proceed.");
                                            break;
                                        case 2:
                                            if (movie.setBlockbuster(false)) System.out.println("Change successful. Press enter to proceed.");
                                            else System.out.println("Change unsuccessful! Press enter to proceed.");
                                            break;
                                        default:
                                            System.out.println("Invalid input! Change unsuccessful. Press enter to proceed.");
                                            break;
                                    }
                                } catch (Exception e) {
                                    System.out.println("Invalid input! Change unsuccessful. Press enter to proceed.");
                                }

                                sc.nextLine();
                                System.out.println();
                                break;
                            case 8:
                                //TODO current category
                                System.out.println();
                                try {
                                    System.out.println("What is the category?");
                                    System.out.println("1. G");
                                    System.out.println("2. PG");
                                    System.out.println("3. PG-13");
                                    System.out.println("4. NC-16");
                                    System.out.println("5. R-21");

                                    int cChoice = Helper.robustNextInt();
                                    switch (cChoice) {
                                        case 1:
                                            movie.setCategory(MovieCategory.G);
                                            break;
                                        case 2:
                                            movie.setCategory(MovieCategory.PG);
                                            break;
                                        case 3:
                                            movie.setCategory(MovieCategory.PG_13);
                                            break;
                                        case 4:
                                            movie.setCategory(MovieCategory.NC_16);
                                            break;
                                        case 5:
                                            movie.setCategory(MovieCategory.R_21);
                                            break;
                                        default:
                                            System.out.println("Invalid input! Change unsuccessful. Press enter to proceed.");
                                            break;
                                    }
                                } catch (Exception e) {
                                    System.out.println("Invalid input! Change unsuccessful. Press enter to proceed.");
                                }
                                System.out.println();
                                break;
                            case 0:
                                break;
                            default:
                                System.out.println("Invalid input! Press enter to proceed.");
                                sc.nextLine();

                        }

                    }
                }
                else {
                    System.out.println("Invalid input! Press enter to return to previous screen.");
                    sc.nextLine();
                }


                ms.serialize();
                break;


        }
    }

    /**
     * View to delete movies from movie database.
     * This is only accessible to administrators.
     * @param accessLevel the access level, customer or administrator
     */
    @Override
    public void delete(AccessLevel accessLevel) {
        clearScreen("Directory: Home > Movie Database > Delete Movie", accessLevel);
        switch (accessLevel) {
            case CUSTOMER:
                System.out.println("Access denied!");
                break;
            case ADMINISTRATOR:
                ms.listMovie();
                System.out.println("Enter movie number to delete: ");
                int i = Helper.robustNextInt();
                Movie movie = ms.getMovie(i);
                if (movie != null) {
                    System.out.println("You are going to remove " + movie.getTitle() + ". Confirm deletion? (Y/N)");
                    char c = sc.next().charAt(0);
                    sc.nextLine();
                    if (c == 'Y') {
                        if (ms.deleteMovie(i)) System.out.println("Deletion successful!");
                        else System.out.println("Deletion unsuccessful!");
                    } else System.out.println("No movie was deleted.");
                    System.out.println();
                } else {
                    System.out.println("Invalid Input Detected. No change was made.");
                }
                System.out.println("Press enter to go to previous page.");
                sc.nextLine();
                break;
        }
    }

    /**
     * View to list movies in movie database.
     * @param accessLevel the access level, customer or administrator
     */
    @Override
    public void list(AccessLevel accessLevel) {

        switch(accessLevel){
            case ADMINISTRATOR:
                clearScreen("Directory: Home > Movie Database > View Movies", accessLevel);
                break;
            case CUSTOMER:
                clearScreen("Directory: Home > Movies > View Movies", accessLevel);
                break;
        }
        if (ms.numMovies() == 0) {
            System.out.println("No movies in the database! Press enter to return back to previous menu.");
            sc.nextLine();
        }
        else {
            Movie movie = ms.selectShowingMovie();
            if (movie != null) {
                System.out.println();

                System.out.print("Title:            ");
                System.out.print(movie.getTitle());
                System.out.println();

                System.out.print("Rating:           ");
                System.out.print(movie.getAvgRating());
                System.out.println();

                System.out.print("Synopsis:         ");
                System.out.print(movie.getSynopsis());
                System.out.println();

                System.out.print("Director:         ");
                System.out.print(movie.getDirector());
                System.out.println();

                System.out.print("Casts:            ");
                for (int i = 0; i < movie.getCasts().size(); i++) {
                    if (i != 0) System.out.print("                  ");
                    System.out.print(movie.getCasts().get(i));
                    System.out.println();
                }


                System.out.print("Showing Status:   ");
                switch (movie.getStatus()) {
                    case COMING_SOON:
                        System.out.print("Coming Soon");
                        break;
                    case PREVIEW:
                        System.out.print("Preview");
                        break;
                    case NOW_SHOWING:
                        System.out.print("Now Showing");
                        break;
                    case END_OF_SHOWING:
                        System.out.print("End of Showing");
                }
                System.out.println();

                System.out.print("Blockbuster:      ");
                if (movie.getBlockbuster()) System.out.print("Yes");
                else System.out.print("No");
                System.out.println();

                System.out.print("Category:         ");
                switch (movie.getCategory()) {
                    case G:
                        System.out.print("G");
                        break;
                    case PG:
                        System.out.print("PG");
                        break;
                    case PG_13:
                        System.out.print("PG-13");
                        break;
                    case R_21:
                        System.out.print("R-21");
                        break;
                    case NC_16:
                        System.out.println("NC-16");
                        break;
                }
                System.out.println();

                System.out.println("Showtimes:      ");
                ShowSystem ss = new ShowSystem();
                if (ss.queryShow(movie, null, null).size() == 0) System.out.println("No show found!");
                else ss.listShow(ss.queryShow(movie, null, null));
                System.out.println();

                movie.printReviews();

                System.out.println("Press enter to go to previous page.");
                sc.nextLine();
            } else {
                System.out.println("Invalid selection! Press enter to go to previous page.");
                sc.nextLine();
            }
        }
    }

    /**
     * View to reset movie database.
     * This is only accessible to administrators.
     * @param accessLevel the access level, customer or administrator
     */
    @Override
    public void reset(AccessLevel accessLevel) {
        clearScreen("Directory: Home > Movie Database > Reset Database", accessLevel);
        switch (accessLevel) {
            case CUSTOMER:
                System.out.println("Access denied!");
                break;
            case ADMINISTRATOR:
                System.out.println("You are going to reset the database. Confirm action? (Y/N)");
                char c = sc.next().charAt(0);
                sc.nextLine();
                if (c == 'Y') {
                    if (ms.resetDatabase()) System.out.println("Reset successful!");
                    else System.out.println("Reset unsuccessful!");
                }
                else System.out.println("Invalid input! No movie was deleted.");
                System.out.println();

                System.out.println("Press enter to go to previous page.");
                sc.nextLine();
                break;
        }
    }

    /**
     * View to add review to a movie in movie database.
     * @param accessLevel the access level, customer or administrator
     */
    public void addReview(AccessLevel accessLevel) {
        switch (accessLevel){
            case ADMINISTRATOR:
                clearScreen("Directory: Home > Movie Database > Add Review", accessLevel);
                break;
            case CUSTOMER:
                clearScreen("Directory: Home > Movies > Add Review", accessLevel);
                break;
        }

        if (ms.numMovies() == 0) {
            System.out.println("No movies to review in the database! Press enter to return back to previous menu.");
            sc.nextLine();
        }
        else{
            Movie movie = ms.selectShowingMovie();

            if (movie != null) {
                System.out.println();

                System.out.print("Title: ");
                System.out.print(movie.getTitle());
                System.out.println();

                System.out.println("Rating (0 - 5): ");
                double rating;
                while(true) {
                    rating = Helper.robustNextDouble();
                    if (rating == -1.0 || rating < 0 || rating > 5) {
                        System.out.println("Invalid Input! Please try again");
                        continue;
                    }
                    break;
                }

                System.out.println("Comment: ");
                String comment = sc.nextLine();

                movie.addReview(rating, comment);
                ms.serialize();

                System.out.println("Press enter to go to previous page.");
                sc.nextLine();
            } else {
                System.out.println("Invalid selection! Press enter to go to previous page.");
                sc.nextLine();
            }

        }
    }

    /**
     * View to delete review of a movie in movie database.
     * This is only accessible to administrators.
     * @param accessLevel the access level, customer or administrator
     */
    public void deleteReview(AccessLevel accessLevel) {
        clearScreen("Directory: Home > Movie Database > Delete Review", accessLevel);
        switch (accessLevel) {
            case CUSTOMER:
                System.out.println("Access denied");
                break;
            case ADMINISTRATOR:
                if (ms.numMovies() == 0) {
                    System.out.println("No movies in the review in Database! Press enter to return back to previous menu.");
                    sc.nextLine();
                }
                else{
                    Movie movie = ms.selectShowingMovie();

                    if (movie != null) {
                        movie.printReviews();
                        System.out.println();

                        System.out.print("Please select the Review you want to delete: ");
                        int reviewNo = Helper.robustNextInt();
                        boolean result = movie.deleteReview(reviewNo);
                        if (result){
                            System.out.println("Review deleted");
                            ms.serialize();
                        }
                        else
                            System.out.println("Error Occurred. Review not deleted.");

                        System.out.println("Press enter to go to previous page.");
                        sc.nextLine();
                    } else {
                        System.out.println("Invalid selection! Press enter to go to previous page.");
                    }

                }
                break;
        }
    }

    /**
     * View to rank movie by booking.
     * It displays the top 5 movies.
     * @param accessLevel the access level, customer or administrator
     */
    public void rankByBooking(AccessLevel accessLevel) {
        switch (accessLevel){
            case ADMINISTRATOR:
                clearScreen("Directory: Home > Movie Database > Booking Ranking", accessLevel);
                break;
            case CUSTOMER:
                clearScreen("Directory: Home > Movies > Hot Movies Right Now", accessLevel);
                break;
        }
        Scanner sc = new Scanner(System.in);
        HashMap<Integer, ArrayList<Object>> results = ms.rankMoviebyBooking();

        if (ms.numMovies() == 0) {
            System.out.println("No movies in the database! Press enter to return back to previous menu.");
            sc.nextLine();
        }
        else {
            int index = Math.min(ms.numMovies(), 5);
            for (int i = 0; i < index; i++) {
                String place = "";
                switch (i) {
                    case 0:
                        place = " First";
                        break;
                    case 1:
                        place = "Second";
                        break;
                    case 2:
                        place = " Third";
                        break;
                    case 3:
                        place = "Fourth";
                        break;
                    case 4:
                        place = " Fifth";
                        break;
                }
                int c = (int) results.get(i + 1).get(1);
                System.out.println();
                System.out.println(ConsoleColours.CYAN + "╔═══════════════════════════════════════╗");
                System.out.println("║             " + place + " Place              ║");
                System.out.println("╚═══════════════════════════════════════╝" + ConsoleColours.RESET);

                Movie m = (Movie) results.get(i + 1).get(0);

                System.out.println();

                System.out.print("Title:            ");
                System.out.print(m.getTitle());
                System.out.println();

                System.out.print("Rating:           ");
                System.out.print(m.getAvgRating());
                System.out.println();

                System.out.print("Synopsis:         ");
                System.out.print(m.getSynopsis());
                System.out.println();

                System.out.print("Director:         ");
                System.out.print(m.getDirector());
                System.out.println();

                System.out.print("Casts:            ");
                for (int j = 0; j < m.getCasts().size(); j++) {
                    if (j != 0) System.out.print("                  ");
                    System.out.print(m.getCasts().get(j));
                    System.out.println();
                }

                System.out.print("Showing Status:   ");
                switch (m.getStatus()) {
                    case COMING_SOON:
                        System.out.print("Coming Soon");
                        break;
                    case PREVIEW:
                        System.out.print("Preview");
                        break;
                    case NOW_SHOWING:
                        System.out.print("Now Showing");
                        break;
                    case END_OF_SHOWING:
                        System.out.print("End of Showing");
                }
                System.out.println();

                System.out.print("Blockbuster:      ");
                if (m.getBlockbuster()) System.out.print("Yes");
                else System.out.print("No");
                System.out.println();
            }
        }
        System.out.println("Press enter to go to previous page.");
        sc.nextLine();
    }

    /**
     * View to rank movie by rating.
     * It displays the top 5 movies.
     * @param accessLevel the access level, customer or administrator
     */
    public void rankByRating(AccessLevel accessLevel) {
        switch (accessLevel){
            case ADMINISTRATOR:
                clearScreen("Directory: Home > Movie Database > Rating Ranking", accessLevel);
                break;
            case CUSTOMER:
                clearScreen("Directory: Home > Movies > Top Movies Right Now", accessLevel);
                break;
        }
        ArrayList<Movie> results = ms.rankMoviebyRatings();
        int i = 0;

        if (ms.numMovies() == 0) {
            System.out.println("No movies in the database! Press enter to return back to previous menu.");
            sc.nextLine();
        }
        else {
            int index = Math.min(ms.numMovies(), 5);
            for (Movie m : results) {
                i++;
                String place = "";
                switch (i) {
                    case 1:
                        place = " First";
                        break;
                    case 2:
                        place = "Second";
                        break;
                    case 3:
                        place = " Third";
                        break;
                    case 4:
                        place = "Fourth";
                        break;
                    case 5:
                        place = " Fifth";
                        break;
                }
                System.out.println();
                System.out.println(ConsoleColours.CYAN + "╔═══════════════════════════════════════╗");
                System.out.println("║             " + place + " Place              ║");
                System.out.println("╚═══════════════════════════════════════╝" + ConsoleColours.RESET);


                System.out.print("Title:            ");
                System.out.print(m.getTitle());
                System.out.println();

                System.out.print("Rating:           ");
                System.out.print(m.getAvgRating());
                System.out.println();

                System.out.print("Synopsis:         ");
                System.out.print(m.getSynopsis());
                System.out.println();

                System.out.print("Director:         ");
                System.out.print(m.getDirector());
                System.out.println();

                System.out.print("Casts:            ");
                for (int j = 0; j < m.getCasts().size(); j++) {
                    if (j != 0) System.out.print("                  ");
                    System.out.print(m.getCasts().get(j));
                    System.out.println();
                }

                System.out.print("Showing Status:   ");
                switch (m.getStatus()) {
                    case COMING_SOON:
                        System.out.print("Coming Soon");
                        break;
                    case PREVIEW:
                        System.out.print("Preview");
                        break;
                    case NOW_SHOWING:
                        System.out.print("Now Showing");
                        break;
                    case END_OF_SHOWING:
                        System.out.print("End of Showing");
                }
                System.out.println();

                System.out.print("Blockbuster:      ");
                if (m.getBlockbuster()) System.out.print("Yes");
                else System.out.print("No");
                System.out.println();

            }
            System.out.println("Press enter to go to previous page.");
            sc.nextLine();
        }
    }

    /**
     * View to search movie in movie database.
     * @param accessLevel the access level, customer or administrator
     */
    public void search(AccessLevel accessLevel) {
        switch (accessLevel){
            case ADMINISTRATOR:
                clearScreen("Directory: Home > Movie Database > Search Movie", accessLevel);
                break;
            case CUSTOMER:
                clearScreen("Directory: Home > Movies > Search Movie", accessLevel);
                break;
        }
        if (ms.numMovies() == 0) {
            System.out.println("No movies in the database! Press enter to return back to previous menu.");
            sc.nextLine();
        }
        else {
            System.out.print("Enter movie title: ");
            String s = sc.nextLine();
            Movie movie = ms.selectShowingMovie(ms.searchMovie(s));
            if (movie != null) {
                System.out.println();

                System.out.print("Title:            ");
                System.out.print(movie.getTitle());
                System.out.println();

                System.out.print("Rating:           ");
                System.out.print(movie.getAvgRating());
                System.out.println();

                System.out.print("Synopsis:         ");
                System.out.print(movie.getSynopsis());
                System.out.println();

                System.out.print("Director:         ");
                System.out.print(movie.getDirector());
                System.out.println();

                System.out.print("Casts:            ");
                for (int i = 0; i < movie.getCasts().size(); i++) {
                    if (i != 0) System.out.print("                  ");
                    System.out.print(movie.getCasts().get(i));
                    System.out.println();
                }


                System.out.print("Showing Status:   ");
                switch (movie.getStatus()) {
                    case COMING_SOON:
                        System.out.print("Coming Soon");
                        break;
                    case PREVIEW:
                        System.out.print("Preview");
                        break;
                    case NOW_SHOWING:
                        System.out.print("Now Showing");
                        break;
                    case END_OF_SHOWING:
                        System.out.print("End of Showing");
                }
                System.out.println();

                System.out.print("Blockbuster:      ");
                if (movie.getBlockbuster()) System.out.print("Yes");
                else System.out.print("No");
                System.out.println();

                System.out.print("Category:         ");
                switch (movie.getCategory()) {
                    case G:
                        System.out.print("G");
                        break;
                    case PG:
                        System.out.print("PG");
                        break;
                    case PG_13:
                        System.out.print("PG-13");
                        break;
                    case R_21:
                        System.out.print("R-21");
                        break;
                    case NC_16:
                        System.out.println("NC-16");
                        break;
                }
                System.out.println();

                System.out.println("Showtimes:      ");
                ShowSystem ss = new ShowSystem();
                if (ss.queryShow(movie, null, null).size() == 0) System.out.println("No show found!");
                else ss.listShow(ss.queryShow(movie, null, null));
                System.out.println();

                movie.printReviews();

                System.out.println("Press enter to go to previous page.");
                sc.nextLine();
            } else {
                System.out.println("Invalid selection! Press enter to go to previous page.");
                sc.nextLine();
            }

        }
    }

    /**
     * View to view reviews of a movie in movie database.
     * @param accessLevel the access level, customer or administrator
     */
    public void viewReviews(AccessLevel accessLevel) {
        switch (accessLevel){
            case ADMINISTRATOR:
                clearScreen("Directory: Home > Movie Database > View Reviews", accessLevel);
                break;
            case CUSTOMER:
                clearScreen("Directory: Home > Movies > Reviews and Ratings", accessLevel);
                break;
        }
        if (ms.numMovies() == 0) {
            System.out.println("No movies in the database! Press enter to return back to previous menu.");
            sc.nextLine();
        }
        else {
            Movie movie = ms.selectShowingMovie();
            if (movie != null) {
                System.out.println();

                System.out.print("Title:            ");
                System.out.print(movie.getTitle());
                System.out.println();

                movie.printReviews();

                System.out.print("Average Rating:   ");
                System.out.print(movie.getAvgRating());
                System.out.println();

                System.out.println("Press enter to go to previous page.");
                sc.nextLine();
            } else {
                System.out.println("Error Occurred.");
                System.out.println("Press enter to go to previous page.");
                sc.nextLine();
            }

        }
    }
}
