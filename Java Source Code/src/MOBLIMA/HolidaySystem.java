package MOBLIMA;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;

/**
 * Represents a controller system which keeps track of the holidays, in which prices will be affected.
 * @author	SS1 Group 2
 * @version	1.0
 * @since	2019-11-13
 */
public class HolidaySystem implements MOBLIMASystem{

    /**
     * Array list of holidays.
     */
    private ArrayList<Date> holidayList;

    /**
     * Constructor for the system.
     * It attempts to deserialize the database file.
     * If file does not exist, it will reset the database.
     */
    public HolidaySystem() {
        if (!deserialize()) resetDatabase();
    }

    /**
     * Get the list of holidays.
     * @return list of holidays
     */
    public ArrayList<Date> getHolidayList() {
        return holidayList;
    }

    /**
     * Add a holiday to the database.
     * @param newHoliday holiday to be added, in string format dd/MM/yyyy
     * @return whether the addition is successful
     */
    public boolean addHoliday(String newHoliday){
        deserialize();
        try {
            Date holiday = Helper.convertDate(newHoliday);
            if (holiday == null) return false;
            if (!holidayList.contains(holiday)) holidayList.add(holiday);
            else return false;
            serialize();
            return true;
        } catch (Exception e){
            return false;
        }
    }

    /**
     * Checks whether the holiday list is empty.
     * @return whether the holiday list is empty
     */
    public boolean holidayIsEmpty(){
        return holidayList.isEmpty();
    }

    /**
     * Delete holiday from the holiday list given its index in the array list.
     * @param index index of holiday in array list
     * @return whether the deletion is successful
     */
    public boolean deleteHoliday(int index)
    {
        deserialize();
        //int index = 0;

        if(holidayIsEmpty())
            return false;
        holidayList.remove(index-1);
        serialize();
        return true;

    }

    /**
     * Print all holidays in the database.
     */
    public void printHoliday()
    {
        deserialize();
        System.out.println("List of holidays: ");
        System.out.println();
        if (holidayList.size()==0)
            System.out.println("No holiday in list.");
        else
            for (int i=0; i< holidayList.size(); i++){
                System.out.println((i + 1) + ". " + Helper.getDateFormat(holidayList.get(i))+" "+Helper.getDay(holidayList.get(i)));
            }
    }

    /**
     * Check whether a given date is a holiday.
     * @param date date to be checked
     * @return whether the date is a holiday
     */
    public boolean checkHoliday(Date date){
        deserialize();
        if(holidayList == null)
            return false;
        if(holidayList.size() == 0)
            return false;
        int i=0;
        String issitHoliday = Helper.getDate(date);

        try
        {
            while(holidayList.get(i) != null)
            {
                String holidays = Helper.getDate(holidayList.get(i));
                if (issitHoliday.equals(holidays))
                    return true;
                i++;
            }
            return false;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    /**
     * Attempts to serialize the array list into a database file.
     * @return whether serialization is successful
     */
    public boolean serialize(){
        try {
            FileOutputStream fos = new FileOutputStream("holidayList.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(holidayList);
            oos.close();
            fos.close();
            return true;
        } catch(Exception e){
            return false;
        }
    }

    /**
     * Attempts to deserialize the database file into holidayList array list
     * @return whether deserialization is successful
     */
    public boolean deserialize(){
        try{
            FileInputStream fis = new FileInputStream("holidayList.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            holidayList = (ArrayList<Date>)ois.readObject();
            ois.close();
            fis.close();
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    /**
     * Resets the holiday database.
     * It is preloaded with default holidays.
     * First, Christmas on 25 December 2019.
     * Second, New Year on 1 January 2020.
     *
     * @return whether resetting the database is successful
     */
    public boolean resetDatabase() {
        holidayList = new ArrayList<Date>();
        addHoliday("25/12/2019");
        addHoliday("01/01/2020");
        return serialize();
    }
    
}

