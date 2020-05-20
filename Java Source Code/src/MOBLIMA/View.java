package MOBLIMA;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a view on the console.
 * This is an abstract class which will be inherited by other views.
 * It has built-in methods like clearScreen().
 * Every view must implement a menu with an access level.
 * @author	SS1 Group 2
 * @version	1.0
 * @since	2019-11-13
 */
public abstract class View {
	/**
	 * Clears the screen of the console and prints the header of the view.
	 * @param directory the current directory which will be printed
	 * @param accessLevel the access level, customer or administrator
	 */
	public void clearScreen(String directory, AccessLevel accessLevel) {
		try {
			new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		}
		catch (Exception e){}


		LocalDateTime myDateObj = LocalDateTime.now();
	    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	    String formattedDate = myDateObj.format(myFormatObj);
	    int count = 45 - formattedDate.length() / 2;
	    String spaces1 = String.format("%" + count +"s", "");
	    String spaces2 = String.format("%" + (90 - count - formattedDate.length()) +"s", "");
	    String spaces3 = String.format("%" + (89 - directory.length()) +"s", "");
	    
	    
	    
		System.out.println(ConsoleColours.YELLOW + "╔══════════════════════════════════════════════════════════════════════════════════════════╗");
		System.out.println("║                                       Welcome to...                                      ║");
		System.out.println("║                  ______      __    __              ______                                ║");
		System.out.println("║                 / ____/___  / /___/ /__  ____     /_  __/___ _      ______               ║");
		System.out.println("║                / / __/ __ \\/ / __  / _ \\/ __ \\     / / / __ \\ | /| / / __ \\              ║");
		System.out.println("║               / /_/ / /_/ / / /_/ /  __/ / / /    / / / /_/ / |/ |/ / / / /              ║");
		System.out.println("║               \\____/\\____/_/\\__,_/\\___/_/ /_/    /_/  \\____/|__/|__/_/ /_/               ║");
		System.out.println("║                                                                                          ║");
		System.out.println("║" + spaces1 + formattedDate + spaces2 + "║");
		System.out.println("╚══════════════════════════════════════════════════════════════════════════════════════════╝");
		System.out.println(ConsoleColours.RESET);
		Helper.printAdminHeader(accessLevel);
		System.out.println("╔══════════════════════════════════════════════════════════════════════════════════════════╗");
		System.out.println("║ " + directory + spaces3 + "║");
		System.out.println("╚══════════════════════════════════════════════════════════════════════════════════════════╝");
		System.out.println();
	}

	/**
	 * A menu display which must be implemented by all views.
	 * @param accessLevel the access level, customer or administrator
	 */
	public abstract void menu(AccessLevel accessLevel);
}
