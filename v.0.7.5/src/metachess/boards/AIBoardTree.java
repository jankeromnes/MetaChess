package metachess.boards;

import metachess.ai.BestMoveSequence;
import metachess.game.Piece;
import metachess.logger.Move;

public class AIBoardTree extends PlayableBoard {

    private static final long serialVersionUID = 1L;
    
    private BestMoveSequence sequence;
    private BestMoveSequence candidate;
    private PlayableBoard parent;
    private Move move;
    private int depth;
    private long complexity;
    private float score;
    private boolean scoreSet;
    
    public AIBoardTree(PlayableBoard ab, int treeDepth) {
    	super();
    	sequence = null;
    	candidate = null;
    	parent = ab;
    	move = null;    	
    	depth = treeDepth;
    	complexity = 0;
    	score = 0;
    	scoreSet = false;
    	
        activeSquareX=-1;
        activeSquareY=-1;
        
    	copyFromParent();
    	
    	computeBestCandidate();
    	
    	sequence = candidate;    	
    }
    
    public AIBoardTree(PlayableBoard ab, Move m, int treeDepth) {
    	super();
    	sequence = null;
    	candidate = null;
    	parent = ab;
    	move = m;    	
    	depth = treeDepth;
    	complexity = 0;
    	score = 0;
    	scoreSet = false;
    	
        activeSquareX=-1;
        activeSquareY=-1;
        
    	copyFromParent();

		playMove(m);
    	
    	computeBestCandidate();
    	
    	if(candidate != null) sequence = new BestMoveSequence(move, candidate);
    	else sequence = new BestMoveSequence(move, getScore());
    	
    	//System.out.println(this);
    	
    }
    
    public void copyFromParent() {
        whitePlaying = parent.isWhitePlaying();
        agbker = parent.isAgbker();
        atomic = parent.iAtomic();
    	width = parent.getCols();
    	height = parent.getRows();
        jokerPiece = parent.getJokerPiece();
    	squares = new AbstractSquare[width][height];
    	for(int i = 0 ; i < width ; i++)
	    for(int j = 0 ; j < height ; j++) {
		squares[i][j] = parent.getSquare(i,j).isNull()
		    ? new EmptySquare(i,j) : new AbstractSquare(parent.getSquare(i, j));
	    }
    }
    
    
    public void computeBestCandidate() {
    	
		AbstractSquare s;
		AIBoardTree child;
    	if(depth>0 && !gameOver){
			for(int x = 0 ; x < width ; x++)
				for(int y = 0 ; y < width ; y++){
					deactivateSquare();
					s = getSquare(x, y);
					if (s.hasPiece() && (s.getPiece().isWhite() == whitePlaying)){
		    			activateSquare(s);
		    			if(isSquareActive()){
		    				for(int i = 0 ; i < width ; i++)
		    					for(int j = 0 ; j < width ; j++)
		    						if(getSquare(i,j).isGreen()){
		    							child = new AIBoardTree(this, new Move(activeSquareX,activeSquareY,i,j,this), depth-1);
		    							BestMoveSequence newCandidate = child.getBestMoveSequence();
		    							// check if candidate was beaten
		    							if (candidate == null
		    									|| ( whitePlaying && ( candidate.getScore() < newCandidate.getScore() ) )
		    									|| ( !whitePlaying && ( candidate.getScore() > newCandidate.getScore() ) )
		    									) candidate = newCandidate;
		    							complexity += child.getComplexity();
		    						}
		    			}
					}
				}
    	}
    }
    
    public BestMoveSequence getBestMoveSequence() {
    	return sequence;
    }
    
    public float getScore() {
    	if(!scoreSet) {
    		complexity++;
    		score = 0;
    		Piece p;
    		for(AbstractSquare sq : this) {
    			p = sq.getPiece();
    			if (p.isWhite()) score+=p.getPrice();
    			else score-=p.getPrice();
    		}
    		if(!deathMatch){
    			if(whiteKingDead) score=-10000;
    			if(blackKingDead) score=10000;
    		}
    	}
    	return score;
    }
    
	public void freeMemory() {
		Runtime r = Runtime.getRuntime();
		long freeMemory = r.freeMemory();
		long totalMemory = r.totalMemory();
		if(freeMemory < (totalMemory*0.05)){
			r.gc();
			int freedPercentage = Math.round(((float)(r.freeMemory()-freeMemory))/((float)totalMemory)*100);
			System.out.println("Freed "+freedPercentage+"% of memory!");
		}
	}
	
    @Override
	public String toString() {
    	StringBuilder letters = new StringBuilder("    ");
    	for(int i = 0 ; i < getCols() ; i++) letters.append(" "+((char)('A'+i)));
    	letters.append("\n");
    	StringBuilder separator = new StringBuilder("     -");
    	for(int i = 0 ; i < getCols()-1 ; i++) separator.append("--");
    	separator.append("\n");
    	StringBuilder sb = new StringBuilder(letters);
    	sb.append(separator);
		for(int j = getRows() - 1 ; j >= 0 ; j--){
			if(j+1<10) sb.append(" ");
			sb.append((j+1)+" | ");
			for(int i = 0 ; i < getCols() ; i++)
			    if(! getSquare(i,j).isNull() && hasPiece(i,j)){
			    Piece p = getPiece(i,j);
			    String icon = p.getName().substring(0,1)+" "; 
			    sb.append((p.isWhite() ? icon.toUpperCase() : icon.toLowerCase()));
    			}
    			else sb.append("_ ");
    		sb.append("| "+(j+1)+"\n");
    	}
    	sb.append(separator);
    	sb.append(letters);
    	if(isSquareActive())sb.append("active square : "+getActiveSquare().getName()+"\n");
    	return sb.toString();
    }
    
	public long getComplexity() {
		return complexity;
	}
    
}



