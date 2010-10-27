package metachess.game;

import metachess.boards.AbstractBoard;

/** Class of the string representation of coordinates
 * @author Agbeladem (7DD)
 * @version 0.8.0
 */
public final class Coords {

    /** Tells whether these coord are valid
     * @param x the column
     * @param y the row
     * @return true if they are
     */
    public static boolean isValid(char x, char y) {
	return x >= 'A' && x <= 'P' && ((y >= '0' && y <= '9') || (y >= 'a' && y <= 'g'));
    }

    private int i;
    private int j;

    /** Create a set of coordinates
     * @param x the column
     * @param y the row
     */
    public Coords(int x, int y) {
	i = x;
	j = y;
    }

    /** Create a set of coordinates from characters
     * @param a the column
     * @param y the row
     */
    public Coords(char a, char b) {
	assert isValid(a, b): "Unvalid coordinates ("+a+','+b+')';
	i = a - 'A';
	j = b >= 'a' ? b-'a'+9 : b-'1';
    }

    /** Get the reverse coordinates in the given board
     * @param board the board
     * @return the reverse coordinates (from the top right corner)
     */
    public Coords reverse(AbstractBoard board) {
	return new Coords(board.getCols()-i-1,  board.getRows()-j-1);
    }

    /** Get the first coordinate of this set
     * @return the column (X Coord)
     */
    public int getColumn() {
	return i;
    }

   /** Get the second coordinate of this set
     * @return the row (Y Coord)
     */
    public int getRow() {
	return j;
    }

    @Override
	public String toString() {
	return Character.toString((char)('A'+i)) +(char)( j > 8 ? 'a'-9+j : '1'+j); 
    }

    @Override
    public boolean equals(Object o) {
	assert o instanceof Coords;
	Coords c = (Coords)o;
	return c.j == j && c.i == i;
    }

}


