package MOBLIMA;

/**
 * Represents a view on the console for a given system.
 * This is an abstract class which will be inherited by other system views..
 * @author	SS1 Group 2
 * @version	1.0
 * @since	2019-11-13
 */
public abstract class ViewSystem extends View{

    /**
     * Every system must implement an add method.
     * @param accessLevel the access level, customer or administrator
     */
    public abstract void add( AccessLevel accessLevel);

    /**
     * Every system must implement a delete method.
     * @param accessLevel the access level, customer or administrator
     */
    public abstract void delete( AccessLevel accessLevel);

    /**
     * Every system must implement a list method.
     * @param accessLevel the access level, customer or administrator
     */
    public abstract void list(AccessLevel accessLevel);

    /**
     * Every system must implement an update method.
     * @param accessLevel the access level, customer or administrator
     */
    public abstract void update(AccessLevel accessLevel);

    /**
     * Every system must implement a reset method.
     * @param accessLevel the access level, customer or administrator
     */
    public abstract void reset(AccessLevel accessLevel);
}
