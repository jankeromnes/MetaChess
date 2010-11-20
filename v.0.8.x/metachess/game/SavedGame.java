package metachess.game;

import java.io.Serializable;
import java.util.ArrayList;

/** Class of a saved metachess game model
 * @author Jan and Agbeladem (7DD)
 * @version 0.8.6
 */
@SuppressWarnings("serial")
public class SavedGame implements Serializable {

    private String setup;
    private boolean atomic;
    private int whiteAILevel;
    private int blackAILevel;
    private ArrayList<Move> moves;
    
    /** Create an empty saved game.
     * Its properties will have to be defined by the set methods.
     */
    public SavedGame() {
	moves = new ArrayList<Move>();
    }
    
    /** Create a saved game
     * @param setup the setup in which this game starts
     * @param whiteAILevel2 the level of the white AI
     * @param blackAILevel2 the level of the black AI
     * @param moves the list of the moves played in this game, in the right order
     */
    public SavedGame(String setup, boolean atomic, int whiteAILevel2, int blackAILevel2, ArrayList<Move> moves) {
	this.setup = setup;
	this.atomic = atomic;
	this.whiteAILevel = whiteAILevel2;
	this.blackAILevel = blackAILevel2;
	this.moves = moves;
    }

   
    public void setSetup(String s) { setup = s; }
    public void setAtomic(boolean a) { atomic = a; }
    public void setBlackAILevel(int l) { blackAILevel = l; } 
    public void setWhiteAILevel(int l) { whiteAILevel = l; } 

    public boolean isAtomic() { return atomic; }    
    public String getSetup() { return setup; }
    public int getBlackAILevel() { return blackAILevel; }
    public int getWhiteAILevel() { return whiteAILevel; }

    /** Add a move to this saved game
     * @param m the move to add
     */
    public void addMove(Move m) {
	moves.add(m);
    }

    /** Get all the moves played in this saved game
     * @return the list of the moves as an ArrayList
     */ 
    public ArrayList<Move> getMoves() {
	return moves;
    }

}
