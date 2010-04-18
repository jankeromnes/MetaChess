package metachess.game;

import java.io.Serializable;
import java.util.ArrayList;

import metachess.logger.Move;

@SuppressWarnings("serial")
public class SavedGame implements Serializable {

	String setup;
	boolean atomic;
	boolean whiteAI;
	boolean blackAI;
	ArrayList<Move> moves;
	
	public SavedGame(String setup, boolean atomic, boolean whiteAI, boolean blackAI, ArrayList<Move> moves) {
		this.setup = setup;
		this.atomic = atomic;
		this.whiteAI = whiteAI;
		this.blackAI = blackAI;
		this.moves = moves;
	}
	
	public String getSetup() { return setup; }
	public boolean isAtomic() { return atomic; }
	public boolean isAI(boolean white) { return (white ? whiteAI : blackAI ); }
	public ArrayList<Move> getMoves() { return moves; }

}
