package MOBLIMA;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

/**
 * Represents a controller system which keeps track of the bookings.
 * @author	SS1 Group 2
 * @version	1.0
 * @since	2019-11-13
 */
public class BookingSystem implements MOBLIMASystem{

	/**
	 * An arraylist of all the bookings.
	 */
	private ArrayList<Booking> bookingList;

	/**
	 * Constructor for the system.
	 * It attempts to deserialize the database file.
	 * If file does not exist, it will reset the database.
	 */
	public BookingSystem() {
		if (!deserialize()) resetDatabase();
	}

	/**
	 * Creates a booking and adds it into the database.
	 * @param customer customer who made the booking
	 * @param show show associated with the booking
	 * @param date date booking was made
	 * @return the booking entity
	 */
	public Booking createBooking(Customer customer, Show show, Date date){
		deserialize();
		String bookingID = generateBookingID(show);
		if (date == null){
			date = new Date();
		}
		Booking booking =  new Booking(show, customer, date, bookingID);
		bookingList.add(booking);
		serialize();
		return bookingList.get(bookingList.size() -1);
	}

	/**
	 * Generates a booking ID based on a given show.
	 * @param show show of the booking
	 * @return the generated booking ID
	 */
	public String generateBookingID(Show show){
		LocalDateTime myDateObj = LocalDateTime.now();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
		String formattedDate = myDateObj.format(myFormatObj);
		return show.getCineplex().getCineplexID() + formattedDate;
	}

	/**
	 * Creates a booking if provided with the cinema seats.
	 * @param customer customer who made the booking
	 * @param show show associated with the booking
	 * @param cinemaseats the seats to be booked
	 * @param datetime date booking was mde
	 * @return transaction ID of the booking
	 */
	public String createBooking(Customer customer, Show show, ArrayList<String> cinemaseats , Date datetime){
		String bookingID;
		try{
			deserialize();
			createBooking(customer, show, datetime);
			for(String i : cinemaseats){
				show.getCinemaLayout().assignSeat(i, bookingList.get(bookingList.size() -1).getBookingID());
			}
			bookingID = bookingList.get(bookingList.size() -1).getBookingID();
			addSeattoBooking(bookingList.get(bookingList.size() -1).getBookingID(), cinemaseats);
			Price price = new Price();
			double seatPrice = price.calculatePrice(show.getDate(),false,false,false, show.getMovie().getBlockbuster(), show.getCinemaHall().getCinemaType());
			double totalPrice = seatPrice * cinemaseats.size();
			bookingList.get(bookingList.size() -1).initiatePayment(bookingID, totalPrice, "VISA");
			serialize();
		}
		catch(Exception exception){
			return null;
		}		
		return bookingID;
	}

	/**
	 * Gets all the bookings in the database.
	 * @return array list of bookings
	 */
	public ArrayList<Booking> getBookings() {deserialize(); return bookingList;}

	/**
	 * Gets all the bookings made by a customer.
	 * @param customer the customer whose history is to be checked
	 * @return array list of bookings made by the customer
	 */
	public ArrayList<Booking> getBookingHistory(Customer customer){
		deserialize();

		ArrayList<Booking> bookinghistory = new ArrayList<>();

		for(int i = 0; i < bookingList.size(); i++) {
			if (bookingList.get(i).getCustomer().getCustomerID().equals(customer.getCustomerID())){
				bookinghistory.add(bookingList.get(i));
			}
		}

		return bookinghistory;
	}

	/**
	 * Gets a booking given the transaction ID
	 * @param bookingID transaction ID of the booking
	 * @return booking with the given transaction ID
	 */
	public Booking getBooking(String bookingID) {
		deserialize();
		try{
			int index = getIndex(bookingID);
			if (index>=0){
				return bookingList.get(index);
			}
		}
		catch(Exception exception){
			return null;
		}
		return null;
	}

	/**
	 * Removes a booking given the transaction ID
	 * @param bookingID transaction ID of the booking
	 * @return whether the removal of the booking was successful
	 */
	public boolean removeBooking(String bookingID){
		deserialize();
		try{
			int index = getIndex(bookingID);
			if (index>0){
				bookingList.remove(index);
			}
		}
		catch(Exception exception){
			return false;
		}
		return true;
	}

	/**
	 * Initiates the payment of a booking.
	 * @param bookingID transaction ID of the booking
	 * @param price price of the booking
	 * @param paymentMode mode of payment
	 * @return whether the payment of the booking is successful
	 */
	public boolean initiatePayment(String bookingID, double price, String paymentMode){
		deserialize();
		try{
			int index = getIndex(bookingID);

			if (index >= 0){
				if(bookingList.get(index).initiatePayment(bookingID, price, paymentMode)){
					serialize();
					return true;}
			}
		}
		catch(Exception exception){
			exception.printStackTrace();
			return false;
		}
		return false;
	}

	/**
	 * Gets the index of the booking in the array list given the transaction ID
	 * @param bookingID transaction ID of the booking
	 * @return index of the booking in the array list
	 */
	public int getIndex(String bookingID){
		deserialize();
		try{
			for(int i = 0; i < bookingList.size(); i++)
			{
				if (bookingList.get(i).getBookingID().equalsIgnoreCase(bookingID)){
					return i;
				}
			}
		}
		catch(Exception exception){
			return -1;
		}
		return -1;
	}

	/**
	 * Attempts to serialize the array list into a database file.
	 * @return whether serialization is successful
	 */
	public boolean serialize() {
		try {
			FileOutputStream fos = new FileOutputStream("bookingList.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(bookingList);
			oos.close();
			fos.close();
			return true;
		}
		catch(Exception e) {
			return false;
		}
	}

	/**
	 * Attempts to deserialize the database file into bookings array list.
	 * @return whether deserialization is successful
	 */
	public boolean deserialize(){
		try {
			FileInputStream fis = new FileInputStream("bookingList.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			bookingList = (ArrayList<Booking>)ois.readObject();
			ois.close();
			fis.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}


	/**
	 * Gets the number of bookings in the booking list.
	 * @return number of bookings in the booking list
	 */
	public int getNumberofBookings(){
		deserialize();
		return bookingList.size();
	}

	/**
	 * Adds seats into a booking.
	 * @param bookingID transaction ID of the booking
	 * @param seatBooked array list of seats to be added
	 * @return whether the addition was successful
	 */
	public boolean addSeattoBooking(String bookingID, ArrayList<String> seatBooked){
		deserialize();
		Booking booking = bookingList.get(getIndex(bookingID));
		for(String i : seatBooked){
			booking.addSeat(i);
		}
		removeBooking(booking.getBookingID());
		serialize();
		deserialize();
		bookingList.add(booking);
		return serialize();

	}

	/**
	 * Prints out all the bookings in the database.
	 */
	public void printBookings(){
		for(Booking b : bookingList){
			b.print();
		}
	}

	/**
	 * Resets the booking database.
	 * It is preloaded with default bookings.
	 * @return whether resetting the database is successful
	 */
	public boolean resetDatabase() {
		bookingList = new ArrayList<Booking>();
		serialize();
		ShowSystem showSystem = new ShowSystem();

		showSystem.resetDatabase();
		CustomerSystem customerSystem = new CustomerSystem();
		int customersize = customerSystem.numberOfCustomer();
		int showsize = showSystem.getNumberOfShows();
		ArrayList<String> seats= new ArrayList<>();
		seats.add("A4");
		seats.add("A5");
		seats.add("A6");
		for(int i=0; i < Math.min(customersize, 3) && i < Math.min(showsize, 3) ;i++){
			String id = createBooking(customerSystem.getCustomer(customerSystem.getCustomerID(i)), showSystem.getShow(i+1),seats,new Date());
			for (String seat : seats) {
                showSystem.getShow(i+1).getCinemaLayout().assignSeat(seat, id);
            }
            addSeattoBooking(id, seats);
		}
		showSystem.serialize();
		return serialize();
	}


	/**
	 * Prints an array list of bookings.
	 * @param input array list of bookings
	 */
	public static void printArrayList(ArrayList<Booking> input){
		try {
			int index = 0;
			while (true) {
				System.out.println("============================================");
				input.get(index).print();
				System.out.println("============================================");
				index++;
			}
		} catch (Exception exception){

		}
	}
}