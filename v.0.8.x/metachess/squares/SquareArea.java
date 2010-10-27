package metachess.squares;

import metachess.boards.AbstractBoard;
import metachess.game.Coords;

/** Class of a Square Area.
 * Square areas are the basic groups of squares of which Areas are composed
 * @author Agbeladem
 * @version 0.8.0
 */
public class SquareArea {

    private AbstractBoard board;
    private Coords c1;
    private Coords c2;

    /** Create a square area
     * @param x1 the bottom-left square's column
     * @param y1 the bottom-left square's row
     * @param x2 the top-right square's column
     * @param y2 the top-right square's row
     * @param ab the abstract board to which this square area belongs
     */
    public SquareArea(char x1, char y1, char x2, char y2, AbstractBoard ab) {
	
	board = ab;

	c1 = new Coords(x1, y1);
	c2 = new Coords(x2, y2);

	assert c1.getColumn() <= c2.getColumn() && c1.getRow() <= c2.getRow();

    }

    /** Tells whether this square area contains the given square
     * @param as the abstract square
     * @param reverse whether this area should be reversed (in case of symmetry)
     * @return true if it does
     */
    public boolean containsSquare(AbstractSquare as, boolean reverse) {

	Coords C1, C2;

	if(reverse) {
	    C1 = c2.reverse(board);
	    C2 = c1.reverse(board);
	} else {
	    C1 = c1;
	    C2 = c2;
	}


	int i = as.getColumn();
	int j = as.getRow();

	return i >= C1.getColumn() && i <= C2.getColumn()
	    && j >= C1.getRow() && j <= C2.getRow();

    }

}

