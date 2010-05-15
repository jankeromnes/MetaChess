package metachess.rules;

import metachess.boards.AbstractBoard;

public class MetaChessRules {
	
	private boolean whitePlaying;
    private boolean agbker;
    private boolean atomic;
    private boolean deathMatch;
    private boolean gameOver;
    private boolean whiteKingDead;
    private boolean blackKingDead;
    
    public MetaChessRules(AbstractBoard board) {
    	init();
    }

    // With setup ? ENUM for basic rule patterns ?
    public MetaChessRules(AbstractBoard board,
    		boolean whitePlaying,
    		boolean agbker,
    		boolean atomic,
    		boolean deathMatch,
    		boolean gameOver,
    		boolean whiteKingDead,
    		boolean blackKingDead) {

    	setWhitePlaying(whitePlaying);
        setAgbker(agbker);
        setAtomic(atomic);
        setDeathMatch(deathMatch);
        setGameOver(gameOver);
        setWhiteKingDead(whiteKingDead);
        setBlackKingDead(blackKingDead);
    }

	public void setWhitePlaying(boolean whitePlaying) {
		this.whitePlaying = whitePlaying;
	}

	public boolean isWhitePlaying() {
		return whitePlaying;
	}

	public void setAgbker(boolean agbker) {
		this.agbker = agbker;
	}

	public boolean isAgbker() {
		return agbker;
	}

	public void setAtomic(boolean atomic) {
		this.atomic = atomic;
	}

	public boolean isAtomic() {
		return atomic;
	}

	public void setDeathMatch(boolean deathMatch) {
		this.deathMatch = deathMatch;
	}

	public boolean isDeathMatch() {
		return deathMatch;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setWhiteKingDead(boolean whiteKingDead) {
		this.whiteKingDead = whiteKingDead;
	}

	public boolean isWhiteKingDead() {
		return whiteKingDead;
	}

	public void setBlackKingDead(boolean blackKingDead) {
		this.blackKingDead = blackKingDead;
	}

	public boolean isBlackKingDead() {
		return blackKingDead;
	}

	public void init() {
    	setWhitePlaying(true);
        setAgbker(false);
        setAtomic(false);
        setDeathMatch(false);
        setGameOver(false);
        setWhiteKingDead(false);
        setBlackKingDead(false);
	}

}
