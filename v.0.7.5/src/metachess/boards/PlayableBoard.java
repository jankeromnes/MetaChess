package metachess.boards;

import metachess.game.Piece;
import metachess.library.Pieces;
import metachess.logger.Move;

public class PlayableBoard extends AbstractBoard {
	
    protected Move lastMove;
    protected boolean whitePlaying;
    protected boolean agbker;
    protected boolean atomic;
    protected boolean deathMatch;
    protected boolean gameOver;
    protected boolean whiteKingDead;
    protected boolean blackKingDead;
    
    public PlayableBoard() {
    	super();
    	lastMove = null;
    }

    public void init(String s, boolean isAtomic){
    	super.init(s);


    	whitePlaying = true;
        agbker = false;
        atomic = false;
        deathMatch = false;
        gameOver = false;
        whiteKingDead = false;
        blackKingDead = false; 
    	atomic = isAtomic;
	
    	activeSquareX = -1;
    	activeSquareY = -1;
    	jokerPiece = null;
    	lastMove = null;
    }
    

    public void checkKingsAreOK() {
    	blackKingDead = true;
    	whiteKingDead = true;
    	for(int i = 0 ; i < getCols() ; i++)
    		for(int j = 0 ; j < getRows() ; j++)
		    if(isSquareValid(i,j) && hasPiece(i,j)) {
    			Piece p = getSquare(i, j).getPiece();
    			if(p.isKing()) {
    				whiteKingDead &= !p.isWhite();
    				blackKingDead &= p.isWhite();
    			}
    		}
    }

    public void nextPlayer() {
    	checkKingsAreOK();
    	deathMatch = atomic && blackKingDead && whiteKingDead;
    	gameOver = !deathMatch && (blackKingDead || whiteKingDead);

    	togglePlayer();
    }

    /** Changes the player who is expected to play next */
    public void togglePlayer() {
    	whitePlaying = !whitePlaying;
    }


    /** Tells whether the player has activated a piece
     * @return true if he has
     */
    public boolean isSquareActive(){
	return isSquareValid(activeSquareX, activeSquareY);
    }

    /** Get the abstract square that has been activated by the player
     * @return the abstract square
     */
    public AbstractSquare getActiveSquare(){
	return getSquare(activeSquareX, activeSquareY);
    }

    public void activateSquare(int i, int j){
    	activateSquare(squares[i][j]);
    }
    
    public void activateSquare(AbstractSquare s){

    	if(s.hasPiece() && s.getPiece().isWhite() == whitePlaying) {
	    activeSquareX=s.getColumn();
	    activeSquareY=s.getRow();
	    if(!s.getPiece().setGreenSquares(activeSquareX,activeSquareY,this))
		deactivateSquare();
    	}
    }

    public void deactivateSquare() {
    	for(int i = 0 ; i < width ; i++)
    		for(int j = 0 ; j < height ; j++)
    			squares[i][j].setGreen(false);
    	activeSquareX=-1;
    	activeSquareY=-1;
    }

    public boolean isKingInCheck() {
    	return isKingInCheck(whitePlaying);
    }
    
    public boolean isKingInCheck(boolean isWhite) {
    	boolean ret = false;
    	Piece p;
    	for(int i = 0 ; i < getCols() ; i++)
    		for(int j = 0 ; j < getRows() ; j++){
		    if(squares[i][j].hasPiece()) {
			p = squares[i][j].getPiece();
    			ret |= p != null && (p.isWhite() != isWhite) && p.checkKingInRange(i, j, this);
		    }
    		}

    	return ret;
    }


	
    /** Set explosion to given coordinates
     * @param i the exploding square's column (X Coord)
     * @param j the exploding square's row (Y Coord)
     */
    public void explode(int i, int j){
    	removePiece(i, j);
    	if(i+1 < getCols()){
	    removePiece(i+1, j);
    		if(j+1<getRows()){
		    removePiece(i, j+1);
		    removePiece(i+1, j+1);
    		}
    		if(j-1 >= 0){
		    removePiece(i, j-1);
		    removePiece(i+1, j-1);
    		}
    	}
    	if(i-1 >= 0){
	    removePiece(i-1, j);
    		if(j+1<getRows()){
		    removePiece(i, j+1);
		    removePiece(i-1, j+1);
    		}
    		if(j-1 >= 0){
		    removePiece(i, j-1);
		    removePiece(i-1, j-1);
    		}
    	}
    }
    


    /** Literally play a given square
     * @param i the square's column (X Coord)
     * @param j the square's row (Y Coord)
     */
    @Override
	public void playSquare(int i, int j){
    	AbstractSquare theSquare = getSquare(i,j);
    	if(isSquareActive()) {
	    if(theSquare.isGreen()) {
		Piece lastPiece = getActiveSquare().getPiece();
		lastPiece.setMoved(true);
		if (!lastPiece.isJoker()) jokerPiece = lastPiece;

		if(theSquare == getActiveSquare()) ;
		else if(atomic && theSquare.hasPiece()) {
		    explode(i,j);
		    getActiveSquare().removePiece(); // not here in v1
		} else {
		    removePiece(theSquare);
		    // Promotion
		    if(lastPiece.isPawn()&&((lastPiece.isWhite()&&theSquare.getRow()==getRows()-1)||(!lastPiece.isWhite()&&theSquare.getRow()==0))) {
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
			if(lastPiece.isKing()){
				int diff = theSquare.getColumn() - getActiveSquare().getColumn();
				if(diff==2||diff==-2){
					squares[theSquare.getColumn()-(diff/2)][theSquare.getRow()].setPiece(squares[(getCols()-1)*((diff+2)/4)][theSquare.getRow()].getPiece());
					getSquare((getCols()-1)*((diff+2)/4), theSquare.getRow()).setPiece(null);
				}
			}
		    }
		    getActiveSquare().setPiece(null);
		}
		lastMove = new Move(activeSquareX, activeSquareY, i,j,this);
		deactivateSquare();
		nextPlayer();
	    }
	    else deactivateSquare(); // else not here in v1
    	}
    	else if(!gameOver) activateSquare(i, j);
    	update(); // not here in v1
    }

	public boolean isWhitePlaying() {
		return whitePlaying;
	}

	public boolean isAgbker() {
		return agbker;
	}

	public boolean iAtomic() {
		return atomic;
	}

    
}
