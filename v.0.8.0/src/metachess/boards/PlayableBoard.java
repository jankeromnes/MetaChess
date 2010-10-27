package metachess.boards;

import metachess.game.Piece;
import metachess.library.Pieces;
import metachess.logger.Move;

/** Class of a playable board
 * @author Jan (7DD)
 * @version 0.8.1
 */
public class PlayableBoard extends AbstractBoard implements Cloneable {
	
    protected Move lastMove;
    protected boolean enabled;
    protected boolean whitePlaying;
    protected boolean atomic;
    protected boolean deathMatch;
    protected boolean gameOver;
    protected boolean whiteKingDead;
    protected boolean blackKingDead;
    
    /** Create an empty playable board */
    public PlayableBoard() {
    	super();
    	enabled = true;
    	lastMove = null;
    }

    /** Create a clone of a given playable board
     * @param parent the playable board to clone
     */
    public PlayableBoard(PlayableBoard parent) {
	this();
    	whitePlaying = parent.isWhitePlaying();
        atomic = parent.iAtomic();
    	width = parent.getCols();
    	height = parent.getRows();
        jokerPiece = parent.getJokerPiece();
    	squares = new AbstractSquare[width][height];
    	for(int i = 0 ; i < width ; i++)
	    for(int j = 0 ; j < height ; j++)
		squares[i][j] = (AbstractSquare)(parent.squares[i][j].clone());
    }
    
    @Override
    public PlayableSquare getSquare(int i, int j) {
	AbstractSquare s = super.getSquare(i, j);
	if(s.isNull()) {
	    assert s instanceof EmptySquare;
	    s = new PlayableSquare((EmptySquare)s);
	}
	return (PlayableSquare)s;
    }
    

    @Override
    protected void initSquare(int i, int j) {
	squares[i][j] = new PlayableSquare(i, j);
    }

   /** Load and start a given setup in this playable board
     * @param setup the setup's name
     * @param isAtomic whether the game shall be played with atomic rules or not
     */
    public void init(String s, boolean isAtomic) {
    	super.init(s);

    	whitePlaying = true;
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

    	checkPlayer();

    }
    

    private void checkKingsAreOK() {
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

    /** Changes the player who is expected to play next */
    public void nextPlayer() {
	
    	checkKingsAreOK();
    	deathMatch = atomic && blackKingDead && whiteKingDead;
	gameOver = ( !deathMatch && (blackKingDead || whiteKingDead) );
	
    	whitePlaying = !whitePlaying;
	checkPlayer();

    }

    private void checkPlayer() {
	
	if(atomic) {
	    checkKingsAreOK();
	    deathMatch = blackKingDead && whiteKingDead;
	}
	/* REMOVAL OF THE ILLEGAL MOVES
	  Three playable squares will be used :
	    as: the square of the piece whose moves are being studied
	     s: the square goal of a specific move
	   as2: the square of the ennemy piece for which the range is being checked
	*/	
	for(AbstractSquare as : this)
	    if(as.hasPiece()) {
		((PlayableSquare)as).clearChoiceList();
		Piece p = as.getPiece();
		if(p.isWhite() == whitePlaying) {
		    for(AbstractSquare s : p.getChoiceList(as.getColumn(), as.getRow(), this)) {
			PlayableBoard pb = new PlayableBoard(this);
			pb.activeSquareX = as.getColumn();
			pb.activeSquareY = as.getRow();
			pb.squares[s.getColumn()][s.getRow()].setGreen(true);
			pb.enabled = false;
			pb.playSquare(s.getColumn(), s.getRow());
			pb.whitePlaying = !whitePlaying;
			boolean illegal = false;
			for(AbstractSquare as2 : pb) {
			    if(as2.hasPiece() && as2.getPiece().isWhite() != whitePlaying
			       && as2.getPiece().checkKingInRange(as2.getColumn(), as2.getRow(), pb)) {
				illegal = true;
				break;
			    }
			}
			pb.resetIterator();
			if(!illegal)
			    ((PlayableSquare)as).addChoice(s.getCoords());
		    }
		    
		}
	    }
	resetIterator();
    }

    /** Tells whether the player has activated a piece
     * @return true if he has
     */
    public boolean isSquareActive() {
	return isSquareValid(activeSquareX, activeSquareY);
    }

    /** Get the abstract square that has been activated by the player
     * @return the abstract square
     */
    public AbstractSquare getActiveSquare() {
	return getSquare(activeSquareX, activeSquareY);
    }

    public void activateSquare(int i, int j) {
	assert squareExists(i, j);
    	activateSquare(squares[i][j]);
    }
    
    public void activateSquare(AbstractSquare s) {
    	if(s.hasPiece() && s.getPiece().isWhite() == whitePlaying) {
		    activeSquareX=s.getColumn();
		    activeSquareY=s.getRow();
		    if(!s.getPiece().setGreenSquares(activeSquareX, activeSquareY, this))
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

    
    
	
    /** Set explosion to given coordinates
     * @param i the exploding square's column (X Coord)
     * @param j the exploding square's row (Y Coord)
     */
    public void explode(int i, int j) {
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
    
    /** Literally play a given square (as a goal for a selected piece)
     * @param i the square's column (X Coord)
     * @param j the square's row (Y Coord)
     */
    @Override
	public void playSquare(int i, int j) {
    	AbstractSquare theSquare = getSquare(i, j);
	boolean capture = false;
    	if(isSquareActive())  {
	    if(theSquare.isGreen()) {
		Piece lastPiece = getActiveSquare().getPiece();
		lastPiece.setMoved(true);
		if (!lastPiece.isJoker()) jokerPiece = lastPiece;
		if(theSquare == getActiveSquare()) ;
		else if(atomic && theSquare.hasPiece()) {
		    capture = true;
		    explode(i,j);
		    getActiveSquare().removePiece(); // not here in v1
		} else {
		    capture = theSquare.hasPiece();
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
			    if(diff==2 || diff==-2) {
				// TO BE DONE
				// squares[theSquare.getColumn()-(diff/2)][theSquare.getRow()].setPiece(squares[(getCols()-1)*((diff+2)/4)][theSquare.getRow()].getPiece());
				removePiece((getCols()-1)*((diff+2)/4), theSquare.getRow());
			    }
			}
		    }
		    getActiveSquare().setPiece(null);
		}
		lastMove = new Move(activeSquareX, activeSquareY, i, j, this);
		lastMove.setCapture(capture);
		deactivateSquare();
		if(enabled) nextPlayer(); 
	    } else deactivateSquare(); // this line not here in v1
    	}
    	else if(!gameOver) activateSquare(i, j);
    	update(); // this line not here in v1
    }

    /**  Tells whether the white player is playing
     * @return true if it is, false if the black player is playing
     */
    public boolean isWhitePlaying() {
	return whitePlaying;
    }

    /** Tells whether this board shall be played with atomic rules or not
     * @return true if it shall
     */
    public boolean iAtomic() {
	return atomic;
    }

    @Override
	public Object clone() {
	return new PlayableBoard(this);
    }
 
}

