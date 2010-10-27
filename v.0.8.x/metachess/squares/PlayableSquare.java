package metachess.squares;

import java.util.ArrayList;

import metachess.boards.PlayableBoard;
import metachess.game.Coords;

/** Class of a playable square
 * @author Agbeladem (7DD)
 * @version 0.8.3
 */
public class PlayableSquare extends AbstractSquare {

    private boolean empty;
    private ArrayList<Coords> choices;

    /** Create a playable square
     * @param x this square's column (X Coord)
     * @param y this square's row (Y Coord)
     */
    public PlayableSquare(int i, int j) {
	super(i, j);
	choices = new ArrayList<Coords>();
	empty = false;
    }

    /** Create a copy of a playable square
     * @param s the playable square to copy
     */
    public PlayableSquare(PlayableSquare s) {
	super(s);
	choices = new ArrayList<Coords>(s.choices);
	empty = false;
    }

    /** Create a playable square twin of an empty square.
     * <br/> This trick permits the getSquare override in PlayableBoard
     * @param s the empty square to emulate
     */
    public PlayableSquare(EmptySquare s) {
	super(s);
	choices = null;
	empty = true;
    }

    // METHODS CONCERNING THE LIST

    /** Clear the choice list. By doing so, no set of coordinates will be reachable
     * by this square's piece (providing there is one !) */
    public void clearChoiceList() {
	assert hasPiece();
	choices.clear();
    }

    /** Add reachable coordinates of this square's piece (providing there is one !)
     * @param choice the coordinates to add into the choice list
     */
    public void addChoice(Coords choice) {
	assert hasPiece();
	choices.add(choice);
    }

    /** Set all the squares reachable by this square's piece green
     * @param b the board in which the squares are to be set green
     * @return true if square are indeed reachable
     */
    public boolean setGreenSquares(PlayableBoard b) {
	assert hasPiece();
	for(Coords c : choices)
	    b.getSquare(c).setGreen(true);
	return !choices.isEmpty();
    }

    public boolean isNull() {
	return empty;
    }

    @Override
    public Object clone() {
	return new PlayableSquare(this);
    }

}
