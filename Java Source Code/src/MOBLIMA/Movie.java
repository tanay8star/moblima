package MOBLIMA;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents a movie.
 * @author	SS1 Group 2
 * @version	1.0
 * @since	2019-11-13
 */

public class Movie implements Serializable, Comparable {

   /**
     * The title of the movie.
     */
    private String title;

    /**
     * The status of the movie, which takes only the enumerated values.
     */
    private MovieStatus status = MovieStatus.COMING_SOON;

    /**
     * The synopsis of the movie.
     */
    private String synopsis;

    /**
     * The director of the movie.
     */
    private String director;

    /**
     * Whether the movie is a blockbuster movie.
     */
    private boolean isBlockbuster = false;

    /**
     * An array list of all the casts in the movie.
     */
    private ArrayList<String> casts = new ArrayList<String>();

    /**
     * An array list of all the reviews given for the movie.
     */
    private ArrayList<Review> reviews = new ArrayList<Review>();

    /**
     * The run time of the movie.
     */
    private int showtime = 0;

    /**
     * The format of the movie, which can be 2D or 3D.
     */
    private String format = "2D";

    /**
     * The category of the movie, which takes only the enumerated values.
     */
    private MovieCategory category = MovieCategory.G;

    /**
     * Constructor of the movie, which is empty at the start.
     * It requires no parameters.
     */
    public Movie() {
    }

    /**
     * Get the title of the movie.
     * It automatically adds "(3D)" if the movie is 3D.
     * @return title of the movie
     */
    public String getTitle() {
        return title + (format.equals("3D") ? " (3D)" : "");
    }

    /**
     * Get the status of the movie.
     * @return status of the movie
     */
    public MovieStatus getStatus() {
        return status;
    }

    /**
     * Get the synopsis of the movie.
     * @return synopsis of the movie
     */
    public String getSynopsis() {
        return synopsis;
    }

    /**
     * Get the director of the movie.
     * @return director of the movie
     */
    public String getDirector() {
        return director;
    }

    /**
     * Get the format of the movie.
     * @return format of the movie
     */
    public String getFormat() {
        return format;
    }

    /**
     * Get an array list of all the casts.
     * @return array list of all the casts
     */
    public ArrayList<String> getCasts() {
        return casts;
    }

    /**
     * Get whether the movie is a blockbuster.
     * @return whether the movie is a blockbuster
     */
    public boolean getBlockbuster() {
        return isBlockbuster;
    }

    /**
     * Get the category of the movie.
     * @return category of the movie
     */
    public MovieCategory getCategory() {
        return category;
    }

    /**
     * Set the title of the movie.
     * @param title title of the movie
     * @return whether setting the title was successful
     */
    public boolean setTitle(String title) {
        this.title = title;
        return true;
    }

    /**
     * Set the status of the movie.
     * @param status status of the movie
     * @return whether setting the status was successful
     */
    public boolean setStatus(MovieStatus status) {
        this.status = status;
        return true;
    }

    /**
     * Set the synopsis of the movie.
     * @param synopsis synopsis of the movie
     * @return whether setting the synopsis was successful
     */
    public boolean setSynopsis(String synopsis) {
        this.synopsis = synopsis;
        return true;
    }

    /**
     * Set the director of the movie.
     * @param director director of the movie
     * @return whether setting the director was successful
     */
    public boolean setDirector(String director) {
        this.director = director;
        return true;
    }

    /**
     * Set the showtime of the movie.
     * @param showtime showtime of the movie
     * @return whether setting the showtime was successful
     */
    public boolean setShowtime(int showtime) {
        this.showtime = showtime;
        return true;
    }

    /**
     * Set the format of the movie.
     * @param format format of the movie
     * @return whether setting the format was successful
     */
    public boolean setFormat(String format) {
        this.format = format;
        return true;
    }

    /**
     * Set the category of the movie.
     * @param category category of the movie
     * @return whether setting the category was successful
     */
    public boolean setCategory(MovieCategory category) {
        this.category = category;
        return true;
    }

    /**
     * Add a cast to the movie cast array list.
     * @param cast cast to be added
     * @return whether addition of the cast was successful
     */
    public boolean addCast(String cast) {
        if (casts.contains(cast)) {
            return false;
        } else {
            casts.add(cast);
            return true;
        }
    }

    /**
     * Remove cast from the movie cast array list.
     * @param cast cast to be removed
     * @return whether deletion of the cast was successful
     */
    public boolean removeCast(String cast) {
        if (casts.contains(cast)) {
            casts.remove(cast);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Set whether a movie is blockbuster.
     * @param isBlockbuster whether a movie is blocksbuster
     * @return whether setting the blockbuster state was successful
     */
    public boolean setBlockbuster(boolean isBlockbuster) {
        this.isBlockbuster = isBlockbuster;
        return true;
    }

    /**
     * Add a review to the movie review list.
     * @param mRating the rating of the review
     * @param mText the review text of the review
     */
    public void addReview(double mRating, String mText) {
        Review mReview = new Review(mText, mRating);
        reviews.add(mReview);
    }

    /**
     * Delete a review from the movie review list.
     * @param i index of the review in the movie review list
     * @return whether deletion was successful
     */
    public boolean deleteReview(int i) {
        try {
            reviews.remove(i - 1);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get the average rating of the movie, to 1 decimal place.
     * @return average rating of the movie, to 1 decimal place
     */
    public double getAvgRating() {
        double sum = 0;

        if (reviews.size() == 0) return Double.NaN;
        for (int i = 0; i < reviews.size(); i++) {
            Review r = reviews.get(i);
            double mRating = r.getRating();
            sum += mRating;
        }

        BigDecimal bd = BigDecimal.valueOf(sum / reviews.size());
        bd = bd.setScale(1, RoundingMode.HALF_UP);
        return bd.doubleValue();

    }

    /**
     * Print all the review texts in the review array list.
     */
    public void printReviews() {
        for (int i = 0; i < reviews.size(); i++) {
            Review r = reviews.get(i);
            double mRating = r.getRating();
            String mComment = r.getComment();
            System.out.println("Review            " + (i + 1));
            System.out.println("Rating:           " + mRating);
            System.out.println("Comments:         " + mComment);
            System.out.println();
        }
    }

    /**
     * Comparison method to allow implementation of Comparable interface.
     * Here we compare the movie title in alphabetical order.
     * No type checking was here as this function is only used for sorting.
     * @param o the object to be compared to
     * @return the comparison result
     */
    @Override
    public int compareTo(Object o) {
        Movie m = (Movie) o;
        return this.getTitle().compareToIgnoreCase(m.getTitle());
    }
}
