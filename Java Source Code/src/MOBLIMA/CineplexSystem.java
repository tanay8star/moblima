package MOBLIMA;

import java.io.*;
import java.util.ArrayList;

/**
 * Represents a controller system which keeps track of all the cineplexes.
 * @author	SS1 Group 2
 * @version	1.0
 * @since	2019-11-13
 */
public class CineplexSystem implements MOBLIMASystem{
	/**
	 * Array list of cineplexes in the database.
	 */
    private ArrayList<Cineplex> cineplexDatabase;

	/**
	 * Constructor for the system.
	 * It attempts to deserialize the database file.
	 * If file does not exist, it will reset the database.
	 */
    public CineplexSystem() {
    	if (!deserialize()) resetDatabase();
    }

	/**
	 * Prints out all the cineplexes in the database.
	 */
	public void listCineplex(){
    	deserialize();
    	System.out.println("List of cineplexes:");
    	if (cineplexDatabase.size() == 0) System.out.println("No cineplex in database!");
    	else for (int i = 0; i < cineplexDatabase.size(); i++) {
    		System.out.println((i + 1) + ". " + cineplexDatabase.get(i).getName());
    	}
    	System.out.println();
    }

	/**
	 * Selector tool of cineplex in database.
	 * Prints a list of cineplexes, and user will have to select a cineplex using its index as printed.
	 * @return cineplex if it exists, else null
	 */
	public Cineplex selectCineplex() {
		listCineplex();
		System.out.println("Enter cineplex number: ");
		int i = Helper.robustNextInt();
		try {
			return getCineplex(i);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Get the cineplex given its index in the array list (index starts from 1).
	 * @param i index in the array list
	 * @return cineplex at the index in the array list
	 */
    public Cineplex getCineplex(int i){
    	deserialize();
    	return cineplexDatabase.get(i - 1);
    }

	/**
	 * Get all the cineplexes in database.
	 * @return array list of all the cineplexes in the database
	 */
	public ArrayList<Cineplex> getCineplexes(){
        deserialize();
    	return cineplexDatabase;
    }

	/**
	 * Attempts to deserialize the database file into an array list of cineplexes.
	 * @return whether deserialization is successful
	 */
	public boolean deserialize() {
		try {
			FileInputStream fis = new FileInputStream("cineplexdb.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			cineplexDatabase = (ArrayList<Cineplex>) ois.readObject();
			ois.close();
			fis.close();
			return true;
		} catch (Exception e) {
			return false;
 		} 
	}

	/**
	 * Attempts to serialize the array list into a database file.
	 * @return whether serialization is successful
	 */
	public boolean serialize() {
		try {
			FileOutputStream fos = new FileOutputStream("cineplexdb.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(cineplexDatabase);
			oos.close();
			fos.close();
			return true;
		}
		catch(Exception e) {
			return false;
		}
	}

	/**
	 * Resets the cineplexes database.
	 * It has 3 cineplexes, Ang Mo Kio, Bedok and Changi.
	 * Each of the cineplex is preloaded with 2 regular halls and 1 golden suite hall.
	 * @return whether resetting the database is successful
	 */
	public boolean resetDatabase() {
		cineplexDatabase = new ArrayList<Cineplex>();
		Cineplex c;
		CinemaHall ch;
		String hallID;
		int i;

		c = new Cineplex("Ang Mo Kio", "AMK");
		for (i = 0; i < 2; i++) {
			hallID = c.getCineplexID() + (c.getCinemaHalls().size() + 1);
			ch = new CinemaHall("Regular", hallID);
			c.addCinemaHall(ch);
		}
		hallID = c.getCineplexID() + 3;
		ch = new CinemaHall("Golden Suite", hallID);
		c.addCinemaHall(ch);
		cineplexDatabase.add(c);

		c = new Cineplex("Bedok", "BED");
		for (i = 0; i < 2; i++) {
			hallID = c.getCineplexID() + (c.getCinemaHalls().size() + 1);
			ch = new CinemaHall("Regular", hallID);
			c.addCinemaHall(ch);
		}
		hallID = c.getCineplexID() + 3;
		ch = new CinemaHall("Golden Suite", hallID);
		c.addCinemaHall(ch);
		cineplexDatabase.add(c);

		c = new Cineplex("Changi", "CHA");
		for (i = 0; i < 2; i++) {
			hallID = c.getCineplexID() + (c.getCinemaHalls().size() + 1);
			ch = new CinemaHall("Regular", hallID);
			c.addCinemaHall(ch);
		}
		hallID = c.getCineplexID() + 3;
		ch = new CinemaHall("Golden Suite", hallID);
		c.addCinemaHall(ch);
		cineplexDatabase.add(c);

		return serialize();
	}

}
