package metachess.boards;

import java.util.ArrayList;

import metachess.ai.AIThread;
import metachess.game.Game;
import metachess.game.Piece;
import metachess.library.Pieces;
import metachess.logger.Move;


/** Class of the real Chess Board
 * @author Jan (7DD)
 * @version 0.8.0
 */
public class ChessBoard extends AbstractBoard {

    private static final long serialVersionUID = 1L;

    private Game game;
    private boolean deathMatch;
    private boolean gameOver;
    private boolean whiteKingDead;
    private boolean blackKingDead;

    /** Create a new Chess Board
     * @param g the game window in which its graphical board will be created
     */
    public ChessBoard(Game g) {
    	super();
    	game = g;
    	gameOver = false;
    }

    public void init(String s, boolean isAtomic){
    	super.init(s, isAtomic);
    	deathMatch = false;
    	whiteKingDead = false;
    	blackKingDead = false;
    }

    /** Literally play a given square
     * @param i the square's column (X Coord)
     * @param j the square's row (Y Coord)
     * @param keep whether the played move should be kept in the logger or not
     */
    public void playSquare(int i, int j, boolean keep){
    	AbstractSquare theSquare = squares[i][j];
    	if(isSquareActive()) {
	    if(theSquare.isGreen()) {
		Piece lastPiece = getActiveSquare().getPiece();
		lastPiece.setMoved(true);
		if (!lastPiece.isJoker()) jokerPiece = lastPiece;

		if(theSquare == getActiveSquare()) ;
		else if(atomic && theSquare.hasPiece()) {
		    explode(i,j);
		    getActiveSquare().removePiece();
		} else {
		    theSquare.removePiece();
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
			if(lastPiece.isKing() && getCols() == 8){
			    int diff = theSquare.getColumn() - getActiveSquare().getColumn();
			    if(diff==2||diff==-2){
				squares[theSquare.getColumn()-(diff/2)][theSquare.getRow()].setPiece(squares[7*((diff+2)/4)][theSquare.getRow()].getPiece());
				squares[7*((diff+2)/4)][theSquare.getRow()].setPiece(null);
			    }
			}
		    }
		    getActiveSquare().setPiece(null);
		}
		if(keep) game.addMove(new Move(activeSquareX, activeSquareY , i,j,this));
		deactivateSquare();
		nextPlayer(keep);
	    }
	    else deactivateSquare();
    	}
    	else activateSquare(i, j);
    	update();
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
    
    public void checkKingsAreOK() {
    	blackKingDead = true;
    	whiteKingDead = true;
	Piece p;
    	for(int i = 0 ; i < getCols() ; i++)
    		for(int j = 0 ; j < getRows() ; j++)
		    if(squares[i][j].hasPiece()) {
			p = squares[i][j].getPiece();
    			if(p.isKing()) {
    				whiteKingDead &= !p.isWhite();
    				blackKingDead &= p.isWhite();
			}
		    }
    }
	
    public void nextPlayer(boolean keep) {

    	//for(AbstractSquare s : this)
        //System.out.println(s.getPiece().getName()+" costs "+s.getPiece().getPrice());

    	checkKingsAreOK();
    	deathMatch = atomic && blackKingDead && whiteKingDead;
    	gameOver = !deathMatch && (blackKingDead || whiteKingDead);
    	togglePlayer();
    	if(gameOver) {
    	    gameOver = false;
    	    game.endGame();
    	}
    	if(keep && game.isAI(whitePlaying)){
    		update();
    		AIThread ait = new AIThread(this);
    		ait.start();
    	}
    }

    public void jump(ArrayList<Move> moves) {
	if(moves != null) {
	    int n = moves.size();
	    for(int i = 0; i < n ; i++)
		playMove(moves.get(i), false);
	}
    }
 

}



