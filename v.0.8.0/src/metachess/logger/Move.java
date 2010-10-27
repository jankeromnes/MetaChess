package metachess.logger;

import java.io.Serializable;

import metachess.boards.AbstractBoard;
import metachess.boards.AbstractSquare;
import metachess.game.Piece;

/** Class of a specific abstract move played by a player in the history
 * @author Agbeladem (7DD)
 * @version 0.8.2
 */
public class Move implements Serializable {

    private static final long serialVersionUID = 1L;
    private int oldx;
    private int oldy;
    private int newx;
    private int newy;
    private boolean capture;
    private Piece piece;
    private transient AbstractBoard board;

    // CONSTRUCTORS

    /** Create an empty move.
     * The move's properties should have to be defined using methods */
    public Move() {}

    /** Creation of a move
     * @param oldxarg the column (X Coord) of the moved piece before the move
     * @param oldyarg the row (Y Coord) of the moved piece before the move
     * @param newxarg the column (X Coord) of the moved piece after the move
     * @param newyarg the row (Y Coord) of the moved piece after the move
     * @param abstractBoard the board in which this move is played
     */
    public Move(int oldxarg, int oldyarg
		, int newxarg, int newyarg
		, AbstractBoard abstractBoard) {

	board = abstractBoard;
	capture = false;
	piece = abstractBoard.getSquare(newxarg, newyarg).getPiece();
	oldx = oldxarg;
	oldy = oldyarg;
	newx = newxarg;
	newy = newyarg;
    }

    /** Creation of a move
     * @param a the square the moved piece belonged to before the move
     * @param newxarg the square the moved piece belonged to after the move
     * @param abstractBoard the board in which this move is played
     */
    public Move(AbstractSquare a, AbstractSquare b, AbstractBoard ab) {
	this(a.getColumn(), a.getRow(), b.getColumn(), b.getRow(), ab);
    }

    // ALGEABRIC CHESS NOTATION SUPPORT
    public void resolveAmbiguity() {

    }

    /** Set whether a piece is captured in this move
     * @param c true if one actually gets captured (or exploded)
     */
    public void setCapture(boolean c) {
	capture = c;
    } 

    // GETTERS

    /** Get the X Coord of the moved piece before the move
     * @return the column (X Coord) of the piece
     */
    public int getOldX() {
	return oldx;
    }

    /** Get the X Coord of the moved piece before the move
     * @return the row (Y Coord) of the piece
     */
    public int getOldY() {
	return oldy;
    }

    /** Get the X Coord of the moved piece after the move
     * @return the column (X Coord) of the piece
     */
    public int getNewX() {
	return newx;
    }

    /** Get the Y Coord of the moved piece after the move
     * @return the row (Y Coord) of the piece
     */
    public int getNewY() {
	return newy;
    }
   
    @Override
	public String toString() {
	StringBuilder s = new StringBuilder();
	//s.append(board.getSquare(oldx,oldy).getCoords().toString().toLowerCase());
	if(!piece.isPawn()) s.append(piece.getLetter());
	if(capture) s.append('x');
	s.append(board.getSquare(newx,newy).getCoords().toString().toLowerCase());
	return s.toString();
    }

}

