package metachess.boards;

import java.util.ArrayList;

import metachess.ai.AIThread;
import metachess.game.Game;
import metachess.logger.Move;


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
    public void init(String s, boolean isAtomic){
    	super.init(s, isAtomic);
    	int AILevel = game.getAILevel(whitePlaying);
    	if(AILevel>0){
    		waitForAI = true;
    		AIThread ait = new AIThread(this, AILevel);
    		ait.start();
    	}
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
    		waitForAI = true;
    		update();
    		AIThread ait = new AIThread(this, AILevel);
    		ait.start();
    	}
    }

    /** Play a given move
     * @param m the move
     * @param keep whether 
     */
    public void playMove(Move m) {
    	playSquare(m.getOldX(), m.getOldY(),true);
    	playSquare(m.getNewX(), m.getNewY(),true);
    }

    /** Play a given move
     * @param m the move
     * @param keep whether 
     */
    public void replayMove(Move m) {
    	playSquare(m.getOldX(), m.getOldY(),false);
    	playSquare(m.getNewX(), m.getNewY(),false);
    }

    /** Play a given move
     * @param m the move
     * @param keep whether 
     */
    public void playAIMove(Move m) {
    	waitForAI = false;
    	playMove(m);
    }
    
    /** Literally play a given square
     * @param i the square's column (X Coord)
     * @param j the square's row (Y Coord)
     */
    public void playSquare(int i, int j, boolean keep){
    	this.keep = keep;
    	if(!waitForAI) super.playSquare(i, j);
    }
    
    public void playSquare(int i, int j) {
    	playSquare(i, j, true);
    }

    public void jump(ArrayList<Move> moves) {
	if(moves != null) {
	    int n = moves.size();
	    for(int i = 0; i < n ; i++)
		replayMove(moves.get(i));
	}
    }
 

}



