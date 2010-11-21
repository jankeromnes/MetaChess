package metachess.game;

import java.io.Serializable;
import java.util.ArrayList;

import metachess.model.GameBehaviour;

/** Class of a saved metachess game model
 * @author Jan and Agbeladem (7DD)
 * @version 0.8.6
 */
@SuppressWarnings("serial")
public class SavedGame implements Serializable, GameBehaviour {

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

   
    @Override
    public void setSetup(String s) { setup = s; }

    @Override
    public void setAtomic(boolean a) { atomic = a; }

    @Override
    public void setBlackAILevel(int l) { blackAILevel = l; } 

    @Override
    public void setWhiteAILevel(int l) { whiteAILevel = l; } 

    @Override
    public boolean isAtomic() { return atomic; }    

    @Override
    public String getSetup() { return setup; }

    @Override
    public int getBlackAILevel() { return blackAILevel; }

    @Override
    public int getWhiteAILevel() { return whiteAILevel; }

    @Override
    public void addMove(Move m) {
	moves.add(m);
    }

    @Override
    public ArrayList<Move> getMoves() {
	return moves;
    }

}

