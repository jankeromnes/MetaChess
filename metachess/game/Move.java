package metachess.game;

import java.util.ArrayList;

import metachess.board.PlayableBoard;
import metachess.library.Pieces;
import metachess.model.PointBehaviour;
import metachess.square.AbstractSquare;

/** Class of a specific abstract move played by a player in the history
 * @author Agbeladem (7DD)
 * @version 0.9.0
 */
public class Move {

    private static final long serialVersionUID = 1L;

    private int oldx;
    private int oldy;
    private int newx;
    private int newy;
    
    private long time;

    private boolean capture;
    private boolean kingInRange;
    private boolean castling;
    private boolean promotion;

    private PlayableBoard board;
    private Piece piece;
    private Piece promotionPiece;

    private boolean resolved;
    private boolean horizontalAmbiguity;
    private boolean verticalAmbiguity;



    private enum Type { PAWN, ATTACK };

    private boolean pawnType;
    private boolean attackType;



    // CONSTRUCTORS

    /** Creation of a move
     * @param oldxarg the column (X Coord) of the moved piece before the move
     * @param oldyarg the row (Y Coord) of the moved piece before the move
     * @param newxarg the column (X Coord) of the moved piece after the move
     * @param newyarg the row (Y Coord) of the moved piece after the move
     * @param timearg the time the player took to play in milliseconds
     * @param abstractBoard the board in which this move is played
     */
    public Move(int oldxarg, int oldyarg,
		int newxarg, int newyarg, long timearg,
		PlayableBoard abstractBoard) {

		castling = false;
		promotion = false;
		board = abstractBoard;
		capture = false;
		kingInRange = false;
		resolved = false;
		verticalAmbiguity = false;
		horizontalAmbiguity = false;
	
		oldx = oldxarg;
		oldy = oldyarg;
		newx = newxarg;
		newy = newyarg;
		
		setTime(timearg);
	
		pawnType = false;
		attackType = false;

    }

    /** Creation of a move
     * @param a the position of the moved piece before the move
     * @param b the position of the moved piece after the move
     * @param ab the board in which this move is played
     */
    public Move(PointBehaviour a, PointBehaviour b, long timearg, PlayableBoard ab) {
	this(a.getColumn(), a.getRow(), b.getColumn(), b.getRow(), timearg, ab);
    }



    // ALGEBRAIC CHESS NOTATION SUPPORT
    /** Resolve ambiguity problems in the algebraic chess notation */
    public void resolveAmbiguity() {
	assert board != null;
	if(!resolved) {
	    board.togglePlaying();
	    piece = board.getSquare(oldx, oldy).getPiece();
	    board.removePiece(oldx, oldy);
	    for(AbstractSquare s : board)
		if(s.hasPiece()) {
		    Piece p = s.getPiece();
		    if(p.isWhite() == piece.isWhite() && p.getLetter() == piece.getLetter()) {
			PlayableBoard b = new PlayableBoard(board);
			b.deactivateSquares();
			b.playSquare(s.getCoords());
			if(b.getSquare(newx, newy).isGreen()) {
			    horizontalAmbiguity = s.getCoords().getColumn() == oldx;
			    verticalAmbiguity = s.getCoords().getRow() == oldy;
			}
		    }
		}
	    board.resetIterator();
	    board.setPiece(oldx, oldy, piece);
	    resolved = true;
	}
    }


    // EN PASSANT SUPPORT

    /** Tell whether this move involved a 'Pawnline' move type.
     * It must be resolved first.
     * @return true if it is and has been resolved
     */
    public boolean isPawnType() {
	return pawnType;
    }

    /** Tell whether this move involved an 'Attack' move type.
     * It must be resolved first.
     * @return true if it is and has been resolved
     */
    public boolean isAttackType() {
	return attackType;
    }


    private boolean resolve(MoveType mt, Type t) {
	switch(t) {
	case PAWN:
	    return mt.isPawnType();
	case ATTACK:
	    return mt.isAttackType();
	}
	return false;
    }

    private void resolve(Type t) {
	assert board != null;
	Piece p = board.getSquare(oldx, oldy).getPiece();
	ArrayList<MoveType> l = p.getMoveTypes();
	ArrayList<MoveType> oldl = new ArrayList<MoveType>();
	ArrayList<MoveType> newl = new ArrayList<MoveType>();
	for(MoveType mt : l) {
	    oldl.add(mt);
	    if(! resolve(mt, t))
		newl.add(mt);
	}
	p.setMoves(newl);

	boolean b = !p.checkSquareInRange(getOldCoords(), getNewCoords(), board); 
	switch(t) {
	case PAWN:
	    pawnType = b;
	    break;
	case ATTACK:
	    attackType = b;
	    break;
	}

	p.setMoves(oldl);
    }

    /** Check if the last move had a 'Pawnline' move type */
    public void resolvePawnType() {
	resolve(Type.PAWN);
    }

    /** Check if the last move had an 'Attack' move type */
    public void resolveAttackType() {
	resolve(Type.ATTACK);
    }
    
    // TIMING
    
	/** Set the time the player took to make this move
	 * @param time the time to set
	 */
	public void setTime(long timearg) {
		time = timearg;
	}

	/** Return the time the player took to make this move
	 * @return the time
	 */
	public long getTime() {
		return time;
	}
	

    // GAME STATE

    /** Set whether a piece is captured in this move
     * @param c true if one actually gets captured (or exploded)
     */
    public void setCapture(boolean c) {
	capture = c;
    }

    /** Set whether this move has put the opponent's king in danger
     * @param k true if it has
     */
    public void setKingInRange(boolean k) {
	kingInRange = k;
    }
    

    // CASTLING

    /** Set whether this move is a castling
     * @param c true if it is
     */
    public void setCastling(boolean c) {
	castling = c;
    }


    // PROMOTION PIECE

    /** Specify that this move has lead to a piece promotion
     * @param piece the promotion piece
     */
    public void setPromotionPiece(Piece piece) {
	promotion = true;
	promotionPiece = piece;
    }

    /** Specify that this loaded move will include a piece promotion
     * @param piece the name of the promotion piece
     */
    public void setPromotionPiece(String piece) {
	setPromotionPiece(Pieces.getPiece(piece));
    }

    /** Get the promotion piece that was chosen in this move
     * @return the promotion piece
     */
    public Piece getPromotionPiece() {
	assert promotion;
	return promotionPiece;
    }

    /** Tell whether this move has lead to a promotion
     * @return true if it has
     */
    public boolean isPromotion() {
	return promotion;
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

    /** Get the format used for the game file (MCG format)
     * @return the format as a string
     */
    public String getMCGFormat() {
		StringBuilder s = new StringBuilder();
		s.append(getOldCoords());
		s.append(getNewCoords());
		if(promotion) {
		    s.append('_');
		    s.append(promotionPiece.getName());
		}
		s.append(' ');
		s.append(time);
		return s.toString();
    }

    @Override
    public String toString() {
	assert resolved;
	StringBuilder s = new StringBuilder();
	if(castling) {
	    boolean kingside = newx - oldx > 0;
	    int nb = kingside ? board.getCols() - 1 - oldx : oldx;
	    nb -= 2;
	    for(int i = 0 ; i < nb ; i++)
		s.append("0-");
	    s.append('0');
	} else {
	    if(!piece.isPawn()) s.append(piece.getLetter());
	    if(capture) s.append('x');
	    if(verticalAmbiguity) s.append(getOldCoords().getColumnChar());
	    else if(horizontalAmbiguity) s.append(getOldCoords().getRowChar());
	    s.append(board.getSquare(newx,newy).getCoords().toString().toLowerCase());
	    if(promotion) {
		s.append(':');
		s.append(promotionPiece.getLetter());
	    }
	    if(kingInRange) s.append('+');

	}
	return s.toString();
    }

}

