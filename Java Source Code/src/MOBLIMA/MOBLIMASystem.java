package MOBLIMA;

/**
 * All the controller systems should inherit from this interface.
 * @author	SS1 Group 2
 * @version	1.0
 * @since	2019-11-13
 */
public interface MOBLIMASystem {
    /**
     * All systems must implement a method to serialize the database.
     * @return whether the serialization is successful
     */
    boolean serialize();

    /**
     * All systems must implement a method to deserialize the database.
     * @return whether the deserialization is successful
     */
    boolean deserialize();

    /**
     * All systems must implement a method to reset the database.
     * @return whether resetting the database is successful
     */
    boolean resetDatabase();
}
