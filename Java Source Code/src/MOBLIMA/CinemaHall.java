package MOBLIMA;

import java.io.Serializable;

/**
 * Represents the entity cinema hall.
 * Every cineplex can have multiple cinema halls.
 * @author	SS1 Group 2
 * @version	1.0
 * @since	2019-11-13
 */

public class CinemaHall implements Serializable {
    /**
     * Hall ID of the cinema hall which uniquely identifies the cinema hall.
     */
    private String hallID;

    /**
     * Type of the cinema, which can be regular or golden suite.
     */
    private String cinemaType;

    /**
     * Constructor for the cinema hall entity.
     * @param cinemaType type of the cinema hall
     * @param hallID hall ID of the cinema hall
     */
    public CinemaHall(String cinemaType, String hallID){
        this.hallID = hallID;
        this.cinemaType = cinemaType;
    }

    /**
     * Get the layout of the cinema, which depends on the cinema hall type.
     * @return layout of the cinema as a CinemaLayout object
     */
    public CinemaLayout getLayout() {
    	if (cinemaType.equals("Regular")) {
    		CinemaLayout layout = new CinemaLayout(10, 10);
    		return layout;
    	}
        else if (cinemaType.equals("Golden Suite")) {
            CinemaLayout layout = new CinemaLayout(8, 8);
            return layout;
        }
    	else {
    		CinemaLayout layout = new CinemaLayout(10, 10);
    		return layout;
    	}	
    }

    /**
     * Get the hall ID of the cinema hall.
     * @return hall ID of the cinema hall
     */
    public String getHallID()  {
    	return hallID;
    }

    /**
     * Get the type of the cinema hall.
     * @return type of the cinema hall
     */
    public String getCinemaType() {
    	return cinemaType;
    }

    /**
     * Set the hall ID of the cinema hall.
     * @param ID hall ID of the cinema hall
     * @return whether setting the hall ID is successful
     */
    public boolean setHallID(String ID) {
    	this.hallID = ID;
    	return true;
    }

    /**
     * Set the type of the cinema hall.
     * @param type type of the cinema hall
     * @return whether setting the cinema hall type is successful
     */
    public boolean setCinemaType(String type) {
    	this.cinemaType = type;
    	return true;
    }
    
}