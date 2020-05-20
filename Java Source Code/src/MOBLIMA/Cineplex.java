package MOBLIMA;

import java.io.*;
import java.util.ArrayList;

/**
 * Represents the cineplex entity.
 * A cineplex may have multiple cinema halls.
 * @author	SS1 Group 2
 * @version	1.0
 * @since	2019-11-13
 */
public class Cineplex implements Serializable {
    /**
     * Name of the cineplex.
     */
	private String name;

    /**
     * ID of the cineplex, which is typically the short form of the name.
     */
	private String cineplexID;

    /**
     * Array list of cinema halls at the cineplex.
     */
	private ArrayList<CinemaHall> cinemaHallList = new ArrayList<CinemaHall>();

    /**
     * Constructor of the cineplex object.
     * @param name name of the cineplex
     * @param cineplexID ID of the cineplex
     */
    public Cineplex(String name, String cineplexID) {this.name = name; this.cineplexID = cineplexID;}
    
    public boolean setName(String name) {
    	this.name = name;
    	return true;
    }

    /**
     * Selector tool of cinema hall at the cineplex.
     * Prints a list of cinema halls, and user will have to select a cinema hall using its index as printed.
     * @return cinema hall if it exists, else null
     */
    public CinemaHall selectCinemaHall() {
        listCinemaHall();
        System.out.println("Enter cinema hall number: ");
        int i = Helper.robustNextInt();
        try {
            return getCinemaHall(i);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Adds a cinema hall to the cineplex.
     * @param cinemaHall cinema hall to be added to the cineplex
     * @return whether the addition is successful
     */
    public boolean addCinemaHall(CinemaHall cinemaHall) {
    	cinemaHallList.add(cinemaHall);
    	return true;
    }

    /**
     * Remove a cinema hall from the cineplex.
     * @param cinemaHall cinema hall to be removed from the cineplex
     * @return whether the removal is successful
     */
    public boolean removeCinemaHall(CinemaHall cinemaHall) {
    	cinemaHallList.remove(cinemaHall);
    	return true;
    }

    /**
     * Prints out all the cinema halls in the cineplex.
     */
    public void listCinemaHall() {
		System.out.println("Cinema Halls:");
		if (cinemaHallList.size() == 0) System.out.println("No halls in database!");
		else for (int i = 0; i < cinemaHallList.size(); i++) {
			System.out.println((i + 1) + ". " + cinemaHallList.get(i).getHallID() + ": " + cinemaHallList.get(i).getCinemaType());
		}
    }

    /**
     * Get a cinema hall given its index in the array list (index starts from 1).
     * @param i index in the array list
     * @return cinema hall with this index
     */
    public CinemaHall getCinemaHall(int i) {
    	return cinemaHallList.get(i - 1);
    }

    /**
     * Get all the cinema halls in the cineplex.
     * @return array list of cinema halls in the cineplex
     */
    public ArrayList<CinemaHall> getCinemaHalls() {
    	return cinemaHallList;
    }

    /**
     * Get the name of the cineplex.
     * @return name of the cineplex
     */
    public String getName(){
    	return name;
    }

    /**
     * Get the ID of the cineplex.
     * @return ID of the cineplex
     */
    public String getCineplexID() {
    	return cineplexID;
    }
}
