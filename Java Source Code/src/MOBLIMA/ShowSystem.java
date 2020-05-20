package MOBLIMA;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Represents a controller system which keeps track of the shows.
 * @author	SS1 Group 2
 * @version	1.0
 * @since	2019-11-13
 */

public class ShowSystem implements MOBLIMASystem{

	/**
	 * An array list of the shows.
	 */
	private ArrayList<Show> showList;

	/**
	 * Constructor for the system.
	 * It attempts to deserialize the database file.
	 * If file does not exist, it will reset the database.
	 */
	public ShowSystem() {
		if (!deserialize()) resetDatabase();
	}

	/**
	 * Selector tool of shows in a given show array list.
	 * Prints a list of shows, and user will have to select a show using its index as printed.
	 * @param array the array list
	 * @return show if it exists, else null
	 */
	public Show selectShow(ArrayList<Show> array) {
		listShow(array);
		System.out.println("Enter show number: ");
		int i;
		do{
			i = Helper.robustNextInt();
			if(i>array.size() || i<0){
				System.out.println("Invalid input! Please enter a valid choice");
			}
		} while (i>array.size() || i<0);
		try {
			return array.get(i - 1);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Gets a show given its index in the database (index starts from 1).
	 * @param i index in the database
	 * @return the show corresponding to that index in the database
	 */
	public Show getShow(int i) {
		deserialize();
		return showList.get(i - 1);
	}

	/**
	 * Lists all the shows in the database.
	 * Listing is sorted by movie, then by date, then by time.
	 * Golden suite shows will be yellow in colour.
	 */
	public void listShow() {
		deserialize();
		MovieSystem ms = new MovieSystem();
		ArrayList<Movie> movies = ms.getMovies();
		CineplexSystem cs = new CineplexSystem();
		ArrayList<Cineplex> cineplexes = cs.getCineplexes();

		for (Movie m : movies) {
			if (queryShow(m, null, null).size() == 0) continue;
			int count = (60 - m.getTitle().length()) / 2;
			String spaces1 = String.format("%" + count + "s", "");
			String spaces2 = String.format("%" + (59 - count - m.getTitle().length()) + "s", "");

			System.out.println(ConsoleColours.CYAN + "╔════════════════════════════════════════════════════════════╗" + ConsoleColours.RESET);
			System.out.println(ConsoleColours.CYAN + "║ " + spaces1 + m.getTitle() + spaces2 + "║" + ConsoleColours.RESET);
			System.out.println(ConsoleColours.CYAN + "╚════════════════════════════════════════════════════════════╝" + ConsoleColours.RESET);
			boolean flag = false;
			for (Cineplex c : cineplexes) {
				ArrayList<Show> result = queryShow(m, c, null);
				if (result.size() == 0) continue;
				System.out.println("<< " + c.getName() + " >>");
				ArrayList<Date> dates = new ArrayList<Date>();
				Collections.sort(dates);
				for (Show s : result) {
					boolean match = false;
					for (Date d : dates) {
						if (Helper.equalsDate(s.getDate(), d)) match = true;
					}
					if (!match) dates.add(s.getDate());
				}

				SimpleDateFormat format1 = new SimpleDateFormat("dd MMM");
				SimpleDateFormat format2 = new SimpleDateFormat("HH:mm");
				ArrayList<Show> results;

				for (Date d : dates) {
					results = new ArrayList<Show>();
					for (Show s : result) {
						if (Helper.equalsDate(s.getDate(), d)) {
							results.add(s);
						}
					}


					Collections.sort(results);
					System.out.print(ConsoleColours.PURPLE + "╔════════╗   " + ConsoleColours.RESET);
					for (int i = 0; i < results.size(); i++) {
						String color = ConsoleColours.WHITE;
						if (results.get(i).getCinemaHall().getCinemaType().equals("Golden Suite")) {color = ConsoleColours.YELLOW; flag = true;}
						System.out.print(color + "╔═══════╗ " + ConsoleColours.RESET);
					}
					System.out.println();
					System.out.print(ConsoleColours.PURPLE + "║ " + format1.format(d) + " ║   " + ConsoleColours.RESET);
					for (int i = 0; i < results.size(); i++) {
						String color = ConsoleColours.WHITE;
						if (results.get(i).getCinemaHall().getCinemaType().equals("Golden Suite")) color = ConsoleColours.YELLOW;
						System.out.print(color + "║ " + format2.format(results.get(i).getDate()) + " ║ " + ConsoleColours.RESET);
					}
					System.out.println();
					System.out.print(ConsoleColours.PURPLE + "╚════════╝   " + ConsoleColours.RESET);
					for (int i = 0; i < results.size(); i++) {
						String color = ConsoleColours.WHITE;
						if (results.get(i).getCinemaHall().getCinemaType().equals("Golden Suite")) color = ConsoleColours.YELLOW;
						System.out.print(color + "╚═══════╝ " + ConsoleColours.RESET);
					}
					System.out.println();
				}


				System.out.println();
			}
			if (flag) System.out.println("Shows in " + ConsoleColours.YELLOW + "yellow" + ConsoleColours.RESET + " indicate Golden Suite show.");
			System.out.println();
		}
	}


	/**
	 * Lists all the shows from an array list of shows.
	 * Listing is sorted by movie, then by date, then by time.
	 * Golden suite shows will be yellow in colour.
	 * @param array array of shows to be displated
	 */
    public void listShow(ArrayList<Show> array) {

		deserialize();
		MovieSystem ms = new MovieSystem();
		ArrayList<Movie> movies = ms.getMovies();
		CineplexSystem cs = new CineplexSystem();
		ArrayList<Cineplex> cineplexes = cs.getCineplexes();

		for (Movie m : movies) {
			if (queryShow(array, m).size() == 0) continue;
			int count = (60 - m.getTitle().length()) / 2;
			String spaces1 = String.format("%" + count + "s", "");
			String spaces2 = String.format("%" + (59 - count - m.getTitle().length()) + "s", "");

			System.out.println(ConsoleColours.CYAN + "╔════════════════════════════════════════════════════════════╗" + ConsoleColours.RESET);
			System.out.println(ConsoleColours.CYAN + "║ " + spaces1 + m.getTitle() + spaces2 + "║" + ConsoleColours.RESET);
			System.out.println(ConsoleColours.CYAN + "╚════════════════════════════════════════════════════════════╝" + ConsoleColours.RESET);
			boolean flag = false;
			for (Cineplex c : cineplexes) {
				ArrayList<Show> result = new ArrayList<Show>();
				for (Show s : array) {
					if (s.getCineplex().getCineplexID().equals(c.getCineplexID())) result.add(s);
				}
				if (result.size() == 0) continue;
				System.out.println("<< " + c.getName() + " >>");
				ArrayList<Date> dates = new ArrayList<Date>();
				Collections.sort(dates);
				for (Show s : result) {
					boolean match = false;
					for (Date d : dates) {
						if (Helper.equalsDate(s.getDate(), d)) match = true;
					}
					if (!match) dates.add(s.getDate());
				}

				SimpleDateFormat format1 = new SimpleDateFormat("dd MMM");
				SimpleDateFormat format2 = new SimpleDateFormat("HH:mm");
				ArrayList<Show> results;

				for (Date d : dates) {
					results = new ArrayList<Show>();
					for (Show s : result) {
						if (Helper.equalsDate(s.getDate(), d)) {
							results.add(s);
						}
					}

					Collections.sort(results);
					System.out.print(ConsoleColours.PURPLE + "╔════════╗   " + ConsoleColours.RESET);
					for (int i = 0; i < results.size(); i++) {
						String color = ConsoleColours.WHITE;
						if (results.get(i).getCinemaHall().getCinemaType().equals("Golden Suite")) {color = ConsoleColours.YELLOW; flag = true;}
						System.out.print(color + "╔═══════╗ " + ConsoleColours.RESET);
					}
					System.out.println();
					System.out.print(ConsoleColours.PURPLE + "║ " + format1.format(d) + " ║   " + ConsoleColours.RESET);
					for (int i = 0; i < results.size(); i++) {
						String color = ConsoleColours.WHITE;
						if (results.get(i).getCinemaHall().getCinemaType().equals("Golden Suite")) color = ConsoleColours.YELLOW;
						System.out.print(color + "║ " + format2.format(results.get(i).getDate()) + " ║ " + ConsoleColours.RESET);
					}
					System.out.println();
					System.out.print(ConsoleColours.PURPLE + "╚════════╝   " + ConsoleColours.RESET);
					for (int i = 0; i < results.size(); i++) {
						String color = ConsoleColours.WHITE;
						if (results.get(i).getCinemaHall().getCinemaType().equals("Golden Suite")) color = ConsoleColours.YELLOW;
						System.out.print(color + "╚═══════╝ " + ConsoleColours.RESET);
					}
					System.out.println();
				}

				System.out.println();
			}
			if (flag) System.out.println("Shows in " + ConsoleColours.YELLOW + "yellow" + ConsoleColours.RESET + " indicate Golden Suite show.");
			System.out.println();
		}

    }

	/**
	 * Get a show from the show database given the movie, cineplex and date.
	 * @param m the movie
	 * @param c the cineplex
	 * @param d the date
	 * @return the corresponding show
	 */
	public Show getShow(Movie m, Cineplex c, Date d){
		deserialize();
		try{
			for (Show s : showList) {
				if (s.getMovie().getTitle().equals(m.getTitle()) && s.getCineplex().getCineplexID().equals(c.getCineplexID()) && s.getDate().equals(d)) return s;
			}
			return null;
		}
		catch(Exception exception){
			return null;
		}
	}

	/**
	 * Search for shows in the database based on movie, cineplex and date.
	 * @param m the movie
	 * @param c the cineplex (can be null)
	 * @param d the date (can be null)
	 * @return an array list of shows matching the criteria
	 */
	public ArrayList<Show> queryShow(Movie m, Cineplex c, Date d){
		deserialize();

		if (c == null) {
			try{
				ArrayList<Show> result = new ArrayList<>();
				for (Show s : showList) {
					if (s.getMovie().getTitle().equals(m.getTitle())) result.add(s);
				}
				return result;
			}
			catch(Exception exception){
				return null;
			}
		}

		if (d == null) {
			try{
				ArrayList<Show> result = new ArrayList<>();
				for (Show s : showList) {
					if (s.getMovie().getTitle().equals(m.getTitle()) && s.getCineplex().getCineplexID().equals(c.getCineplexID())) result.add(s);
				}
				return result;
			}
			catch(Exception exception){
				return null;
			}
		}

		try{
			ArrayList<Show> result = new ArrayList<>();
			for (Show s : showList) {
				if (s.getMovie().getTitle().equals(m.getTitle()) && s.getCineplex().getCineplexID().equals(c.getCineplexID()) && Helper.equalsDate(s.getDate(), d)) result.add(s);
			}
			return result;
		}
		catch(Exception exception){
			return null;
		}
	}

	/**
	 * Search for shows from an array list based on movie.
	 * @param input the array list of shows
	 * @param movie the movie
	 * @return an array list of shows matching the criteria
	 */
	public ArrayList<Show> queryShow(ArrayList<Show> input, Movie movie){
    	deserialize();
		try{
			ArrayList<Show> result = new ArrayList<>();
			for(int i = 0; i < input.size(); i++)
			{
				if (input.get(i).getMovie().getTitle().equals(movie.getTitle())){
					result.add(input.get(i));
				}
			}
			return result;
		}
		catch(Exception exception){
			return null;
		}
	}

	/**
	 * Search for shows in the database based on movie, cineplex, date and whether time is to be considered..
	 * @param movie the movie (can be null)
	 * @param cineplex the cineplex (can be null)
	 * @param datetime the datetime (can be null)
	 * @param hasTime whether time is to be considered
	 * @return an array list of shows matching the criteria
	 */
	public ArrayList<Show> queryShow(Movie movie, Cineplex cineplex, Date datetime, boolean hasTime){
    	deserialize();

		ArrayList<Show> input = showList;

		if (cineplex != null){
			try{
				ArrayList<Show> result = new ArrayList<>();
				for(int i = 0; i < input.size(); i++)
				{
					if (input.get(i).getCineplex().getCineplexID().equals(cineplex.getCineplexID())){
						result.add(input.get(i));
					}
				}
				input = result;
			}
			catch(Exception exception){
				input = null;
			}
		}

		if (movie != null){
			input = queryShow(input, movie);
		}

		if (hasTime){
			if (datetime != null){
				try{
					ArrayList<Show> result = new ArrayList<>();
					for(int i = 0; i < input.size(); i++)
					{
						if (input.get(i).getDate().compareTo(datetime) == 0){
							result.add(input.get(i));
						}
					}
					input = result;
				}
				catch(Exception exception){
					input = null;
				}
			}
		} else {
			ArrayList<Show> result = new ArrayList<>();
			try {

				for (Show show : input) {
					if (Helper.getDate(show.getDate()).equals(Helper.getDate(datetime))) {
						result.add(show);
					}
				}
			} catch (Exception e){
				 input = null;
			}
			input = result;
		}
		return input;
	}

	/**
	 * Add a show to the show list.
	 * @param cineplex cineplex of the show
	 * @param cinemaHall cinema hall of the show
	 * @param date datetime of the show
	 * @param movie movie of the show
	 * @return whether the addition is successful
	 */
	public boolean addShow(Cineplex cineplex, CinemaHall cinemaHall, Date date, Movie movie){
    	deserialize();
		try {
			Show newShow = new Show(cineplex, cinemaHall, date, movie);
			showList.add(newShow);
			serialize();
			updateMovieStatus(movie);
			serialize();
			return true;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Delete a show from the show list, given its index in the database (index starts from 0).
	 * @param index index in the database
	 * @return whether the deletion is successful
	 */
	public boolean deleteShow(int index){
    	deserialize();
		updateMovieStatus(getShow(index).getMovie());
		showList.remove(index);
		serialize();
		return true;
	}

	/**
	 * Update the status of movie upon changes in shows.
	 * If the movie is coming soon and there is show in the database, the movie status will be set to now showing.
	 * If the movie is now showing or preview but no more shows, then the movie status will be set to end of showing.
	 * @param movie the movie to be updated
	 * @return whether update is successful
	 */
	public boolean updateMovieStatus(Movie movie) {
		MovieSystem ms = new MovieSystem();
		Movie m = ms.getMovie(movie.getTitle());
		if (m.getStatus() == MovieStatus.COMING_SOON && queryShow(showList, m).size() > 0) m.setStatus(MovieStatus.NOW_SHOWING);
		if (m.getStatus() == MovieStatus.NOW_SHOWING && queryShow(showList, m).size() == 0) m.setStatus(MovieStatus.END_OF_SHOWING);
		if (m.getStatus() == MovieStatus.PREVIEW && queryShow(showList, m).size() == 0) m.setStatus(MovieStatus.END_OF_SHOWING);
		ms.serialize();
		return true;
	}

	/**
	 * Attempts to serialize the array list into a database file.
	 * @return whether serialization is successful
	 */
	public boolean serialize() {
		try {
			FileOutputStream fos = new FileOutputStream("showList.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(showList);
			oos.close();
			fos.close();
			return true;
		}
		catch(Exception e) {
			return false;
		}
	}

	/**
	 * Attempts to deserialize the database file into an array list of movies.
	 * @return whether deserialization is successful
	 */
	public boolean deserialize(){
		try {
			FileInputStream fis = new FileInputStream("showList.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			showList = (ArrayList<Show>)ois.readObject();
			ois.close();
			fis.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Resets the movie database.
	 * It is preloaded with 3 shows for every movie at 3 different dates (1, 2 and 3 December 2019).
	 * @return whether resetting the database is successful
	 */
	public boolean resetDatabase() {
		this.showList = new ArrayList<Show>();
		Show show;
		CineplexSystem cs = new CineplexSystem();
		MovieSystem ms = new MovieSystem();

		for (int i = 1; i <= 3; i++) {
			for (Cineplex c : cs.getCineplexes()) {


				try {
					show = new Show(c, c.getCinemaHall(1), Helper.convertDateTime("0"+ i + "/12/2019 10:00"), ms.getMovie(1));
					showList.add(show);
				} catch (Exception e) {}
				try {
					show = new Show(c, c.getCinemaHall(2), Helper.convertDateTime("0" + i + "/12/2019 14:00"), ms.getMovie(1));
					showList.add(show);
				} catch (Exception e) {}
				try {
					show = new Show(c, c.getCinemaHall(3), Helper.convertDateTime("0" + i + "/12/2019 18:00"), ms.getMovie(1));
					showList.add(show);
				} catch (Exception e) {}
				updateMovieStatus(ms.getMovie(1));


				try {
					show = new Show(c, c.getCinemaHall(3), Helper.convertDateTime("0" + i + "/12/2019 10:00"), ms.getMovie(2));
					showList.add(show);
				} catch (Exception e) {}
				try {
					show = new Show(c, c.getCinemaHall(1), Helper.convertDateTime("0" + i + "/12/2019 14:00"), ms.getMovie(2));
					showList.add(show);
				} catch (Exception e) {}
				try {
					show = new Show(c, c.getCinemaHall(2), Helper.convertDateTime("0" + i + "/12/2019 18:00"), ms.getMovie(2));
					showList.add(show);
				} catch (Exception e) {}
				updateMovieStatus(ms.getMovie(2));


				try {
					show = new Show(c, c.getCinemaHall(2), Helper.convertDateTime("0" + i + "/12/2019 10:00"), ms.getMovie(3));
					showList.add(show);
				} catch (Exception e) {}
				try {
					show = new Show(c, c.getCinemaHall(3), Helper.convertDateTime("0" + i + "/12/2019 14:00"), ms.getMovie(3));
					showList.add(show);
				} catch (Exception e) {}
				try {
					show = new Show(c, c.getCinemaHall(1), Helper.convertDateTime("0" + i + "/12/2019 18:00"), ms.getMovie(3));
					showList.add(show);
				} catch (Exception e) {}
				updateMovieStatus(ms.getMovie(3));

				try {
					show = new Show(c, c.getCinemaHall(1), Helper.convertDateTime("0" + i + "/12/2019 12:00"), ms.getMovie(4));
					showList.add(show);
				} catch (Exception e) {}
				try {
					show = new Show(c, c.getCinemaHall(2), Helper.convertDateTime("0" + i + "/12/2019 16:00"), ms.getMovie(4));
					showList.add(show);
				} catch (Exception e) {}
				try {
					show = new Show(c, c.getCinemaHall(3), Helper.convertDateTime("0" + i + "/12/2019 20:00"), ms.getMovie(4));
					showList.add(show);
				} catch (Exception e) {}

				updateMovieStatus(ms.getMovie(4));

				try {
					show = new Show(c, c.getCinemaHall(3), Helper.convertDateTime("0" + i + "/12/2019 12:00"), ms.getMovie(5));
					showList.add(show);
				} catch (Exception e) {}
				try {
					show = new Show(c, c.getCinemaHall(1), Helper.convertDateTime("0" + i + "/12/2019 16:00"), ms.getMovie(5));
					showList.add(show);
				} catch (Exception e) {}
				try {
					show = new Show(c, c.getCinemaHall(2), Helper.convertDateTime("0" + i + "/12/2019 20:00"), ms.getMovie(5));
					showList.add(show);
				} catch (Exception e) {}
				updateMovieStatus(ms.getMovie(5));


				try {
					show = new Show(c, c.getCinemaHall(2), Helper.convertDateTime("0" + i + "/12/2019 12:00"), ms.getMovie(6));
					showList.add(show);
				} catch (Exception e) {}
				try {
					show = new Show(c, c.getCinemaHall(3), Helper.convertDateTime("0" + i + "/12/2019 16:00"), ms.getMovie(6));
					showList.add(show);
				} catch (Exception e) {}
				try {
					show = new Show(c, c.getCinemaHall(1), Helper.convertDateTime("0" + i + "/12/2019 20:00"), ms.getMovie(6));
					showList.add(show);
				} catch (Exception e) {}
				updateMovieStatus(ms.getMovie(6));
			}
		}


		return serialize();
	}

	/**
	 * Gets the number of shows.
	 * @return number of shows
	 */
	public int getNumberOfShows(){
    	deserialize();
		return showList.size();
	}

	/**
	 * Checks whether there is a duplicate show in the show list.
	 * If there is, it returns the duplicate show.
	 * If not, it returns null.
	 * @param cineplex cineplex of the show
	 * @param cinemaHall cinema hall of the show
	 * @param movie movie of the show
	 * @param datetime datetime of the show
	 * @return the duplicated show if there is, else it returns null
	 */
	public Show checkDuplicate(Cineplex cineplex, CinemaHall cinemaHall, Movie movie, Date datetime){
    	for (Show show : showList){
    		if(cineplex.getCineplexID().equals(show.getCineplex().getCineplexID()) && cinemaHall.getHallID().equals(show.getCinemaHall().getHallID()) && movie.getTitle().equals(show.getMovie().getTitle()) && (datetime.compareTo(show.getDate()) == 0)){
    			return show;
			}
		}
    	return null;
	}

	/**
	 * Get the index of the show in the show database.
	 * @param show the show to be searched for
	 * @return the index of the show in the show database
	 */
	public int getIndex(Show show){
    	for(int i =0;i<showList.size();i++){
			if(showList.get(i).getCineplex().getCineplexID().equals(show.getCineplex().getCineplexID()) && showList.get(i).getCinemaHall().getHallID().equals(show.getCinemaHall().getHallID()) && showList.get(i).getMovie().getTitle().equals(show.getMovie().getTitle()) && (showList.get(i).getDate().compareTo(show.getDate()) == 0)){
				return i;
			}
		}
    	return -1;
	}

	/**
	 * Adds a show into the database.
	 * @param show the show to be added
	 * @return whether the show is successfully added into the database
	 */
	public boolean addShow(Show show){
    	showList.add(show);
    	return true;
	}

	/**
	 * Removes all shows with associated movie into the database.
	 * @param movie the movie to be added
	 * @return whether deletion is successful
	 */
	public boolean removeMovie(Movie movie){
		deserialize();
		for (Show s : showList) {
			if (s.getMovie().getTitle().equals(movie.getTitle())) showList.remove(s);
		}
		updateMovieStatus(movie);
		return serialize();
	}
}
