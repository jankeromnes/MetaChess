package metachess.game;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import metachess.exception.WriteException;
import metachess.model.GameBehaviour;

/** Class of a saved metachess game model
 * @author Jan and Agbeladem (7DD)
 * @version 0.9.0
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


    public void save(File f) throws WriteException {

	try {
	    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(f)));
	    pw.println("# Metachess v.0.8.6/MCG=v.3");
	    pw.println("# Automatically generated");
	    pw.println("");
	    pw.println("atomic = "+(atomic?"TRUE":"FALSE"));
	    pw.println("setup = "+setup);
	    pw.println("whitelevel = "+whiteAILevel);
	    pw.println("blacklevel = "+blackAILevel);
	    pw.println("\n{BEGIN}\n");
	    
	    int n = moves.size();
	    for(int i = 0 ; i < n ; i++)
		pw.println(moves.get(i).getMCGFormat());

	    pw.close();

	} catch(IOException e) {
	    throw new WriteException(f.getPath());
	}

    }

	    
   
    // GAME BEHAVIOUR

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

