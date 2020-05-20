package MOBLIMA;

import java.io.Console;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a controller system which keeps track of all the customers.
 * @author	SS1 Group 2
 * @version	1.0
 * @since	2019-11-13
 */

public class CustomerSystem implements MOBLIMASystem{
    /**
     * Array list of all the customers in the database.
     */
    private ArrayList<Customer> customerList;

    /**
     * Constructor for the system.
     * It attempts to deserialize the database file.
     * If file does not exist, it will reset the database.
     */
    public CustomerSystem() {
        if (!deserialize()) resetDatabase();
    }

    /**
     * Check whether the customer ID matches the password in the database.
     * @param input customer ID of the customer
     * @param password input password given
     * @return whether the customer ID matches the password in the database
     */
    public boolean checkLogin(String input, String password){
        deserialize();
        String customerID = input.toUpperCase();
        try{
            int index = searchCustomer(customerID);
            if (index >= 0){
                if (customerList.get(index).getCustomerID().equals(customerID) && customerList.get(index).getPassword().equals(password)){
                    return true;
                }
            }
        }
        catch(Exception exception){
			return false;
		}
		return false;
    }


    /**
     * Adds a customer into the database.
     * @param input customer ID of the customer
     * @param customerName name of the customer
     * @param customerEmail email of the customer
     * @param customerMobileNo mobile number of the customer
     * @param customerAge age of the customer
     * @param password password of the customer
     * @return whether the creation of the customer is successful
     */
    public boolean addCustomer(String input, String customerName, String customerEmail, String customerMobileNo, String customerAge, String password) {
        deserialize();
        String customerID = input.toUpperCase();
        Customer customer = new Customer(customerID, customerName, customerEmail, customerMobileNo, customerAge, password);
        try {
            customerList.add(customer);
            serialize();
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Delete a customer given the customer ID.
     * @param input customer ID of customer to be deleted
     * @return whether deletion is successful
     */
    public boolean deleteCustomer(String input){
        String customerID = input.toUpperCase();
        deserialize();
        try{
            int index = searchCustomer(customerID);
            if (index >= 0){
                customerList.remove(index);
            }
            serialize();
        }
        catch(Exception exception){
			return false;
		}
		return true;
    }


    /**
     * A dynamic customer updater depending on what is to be edited in the customer.
     * @param input customer ID of the customer
     * @param field what is to be edited -- this can be "name", "age", "email", "mobile number" or "password"
     * @param value what the value is to be updated to
     * @return whether change was successful
     */
    public boolean updateCustomer(String input, String field, String value) {
        String customerID = input.toUpperCase();
        deserialize();
        try {
            int index = searchCustomer(customerID);
            if (index >= 0) {
                if (field.equals("name")){
                    customerList.get(index).setName(value);
                }
                else if (field.equals("age")){
                    customerList.get(index).setAge(value);
                }
                else if (field.equals("email")){
                    customerList.get(index).setEmail(value);
                }
                else if (field.equals("mobile number")){
                    customerList.get(index).setMobileNo(value);
                }
                else if (field.equals("password")){
                    customerList.get(index).setPassword(value);
                }
                serialize();
                return true;
            }
        } catch (Exception exception) {
            return false;
        }
        return false;
    }

    /**
     * Get a customer object given the customer ID.
     * @param input ID of the customer
     * @return the customer object with the customer ID
     */
    public Customer getCustomer(String input){
        String customerID = input.toUpperCase();
        deserialize();
        try{
            int index = searchCustomer(customerID);
            if (index >= 0){
                return customerList.get(index);
            }
        }
        catch (Exception exception){
            return null;
        }
        return null;
    }

    /**
     * Print information about a customer given the customer ID.
     * @param input ID of the customer
     */
    public void printCustomerInfo(String input){
        String customerID = input.toUpperCase();
        deserialize();
        int index = searchCustomer(customerID);
        Customer customer = customerList.get(index);
        System.out.printf("Customer ID: %s \n", customerID);
        System.out.printf("Customer Name: %s \n", customer.getName());
        System.out.printf("Customer Age: %s \n", customer.getAge());
        System.out.printf("Customer Email: %s \n", customer.getEmail());
        System.out.printf("Customer Mobile Number: %s \n", customer.getMobileNo());
    }

    /**
     * Get the index of the customer in the array list.
     * @param input ID of the customer
     * @return index of the customer in the array list
     */

    public int searchCustomer(String input) {
        String customerID = input.toUpperCase();
        deserialize();
        try {
            for (int i = 0; i < customerList.size(); i++) {
                if (customerList.get(i).getCustomerID().equals(customerID)) {
                    return i;
                }
            }
        } catch (Exception exception) {
            return -1;
        }
        return -1;
    }

    /**
     * Attempts to serialize the array list into a database file.
     * @return whether serialization is successful
     */
    public boolean serialize(){
        try {
            FileOutputStream fos = new FileOutputStream("customerList.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(customerList);
            oos.close();
            fos.close();
            return true;
        } catch(Exception e){
            return false;
        }
    }

    /**
     * Attempts to deserialize the database file into an array list of customers.
     * @return whether deserialization is successful
     */
    public boolean deserialize(){
        try{
            FileInputStream fis = new FileInputStream("customerList.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            customerList = (ArrayList<Customer>)ois.readObject();
            ois.close();
            fis.close();
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    /**
     * Resets the customer database.
     * It is preloaded with 35 different customers.
     * @return whether resetting the database is successful
     */
    public boolean resetDatabase() {
        customerList = new ArrayList<Customer>();

        //Customer customer1 = new Customer("SIERR613", "Sierra Song", "SIERR613@e.ntu.edu.sg", "90983005", "21", "password");
        //customerList.add(customer1);
        Customer customer1 = new Customer("SIERR613" , "Sierra Song" , "SIERR613@gmail.com" , "90983005" , "33" , "password");	customerList.add(customer1);
        Customer customer2 = new Customer("ALLEN015" , "Allene Langston" , "ALLEN015@gmail.com" , "82418710" , "30" , "password");	customerList.add(customer2);
        Customer customer3 = new Customer("MARGA347" , "Margaret Blessing" , "MARGA347@gmail.com" , "96721106" , "28" , "password");	customerList.add(customer3);
        Customer customer4 = new Customer("ISAM971" , "Isa Mora" , "ISAM971@gmail.com" , "89005553" , "36" , "password");	customerList.add(customer4);
        Customer customer5 = new Customer("CORIN677" , "Corinna Barrington" , "CORIN677@gmail.com" , "93199133" , "23" , "password");	customerList.add(customer5);
        Customer customer6 = new Customer("MALCO162" , "Malcolm Aquilino" , "MALCO162@gmail.com" , "83858581" , "39" , "password");	customerList.add(customer6);
        Customer customer7 = new Customer("MINA067" , "Mina Canon" , "MINA067@gmail.com" , "81263650" , "40" , "password");	customerList.add(customer7);
        Customer customer8 = new Customer("NAKO588" , "Na Kowalewski" , "NAKO588@gmail.com" , "90792501" , "39" , "password");	customerList.add(customer8);
        Customer customer9 = new Customer("HYUN591" , "Hyun Bouton" , "HYUN591@gmail.com" , "88205677" , "25" , "password");	customerList.add(customer9);
        Customer customer10 = new Customer("HILDE537" , "Hildegarde Brumbelow" , "HILDE537@gmail.com" , "91679309" , "20" , "password");	customerList.add(customer10);
        Customer customer11 = new Customer("IVAT528" , "Iva Tippens" , "IVAT528@gmail.com" , "83455347" , "34" , "password");	customerList.add(customer11);
        Customer customer12 = new Customer("GINGE229" , "Ginger Branum" , "GINGE229@gmail.com" , "99128096" , "37" , "password");	customerList.add(customer12);
        Customer customer13 = new Customer("BRITA479" , "Britany Burrill" , "BRITA479@gmail.com" , "90717244" , "16" , "password");	customerList.add(customer13);
        Customer customer14 = new Customer("SHANI051" , "Shanice Twiford" , "SHANI051@gmail.com" , "85375946" , "17" , "password");	customerList.add(customer14);
        Customer customer15 = new Customer("YING324" , "Ying Mani" , "YING324@gmail.com" , "84187914" , "19" , "password");	customerList.add(customer15);
        Customer customer16 = new Customer("ERNES942" , "Ernesto Bobadilla" , "ERNES942@gmail.com" , "92760723" , "20" , "password");	customerList.add(customer16);
        Customer customer17 = new Customer("CLARI478" , "Clarice Stecklein" , "CLARI478@gmail.com" , "83441501" , "15" , "password");	customerList.add(customer17);
        Customer customer18 = new Customer("VONNI966" , "Vonnie Bakke" , "VONNI966@gmail.com" , "80773844" , "19" , "password");	customerList.add(customer18);
        Customer customer19 = new Customer("NICKY148" , "Nicky Hartz" , "NICKY148@gmail.com" , "85552120" , "18" , "password");	customerList.add(customer19);
        Customer customer20 = new Customer("LAILA887" , "Laila Book" , "LAILA887@gmail.com" , "98811585" , "47" , "password");	customerList.add(customer20);
        Customer customer21 = new Customer("VINCE755" , "Vincent Lindell" , "VINCE755@gmail.com" , "91870680" , "54" , "password");	customerList.add(customer21);
        Customer customer22 = new Customer("TRENT001" , "Trent Feltz" , "TRENT001@gmail.com" , "84374270" , "71" , "password");	customerList.add(customer22);
        Customer customer23 = new Customer("CARY778" , "Cary Sabatino" , "CARY778@gmail.com" , "90323299" , "53" , "password");	customerList.add(customer23);
        Customer customer24 = new Customer("ROSAL043" , "Rosalind Wedge" , "ROSAL043@gmail.com" , "93904349" , "90" , "password");	customerList.add(customer24);
        Customer customer25 = new Customer("MICAH532" , "Micah Deavers" , "MICAH532@gmail.com" , "95225721" , "90" , "password");	customerList.add(customer25);
        Customer customer26 = new Customer("CAREN820" , "Caren Givan" , "CAREN820@gmail.com" , "84068225" , "97" , "password");	customerList.add(customer26);
        Customer customer27 = new Customer("NADIN911" , "Nadine Wallen" , "NADIN911@gmail.com" , "88925164" , "26" , "password");	customerList.add(customer27);
        Customer customer28 = new Customer("AGUST806" , "Agustin Grasty" , "AGUST806@gmail.com" , "87261958" , "77" , "password");	customerList.add(customer28);
        Customer customer29 = new Customer("LILA807" , "Lila Fey" , "LILA807@gmail.com" , "85367493" , "66" , "password");	customerList.add(customer29);
        Customer customer30 = new Customer("DAISE381" , "Daisey Haugen" , "DAISE381@gmail.com" , "82662058" , "22" , "password");	customerList.add(customer30);
        Customer customer31 = new Customer("ACHUA021" , "Alice Chua" , "Chua.alice1@gmail.com" , "83388215" , "20" , "aliceisthebest");	customerList.add(customer31);
        Customer customer32 = new Customer("THANKYOUFORCARRY" , "Johnson Foo" , "johnsonfoo@gmail.com" , "83388215" , "21" , "passwordispassword");	customerList.add(customer32);
        Customer customer33 = new Customer("ANONYFEATHER" , "Tanos" , "tanay8star@gmail.com" , "83388216" , "20" , "haha");	customerList.add(customer33);
        Customer customer34 = new Customer("MALALALA" , "Vincent" , "vribli@gmail.com" , "83388217" , "20" , "hahaha");	customerList.add(customer34);
        Customer customer35 = new Customer("OPSJX" , "Josephine" , "josephine@gmail.com" , "83388218" , "20" , "ilovejx");	customerList.add(customer35);

        return serialize();
    }

    /**
     * Requests password input by masking the string being typed in.
     * @return the password string
     */
    public String getPasswordInput(){
		Console console = System.console();
		String password = null;
		try {
			char[] input = console.readPassword();
			password = String.copyValueOf(input);
		} catch (Exception e){
			Scanner sc = new Scanner(System.in);
			password = sc.nextLine();
		}
		return password;
	}

    /**
     * Prints how many customers are there in the database.
     */
    public void printCustomerNumber(){
    	deserialize();
    	if (customerList.size() == 0) System.out.println("No customer in database!");
    	else {
			System.out.printf("Number of customers in database: ");
			System.out.println(customerList.size());
		}
		System.out.println();
    }

    /**
     * Lists all the customers in the database.
     */
    public void listCustomer(){
    	deserialize();
    	if (customerList.size() == 0) System.out.println("No customer in database!");
    	else {
            System.out.println("Here are the customers in database: ");
            System.out.println();
			for (int index = 0; index < customerList.size(); index++){
                System.out.printf("%d. %s \n", index+1, customerList.get(index).getCustomerID());
            }
		}
		System.out.println();
    }

    /**
     * Get the ID of the customer given index in the array list.
     * @param index index in the array list
     * @return ID of the customer
     */
    public String getCustomerID(int index){return customerList.get(index).getCustomerID();}

    /**
     * Get the number of customers in the database.
     * @return number of customers in the database
     */
    public int numberOfCustomer(){ return customerList.size();}
}
