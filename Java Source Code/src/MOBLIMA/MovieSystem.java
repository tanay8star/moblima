package MOBLIMA;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


/**
 * Represents a controller system which keeps track of all movies.
 * @author	SS1 Group 2
 * @version	1.0
 * @since	2019-11-13
 */
public class MovieSystem implements MOBLIMASystem{

	/**
	 * Array list of all movies in the database.
	 */
    private ArrayList<Movie> movieDatabase;

	/**
	 * Constructor for the system.
	 * It attempts to deserialize the database file.
	 * If file does not exist, it will reset the database.
	 */
    public MovieSystem() {
    	if (!deserialize()) resetDatabase();
    }


	/**
	 * Sorts the movies in the movie database based on movie title.
	 */
	public void sortMovie(){
    	Collections.sort(movieDatabase);
    	serialize();
	}

	/**
	 * Prints all the movie in the database, in alphabetical order.
	 */
    public void listMovie(){
    	deserialize();
    	sortMovie();
    	serialize();

    	System.out.println("List of movies:");
    	if (movieDatabase.size() == 0) System.out.println("No movie in database!");
    	else for (int i = 0; i < movieDatabase.size(); i++) {
    		System.out.println((i + 1) + ". " + movieDatabase.get(i).getTitle());
    	}
    	System.out.println();
    }

	/**
	 * Prints all the movies given in an array list of movies.
	 * @param array array list of movies to be printed
	 */
	public void listMovie(ArrayList<Movie> array){
		System.out.println("List of movies:");
		if (array.size() == 0) System.out.println("No matching movies!");
		else for (int i = 0; i < array.size(); i++) {
			System.out.println((i + 1) + ". " + array.get(i).getTitle());
		}
		System.out.println();
	}

	/**
	 * Prints all the movies that are showing.
	 * @return whether there is movie showing or not
	 */
	public boolean listShowingMovies(){
		System.out.println("List of movies:");
		if (getShowingMovies().size() == 0) {System.out.println("No showing movie in database!"); return false;}
		else for (int i = 0; i < getShowingMovies().size(); i++) {
			System.out.println((i + 1) + ". " + getShowingMovies().get(i).getTitle());
		}
		System.out.println();
		return true;
	}

	/**
	 * Gets all the showing movies.
	 * @return array list of all showing movies
	 */
	public ArrayList<Movie> getShowingMovies() {
		deserialize();
		sortMovie();
		serialize();
		ArrayList<Movie> result = new ArrayList<Movie>();

		for (Movie M : movieDatabase) {
			if (M.getStatus() == MovieStatus.NOW_SHOWING || M.getStatus() == MovieStatus.PREVIEW) {
				result.add(M);
			}
		}
		return result;
	}

	/**
	 * Searches for a movie based on its title.
	 * The search mechanism is "contains" where one checks if one is substring of another, and is not case-sensitive.
	 * As such, one can search "LaDd" and "Aladdin" will appear.
	 * @param s substring to be searched for
	 * @return array list of movies which contains the substring
	 */

	public ArrayList<Movie> searchMovie(String s){
		ArrayList<Movie> results = new ArrayList<Movie>();
		for (Movie m : movieDatabase) {
			if (m.getTitle().toLowerCase().contains(s.toLowerCase())) results.add(m);
		}
		return results;
	}

	/**
	 * Gets the number of movies in the database.
	 * @return number of movies in the database
	 */

    public int numMovies() {return movieDatabase.size();}



	/**
	 * Selector tool of movies in a given movie array list.
	 * Prints a list of movies, and user will have to select a movie using its index as printed.
	 * @param array the array list of movies
	 * @return movie if it exists, else null
	 */
	public Movie selectShowingMovie(ArrayList<Movie> array) {
		listMovie(array);
		if (array.size() == 0) return null;
		System.out.println("Enter movie number: ");
		int i = Helper.robustNextInt();
		try {
			return array.get(i - 1);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Selector tool of movies in database, only for movies which are showing.
	 * Prints a list of movies, and user will have to select a movie using its index as printed.
	 * @return movie if it exists, else null
	 */
	public Movie selectShowingMovie() {
		if (listShowingMovies()) {
			System.out.println("Enter movie number: ");
			int i = Helper.robustNextInt();
			try {
				return getShowingMovies().get(i - 1);
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}

	/**
	 * Gets all movies in the database.
	 * @return array list of all movies in the database.
	 */
	public ArrayList<Movie> getMovies() {
    	return movieDatabase;
    }

	/**
	 * Gets a movie in database with exact title given.
	 * @param title title of the movie
	 * @return movie with corresponding title
	 */
	public Movie getMovie(String title){
		deserialize();
        for (Movie i : movieDatabase) {
            if (i.getTitle().equals(title))
                return i;
        }
        return null;
    }

	/**
	 * Gets a movie given its index in the database (index starts from 1).
	 * @param i index in the database
	 * @return movie corresponding to this index
	 */
	public Movie getMovie(int i){
		deserialize();
		try {
			return movieDatabase.get(i - 1);
		} catch (Exception e){
			return null;
		}
    }

	/**
	 * Adds a movie into the database.
	 * @param movie movie to be added to the database
	 */

	public void addMovie(Movie movie) {
		deserialize();
    	movieDatabase.add(movie);
    	serialize();
    }

	/**
	 * Deletes movie at given index in the database (index starts from 1).
	 * @param i index in the database
	 * @return whether deletion was successful
	 */
	public boolean deleteMovie(int i) {
		deserialize();
		try {
			movieDatabase.remove(i - 1);
			return serialize();
		} catch (Exception e) {
			return false;
		}

    }

	/**
	 * Get top 5 movies by ranking.
	 * @return a hash map of the ranking, where the key is the ranking (1, 2, 3, 4 and 5) and the value is an array list of the movie and its booking count
	 */
	public HashMap<Integer, ArrayList<Object>> rankMoviebyBooking() {
		deserialize();
    	HashMap<String, Integer> count = new HashMap<String, Integer>();
    	for (Movie m : movieDatabase) {
    		count.put(m.getTitle(), 0);
		}

    	BookingSystem bs = new BookingSystem();
    	for (Booking b : bs.getBookings()) {
    		count.put(b.getShow().getMovie().getTitle(), count.get(b.getShow().getMovie().getTitle()) + 1);
		}

    	String m1 = "", m2 = "", m3 = "", m4 = "", m5 = "";
    	int c1 = -1, c2 = -1, c3 = -1, c4 = -1, c5 = -1, c;

    	for (String m : count.keySet()) {
    		c = count.get(m);
    		if (c > c1) {
				m5 = m4; m4 = m3; m3 = m2; m2 = m1; m1 = m;
    			c5 = c4; c4 = c3; c3 = c2; c2 = c1; c1 = c;
			} else if (c > c2){
				m5 = m4; m4 = m3; m3 = m2; m2 = m;
				c5 = c4; c4 = c3; c3 = c2; c2 = c;
			} else if (c > c3) {
				m5 = m4; m4 = m3; m3 = m;
				c5 = c4; c4 = c3; c3 = c;
			} else if (c > c4) {
				m5 = m4; m4 = m;
				c5 = c4; c4 = c;
			} else if (c > c4) {
				m5 = m;
				c5 = c;
			}
		
		}

    	HashMap<Integer, ArrayList<Object>> results = new HashMap<Integer, ArrayList<Object>>();
    	if (m1 != "") {
			ArrayList<Object> ar = new ArrayList<Object>();
			ar.add(getMovie(m1));
			ar.add(c1);
			results.put(1, ar);
		}
		if (m2 != "") {
			ArrayList<Object> ar = new ArrayList<Object>();
			ar.add(getMovie(m2));
			ar.add(c2);
			results.put(2, ar);
		}
		if (m3 != "") {
			ArrayList<Object> ar = new ArrayList<Object>();
			ar.add(getMovie(m3));
			ar.add(c3);
			results.put(3, ar);
		}
		if (m4 != "") {
			ArrayList<Object> ar = new ArrayList<Object>();
			ar.add(getMovie(m4));
			ar.add(c4);
			results.put(4, ar);
		}
		if (m5 != "") {
			ArrayList<Object> ar = new ArrayList<Object>();
			ar.add(getMovie(m3));
			ar.add(c5);
			results.put(5, ar);
		}
		return results;
	}

	/**
	 * Get top 5 movies by ratings.
	 * @return array list of the top 5 movies, where the movies are sorted in descending order of rating
	 */
	public ArrayList<Movie> rankMoviebyRatings() {
		deserialize();

		Movie m1 = null, m2 = null, m3 = null, m4 = null, m5 = null;
		double r1 = -1, r2 = -1, r3 = -1, r4 = -1, r5 = -1, r;

		for (Movie m : movieDatabase) {
			r = m.getAvgRating();
			if (r > r1 || m1 == null) {
				m5 = m4; m4 = m3; m3 = m2; m2 = m1; m1 = m;
				r5 = r4; r4 = r3; r3 = r2; r2 = r1; r1 = r;
			} else if (r > r2 || m2 == null){
				m5 = m4; m4 = m3; m3 = m2; m2 = m;
				r5 = r4; r4 = r3; r3 = r2; r2 = r;
			} else if (r > r3 || m3 == null) {
				m5 = m4; m4 = m3; m3 = m;
				r5 = r4; r4 = r3; r3 = r;
			} else if (r > r4 || m4 == null) {
				m5 = m4; m4 = m;
				r5 = r4; r4 = r;
			} else if (r > r4 || m5 == null) {
				m5 = m;
				r5 = r;
			}
		}

		ArrayList<Movie> results = new ArrayList<Movie>();
		if (m1 != null) results.add(m1);
		if (m2 != null) results.add(m2);
		if (m3 != null) results.add(m3);
		if (m4 != null) results.add(m4);
		if (m5 != null) results.add(m5);
		return results;
	}

	/**
	 * Attempts to deserialize the database file into an array list of movies.
	 * @return whether deserialization is successful
	 */
    public boolean deserialize() {
		try {
			FileInputStream fis = new FileInputStream("moviedb.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			movieDatabase = (ArrayList<Movie>) ois.readObject();
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
			FileOutputStream fos = new FileOutputStream("moviedb.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(movieDatabase);
			oos.close();
			fos.close();
			return true;
		}
		catch(Exception e) {
			return false;
		}
	}

	/**
	 * Resets the movie database.
	 * It is preloaded with 6 different movies.
	 * @return whether resetting the database is successful
	 */
	public boolean resetDatabase() {
		movieDatabase = new ArrayList<Movie>();

		Movie movie;

		movie = new Movie();
		movie.setTitle("Aladdin");
		movie.setSynopsis("A kind-hearted street urchin and a power-hungry Grand Vizier vie for a magic lamp that has the power to make their deepest wishes come true.");
		movie.setDirector("Guy Ritchie");
		movie.addCast("Will Smith");
		movie.addCast("Mena Massoud");
		movie.addCast("Naomi Scott");
		movie.addCast("Marwan Kenzari");
		movie.addCast("Navid Negahban");
		movie.setShowtime(128);
		movie.setBlockbuster(false);
		movie.setFormat("2D");
		movie.addReview(5, "Beautiful soundtrack, i really enjoyed a whole new world and how naomi portrays jasmine");
		movie.addReview(4, "I know everyone wants to compare this to the Animated version, but don't. Take it as it comes and you will thoroughly enjoy it. It does stay pretty faithful to the animated version I think.");
		movie.addReview(3, "I was left cringing during each musical sequence due to the blatant over use of autotune. The actors bring no emotion to the story.");
		movie.addReview(4.2, "Aladdin truly is magical. Aladdin has a long and illustrious legacy. As usual, the plot of beggar Aladdin and Princess Jasmine fighting for their love is incredible. This movie was great !!");
		movie.setCategory(MovieCategory.G);
		movieDatabase.add(movie);

		movie = new Movie();
		movie.setTitle("Avengers: Endgame");
		movie.setSynopsis("After the devastating events of Avengers: Infinity Wars, the universe is in ruins. With the help of remaining allies, the Avengers assemble once more in order to reverse Thanos' actions and restore balance in the universe.");
		movie.setDirector("Anthony Russo");
		movie.addCast("Robert Downey Jr.");
		movie.addCast("Chris Evans");
		movie.addCast("Mark Ruffalo");
		movie.addCast("Chris Hemworth");
		movie.addCast("Scarlet Johansson");
		movie.setShowtime(181);
		movie.setBlockbuster(true);
		movie.setFormat("2D");
		movie.addReview(5, "Endgame is a great popcorn action movie to finish a saga of popcorn action movies. This isn't serious entertainment and shouldn't be considered as such, and it reminds me of how George Lucas made Star Wars as an homage to the cheap serials like Flash Gordon. Especially as comic-sourced material, this is what the MCU is. Some part of me is actually a bit pained to like it as much as I do, given that it is just playing on base emotions to make money for a massive conglomerate like Disney.");
		movie.addReview(4.5, "After waiting for a year, Avengers: Endgame hit the theatres and shattered a number of box office records worldwide. But, was it really so perfectly great? No.");
		movie.addReview(1, "Finally got around to watching this film. I wanted to like it... I'll stick with Logan , Deadpool and the gritty old Batman's. It's clear the direction Disney is going to take marvel and it's straight to cartoon land.");
		movie.addReview(5, "Walking in, given Marvel's latest publicity, I was expecting, well, I was expecting an absolute cringe fest, and thankfully I didn't get it. In fact, there were only a couple of absolute cringe moments and each dealt with Brie Larson, particularly her appearance in the final fight.");
		movie.setCategory(MovieCategory.PG_13);
		movieDatabase.add(movie);

		movie = new Movie();
		movie.setTitle("Frozen II");
		movie.setSynopsis("Elsa the Snow Queen and her sister Anna embark on an adventure far away from the kingdom of Arendelle. They are joined by friends, Kristoff, Olaf and Sven.");
		movie.setDirector("Jennifer Lee");
		movie.addCast("Kristen Bell");
		movie.addCast("Jason Ritter");
		movie.addCast("Evan Rachel Wood");
		movie.addCast("Jonathan Groff");
		movie.addCast("Rachel Matthews");
		movie.setShowtime(103);
		movie.setBlockbuster(false);
		movie.setFormat("2D");
		movie.addReview(4, "Very good movie i like it alot");
		movie.addReview(5,"Frozen is a masterful representation fo family love and friendship");
		movie.addReview(4.5, "This is a wonderful movie to watch over christmas my daughter and i have enjoyed the movie so much that our go to song for karaoke is let it go");
		movie.setCategory(MovieCategory.G);
		movieDatabase.add(movie);

		movie = new Movie();
		movie.setTitle("Frozen II");
		movie.setSynopsis("Elsa the Snow Queen and her sister Anna embark on an adventure far away from the kingdom of Arendelle. They are joined by friends, Kristoff, Olaf and Sven.");
		movie.setDirector("Jennifer Lee");
		movie.addCast("Kristen Bell");
		movie.addCast("Jason Ritter");
		movie.addCast("Evan Rachel Wood");
		movie.addCast("Jonathan Groff");
		movie.addCast("Rachel Matthews");
		movie.setShowtime(103);
		movie.setBlockbuster(false);
		movie.setFormat("3D");
		movie.addReview(5,"Frozen's wonderful landscape is represented perfectly by the wonderful visual artist who helped to animate the 3D version for this flim");
		movie.addReview(2, "It is the same feeling as watching the 2D, totally not worth it given the absurbed pricing for the 3D screening");
		movie.setCategory(MovieCategory.G);
		movieDatabase.add(movie);

		movie = new Movie();
		movie.setTitle("Shazam!");
		movie.setSynopsis("We all have a superhero inside us, it just takes a bit of magic to bring it out. In Billy Batson's case, by shouting out one word - SHAZAM - this streetwise fourteen-year-old foster kid can turn into the grown-up superhero Shazam.");
		movie.setDirector("David F Sandberg");
		movie.addCast("Zachary Levi");
		movie.addCast("Mark Strong");
		movie.addCast("Asher Angel");
		movie.addCast("Jack Dylan Grazer");
		movie.addCast("Adam Brody");
		movie.setShowtime(132);
		movie.setBlockbuster(false);
		movie.setFormat("2D");
		movie.addReview(4.5, "I feel like David Samberg is the DCEU's Taika Waititi. He's this movie director that really isn't known for much past some horror, which made him an initially odd choice for this movie; a choice that paid off tremendously.");
		movie.addReview(3.5, "It was pretty much exactly what I expected which is a really enjoyable and lighthearted superhero film with some funny moments and that will be enough for most people but it just didn't blow me away.");
		movie.addReview(5, "Seriously can't believe how much I loved this movie! This felt like a proper superhero movie felt when I was a kid, they captured the perfect tone, can't fault it.");
		movie.setCategory(MovieCategory.PG);
		movieDatabase.add(movie);

		movie = new Movie();
		movie.setTitle("Toy Story 4");
		movie.setSynopsis("When a new toy called Forky joins Woody and the gang, a road trip alongside old and new friends reveals how big the world can be for a toy.");
		movie.setDirector("Josh Cooley");
		movie.addCast("Tom Hanks");
		movie.addCast("Tim Allen");
		movie.addCast("Annie Potts");
		movie.addCast("Tony Hale");
		movie.addCast("Keegan-Michael Key");
		movie.setShowtime(100);
		movie.setBlockbuster(false);
		movie.setFormat("2D");
		movie.addReview(5, "Brings me back to my childhood it was a very nice sequel to the previous installments of the toy series");
		movie.addReview(4, "Very realistic animation, however the story could be improved, found it abit cliche");
		movie.addReview(5, "This movie will delight every Toy Story fan with the humor, heart, characters, and music.");
		movie.setCategory(MovieCategory.G);
		movieDatabase.add(movie);

		return serialize();
	}

}
