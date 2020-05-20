package MOBLIMA;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Represents a view to manage bookings.
 * @author	SS1 Group 2
 * @version	1.0
 * @since	2019-11-13
 */
public class ViewBooking extends ViewSystem {

    Scanner sc = new Scanner(System.in);

    /**
     * MOBLIMA's booking system
     */
    private BookingSystem bs;

    /**
     * MOBLIMA's show system
     */
    private ShowSystem ss;

    /**
     * MOBLIMA's cineplex system
     */
    private CineplexSystem cs;

    /**
     * MOBLIMA's movie system
     */
    private MovieSystem ms;

    /**
     * MOBLIMA's customer system
     */
    private CustomerSystem cus;

    /**
     * MOBLIMA's systems hash map
     */
    private HashMap<String, MOBLIMASystem> systems;

    /**
     * Constructor of the view.
     * It will load up the relevant systems and even save the hash map.
     * @param systems MOBLIMA's systems hash map
     */
    public ViewBooking(HashMap<String, MOBLIMASystem> systems){
        bs = (BookingSystem) systems.get("Booking");
        ss = (ShowSystem) systems.get("Show");
        cs = (CineplexSystem) systems.get("Cineplex");
        ms = (MovieSystem) systems.get("Movie");
        cus = (CustomerSystem) systems.get("Customer");
        this.systems = systems;

    }

    /**
     * Main menu of the booking management view.
     * View differs based on access level.
     * @param accessLevel the access level, customer or administrator
     */
    @Override
    public void menu(AccessLevel accessLevel) {
        clearScreen("Directory: Home > Booking Database", accessLevel);
        switch (accessLevel) {
            case CUSTOMER:
                add(accessLevel);
                break;
            case ADMINISTRATOR:
                int choice = -1;
                do{
                    clearScreen("Directory: Home > Booking Database", accessLevel);
                    try {
                        bs.deserialize();
                        System.out.println("Current active bookings held: " + bs.getNumberofBookings());

                        System.out.println();

                        System.out.println("What do you want to do?");
                        System.out.println("1. List customer's bookings");
                        System.out.println("2. Add booking");
                        System.out.println("3. Query booking");
                        System.out.println("4. Reset Database");
                        System.out.println("0. Back to Previous");
                        choice = Helper.robustNextInt();

                        switch(choice){
                            case 1:
                                list(accessLevel);
                                break;
                            case 2:
                                add(accessLevel);
                                break;
                            case 3:
                                query(accessLevel);
                                break;
                            case 4:
                                reset(accessLevel);
                                break;
                            case 0:
                                break;
                            default:
                                System.out.println("Invalid input! Press enter to continue.");
                                sc.nextLine();
                                break;
                        }
                    } catch (Exception e) {
                        System.out.println("Invalid input! Press enter to continue.");
                        sc.nextLine();
                    }

                } while (choice != 0);
                break;


        }
    }

    /**
     * Add booking menu of the view.
     * @param accessLevel the access level, customer or administrator
     */
    @Override
    public void add(AccessLevel accessLevel) {
        switch (accessLevel){
            case ADMINISTRATOR:
                clearScreen("Directory: Home > Booking Database > Add Booking", accessLevel);
                break;
            case CUSTOMER:
                clearScreen("Directory: Home > Add Booking", accessLevel);
                break;
        }

        Cineplex cineplex;
        do {
            cineplex = cs.selectCineplex();
        } while (cineplex == null);

        System.out.println();

        Movie movie;
        do {
            movie = ms.selectShowingMovie();
        } while (movie == null);
        System.out.println();

        String date1;
        while (true) {
            try {
                System.out.println("Enter date (DD/MM/YYYY): ");
                date1 = sc.nextLine();
                System.out.println();
                if (ss.queryShow(movie, cineplex, Helper.convertDate(date1)).size() != 0) {
                    switch (accessLevel){
                        case ADMINISTRATOR:
                            clearScreen("Directory: Home > Booking Database > Add Booking", accessLevel);
                            break;
                        case CUSTOMER:
                            clearScreen("Directory: Home > Add Booking", accessLevel);
                            break;
                    }
                    ss.listShow(ss.queryShow(movie, cineplex, Helper.convertDate(date1)));
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

                show = ss.getShow(movie, cineplex, Helper.convertDateTime(date1 + " " + time1));
                break;
            } catch (Exception e) {
                System.out.println("Invalid time given!");
            }
        }

        if (show != null){
            System.out.println("Creating booking...");
            System.out.println();
            ViewLogin view = new ViewLogin(systems);
            String result = view.menuString(AccessLevel.CUSTOMER);
            if (result == null){
                return;
            }
            else{
                Customer customer = cus.getCustomer(result);

                Date date = new Date();
                Booking booking = bs.createBooking(customer, show, date);
                ArrayList < String > seatBooked = selectSeats(accessLevel, show, booking.getBookingID());
                try {
                    if (seatBooked.size() == 0) {
                        throw new NoSeatBookedException("No Seats were Booked.");
                    }
                } catch (NoSeatBookedException e){
                    System.out.println("Error occurred! Please try again. Press Enter to continue.");
                    sc.nextLine();
                    return;
                }
                for (String seat : seatBooked) {
                    show.getCinemaLayout().assignSeat(seat, booking.getBookingID());
                }
                bs.addSeattoBooking(booking.getBookingID(), seatBooked);
                ss.serialize();
                bs.serialize();
                switch (accessLevel){
                    case ADMINISTRATOR:
                        clearScreen("Directory: Home > Booking Database > Add Booking", accessLevel);
                        break;
                    case CUSTOMER:
                        clearScreen("Directory: Home > Add Booking", accessLevel);
                        break;
                }

                System.out.println(bs.getBookings().get(bs.getIndex(booking.getBookingID())).getSeats());
                System.out.println("The following ticket types are available for this show:");

                Price price = new Price();
                String format = "2D";
                Object[] ticketTypes = price.checkTicketType(format, show.getDate());

                int i = 0;
                while(i <ticketTypes.length){
                    System.out.println(ticketTypes[i]);
                    i++;
                }
                System.out.println();
                boolean isStudent = false;
                boolean isSenior = false;
                boolean is3D = false;
                while(true) {
                    System.out.println("Enter the ticket type you would like to purchase:");
                    String ticketType = sc.next();

                    if (ticketType.equalsIgnoreCase("Student")) {
                        isStudent = true;
                        break;
                    } else if (ticketType.equalsIgnoreCase("Senior")) {
                        isSenior = true;
                        break;
                    } else if (ticketType.equalsIgnoreCase("Normal")) {
                        break;
                    } else {
                        System.out.println("Invalid Ticket Type!");
                    }
                }
                if(show.getMovieFormat().equalsIgnoreCase("3D")){
                    is3D=true;
                }
                System.out.println();
                boolean isBlockbuster = show.getMovie().getBlockbuster();
                String cinemaType = show.getCinemaHall().getCinemaType();
                double seatPrice = price.calculatePrice(show.getDate(),isStudent,isSenior,is3D, isBlockbuster, cinemaType);
                double totalPrice = seatPrice * seatBooked.size();
                System.out.println("Each ticket costs " + seatPrice);
                System.out.println("The total price will be " + totalPrice);
                System.out.println();
                sc.nextLine();
                System.out.println("Enter payment mode: ");
                String paymentMode = sc.nextLine();

                bs.initiatePayment(booking.getBookingID(), totalPrice, paymentMode);
                System.out.println("Payment Processing...");
                System.out.println("Payment Successful!");
                System.out.println("Booking Successful!");

                bs.deserialize();
                displayBooking(accessLevel, booking.getBookingID());
                bs.serialize();
                System.out.println(bs.getNumberofBookings());
            }

        } else {
            System.out.println("Error occurred! Please try again. Press Enter to continue.");
            sc.nextLine();
        }
    }

    /**
     * Update booking menu of the view.
     * @param accessLevel the access level, customer or administrator
     */
    @Override
    public void update(AccessLevel accessLevel) {
        clearScreen("Directory: Home > Booking Database > Update Booking", accessLevel);
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
     * Delete booking menu of the view.
     * @param accessLevel the access level, customer or administrator
     */
    @Override
    public void delete(AccessLevel accessLevel) {
        clearScreen("Directory: Home > Booking Database > Delete Booking", accessLevel);
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
     * Reset booking database menu of the view.
     * Note that it is only accessible to administrators.
     * @param accessLevel the access level, customer or administrator
     */
    @Override
    public void reset(AccessLevel accessLevel) {
        clearScreen("Directory: Home > Booking Database > Reset Database", accessLevel);
        switch (accessLevel) {
            case CUSTOMER:
                System.out.println("Access denied!");
                break;
            case ADMINISTRATOR:
                System.out.println("You are going to reset the database. Confirm action? (Y/N)");
                char c = sc.next().charAt(0);
                sc.nextLine();
                if (c == 'Y') {
                    if (bs.resetDatabase()) System.out.println("Reset successful!");
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
     * List booking menu of the view.
     * Note that it is only accessible to administrators.
     * @param accessLevel the access level, customer or administrator
     */
    public void list(AccessLevel accessLevel) {
        clearScreen("Directory: Home > Booking Database > List Bookings", accessLevel);
        switch (accessLevel) {
            case CUSTOMER:
                System.out.println("Access denied!");
                break;
            case ADMINISTRATOR:
                System.out.println("Input customer username: ");
                String customerID = sc.next();
                Customer customer = cus.getCustomer(customerID);
                if (customer != null){
                    bs.printArrayList(bs.getBookingHistory(customer));
                    System.out.println("Press Enter to continue");
                    sc.nextLine();
                    sc.nextLine();
                } else {
                    System.out.println("No bookings found under this customer!");
                    System.out.println("Press Enter to continue");
                    sc.nextLine();
                    sc.nextLine();
                }
                break;
        }
    }

    /**
     * Query booking menu of the view.
     * Note that it is only accessible to administrators.
     * @param accessLevel the access level, customer or administrator
     */
    public void query(AccessLevel accessLevel) {
        clearScreen("Directory: Home > Booking Database > Query Booking", accessLevel);
        switch (accessLevel) {
            case CUSTOMER:
                System.out.println("Access denied!");
                break;
            case ADMINISTRATOR:
                System.out.println("Input Transaction ID:");
                String bookingID = sc.nextLine();
                Booking booking = bs.getBooking(bookingID);
                if (booking != null){
                    System.out.println("These are the details of your booking.");
                    booking.print();
                    System.out.println("==========================================");
                    System.out.println();
                    System.out.println("Press Enter to return to Home");
                    sc.nextLine();
                } else {
                    System.out.println("No booking found! Press Enter to return to Home");
                    sc.nextLine();
                }
                break;


        }
    }

    /**
     * Display booking menu of the view.
     * @param accessLevel the access level, customer or administrator
     * @param bookingID the booking ID
     */
    public void displayBooking(AccessLevel accessLevel, String bookingID) {
        switch (accessLevel){
            case ADMINISTRATOR:
                clearScreen("Directory: Home > Booking Database > Booking Confirmation", accessLevel);
                break;
            case CUSTOMER:
                clearScreen("Directory: Home > Booking Confirmation", accessLevel);
                break;
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("These are the details of your confirmed booking. Thank you for choosing Golden Town!");
        BookingSystem bs = new BookingSystem();
        Booking booking = bs.getBookings().get(bs.getIndex(bookingID));
        booking.print();
        System.out.println("=======================");
        System.out.println();
        System.out.println("Press Enter to return to Home");
        sc.nextLine();
    }

    public ArrayList<String> selectSeats(AccessLevel accessLevel, Show show, String bookingID) {

        ArrayList<String> result = new ArrayList<String>();
        while (true) {
            switch (accessLevel){
                case ADMINISTRATOR:
                    clearScreen("Directory: Home > Booking Database > Select Seats", accessLevel);
                case CUSTOMER:
                    clearScreen("Directory: Home > Add Booking > Select Seats", accessLevel);
            }

            System.out.println("Select the seats");
            show.getCinemaLayout().printLayout();
            System.out.println();
            System.out.println("Add seats by seat number, ending with #");
            System.out.println("E.g. [A2 A3 A4 #]");
            String seat_number = sc.next();
            if (seat_number.equals("#")) {
                break;
            }
            boolean assignResult = show.getCinemaLayout().assignSeat(seat_number.toUpperCase(), bookingID);
            if (assignResult) {
                result.add(seat_number);
            } else {
                System.out.println("Seat allocation failed. Please check inputs and try again. Press enter to continue.");
                sc.nextLine();
                sc.nextLine();
            }
        }

        System.out.println("Please confirm the seats allocated. Press Enter to continue.");
        System.out.println(result);
        sc.nextLine();
        sc.nextLine();

        return result;
    }

}
