package MOBLIMA;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Represents a review entity.
 * Reviews can be made for a movie.
 * @author	SS1 Group 2
 * @version	1.0
 * @since	2019-11-13
 */
public class Review implements Serializable {

    /**
     * Rating of the movie, between 1 and 5.
     */
    private double rating;

    /**
     * Review text of the review.
     */
    private String text;

    /**
     * Datetime in which review was made.
     */
    private LocalDateTime datetime;


    /**
     * Constructor of the review object.
     * @param text the review text of the review
     * @param rating the rating of the review
     */
    public Review(String text, double rating){
        this.rating = rating;
        this.text = text;
        this.datetime = LocalDateTime.now();
    }


    /**
     * Get the rating of the review.
     * @return rating of the review
     */
    public double getRating(){
        return rating;
    }

    /**
     * Get the review text of the review.
     * @return review text of the review
     */
    public String getComment(){
        return text;
    }
}
