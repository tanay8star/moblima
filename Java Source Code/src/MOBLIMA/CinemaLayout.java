package MOBLIMA;

import java.io.Serializable;

/**
 * Represents the layout of a cinema.
 * Every cinema hall has a specific cinema layout.
 * Seats can be identified by their array index, row/column number or their seat number.
 * Example is array index [0][0] is the same as r = 1, c = 1 and is the same as seat A1.
 * @author	SS1 Group 2
 * @version	1.0
 * @since	2019-11-13
 */
public class CinemaLayout implements Serializable, Cloneable{

	/**
	 * 2D array of cinema seat representation of the cinema layout.
	 */
    private CinemaSeat[][] seatGrid;

	/**
	 * Number of rows of the cinema layout.
	 */
	private int row;

	/**
	 * Number of columns of the cinema layout.
	 */
    private int column;

	/**
	 * Constructor of the cinema layout.
	 * @param row number of rows of the cinema hall
	 * @param column number of columns of the cinema hall
	 */
	public CinemaLayout(int row, int column) {
    	this.row = row;
    	this.column = column;
        seatGrid = new CinemaSeat[10][10];
        for(int r = 1; r <= row; r++){
            for(int c = 1; c <= column; c++){
                seatGrid[r - 1][c - 1] = new CinemaSeat(rowToIndex(r) + Integer.toString(c));
            }
        }
    }

	/**
	 * Get the cinema seat at a specified row and column index
	 * @param r row index of the seat
	 * @param c column index of the seat
	 * @return cinema seat at the specified row and column index
	 */
	public CinemaSeat seatAt(int r, int c) {
    	return seatGrid[r - 1][c - 1];
    }

	/**
	 * Convert row index number to its character representation
	 * @param row row index number
	 * @return row character representation
	 */
	public char rowToIndex(int row) {
    	char index = 'A';
    	for (int i = 0; i < row - 1; i++) {
    		index++;
    	}
    	return index;
    }

	/**
	 * Convert row character representation to its index number.
	 * @param index row character representation
	 * @return row index number
	 */
	public int indexToRow(char index) {
    	int r = 1;
    	while (index != 'A') {r++; index--;}
    	return r;
    }


	/**
	 * Print the cinema layout, where occupied seats are represented with [X] and vacant seats are represented with [ ].
	 */
	public void printLayout() {
    	System.out.println();
    	if (column >= 2) {
    		for(int c = 1; c <= 2 * column - 2; c++) {
        		System.out.print(" ");
        	} 
    		System.out.print("SCREEN");
    	}
    	System.out.println();
    	for(int r = 1; r <= row; r++){
    		System.out.print(rowToIndex(r));
            for(int c = 1; c <= column; c++){
                if (seatGrid[r - 1][c - 1].isAssigned()) System.out.print(ConsoleColours.RED + " [X]" + ConsoleColours.RESET);
                else System.out.print(ConsoleColours.GREEN + " [ ]" + ConsoleColours.RESET);
            }
            System.out.println();
        }
    	System.out.print(" ");
    	for (int c = 1; c <= column; c++) {
    		System.out.print("  " + c + " ");
    	}
    }

	/**
	 * Assign seat at the cinema layout.
	 * @param seatNo seat number in string format
	 * @param bookingID bookingID attached to the booking
	 * @return whether assignment of seat is successful
	 */
	public boolean assignSeat(String seatNo, String bookingID) {
    	char index = seatNo.charAt(0);
    	int r = indexToRow(index);
    	int c = 0;
    	if (seatNo.length() == 2) c = Character.getNumericValue(seatNo.charAt(1));
    	else if (seatNo.length() == 3) c = Character.getNumericValue(seatNo.charAt(1)) * 10 + Character.getNumericValue(seatNo.charAt(2));
    	try {
			if (seatAt(r, c).isAssigned()) return false;
			else {
				seatAt(r, c).assign(bookingID);
				return true;
			}
		} catch (Exception e){
    		return false;
		}

    }

	/**
	 * Unassign seat at the cinema layout.
	 * @param seatNo seat number in string format
	 * @return whether unassigning the seat was successful
	 */
	public boolean unassignSeat(String seatNo) {
    	char index = seatNo.charAt(0);
    	int r = indexToRow(index);
    	int c = Character.getNumericValue(seatNo.charAt(1));
    	if (seatAt(r, c).isAssigned()) {
    		seatAt(r,c).unassign();
    		return true;}
    	else return false;
    }

	/**
	 * Allows cloning of the object.
	 * @return the cloned object
	 * @throws CloneNotSupportedException exception when clone is not supported
	 */
	public Object clone() throws CloneNotSupportedException{
    	return super.clone();
	}
}
