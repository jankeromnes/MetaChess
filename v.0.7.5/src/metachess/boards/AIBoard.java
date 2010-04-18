package metachess.boards;

import java.util.Collections;
import java.util.Vector;

import metachess.game.Piece;
import metachess.library.Pieces;
import metachess.logger.Move;

public class AIBoard extends AbstractBoard implements Comparable<AIBoard> {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private AbstractBoard parent;
	private Vector<AIBoard> children;
	private Move move;
	private float score;
	boolean scoreCalculated;
	boolean avoidBadScore;

    private boolean deathMatch;
    private boolean gameOver;
    private boolean whiteKingDead;
    private boolean blackKingDead;
    
    public AIBoard() {
    	super();
    	children = new Vector<AIBoard>(0);
    	move = null;
    	scoreCalculated = false;
    	avoidBadScore = true;
        activeSquareX=-1;
        activeSquareY=-1;
    }

    public AIBoard(AbstractBoard ab) {
    	this();
    	parent = ab;
    	copyFromParent();
    }

    public AIBoard(Move m, AbstractBoard ab) {
    	this(ab);
    	move = m;
    	boolean old = whitePlaying;
    	String test = this.toString();
    	playMove(m, false);
    	if(old == whitePlaying){
    		System.out.println("PLAYER DID'NT CHANGE !!!");
    		System.out.println(test+"\t\t(MOVE : "+move+" )");
    		System.out.println(this);
    	}
    }
    
    public Move getMove() { return move; }
    
    public Vector<AIBoard> getChildren() { return children; }

	public AIBoard getChild(int i) {
		AIBoard child = null;
		if(i>=0 && i<children.size()) child = children.get(i);
		return child;
	}
    
    public void copyFromParent() {
        whitePlaying = parent.whitePlaying;
        agbker = parent.agbker;
        atomic = parent.atomic;
    	width = parent.getCols();
    	height = parent.getRows();
        jokerPiece = parent.getJokerPiece();
    	squares = new AbstractSquare[width][height];
    	for(int i = 0 ; i < width ; i++)
        	for(int j = 0 ; j < height ; j++)
        		squares[i][j] = new AbstractSquare(parent.getSquare(i, j));
    }
    
    public void generateProgeny(int depth, int breadth) {
    	boolean childCreated = false;
		AbstractSquare s;
    	if(depth>0 && !gameOver){
			for(int x = 0 ; x < width ; x++)
				for(int y = 0 ; y < width ; y++){
					deactivateSquare();
					s = parent.getSquare(x, y);
					if (s.getPiece() != null && (s.getPiece().isWhite() == whitePlaying)){
		    			activateSquare(s);
		    			if(isSquareActive()){
		    				for(int i = 0 ; i < width ; i++)
		    					for(int j = 0 ; j < width ; j++)
		    						if(squares[i][j].isGreen()){
		    							childCreated = true;
		    							AIBoard child = new AIBoard(new Move(activeSquareX,activeSquareY,i,j,this), this);
		    							children.add(child);
		    						}
		    			}
					}
				}
	    	if(!childCreated)depth=0;
			Collections.sort(children);
			if(move!=null)keepBestChildren(breadth); // do not limit children for first board
	    	for(AIBoard child : children){
	    		child.generateProgeny(depth-1, breadth);
	    	}
			Collections.sort(children);
			/*if(avoidBadScore && children.size()>0 && (children.get(0).getScore(!whitePlaying)-getScore(!whitePlaying))<-2){
				avoidBadScore = false;
				generateProgeny(2, 0);
			}*/
    	}
    }
    
    private void keepBestChildren(int nb) {
    	if(nb > 0 && nb < children.size()){
    		Vector<AIBoard> newVector = new Vector<AIBoard>(0);
    		for(int i = 0 ; i < nb ; i++) newVector.add(children.get(i));
    		children = newVector;
    	}
		freeMemory();
    }
    
    public float getScore(boolean white) {
    	if(!scoreCalculated){
    		scoreCalculated = true;
    		score = 0;
			for(AbstractSquare s : this){
				Piece p = s.getPiece();
				if (p.isWhite()) score+=p.getPrice();
				else score-=p.getPrice();
			}
        	if(!deathMatch){
    			if(whiteKingDead) score=-10000;
    			if(blackKingDead) score=10000;
    		}
    	}
    	return (white ? score : -score);
    }
    
    public float getBestScore(boolean white) {
    	float score = 0;
    	if(children.size() > 0) score = children.get(0).getBestScore(white);
    	else score = getScore(white);
    	return score;
    }
    
    public Move getBestMove() {
    	System.out.print("Best move is ");
    	//for(AIBoard child : children.get(0).getChildren()) System.out.println(child.getMove()+" : "+child.getBestScore(!whitePlaying));
    	for(AIBoard child : children) System.out.println(child.getMove()+" : "+child.getBestScore(whitePlaying));
    	return children.get(0).getMove();
    }

    public void playSquare(int i, int j, boolean keep){
    	AbstractSquare theSquare = squares[i][j];
    	if(isSquareActive()) {
    		if(theSquare.isGreen()) {
    			Piece lastPiece = getActiveSquare().getPiece();
    			lastPiece.setMoved(true);
    			if (!lastPiece.isJoker()) jokerPiece = lastPiece;
    			if(atomic && theSquare.getPiece()!=null) explode(i, j);
    			else {
					theSquare.removePiece();
    				// Promotion
    				if(lastPiece.isPawn()&&((lastPiece.isWhite()&&theSquare.getRow()==7)||(!lastPiece.isWhite()&&theSquare.getRow()==0))) {
    					// Classic Promotion to Queen
    					if(lastPiece.getName().equals("pawn"))
    						theSquare.setPiece(Pieces.getPiece("queen", lastPiece.isWhite()));
    					// Promotion to Amazon
    					else
    						theSquare.setPiece(Pieces.getPiece("amazon", lastPiece.isWhite()));
    				}
    				else {
    					theSquare.setPiece(lastPiece);
    					// Castle
    					if(lastPiece.isKing() && getCols() == 8){
    						int diff = theSquare.getColumn() - getActiveSquare().getColumn();
    						if(diff==2||diff==-2){
    							squares[theSquare.getColumn()-(diff/2)][theSquare.getRow()].setPiece(squares[7*((diff+2)/4)][theSquare.getRow()].getPiece());
    							squares[7*((diff+2)/4)][theSquare.getRow()].setPiece(null);
    						}
    					}
    				}
    			}
    			getActiveSquare().setPiece(null);
    			nextPlayer();
    		}
    		deactivateSquare();
    	}
    	else activateSquare(i, j);
    }
	
    public void nextPlayer() {
    	checkKingsAreOK();
    	deathMatch = atomic && blackKingDead && whiteKingDead;
    	gameOver = !deathMatch && (blackKingDead || whiteKingDead);
    	togglePlayer();
    }
    
    public void checkKingsAreOK() {
    	blackKingDead = true;
    	whiteKingDead = true;
    	for(int i = 0 ; i < getCols() ; i++)
    		for(int j = 0 ; j < getRows() ; j++){
    			Piece p = squares[i][j].getPiece();
    			if(p!=null && p.isKing()){
    				whiteKingDead &= !p.isWhite();
    				blackKingDead &= p.isWhite();
    			}
    		}
    }
    
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	for(int i = 0 ; i < getCols() ; i++){
    		for(int j = 0 ; j < getRows() ; j++){
    			Piece p = squares[i][j].getPiece();
    			if(p!=null) sb.append(p.getName().substring(0,1).toUpperCase()+" ");
    			else sb.append("_ ");
    		}
    		sb.append("\n");
    	}
    	if(isSquareActive())sb.append("active square : "+getActiveSquare().getName()+"\n");
    	return sb.toString();
    }
	
	public String getBestSequence() {
		String s = "";
		if(children.size() > 0)s=children.get(0).getBestSequence();
		return "("+(whitePlaying?"black":"white")+") "+move+" ; "+s;
	}

	public int compareTo(AIBoard board) {
		int ret=0;
		float score1 = getBestScore(!whitePlaying);
		float score2 = board.getBestScore(!whitePlaying);
		if(score1 < score2) ret=1;
		else if(score1 > score2) ret=-1;
		return ret;
	}
	
	public void freeMemory() {
		Runtime r = Runtime.getRuntime();
		long freeMemory = r.freeMemory();
		long totalMemory = r.totalMemory();
		if(freeMemory < (totalMemory*0.05)){
			r.gc();
			int freedPercentage = Math.round(((float)(r.freeMemory()-freeMemory))/((float)totalMemory)*100);
			System.out.println("Freed "+freedPercentage+"% of memory!");
			if(freedPercentage < 3)children = new Vector<AIBoard>(0);
			if(freedPercentage < 10)keepBestChildren(3);
		}
	}
    
}



