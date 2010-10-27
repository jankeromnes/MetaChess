package metachess.game;

import java.io.Serializable;
import java.util.ArrayList;


@SuppressWarnings("serial")
public class SavedGame implements Serializable {

	String setup;
	boolean atomic;
	int whiteAILevel;
	int blackAILevel;
	ArrayList<Move> moves;
	
	public SavedGame(String setup, boolean atomic, int whiteAILevel2, int blackAILevel2, ArrayList<Move> moves) {
		this.setup = setup;
		this.atomic = atomic;
		this.whiteAILevel = whiteAILevel2;
		this.blackAILevel = blackAILevel2;
		this.moves = moves;
	}
	
	public String getSetup() { return setup; }
	public boolean isAtomic() { return atomic; }
	public int getAILevel(boolean white) { return (white ? whiteAILevel : blackAILevel); }
	public ArrayList<Move> getMoves() { return moves; }

}
