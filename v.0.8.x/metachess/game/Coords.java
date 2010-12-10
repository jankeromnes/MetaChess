package metachess.game;

import metachess.board.AbstractBoard;
import metachess.model.PointBehaviour;

/** Class of the string representation of coordinates
 * @author Agbeladem (7DD)
 * @version 0.8.8
 */
public final class Coords implements PointBehaviour {

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

    /** Get the difference between two sets of coordinates
     * @param c the coordinates to substract to these
     */
    public Coords substract(Coords c) {
	return new Coords(i-c.i, c.j);
    }


    @Override
    public Coords getCoords() { return this; }

    @Override
    public int getColumn() { return i; }

    @Override
    public int getRow() { return j; }

    /** Get the character that corresponds to these Coords' column
     * @return the char starting from 'A' as the first column
     */
    public char getColumnChar() {
	return (char)('A'+i);
    }

    /** Get the character that corresponds to these Coords' row
     * @return the char starting from '0' as the first row
     * and 'a' starting from the tenth row
     */
    public char getRowChar() {
	return (char)( j > 8 ? 'a'-9+j : '1'+j);
    }


    @Override
    public String toString() {
	return Character.toString(getColumnChar())+getRowChar();
    }

    @Override
    public boolean equals(Object o) {
	if(o == null) return false;
	else {
	    assert o instanceof Coords;
	    Coords c = (Coords)o;
	    return c.j == j && c.i == i;
	}
    }

}


