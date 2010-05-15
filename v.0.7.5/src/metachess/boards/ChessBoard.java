package metachess.boards;

import java.util.ArrayList;

import metachess.ai.AIThread;
import metachess.game.Game;
import metachess.logger.Move;


/** Class of the real Chess Board
 * @author Jan (7DD)
 * @version 0.8.0
 */
public class ChessBoard extends ChessRulesBoard {

    private static final long serialVersionUID = 1L;

    private Game game;
    private boolean keep;

    /** Create a new Chess Board
     * @param g the game window in which its graphical board will be created
     */
    public ChessBoard(Game g) {
    	super();
    	game = g;
    }
    
    @Override
    public void nextPlayer() {
    	super.nextPlayer();
    	
    	if(keep) game.addMove(lastMove);

    	if(gameOver) {
    	    gameOver = false;
    	    game.endGame();
    	}
    	int AILevel = game.getAILevel(whitePlaying); 
    	if(keep && AILevel>0){
    		update();
    		AIThread ait = new AIThread(this, AILevel);
    		ait.start();
    	}
    }

    /** Play a given move
     * @param m the move
     * @param keep whether 
     */
    public void playMove(Move m, boolean keep) {
    	playSquare(m.getOldX(), m.getOldY(),keep);
    	playSquare(m.getNewX(), m.getNewY(),keep);
    }
    
    /** Literally play a given square
     * @param i the square's column (X Coord)
     * @param j the square's row (Y Coord)
     */
    public void playSquare(int i, int j, boolean keep){
    	this.keep = keep;
    	super.playSquare(i, j);
    }
    
    public void playSquare(int i, int j) {
    	playSquare(i, j, true);
    }

    public void jump(ArrayList<Move> moves) {
	if(moves != null) {
	    int n = moves.size();
	    keep = false;
	    for(int i = 0; i < n ; i++)
		playMove(moves.get(i));
	}
    }
 

}



