package metachess.ai;

import metachess.board.ChessBoard;
import metachess.board.PlayableBoard;
import metachess.game.Move;
import metachess.game.Piece;
import metachess.square.AbstractSquare;

/** Class of an AI Board Tree, child of another Playable Board, with progeny
 * @author Jan (7DD)
 * @version 0.8.0
 */
public class AITree extends PlayableBoard {

    private static final long serialVersionUID = 1L;
    private ChessBoard cb;
    private BestMoveSequence sequence;
    private BestMoveSequence candidate;
    private Move move;
    private int depth;
    private long complexity;
    private float score;
    private boolean scoreSet;
    private int children;
    private float percent;
    private boolean trace;
    
    /** Create the root node of the AITree from a given ChessBoard with a given depth
     * @param cb the ChessBoard
     * @param treeDepth the depth of the tree
     */
    public AITree(ChessBoard cb, int treeDepth) {
    	super(cb);
    	this.cb = cb;
    	
    	sequence = null;
    	candidate = null;
    	move = null;    	
    	depth = treeDepth;
    	complexity = 0;
    	score = 0;
    	scoreSet = false;
    	children = 0;
    	percent = 0;
    	trace = true;
    	
	squareActive = false;
    	
    	computeBestCandidate();
    	
    	sequence = candidate;
    }
    
    /** Create the child nodes of the AITree from a given PlayableBoard with a given depth
     * @param pb the PlayableBoard
     * @param m the move that lead to that position
     * @param treeDepth the depth of the tree
     */
    public AITree(PlayableBoard pb, Move m, int treeDepth) {
    	super(pb);
    	this.cb = null;
    	
    	foreseer = false;
    	sequence = null;
    	candidate = null;
    	move = m;    	
    	depth = treeDepth;
    	complexity = 0;
    	score = 0;
    	scoreSet = false;
    	children = 0;
    	percent = 0;
    	trace = false;
    	
	squareActive = false;

        playMove(m);
    	
    	computeBestCandidate();
    	
    	if(candidate != null) sequence = new BestMoveSequence(move, candidate);
    	else sequence = new BestMoveSequence(move, getScore());
    	
    }
    
    /** Compute the best move to play from the actual position
     */
    public void computeBestCandidate() {
    	if(trace) {
	    children = getChoiceCount();
	    percent = 0;
    	}
	resetIterator();
	AITree child;
    	if(depth>0 && !gameOver) {
	    for(AbstractSquare s : this) {
		deactivateSquares();
		// optimization idea: if reached maximum final score, don't search for other options (complexity gets a little bit better)
		if (s.hasPiece() && (s.getPiece().isWhite() == whitePlaying) && ( candidate == null || ( !whitePlaying && candidate.getScore() > -9999 ) || ( whitePlaying && candidate.getScore() < 9999 ) ) ) {
		    activateSquare(s);
		    if(isSquareActive()) {
			for(int i = 0 ; i < width ; i++) {
			    for(int j = 0 ; j < width ; j++) {
				if(getSquare(i,j).isGreen()) {
				    Move m = new Move(activeSquare.getColumn(),activeSquare.getRow(), i, j, 0, this);
				    child = new AITree(this, m, depth-1);
				    BestMoveSequence newCandidate = child.getBestMoveSequence();
				    // check if candidate was beaten
				    if (candidate == null
					|| ( whitePlaying && ( candidate.getScore() < newCandidate.getScore() ) )
					|| ( !whitePlaying && ( candidate.getScore() > newCandidate.getScore() ) )
					) candidate = newCandidate;
				    complexity += child.getComplexity();
				    if(trace) {
						percent += 100./(float)children;
						cb.updateAIPercentage(percent);
				    }
				}
			    }
			}
		    }
		}
	    }
	    resetIterator();
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
		if(sq.hasPiece()) {
		    p = sq.getPiece();
		    if (p.isWhite()) score+=p.getPrice();
		    else score-=p.getPrice();
		}
	    }
	    if(!deathMatch){
		if(whiteKingDead) score=-10000;
		if(blackKingDead) score=10000;
	    }
    	}
    	return score;
    }
    
    
    /*
    // DEPRECATED
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
    */
	
    public long getComplexity() {
	return complexity;
    }
    
}



