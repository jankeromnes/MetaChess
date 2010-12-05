package metachess.boards;

import java.util.ArrayList;

import metachess.ai.AIThread;
import metachess.dialog.PromotionBox;
import metachess.game.Coords;
import metachess.game.Game;
import metachess.game.Move;
import metachess.game.Piece;
import metachess.library.Pieces;
import metachess.model.PointBehaviour;
import metachess.squares.AbstractSquare;

/** Class of the real Chess Board.
 * It is a playable board which has support for artificial intelligence.
 * @author Jan (7DD) and Agbeladem (7DD)
 * @version 0.8.7
 */
public class ChessBoard extends PlayableBoard {

    /* v.0.8.0- : Jan
       v.0.8.6+ : Agbeladem */ 

    private static final long serialVersionUID = 1L;

    private PromotionBox box;
    private Game game;

    private AbstractSquare promotionSquare;
    private boolean promotionWhite;

    private boolean keep;

    /** Create a new Chess Board
     * @param g the game window in which its graphical board will be created
     */
    public ChessBoard(Game g) {
    	super();
    	game = g;
	box = new PromotionBox(g, this);
    }


    private int getAILevel() {
	return whitePlaying? game.getWhiteAILevel(): game.getBlackAILevel();
    }

    /** Launch the game, to be sent after init */
    public void launch() {
	int AILevel = getAILevel();
	if(AILevel > 0) {
	    lock();
	    AIThread ait = new AIThread(this, AILevel);
	    toggleEnabled();
	    ait.start();
    	}
    }
    
    @Override
    public void nextPlayer() {
    	super.nextPlayer();
    	if(keep)
	    game.addMove(lastMove);
    	if(gameOver)
    	    game.endGame();
    	int AILevel = getAILevel();
    	if(keep && AILevel > 0) {
	    toggleEnabled();
	    lock();
	    //update();
	    AIThread ait = new AIThread(this, AILevel);
	    ait.start();
    	}
    }

    /** Remove the piece at the given coordinates
     * @param i the piece's square's column (X Coord)
     * @param j the piece's square's row (Y Coord)
     */
    @Override
	public void removePiece(int i, int j) {

	//assert isSquareValid(i,j);
	if(squares[i][j].hasPiece()) {
	    Piece p = squares[i][j].getPiece();
	    game.count(p.getName(), p.isWhite());
	}
	super.removePiece(i, j);
	//squares[i][j].setPiece(null);
    }

    /** Play a specific move in this board
     * @param m the move to play
     */
    public void playMove(Move m) {
    	playSquare(m.getOldCoords(), true);
    	playSquare(m.getNewCoords(), true);
    }

    /** Replay a move, and prevent the AI from playing if needed
     * @param m the move to play
     */
    public void replayMove(Move m) {
    	playSquare(m.getOldCoords(), false);
    	playSquare(m.getNewCoords(), false);
    }

    /** Make the AI play a move.
     *  This is used when the AIThread has chosen its best move
     * @param m the move to play
     */
    public void playAIMove(Move m) {
    	toggleEnabled();
    	unlock();
    	playSquare(m.getOldCoords(), true);
    	lock();
    	try { Thread.sleep(200); } catch (Exception e) { e.printStackTrace(); }
    	unlock();
    	playSquare(m.getNewCoords(), true);
    }
    
    /** Literally play a given square
     * @param c the square's Coords
     * @param keep whether the move is being kept in the logger
     */
    public void playSquare(PointBehaviour c, boolean keep) {
    	this.keep = keep;
	if(!isLocked()) super.playSquare(c);
    }
    
    @Override
    public void playSquare(PointBehaviour c) {
	playSquare(c, true);
    }

    @Override
    protected void promote(AbstractSquare as, boolean white) {
	assert !promotions.isEmpty();

	promotionSquare = as;
	promotionWhite = white;
	lock();

	if(getAILevel() > 0) {

	    // The best of the available pieces
	    float price = 0f;
	    String piece = null;
	    for(String s: promotions) {
		float p = Pieces.getPiece(s).getPrice();
		if(p > price) {
		    piece = s;
		    price = p;
		}
	    }

	    as.setPiece(Pieces.getPiece(piece, white));
	    unlock();
	    checkKingInRange();

	} else if(isPlaying()) {

	    // Promotion Box
	    togglePlaying();
	    box.launch(promotions);

	} // Else the move is being loaded or replayed

     }


     /** Confirm the promotion piece
      * @param piece the name of the piece that will be added on this board
      */
     public void validatePromotion(String piece) {
	 assert !isPlaying();
	 unlock();
	 deactivateSquares();
	 promotionSquare.setPiece(Pieces.getPiece(piece, promotionWhite));
	 lastMove.resolveAmbiguity();
	 checkKingInRange();
	 togglePlaying();
	 update();
	 nextPlayer();
    }


    /** Replay a list of moves in this board, starting from the beginning of the setup
     * @param moves the list of moves to load
     */
    public void jump(ArrayList<Move> moves) {
	assert moves != null;
	int n = moves.size();
	for(int i = 0 ; i < n ; i++)
	    replayMove(moves.get(i));
    }

}



