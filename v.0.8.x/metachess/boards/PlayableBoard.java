package metachess.boards;

import metachess.game.Coords;
import metachess.game.Move;
import metachess.game.Piece;
import metachess.library.Pieces;
import metachess.model.PointBehaviour;
import metachess.squares.AbstractSquare;
import metachess.squares.EmptySquare;
import metachess.squares.PlayableSquare;

/** Class of a playable board
 * @author Jan (7DD) and Agbeladem (7DD)
 * @version 0.8.5
 */
public class PlayableBoard extends AbstractBoard implements Cloneable {
	
    protected Move lastMove;    // Used for the joker movetype determination
    private boolean enabled;    // Tell whether the actual game is being played
    private boolean playing;    // When doing the calculations, the AI is calculating too
    protected boolean foreseer; // Whether to forbid illegal moves in nextPlayer

    protected boolean whitePlaying;
    protected boolean atomic;
    protected boolean gameOver;
    private boolean kingInRange;

    // Used to start atomic death-match
    protected boolean deathMatch;
    protected boolean whiteKingDead;
    protected boolean blackKingDead;

    
    /** Create an empty playable board */
    public PlayableBoard() {
    	super();
    }

    /** Create a clone of a given playable board
     * @param parent the playable board to clone
     */
    public PlayableBoard(PlayableBoard parent) {
	this();
	lastMove = parent.lastMove;
	enabled = parent.enabled;
	playing = parent.playing;
    	whitePlaying = parent.isWhitePlaying();
        atomic = parent.iAtomic();
	foreseer = parent.foreseer;
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

	lastMove = null;

	playing = true;
    	enabled = true;
    	whitePlaying = true;
        deathMatch = false;
        gameOver = false;
	kingInRange = false;
        whiteKingDead = false;
        blackKingDead = false; 

    	atomic = isAtomic;
	foreseer = !atomic;

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
	
	if(foreseer)
	    setChoices();

    }

    private void setChoices() {
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
			pb.togglePlaying();
			pb.playSquare(s);
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

    @Override
    public void playSquare(PointBehaviour c) {
	int i = c.getColumn();
	int j = c.getRow();
    	AbstractSquare theSquare = getSquare(i, j);
	boolean capture = false;
	PlayableBoard board = enabled ? new PlayableBoard(this) : null;
    	if(isSquareActive())  {
	    if(theSquare.isGreen()) {
		Piece lastPiece = getActiveSquare().getPiece();
		lastPiece.setMoved(true);
		if (!lastPiece.isJoker()) jokerPiece = lastPiece;
		if(theSquare == getActiveSquare()) ;
		else if(atomic && theSquare.hasPiece()) {
		    capture = true;
		    explode(i, j);
		    getActiveSquare().removePiece();
		} else {
		    capture = theSquare.hasPiece();
		    removePiece(theSquare);

		    // Promotion
		    if(lastPiece.isPawn() &&
		       ( ( lastPiece.isWhite() && theSquare.getRow() == getRows()-1)
		       || (!lastPiece.isWhite() && theSquare.getRow() == 0)))
			promote(theSquare, lastPiece.isWhite());

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
		lastMove = new Move(activeSquareX, activeSquareY, i, j, board);
		lastMove.setCapture(capture);
		if(enabled) {
		    lastMove.resolveAmbiguity();
		    deactivateSquare();
		    kingInRange = getSquare(i, j).hasPiece() && getSquare(i,j).getPiece().checkKingInRange(i, j, this);
		    lastMove.setKingInRange(kingInRange);
		}
		if(playing)
		    nextPlayer(); 
	    } else if(enabled) deactivateSquare();
    	}
    	else if(!gameOver) activateSquare(i, j);
    	update();
    }


    /** Promote a pawn, ie add a piece where the pawn was promoted
     * @param as the square where the pawn is being promoted
     * @param white whether the new piece will be white
     */
    protected void promote(AbstractSquare as, boolean white) {
	as.setPiece(Pieces.getPiece("queen", white));
    }


    /** Toggle the playing value which decribes whether moves are being played.
     * <br/> Note that this value is true in AIBoardTree (while enabled is not).
     */
    public void togglePlaying() {
	playing = !playing;
	enabled = playing;
    }

    /** Toggle the enabled value which describes
     * whether the game is actually being played */
    public void toggleEnabled() {
	enabled = !enabled;
    }

    // GETTERS

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

    /** Tell whether this game has to check for illegal moves when the turn changes
     * @return true if it has, false if moves are calculated when activating a piece
     * @see PlayableBoard (choice list)
     */
    public boolean isForeseer() {
	return foreseer;
    }
    
    /** Tell whether one player's king is in danger
     * @return true if it is, ie if one piece of the opponent has the king in its range
     */
    public boolean isKingInRange() {
	return kingInRange;
    }

    @Override
	public Object clone() {
	return new PlayableBoard(this);
    }
 
}

