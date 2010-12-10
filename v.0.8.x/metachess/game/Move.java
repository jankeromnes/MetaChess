package metachess.game;

import metachess.board.PlayableBoard;
import metachess.library.Pieces;
import metachess.model.PointBehaviour;
import metachess.square.AbstractSquare;

/** Class of a specific abstract move played by a player in the history
 * @author Agbeladem (7DD)
 * @version 0.8.6
 */
public class Move {

    private static final long serialVersionUID = 1L;
    private int oldx;
    private int oldy;
    private int newx;
    private int newy;

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



    // CONSTRUCTORS

    /** Creation of a move
     * @param oldxarg the column (X Coord) of the moved piece before the move
     * @param oldyarg the row (Y Coord) of the moved piece before the move
     * @param newxarg the column (X Coord) of the moved piece after the move
     * @param newyarg the row (Y Coord) of the moved piece after the move
     * @param abstractBoard the board in which this move is played
     */
    public Move(int oldxarg, int oldyarg,
		int newxarg, int newyarg,
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

    }

    /** Creation of a move
     * @param a the position of the moved piece before the move
     * @param b the position of the moved piece after the move
     * @param ab the board in which this move is played
     */
    public Move(PointBehaviour a, PointBehaviour b, PlayableBoard ab) {
	this(a.getColumn(), a.getRow(), b.getColumn(), b.getRow(), ab);
    }



    // ALGEABRIC CHESS NOTATION SUPPORT
    /** Resolve ambiguity problems in the algeabric chess notation */
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
	    resolved = true;
	}
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
	return s.toString();
    }

    @Override
    public String toString() {
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

