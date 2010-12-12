package metachess.board;

import metachess.game.Coords;
import metachess.game.Move;
import metachess.game.Piece;
import metachess.library.Pieces;
import metachess.model.PointBehaviour;
import metachess.square.AbstractSquare;
import metachess.square.EmptySquare;
import metachess.square.PlayableSquare;

/** Class of a playable board
 * @author Jan (7DD) and Agbeladem (7DD)
 * @version 0.8.8
 */
public class PlayableBoard extends AbstractBoard implements Cloneable {
	
    protected Move lastMove;    // Used for the joker movetype determination
    protected boolean foreseer; // Whether to forbid illegal moves in nextPlayer

    private boolean enabled;    // Tell whether the actual game is being played
    private boolean playing;    // When doing the calculations, the AI is calculating too
    private boolean locked;

    protected boolean whitePlaying;
    protected boolean atomic;
    protected boolean gameOver;
    private boolean kingInRange;
    private boolean hasMoves;

    // Used for atomic death-match support
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
	foreseer = parent.foreseer;

	enabled = parent.enabled;
	playing = parent.playing;
	locked = parent.locked;

	whitePlaying = parent.whitePlaying;
        atomic = parent.atomic;
	kingInRange = parent.kingInRange;
	gameOver = parent.gameOver;

	deathMatch = parent.deathMatch;
	whiteKingDead = parent.whiteKingDead;
	blackKingDead = parent.blackKingDead;

    	width = parent.width;
    	height = parent.height;

        jokerPiece = parent.getJokerPiece();
	areas = parent.areas;
	promotions = parent.promotions;
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
	hasMoves = true;
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
	hasMoves = false;
	for(AbstractSquare as : this)
	    if(as.hasPiece()) {
		((PlayableSquare)as).clearChoiceList();
		Piece p = as.getPiece();
		if(p.isWhite() == whitePlaying) {
		    //boolean moved = p.hasMoved();
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
			//pb.resetIterator();
			if(!illegal) {
			    ((PlayableSquare)as).addChoice(s.getCoords());
			    hasMoves = true;
			}
		    }
		    // p.setMoved(moved);
		}
	    }
	resetIterator();
	gameOver |= !hasMoves && kingInRange;
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
		    	deactivateSquares();
	    }
    }

    public void deactivateSquares() {
	for(int i = 0 ; i < width ; i++)
	    for(int j = 0 ; j < height ; j++)
		squares[i][j].setGreen(false);
	activeSquareX=-1;
	activeSquareY=-1;
    }

    
    /** Set explosion to given coordinates
     * @param c the coordinates of the explosion
     */
    public void explode(Coords c) {
	explode(c.getColumn(), c.getRow());
    }

    /** Set explosion to given coordinates
     * @param i the exploding square's column (X Coord)
     * @param j the exploding square's row (Y Coord)
     */
    public void explode(int i, int j) {
   	removePiece(i, j);
    	if(i+1 < getCols()){
	    removePiece(i+1, j);
    		if(j+1 < getRows()){
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
    		if(j+1 < getRows()){
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
	Move oldMove = lastMove;
    	if(isSquareActive())  {
	    if(theSquare.isGreen()) {
		Piece lastPiece = getActiveSquare().getPiece();
		if(playing || enabled)
		    lastMove = new Move(activeSquareX, activeSquareY, i, j, board);
		if (!lastPiece.isJoker()) jokerPiece = lastPiece;
		if(theSquare != getActiveSquare()) {
		    if(atomic && theSquare.hasPiece()) {
			capture = true;
			explode(i, j);
			getActiveSquare().removePiece();
		    } else {
			capture = theSquare.hasPiece();
			if(capture)
			    removePiece(theSquare);

			// Promotion
			if(lastPiece.isPawn() && !promotions.isEmpty() &&
			   ( ( lastPiece.isWhite() && theSquare.getRow() == getRows()-1)
			     || (!lastPiece.isWhite() && theSquare.getRow() == 0)))
			    promote(theSquare, lastPiece.isWhite());
			else {
			    theSquare.setPiece(lastPiece);
			    // Castling
			    if(lastPiece.isKing()) {
				int diff = theSquare.getColumn() - getActiveSquare().getColumn();
				boolean right = diff > 0;
				if(diff == 2 || diff == -2) {
				    AbstractSquare rookSquare = getSquare(right? getCols()-1: 0, theSquare.getRow());
				    setPiece(theSquare.getColumn()-(diff/2), theSquare.getRow(), rookSquare.getPiece());
				    rookSquare.removePiece();
				    if(playing || enabled)
					lastMove.setCastling(true);
				}
			    }
			    // En Passant
			    else if(enabled && lastPiece.isPawn() && oldMove != null && oldMove.isPawnType()) {
				lastMove.resolveAttackType();
				if(lastMove.isAttackType()) {
				    capture = true;
				    Coords co = oldMove.getNewCoords();
				    if(atomic) {
					explode(co);
					getActiveSquare().removePiece();
				    } else removePiece(co);
				}
			    }
			}
			getActiveSquare().removePiece();
		    }
		}

		if(playing || enabled)
		    lastMove.setCapture(capture);
		if(enabled) {
		    lastMove.resolveAmbiguity();
		    lastMove.resolvePawnType();
		    deactivateSquares();
		    checkKingInRange();
		    lastPiece.setMoved(true);
		}

		update();

		if(playing)
		    nextPlayer(); 

	    } else if(enabled)
		deactivateSquares();
    	}
    	else if(!gameOver)
	    activateSquare(i, j);

	update();
	 
    }

    @Override
    public void update() {
	// Eliminates many useless updates
	if(enabled)
	    super.update();
    }

    /** Check whether the last move put the opponent's king in check */
    protected void checkKingInRange() {
	Coords c = lastMove.getNewCoords();
	AbstractSquare as = getSquare(c);
	kingInRange = as.hasPiece() && as.getPiece().checkKingInRange(c.getColumn(), c.getRow(), this);
	lastMove.setKingInRange(kingInRange);
    }

    /** Promote a pawn, ie add a piece where the pawn was promoted
     * @param as the square where the pawn is being promoted
     * @param white whether the new piece will be white
     */
    protected void promote(AbstractSquare as, boolean white) {
	// QUEENING BY DEFAULT
	as.setPiece(Pieces.getPiece("queen", white));
    }



    // STATES

    /** Toggle the playing value which decribes whether moves are being played.
     * <br/> Note that this value is true in AIBoardTree (while enabled is not).
     */
    public void togglePlaying() {
	playing = !playing;
	enabled = playing;
    }

    /** Tell whether moves are being played
     * @return true if they are, ie if players turns are actually being alternated
     */
    public boolean isPlaying() {
	return playing;
    }

    /** Toggle the enabled value which describes
     * whether the game is actually being played */
    public void toggleEnabled() {
	enabled = !enabled;
    }

    /** Toggle the locked value */
    public void toggleLocked() {
	locked = !locked;
    }

    /** Check if the board is locked
     * @return true if it is
     */
    public boolean isLocked() {
	return locked;
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

    /** Get the last move actually played in this board
     * @return the last move played by either the human player or the AI
     */
    public Move getLastMove() {
	return lastMove;
    }

    @Override
	public Object clone() {
	return new PlayableBoard(this);
    }
 

}

