package metachess.boards;

import java.util.Iterator;

import metachess.game.Piece;
import metachess.library.Loader;
import metachess.logger.Move;

/** Class of an Abstract Board
 * @author Agbeladem [0.7.3] + Jan (7DD) [0.7.5]
 * @version 0.7.3
 */
public abstract class AbstractBoard implements Iterable<AbstractSquare> {

    protected int width;
    protected int height;
    private AbstractSquareIterator browser = new AbstractSquareIterator();

    protected GraphicBoard gb;
    protected AbstractSquare[][] squares;
    protected int activeSquareX;
    protected int activeSquareY;
    protected Piece jokerPiece;

    public boolean whitePlaying;
    public boolean agbker;
    public boolean atomic;

    private int lastBlank;

    public AbstractBoard() {
    	super();
    }
    
    /** Associate the graphic board representation of this abstract board
     * @param gboard the board
     */
    public void setGraphicBoard(GraphicBoard gboard) {
    	gb = gboard;
    }
    
    /** Update the graphic board's appearance */
    public void update() {
    	if(gb != null) gb.update();
    }

    // ITERATOR
    
    /** Iterator of the abstract squares in the board that contain a piece */
    private class AbstractSquareIterator implements Iterator<AbstractSquare> {
    	private int i, j, blank;
    	public AbstractSquareIterator() {
    		reset();
    	}
    	public void reset() { i=0; j=0; blank=0; }
    	public boolean hasNext() {
    		boolean ret = hasPiece(i,j);
    		while( j < height && ! ret) {
    			if(hasPiece(i,j))
    				ret = true;
    			else {
    				blank++;
    				if(++i == width) { i = 0 ; ++j; }
    			}
    		}
    		return ret;
    	}
    	public AbstractSquare next() {
    		AbstractSquare sq = null;
    		if(hasNext()) {
    			lastBlank = blank; 
    			blank = 0;
    			sq = squares[i][j];
    			if(++i == width) { i = 0 ; ++j; };
    		}
	    return sq;
    	}
    	public void remove() {}
    }

    public Iterator<AbstractSquare> iterator() { return browser ; }
    
    public void resetIterator() { browser.reset(); }
    
    public int getIteratorLastBlank() { return lastBlank; }





    // INITIATION

    /** Initiate a given abstract square of the board
     * @param i the square's column (X Coord)
     * @param j the square's row (Y Coord)
     */
    protected void initSquare(int i, int j) {
    	squares[i][j] = new AbstractSquare(i, j, this);
    }

    /** Launch a given setup
     * @param setup the setup's name
     * @param isAtomic whether the game shall be played with atomic rules or not
     */
    public void init(String setup, boolean isAtomic) {
    	atomic = isAtomic;
	
    	activeSquareX = -1;
    	activeSquareY = -1;
	
    	whitePlaying = true;
	
    	Loader.loadSetup(this, setup);
    }


    /** Automatically launched after the setup's been loaded with init method */
    public void endInit() {

    	squares = new AbstractSquare[width][height];
    	for(int j = height-1 ; j >= 0 ; j--)
    		for(int i = 0 ; i < width ; i++)
    			initSquare(i, j);

    	jokerPiece = null;
    }

    /** Play a given move
     * @param m the move
     * @param keep whether 
     */
    public void playMove(Move m, boolean keep) {
    	playSquare(m.getOldX(), m.getOldY(),keep);
    	playSquare(m.getNewX(), m.getNewY(),keep);
    }

    /** Play a given square and keep the move in the logger
     * @param i the square's column (X Coord)
     * @param j the square's row (Y Coord)
     */
    public void playSquare(int i, int j) {
    	playSquare(i,j,true);
    }

    /** Play a given square ; must be overwritten to have any effects
     * @param i the square's column (X Coord)
     * @param j the square's row (Y Coord)
     * @param keep whether the played move should be kept in the logger or not
     */
    public void playSquare(int i, int j, boolean keep) {};


    /** Tells whether given coordinates match a valid square of the board or not
     * @param x the square's column (X Coord)
     * @param y the square's row (Y Coord)
     * @return true if it is valid
     */
    private boolean isSquareValid(int x, int y) {
	return (x >= 0 && x < width &&
		y >= 0 && y < height); 	
    }


    public boolean isSquareActive(){
	return isSquareValid(activeSquareX, activeSquareY);
    }

    public AbstractSquare getActiveSquare(){
	return getSquare(activeSquareX, activeSquareY);
    }

    /** Get the abstract square with the given 
     * @param x the square's column (X Coord)
     * @param y the square's row (Y Coord)
     * @return the square
     */
    public AbstractSquare getSquare(int i, int j){
	AbstractSquare square = null;
	if (isSquareValid(i,j)) square = squares[i][j];
	return square;
    }

    public boolean hasPiece(int i, int j) {
    	return isSquareValid(i, j) && squares[i][j].getPiece() != null;
    }

    public void setPiece(int i, int j, Piece p) {
    	squares[i][j].setPiece(p);
    }

    public void activateSquare(int i, int j){
    	activateSquare(squares[i][j]);
    }
    
    public void activateSquare(AbstractSquare s){
    	if(s.getPiece()!=null && s.getPiece().isWhite()==whitePlaying){
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

    /** Changes the player who is expected to play next */
    public void togglePlayer() {
    	whitePlaying = !whitePlaying;
    }
	
    /** Set explosion to given coordinates
     * @param i the exploding square's column (X Coord)
     * @param j the exploding square's row (Y Coord)
     */
    public void explode(int i, int j){
    	squares[i][j].removePiece();
    	if(i+1<getCols()){
    		squares[i+1][j].removePiece();
    		if(j+1<getRows()){
    			squares[i][j+1].removePiece();
    			squares[i+1][j+1].removePiece();
    		}
    		if(j-1>=0){
    			squares[i][j-1].removePiece();
    			squares[i+1][j-1].removePiece();
    		}
    	}
    	if(i-1>=0){
    		squares[i-1][j].removePiece();
    		if(j+1<getRows()){
    			squares[i][j+1].removePiece();
    			squares[i-1][j+1].removePiece();
    		}
    		if(j-1>=0){
    			squares[i][j-1].removePiece();
    			squares[i-1][j-1].removePiece();
    		}
    	}
    }
    
    public Piece getJokerPiece(){ return jokerPiece; }

    public void setCols(int x) { width = x; }

    public void setRows(int y) { height = y; }

    public int getCols() { return width; }

    public int getRows() { return height; }

}


