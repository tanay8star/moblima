package MOBLIMA;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Static methods which will be available for all classes to use.
 * @author	SS1 Group 2
 * @version	1.0
 * @since	2019-11-13
 */

public class Helper {

    /**
     * Get the day given the date.
     * @param date the date
     * @return the day
     */
    public static String getDay(Date date){
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
        return dayFormat.format(date);
    }

    /**
     * Get the hour given the date.
     * @param date the date
     * @return the hour
     */
    public static String getHourofDay(Date date){
        SimpleDateFormat dayFormat = new SimpleDateFormat("HH");
        return dayFormat.format(date);
    }


    /**
     * Get the date string in format ddMMyyyy given the date.
     * @param date the date
     * @return the date string (ddMMyyyy)
     */
    public static String getDate(Date date){
        SimpleDateFormat dayFormat = new SimpleDateFormat("ddMMyyyy");
        return dayFormat.format(date);
    }

    /**
     * Get the date string in format dd/MM/yyyy given the date.
     * @param date the date
     * @return the date string (dd/MM/yyyy)
     */
    public static String getDateFormat(Date date){
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dayFormat.format(date);
    }

    /**
     * The serialVersionUID for serialization purposes.
     */
    public static final long serialVersionUID = 1L;


    /**
     * Converts a date string with format dd/MM/yyyy into a date object.
     * @param date the date string (dd/MM/yyyy)
     * @return the date
     */
    public static Date convertDate(String date){
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            format.setLenient(false);
            Date date1 = format.parse(date);
            return date1;
        } catch(Exception e) {
            return null;
        }
    }

    /**
     * Converts a date string with format dd/MM/yyyy HH:mm into a date object.
     * @param datetime the date string (dd/MM/yyyy HH:mm)
     * @return the date
     */
    public static Date convertDateTime(String datetime){
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            format.setLenient(false);
            Date date1 = format.parse(datetime);
            return date1;
        } catch(Exception e) {
            return null;
        }
    }

    /**
     * Check whether two dates have the same date, ignoring the time.
     * @param date1 the first date
     * @param date2 the second date
     * @return whether the two dates have the same date, ignoring the time
     */
    public static boolean equalsDate(Date date1, Date date2) {
        return date1.getDay() == date2.getDay() && date1.getMonth() == date2.getMonth() && date1.getYear() == date2.getYear();
    }

    /**
     * Show loading status, damp it by 500 milliseconds.
     */
    public static void load(){
        System.out.printf("║║║║║║║║║║║║");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
        }
    }

    /**
     * Check whether the given string can be casted to numeric.
     * @param str the string
     * @return whether it can be casted to numeric
     */
    public static boolean isNumeric(String str){  
        try{  
            Double.parseDouble(str);
        }  
        catch(NumberFormatException nfe){  
            return false;  
        }  
        return true;  
    }

    /**
     * Print an admin header display, if the AccessLevel is ADMINISTRATOR.
     * @param accessLevel the AccessLevel
     */
    public static void printAdminHeader(AccessLevel accessLevel){
        if (accessLevel == AccessLevel.ADMINISTRATOR) {
            System.out.println(ConsoleColours.RED + "╔══════════════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                                       " + "ADMIN MODE" + "                                         ║");
            System.out.println("╚══════════════════════════════════════════════════════════════════════════════════════════╝" + ConsoleColours.RESET);
        }
    }

    /**
     * An input tool which is a robust version of sc.nextInt(), which will return -1 instead of throwing an error if a non-numeric input is given.
     * @return the desired integer, if numeric, else -1
     */
    public static int robustNextInt() {
        try {
            Scanner sc = new Scanner(System.in);
            int i = sc.nextInt();
            sc.nextLine();
            return i;
        } catch (Exception e) {return -1;}
    }

    /**
     * An input tool which is a robust version of sc.nextDouble(), which will return -1 instead of throwing an error if a non-numeric input is given.
     * @return the desired double, if numeric, else -1
     */
    public static double robustNextDouble() {
        try {
            Scanner sc = new Scanner(System.in);
            double d = sc.nextDouble();
            sc.nextLine();
            return d;
        } catch (Exception e) {return -1;}
    }
}
