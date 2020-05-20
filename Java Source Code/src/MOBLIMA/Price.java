package MOBLIMA;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

/**
 * Represents a controller system which keeps track of the price mechanism.
 * @author	SS1 Group 2
 * @version	1.0
 * @since	2019-11-13
 */
public class Price implements MOBLIMASystem{
    /**
     * Hash map which keep tracks of the prices.
     * The first key is the format (2D or 3D).
     * The second key is the day (Monday - Friday).
     * The third key is the timing (Before 6 or After 6).
     * The fourth key is the type (Normal, Student, Senior).
     * The value is the price.
     */
    private HashMap<String,HashMap<String, HashMap<String, HashMap<String, Double>>>> priceMap;

    /**
     * Surcharge added when movie is blockbuster.
     */
    private double blockbusterSurcharge;

    /**
     * Multiplier by which price is multiplied if cinema hall is golden suite.
     */
    private double goldenSuiteMultiplier;

    /**
     * Get the blockbuster surcharge.
     * @return the blockbuster surcharge
     */

    public double getBlockbusterSurcharge() {
        return blockbusterSurcharge;
    }

    /**
     * Set the blockbuster surcharge.
     * @param blockbusterSurcharge the blockbuster surcharge
     */
    public void setBlockbusterSurcharge(double blockbusterSurcharge) {
        this.blockbusterSurcharge = blockbusterSurcharge;
    }

    /**
     * Get the golden suite multiplier.
     * @return the golden suite multiplier
     */
    public double getGoldenSuiteMultiplier() {
        return goldenSuiteMultiplier;
    }

    /**
     * Set the golden suite multiplier.
     * @param goldenSuiteMultiplier the golden suite multiplier
     */
    public void setGoldenSuiteMultiplier(double goldenSuiteMultiplier) {
        this.goldenSuiteMultiplier = goldenSuiteMultiplier;
    }

    /**
     * Constructor for the system.
     * It attempts to deserialize the database file.
     * If file does not exist, it will reset the database.
     */
    public Price(){
        if (!deserialize()) resetDatabase();
    }


    /**
     * Populates the hash map with prices for different categories.
     */
    private void initialise(){

        String[] formatList = new String[2];
        formatList[0] = "2D";
        formatList[1] = "3D";
        priceMap.put("2D", new HashMap<>());
        priceMap.put("3D", new HashMap<>());

        String[] dateList = new String[10];
        dateList[0] = "Monday";
        dateList[1] = "Tuesday";
        dateList[2] = "Wednesday";
        dateList[3] = "Thursday";
        dateList[4] = "Friday";
        dateList[5] = "Holiday";

        // populating for 2D

        for (int count = 0; count < 6; count++) {
            priceMap.get(formatList[0]).put(dateList[count], new HashMap<>());
            if (count != 5) {
                priceMap.get(formatList[0]).get(dateList[count]).put("Before 6", new HashMap<>());
                priceMap.get(formatList[0]).get(dateList[count]).put("After 6", new HashMap<>());
                priceMap.get(formatList[0]).get(dateList[count]).get("Before 6").put("Senior", 4.00);
                priceMap.get(formatList[0]).get(dateList[count]).get("Before 6").put("Student", 7.00);
                if (count <= 2) {
                    priceMap.get(formatList[0]).get(dateList[count]).get("Before 6").put("Normal", 8.50);
                    priceMap.get(formatList[0]).get(dateList[count]).get("After 6").put("Normal", 8.50);
                } else if (count == 3) {
                    priceMap.get(formatList[0]).get(dateList[count]).get("Before 6").put("Normal", 9.50);
                    priceMap.get(formatList[0]).get(dateList[count]).get("After 6").put("Normal", 9.50);
                } else if (count == 4) {
                    priceMap.get(formatList[0]).get(dateList[count]).get("Before 6").put("Normal", 9.50);
                    priceMap.get(formatList[0]).get(dateList[count]).get("After 6").put("Normal", 11.00);
                }
            } else {
                priceMap.get(formatList[0]).get(dateList[count]).put("All Times", new HashMap<>());
                priceMap.get(formatList[0]).get(dateList[count]).get("All Times").put("Normal", 11.00);
            }
            serialize();
        }

        //populating for 3D

        for (int count = 0; count < 6; count++) {
            priceMap.get(formatList[1]).put(dateList[count], new HashMap<>());
            if (count != 5) {
                priceMap.get(formatList[1]).get(dateList[count]).put("Before 6", new HashMap<>());
                priceMap.get(formatList[1]).get(dateList[count]).put("After 6", new HashMap<>());
                priceMap.get(formatList[1]).get(dateList[count]).get("Before 6").put("Senior", 7.00);
                priceMap.get(formatList[1]).get(dateList[count]).get("Before 6").put("Student", 9.00);
                if (count <= 3) {
                    priceMap.get(formatList[1]).get(dateList[count]).get("Before 6").put("Normal", 11.00);
                    priceMap.get(formatList[1]).get(dateList[count]).get("After 6").put("Normal", 11.00);
                } else if (count == 4) {
                    priceMap.get(formatList[1]).get(dateList[count]).get("Before 6").put("Normal", 15.00);
                    priceMap.get(formatList[1]).get(dateList[count]).get("After 6").put("Normal", 15.00);
                }
            } else {
                priceMap.get(formatList[1]).get(dateList[count]).put("All Times", new HashMap<>());
                priceMap.get(formatList[1]).get(dateList[count]).get("All Times").put("Normal", 15.00);
            }
        }
    }

    /**
     * Calculate the price of ticket given different parameters.
     * @param date date of the show
     * @param isStudent whether customer is a student
     * @param isSenior whether customer is a senior
     * @param is3D whether movie is 3D
     * @param isBlockbuster whether movie is blockbuster
     * @param cinemaType cinema hall type (regular or golden suite)
     * @return price of the ticket
     */
    public double calculatePrice(Date date, boolean isStudent, boolean isSenior, boolean is3D, boolean isBlockbuster, String cinemaType){
        // calculates price given a Date object
        // Using SimpleDateFormat and ASCII Codes, we can express the Date object in any format.
        HolidaySystem hs = new HolidaySystem();
        boolean isHoliday = hs.checkHoliday(date);

        String day = Helper.getDay(date);
        String hour = Helper.getHourofDay(date);
        boolean holidayOrSaturday = isHoliday || day.equals("Saturday") || day.equals("Sunday");
        double result;
        if(is3D){
            if (holidayOrSaturday) {
                result =  priceMap.get("3D").get("Holiday").get("All Times").get("Normal");
            } else {
                // Checking if before 6pm
                if (Integer.valueOf(hour) < 18) {
                    if (isStudent) {
                        result =  priceMap.get("3D").get(day).get("Before 6").get("Student");
                    } else if (isSenior) {
                        result =  priceMap.get("3D").get(day).get("Before 6").get("Senior");
                    } else {
                        result =  priceMap.get("3D").get(day).get("Before 6").get("Normal");
                    }
                } else {
                    result =  priceMap.get("3D").get(day).get("After 6").get("Normal");
                }
            }
        } else {
            if (holidayOrSaturday) {
                result =  priceMap.get("2D").get("Holiday").get("All Times").get("Normal");
            } else {
                // Checking if before 6pm
                if (Integer.valueOf(hour) < 18) {
                    if (isStudent) {
                        result =  priceMap.get("2D").get(day).get("Before 6").get("Student");
                    } else if (isSenior) {
                        result =  priceMap.get("2D").get(day).get("Before 6").get("Senior");
                    } else {
                        result =  priceMap.get("2D").get(day).get("Before 6").get("Normal");
                    }
                } else {
                    result =  priceMap.get("2D").get(day).get("After 6").get("Normal");
                }
            }
        }

        if (isBlockbuster){
            result+=blockbusterSurcharge;
        }

        if(cinemaType.equals("Golden Suite")){
            result *= goldenSuiteMultiplier;
        }
        return result;
    }

    /**
     * Check what are the available ticket types.
     * @param format format of the movie
     * @param date date of the show
     * @return ticket types available
     */
    public Object[] checkTicketType(String format, Date date){
        String hourOfDay = Helper.getHourofDay(date);
        HolidaySystem hs = new HolidaySystem();
        String day = null;
        if(!hs.checkHoliday(date)){
            day = Helper.getDay(date);
        } else {
            day = "Holiday";
        }
        if (day.equalsIgnoreCase("Holiday")|| day.equalsIgnoreCase("Saturday") || day.equalsIgnoreCase("Sunday")){
            Set keys = priceMap.get(format).get("Holiday").get("All Times").keySet();
            return keys.toArray();
        }
        if(Integer.valueOf(hourOfDay) < 18){
            Set keys = priceMap.get(format).get(day).get("Before 6").keySet();
            return keys.toArray();

        } else {
            Set keys = priceMap.get(format).get(day).get("After 6").keySet();
            return keys.toArray();
        }
    }

    /**
     * Attempts to serialize the price hash map into a database file.
     * It also serializes the surcharge and the multiplier.
     * @return whether serialization is successful
     */
    public boolean serialize() {
        try {
            FileOutputStream fos = new FileOutputStream("priceMap.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(priceMap);
            oos.close();
            fos.close();
            FileOutputStream fos2 = new FileOutputStream("blockbusterSurcharge.ser");
            ObjectOutputStream oos2 = new ObjectOutputStream(fos);
            oos.writeObject(blockbusterSurcharge);
            oos2.close();
            fos2.close();
            FileOutputStream fos3 = new FileOutputStream("goldenSuiteMultiplier.ser");
            ObjectOutputStream oos3= new ObjectOutputStream(fos);
            oos.writeObject(goldenSuiteMultiplier);
            oos3.close();
            fos3.close();
            return true;
        }
        catch(Exception e) {
            return false;
        }
    }

    /**
     * Attempts to deserialize the database file into a price hash map.
     * It also deserializes the surcharge and multiplier.
     * @return whether deserialization is successful
     */
    public boolean deserialize(){
        try {
            FileInputStream fis = new FileInputStream("priceMap.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            priceMap = (HashMap<String,HashMap<String, HashMap<String, HashMap<String, Double>>>>)ois.readObject();
            ois.close();
            fis.close();
            FileInputStream fis2 = new FileInputStream("blockbusterSurcharge.ser");
            ObjectInputStream ois2 = new ObjectInputStream(fis);
            blockbusterSurcharge = (double)ois.readObject();
            ois2.close();
            fis2.close();
            FileInputStream fis3 = new FileInputStream("goldenSuiteMultiplier.ser");
            ObjectInputStream ois3 = new ObjectInputStream(fis);
            goldenSuiteMultiplier = (double)ois.readObject();
            ois3.close();
            fis3.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Resets the price scheme.
     * It initialises with the default price scheme.
     * @return whether resetting the database is successful
     */
    public boolean resetDatabase() {
        priceMap = new HashMap<>();
        initialise();
        blockbusterSurcharge = 1.0;
        goldenSuiteMultiplier = 2.5;
        return serialize();
    }

    public HashMap<String,HashMap<String, HashMap<String, HashMap<String, Double>>>> getMap(){
        return priceMap;
    }

}