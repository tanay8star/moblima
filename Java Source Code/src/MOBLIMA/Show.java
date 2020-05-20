package MOBLIMA;

import java.io.Serializable;
import java.util.Date;

/**
 * Represents a show entity.
 * This is different from a movie as a show represents when a movie is showed at a cineplex at a given date and time.
 * A movie can have multiple shows.
 * @author	SS1 Group 2
 * @version	1.0
 * @since	2019-11-13
 */
public class Show implements Serializable, Comparable {

    /**
     * The cineplex of the show.
     */
    private Cineplex cineplex = null;

    /**
     * The cinema hall of the show.
     */
    private CinemaHall cinemaHall = null;

    /**
     * The cinema layout of the show, to keep track of the layout, which seats are occupied and which are not.
     */
    private CinemaLayout cinemaLayout = null;

    /**
     * Datetime of the show.
     */
    private Date date;

    /**
     * The movie of the show.
     */
    private Movie movie = null;

    /**
     * The status of the show.
     */
    private ShowStatus status = null;

    /**
     * Constructor of a show, given its details.
     * @param cineplex cineplex of the show
     * @param cinemaHall cinema hall of the show
     * @param date datetime of the shpw
     * @param movie movie of the show
     */
    public Show(Cineplex cineplex, CinemaHall cinemaHall, Date date, Movie movie){
        this.cinemaHall = cinemaHall;
        this.date = date;
        this.movie = movie;
        this.status = ShowStatus.AVAILABLE;
        try {
            this.cinemaLayout = (CinemaLayout) cinemaHall.getLayout().clone();
        } catch (Exception e) {}
        this.cineplex = cineplex;
    }


    /**
     * Get the cinema hall of the show.
     * @return cinema hall of the show
     */
    public CinemaHall getCinemaHall() {
        return cinemaHall;
    }

    /**
     * Get the datetime of the show.
     * @return datetime of the show
     */
    public Date getDate(){return date;}

    /**
     * Get the movie of the show.
     * @return movie of the show
     */
    public Movie getMovie() {
        return movie;
    }

    /**
     * Get the cineplex of the show
     * @return cineplex of the show
     */
    public Cineplex getCineplex(){return cineplex;}

    /**
     * Print the show details
     */
    public void print(){
        System.out.println(cinemaHall.getHallID());
        System.out.println(date);
        System.out.println(movie.getTitle());
        System.out.println(status);
    }

    /**
     * Get the layout of the cinema hall of the show.
     * @return layout of the cinema hall of the show
     */
    public CinemaLayout getCinemaLayout(){return cinemaLayout;}

    /**
     * Get the format of the movie.
     * @return format of the movie
     */
    public String getMovieFormat(){return movie.getFormat();}

    /**
     * Set the cineplex of the show.
     * @param cineplex cineplex of the show
     * @param cinemaHall cinema hall of the show
     */
    public void setCineplex(Cineplex cineplex, CinemaHall cinemaHall){
        this.cineplex = cineplex;
        this.cinemaHall = cinemaHall;
    }

    /**
     * Set the date of the show.
     * @param date date of the show
     */
    public void setDate(Date date){
        this.date = date;
    }

    /**
     * Set the movie of the show.
     * @param movie movie of the show
     */
    public void setMovie(Movie movie){
        this.movie = movie;
    }

    /**
     * Comparison method to make the shows comparable, where comparison is based on datetime.
     * No type checking is done as comparison is only used to sort.
     * @param o show to be compared to
     * @return Comparison results
     */
    @Override
    public int compareTo(Object o) {
        Show s = (Show) o;
        return this.getDate().compareTo(s.getDate());
    }
}