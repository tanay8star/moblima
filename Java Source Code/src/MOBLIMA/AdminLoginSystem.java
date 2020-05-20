package MOBLIMA;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Represents a controller system which keeps track of the administrator login.
 * @author	SS1 Group 2
 * @version	1.0
 * @since	2019-11-13
 */
public class AdminLoginSystem implements MOBLIMASystem{

	/**
	 * A hashmap which stores the username and password of all administrators.
	 * The key is the username and the value is the password.
	 */
	private HashMap<String, String> loginDatabase;

	/**
	 * Constructor for the system.
	 * It attempts to deserialize the database file.
	 * If file does not exist, it will reset the database.
	 */
	public AdminLoginSystem() {
		if (!deserialize()) resetDatabase();
	}

	/**
	 * Gets the number of administrators in the system.
	 * @return number of administrators in the system.
	 */
	public int getAdminNumber(){
		deserialize();
		return loginDatabase.size();
	}

	/**
	 * Prints the number of administrators in the system.
	 */
	public void printAdminNumber(){
    	deserialize();
    	if (loginDatabase.size() == 0) System.out.println("No admin in database!");
    	else {
			System.out.printf("Number of admins in database: ");
			System.out.println(loginDatabase.size());
		}
		System.out.println();
    }

	/**
	 * Attempts to deserialize the database file into loginDatabase hashmap.
	 * @return whether deserialization is successful
	 */
	public boolean deserialize() {
		try {
			FileInputStream fis = new FileInputStream("logindb.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			loginDatabase = (HashMap<String, String>) ois.readObject();
			ois.close();
			fis.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Attempts to serialize the hashmap into a database file.
	 * @return whether serialization is successful
	 */
	public boolean serialize() {
		try {
			FileOutputStream fos = new FileOutputStream("logindb.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(loginDatabase);
			oos.close();
			fos.close();
			return true;
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
			return false;
		}
	}

	/**
	 * Adds an administrator into the database.
	 * @param username username of the administrator
	 * @param password password of the administrator
	 * @return whether adding the account into the database is successful
	 */
	public boolean addAdmin(String username, String password) {
		deserialize();
		loginDatabase.put(username, password);
		return serialize();
	}

	/**
	 * Removes administrator into the database.
	 * @param username username of the administrator
	 * @return whether deleting the administrator from the database is successful
	 */
	public boolean removeAdmin(String username){
		deserialize();
		loginDatabase.remove(username);
		return serialize();
	}

	/**
	 * Updates the password of the administrator.
	 * @param username username of the administrator
	 * @param password password of the administrator
	 * @return whether updating the password is successful
	 */
	public boolean updateAdmin(String username, String password){
		if (loginDatabase.get(username).equals(password)){
			return false;
		}
		else{
			deserialize();
			loginDatabase.replace(username, password);
			return serialize();
		}
	}


	/**
	 * Resets the administrator login database.
	 * It is preloaded with default administrators.
	 * By default, use username "admin" and password "123456"
	 * @return whether resetting the database is successful
	 */
	public boolean resetDatabase() {
		loginDatabase = new HashMap<String, String>();
		loginDatabase.put("ADMIN", "123456");
		loginDatabase.put("ALICE", "83388215");
		loginDatabase.put("TANAY", "password");
		loginDatabase.put("VINCENT", "hello");
		loginDatabase.put("JOHNSON", "passwordpassword");
		loginDatabase.put("JOSE", "yay");
		return serialize();
	}

	/**
	 * Checks whether the username exists in the login database.
	 * @param username username of the administrator
	 * @return whether the database contains the administrator
	 */
	public boolean checkUsername(String username) {
        return loginDatabase.containsKey(username);
	}

	/**
	 * Checks whether the password of the given username matches the database password.
	 * @param username username of the administrator
	 * @param password password of the administrator
	 * @return whether the password of the given username matches the database password
	 */
	public boolean checkPassword(String username, String password) {
		if (!loginDatabase.containsKey(username)) return false;
		else return loginDatabase.get(username).equals(password);
	}

	/**
	 * Requests password input by masking the string being typed in.
	 * @return the password string
	 */
	public String getPasswordInput(){
		String password = null;
		try {
			Console console = System.console();
			char[] input = console.readPassword();
			password = String.copyValueOf(input);
		} catch (Exception e){
			Scanner sc = new Scanner(System.in);
			password = sc.nextLine();
		}
		return password;
	}

	/**
	 * Prints a list of administrator usernames in the database.
	 */
	public void listAdmin(){
    	deserialize();
    	if (loginDatabase.size() == 0) System.out.println("No admin in database!");
    	else {
			System.out.println("Here are the admins in database: ");
			int index = 1;
			for (String key : loginDatabase.keySet()) {
				System.out.printf("%d. %s \n", index, key);
				index++;
			}
		}
		System.out.println();
    }
}
