package metachess.boards;

import java.util.ArrayList;

import metachess.ai.AIThread;
import metachess.game.Coords;
import metachess.game.Game;
import metachess.game.Move;
import metachess.game.Piece;
import metachess.squares.AbstractSquare;

/** Class of the real Chess Board
 * @author Jan (7DD)
 * @version 0.8.0
 */
public class ChessBoard extends PlayableBoard {

    private static final long serialVersionUID = 1L;

    private Game game;
    private boolean keep;
    private boolean waitForAI;

    /** Create a new Chess Board
     * @param g the game window in which its graphical board will be created
     */
    public ChessBoard(Game g) {
    	super();
    	game = g;
    }
    
    @Override
    public void init(String s, boolean isAtomic) {
    	super.init(s, isAtomic);
    	int AILevel = game.getAILevel(whitePlaying);
    	if(AILevel > 0){
    		waitForAI = true;
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
    	int AILevel = game.getAILevel(whitePlaying); 
    	if(keep && AILevel > 0) {
	    toggleEnabled();
	    waitForAI = true;
	    update();
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
    	waitForAI = false;
    	playSquare(m.getOldCoords(), true);
    	waitForAI = true;
    	try { Thread.sleep(200); } catch (Exception e) { }
    	waitForAI = false;
    	playSquare(m.getNewCoords(), true);
    }
    
    /** Literally play a given square
     * @param c the square's Coords
     * @param keep whether the move is being kept in the logger
     */
    public void playSquare(Coords c, boolean keep){
    	this.keep = keep;
    	if(!waitForAI) super.playSquare(c);
    }
    
    @Override
    public void playSquare(Coords c) {
	playSquare(c, true);
    }

    /** Play a list of moves in this board, starting from the beginning of the setup
     * @param moves the list of moves to load
     */
    public void jump(ArrayList<Move> moves) {
	assert moves != null;
	int n = moves.size();
	for(int i = 0; i < n ; i++)
	    replayMove(moves.get(i));
    }
 

}


