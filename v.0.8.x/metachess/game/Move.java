package metachess.game;

import java.io.Serializable;

import metachess.boards.PlayableBoard;
import metachess.squares.AbstractSquare;

/** Class of a specific abstract move played by a player in the history
 * @author Agbeladem (7DD)
 * @version 0.8.4
 */
public class Move implements Serializable {

    private static final long serialVersionUID = 1L;
    private int oldx;
    private int oldy;
    private int newx;
    private int newy;
    private boolean capture;
    private Piece piece;
    private transient PlayableBoard board;
    private transient boolean horizontalAmbiguity;
    private transient boolean verticalAmbiguity;

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
		, PlayableBoard abstractBoard) {

	board = abstractBoard;
	capture = false;
	verticalAmbiguity = false;
	horizontalAmbiguity = false;

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
    public Move(AbstractSquare a, AbstractSquare b, PlayableBoard ab) {
	this(a.getColumn(), a.getRow(), b.getColumn(), b.getRow(), ab);
    }

    // ALGEABRIC CHESS NOTATION SUPPORT
    public void resolveAmbiguity() {
	board.togglePlaying();
	piece = board.getSquare(oldx, oldy).getPiece();
	board.removePiece(oldx, oldy);
	for(AbstractSquare s : board)
	    if(s.hasPiece()) {
		Piece p = s.getPiece();
		if(p.isWhite() == piece.isWhite() && p.getLetter() == piece.getLetter()) {
		    PlayableBoard b = new PlayableBoard(board);
		    b.deactivateSquare();
		    b.playSquare(s.getCoords());
		    if(b.getSquare(newx, newy).isGreen()) {
			if(s.getCoords().getColumn() == oldx)
			    verticalAmbiguity = true;
			else
			    horizontalAmbiguity = true;
		    }
		}
	    }
	board.resetIterator();
    }

    /** Set whether a piece is captured in this move
     * @param c true if one actually gets captured (or exploded)
     */
    public void setCapture(boolean c) {
	capture = c;
    } 

    // GETTERS

    /** Get the Coords of the moved piece before the move
     * @return the Coords of the piece
     */
    public Coords getOldCoords() {
	return new Coords(oldx, oldy);
    }

    /** Get the Coords of the moved piece after the move
     * @return the Coords of the piece
     */
    public Coords getNewCoords() {
	return new Coords(newx, newy);
    }

    @Override
	public String toString() {
	StringBuilder s = new StringBuilder();
	if(!piece.isPawn()) s.append(piece.getLetter());
	if(capture) s.append('x');
	if(verticalAmbiguity) s.append(getOldCoords().getColumnChar());
	else if(horizontalAmbiguity) s.append(getOldCoords().getRowChar());
	s.append(board.getSquare(newx,newy).getCoords().toString().toLowerCase());
	return s.toString();
    }

}

