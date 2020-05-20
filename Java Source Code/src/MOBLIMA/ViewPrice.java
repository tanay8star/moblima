package MOBLIMA;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Represents a view to manage prices.
 * @author	SS1 Group 2
 * @version	1.0
 * @since	2019-11-13
 */
public class ViewPrice extends View {
    /**
     * MOBLIMA's pricing scheme
     */
    private Price price;
    private Scanner sc = new Scanner(System.in);

    /**
     * Constructor of the view.
     * It will load up the pricing scheme.
     * @param systems MOBLIMA's systems hash map
     */
    public ViewPrice(HashMap<String, MOBLIMASystem> systems){
        price = (Price)systems.get("Price");
    }

    /**
     * Main menu of the price management view.
     * Note that this is only accessible to administrators.
     * @param accessLevel the access level, customer or administrator
     */
    @Override
    public void menu(AccessLevel accessLevel) {
        clearScreen("Directory: Home > Price Database", accessLevel);
        switch(accessLevel) {
            case ADMINISTRATOR:
                HashMap<String, HashMap<String, HashMap<String, HashMap<String, Double>>>> map = price.getMap();
                int choice = -1;

                do {
                    clearScreen("Directory: Home > Price Database", accessLevel);
                    System.out.println("Welcome to the Pricing System!");
                    System.out.println("1. Query Price");
                    System.out.println("2. Set Price");
                    System.out.println("3. Set Blockbuster Surcharge");
                    System.out.println("4. Set Golden Suite Surcharge");
                    System.out.println("0. Return to Main Menu");
                    System.out.println();
                    System.out.println("Enter Choice: ");
                    choice = Helper.robustNextInt();

                    switch (choice) {
                        case 1:
                            try {
                                System.out.println("The price is: " + getPrice(map));
                                System.out.println();
                                System.out.println("Press Enter to Continue");
                                sc.nextLine();
                            } catch(Exception e){
                                System.out.println("Invalid Input. Press Enter to Continue");
                                sc.nextLine();
                            };
                            break;
                        case 2:
                            try {
                                setPrice(map);
                                System.out.println("Press Enter to Continue");
                                sc.nextLine();
                            } catch (Exception e){
                                System.out.println("Invalid input. Press Enter to continue.");
                                sc.nextLine();
                            }
                            break;
                        case 3:
                            System.out.println("The current blockbuster surcharge is:");
                            System.out.println(price.getBlockbusterSurcharge());
                            System.out.println();
                            do {
                                System.out.println("Enter New Surcharge:");
                                price.setBlockbusterSurcharge(Helper.robustNextDouble());
                            } while (price.getBlockbusterSurcharge() < 0);
                            break;
                        case 4:
                            System.out.println("The current Golden Suite Multiplier is:");
                            System.out.println(price.getGoldenSuiteMultiplier());
                            System.out.println();
                            do {
                                System.out.println("Enter New Multiplier:");
                                price.setGoldenSuiteMultiplier(Helper.robustNextDouble());
                            } while (price.getGoldenSuiteMultiplier() < 1);
                            break;
                        default:
                            System.out.println("Invalid input. Press enter to continue.");
                            sc.nextLine();
                            clearScreen("Directory: Home > Price Database", accessLevel);
                            break;
                    }
                } while (choice != 0);
                break;
            case CUSTOMER:
                System.out.println("Access denied!");
                break;
        }
    }

    /**
     * Method to select an option from an array.
     * @param list the array
     * @return the selected integer
     */
    private int selectOption(Object[] list){
        int count = 1;
        for(Object i : list){
            System.out.println(count + ": " + i);
            count++;
        }
        Scanner sc = new Scanner(System.in);
        System.out.println();
        System.out.println("Please key in the option required.");
        int result = Helper.robustNextInt();

        return result -1;
    }

    /**
     * Method to get price.
     * @param map the pricing scheme
     * @return the price
     */
    private double getPrice(HashMap<String,HashMap<String, HashMap<String, HashMap<String, Double>>>> map){
        Object[] keys = map.keySet().toArray();
        HashMap<String, HashMap<String, HashMap<String, Double>>> nextMap = map.get(keys[selectOption(keys)]);
        Object[] nextKeys = new Object[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Holiday"};
        HashMap<String, HashMap<String, Double>> yetAnotherMap = nextMap.get(nextKeys[selectOption(nextKeys)]);
        Object[] yetAnotherKeys = yetAnotherMap.keySet().toArray();
        HashMap<String, Double> tooManyMaps = yetAnotherMap.get(yetAnotherKeys[selectOption(yetAnotherKeys)]);
        Object[] tooManyKeys = tooManyMaps.keySet().toArray();
        return tooManyMaps.get(tooManyKeys[selectOption(tooManyKeys)]);
    }

    /**
     * Method to set price.
     * @param map the pricing scheme
     */
    private void setPrice(HashMap<String,HashMap<String, HashMap<String, HashMap<String, Double>>>> map){
        Object[] keys = map.keySet().toArray();
        HashMap<String, HashMap<String, HashMap<String, Double>>> nextMap = map.get(keys[selectOption(keys)]);
        Object[] nextKeys = new Object[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Holiday"};
        HashMap<String, HashMap<String, Double>> yetAnotherMap = nextMap.get(nextKeys[selectOption(nextKeys)]);
        Object[] yetAnotherKeys = yetAnotherMap.keySet().toArray();
        HashMap<String, Double> tooManyMaps = yetAnotherMap.get(yetAnotherKeys[selectOption(yetAnotherKeys)]);
        Object[] tooManyKeys = tooManyMaps.keySet().toArray();
        Object hello = tooManyKeys[selectOption(tooManyKeys)];
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the new price.");
        double price = Helper.robustNextDouble();
        tooManyMaps.put((String)hello, price);
    }
}
