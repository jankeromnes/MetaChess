package metachess.boards;

import java.util.HashMap;
import java.util.Iterator;

import metachess.game.Piece;
import metachess.library.Loader;
import metachess.logger.Move;

/** Class of an Abstract Board
 * @author Agbeladem and Jan (7DD)
 * @version 0.8.0
 */
public abstract class AbstractBoard implements Iterable<AbstractSquare> {

    /* v.0.7.3 : Agbeladem
       v.0.7.5 : Jan
       v.0.8.0 : Agbeladem */

    protected int width;
    protected int height;
    private int maxDistance;
    private AbstractSquareIterator browser = new AbstractSquareIterator();

    protected GraphicalBoard gb;
    protected AbstractSquare[][] squares;
    protected int activeSquareX;
    protected int activeSquareY;

    private int lastBlank;
    protected Piece jokerPiece;
    protected HashMap<String, Area> areas;

    protected class EmptySquare extends AbstractSquare {

	public EmptySquare(int i, int j) {
	    super(i, j);
	    piece = new Piece();
	}
	@Override
	    public boolean isNull() { return true; }
	@Override
	    public boolean hasPiece() { return true; }
	@Override
	    public boolean isGreen() { return false; }

    }


    /** Create an empty abstract board */
    public AbstractBoard() {
	areas = new HashMap<String, Area>();
    	jokerPiece = null;
    }
    
    /** Associate the graphic board representation of this abstract board
     * @param gboard the board
     */
    public void setGraphicBoard(GraphicalBoard gboard) {
    	gb = gboard;
    }
    
    /** Update the graphic board's appearance */
    
    public void update() {
    	if(gb != null) gb.update();
    }
    
    // AREAS

    /** Add a given area to this board
     * @param a the area to be added
     */
    public void add(Area a) {
	areas.put(a.getName(), a);
    }


    // ITERATOR
    
    /** Iterator of the pieces in the board */
    private class AbstractSquareIterator implements Iterator<AbstractSquare> {
    	private int i, j, blank;
    	public AbstractSquareIterator() {
	    reset();
    	}
    	public void reset() { i=0; j=0; blank=0; }
    	public boolean hasNext() {
	    boolean ret = squareExists(i,j) && (getSquare(i,j).isNull() || hasPiece(i,j));
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
		if(++i == width) { i = 0 ; ++j; }
	    }
	    return sq;
    	}
    	public void remove() {}
    }

    public Iterator<AbstractSquare> iterator() { return browser ; }

    public void resetIterator() { browser.reset(); }
    
    /** Get the number of squares that were ignored to find a piece.
     * It is used by the SetupBuilder to create a MCS file
     * @return the number of squares
     */
    public int getIteratorLastBlank() { return lastBlank; }


    // INITIATION

    /** Initiate a given abstract square of the board
     * @param i the square's column (X Coord)
     * @param j the square's row (Y Coord)
     */
    protected void initSquare(int i, int j) {
    	squares[i][j] = new AbstractSquare(i, j);
    }

    public void removeSquare(int i, int j) {
	assert squareExists(i, j);
	squares[i][j] = new EmptySquare(i, j);
    }

    /** Launch a given setup
     * @param setup the setup's name
     * @param isAtomic whether the game shall be played with atomic rules or not
     */
    public void init(String setup) {
    	Loader.loadSetup(this, setup);
    }


    /** Automatically launched after the setup's been loaded with init method */
    public void endInit() {
	maxDistance = Math.max(width, height);
    	squares = new AbstractSquare[width][height];
    	for(int j = height-1 ; j >= 0 ; j--)
	    for(int i = 0 ; i < width ; i++)
		initSquare(i, j);
    }

    /** Play a given move
     * @param m the move
     * @param keep whether 
     */
    public void playMove(Move m) {
    	playSquare(m.getOldX(), m.getOldY());
    	playSquare(m.getNewX(), m.getNewY());
    }

    /** Play a given square and keep the move in the logger
     * @param i the square's column (X Coord)
     * @param j the square's row (Y Coord)
     */
    public void playSquare(int i, int j) { };

    /** Tells whether given coordinates match a valid square of the board or not
     * <br/> A square is valid if it exists and if it was not removed
     * @param x the square's column (X Coord)
     * @param y the square's row (Y Coord)
     * @return true if it is valid
     */
    public boolean isSquareValid(int x, int y) {
	return squareExists(x, y) && !getSquare(x,y).isNull() ; 	
    }

    /** Tells whether given coordinates match an existing square of the board
     * @param x the square's column (X Coord)
     * @param y the square's row (Y Coord)
     * @return true if it exists
     */
    public boolean squareExists(int x, int y) {
	return x >= 0 && x < width
	    && y >= 0 && y < height;
    }

    /** Get the abstract square with the given 
     * @param x the square's column (X Coord)
     * @param y the square's row (Y Coord)
     * @return the square
     */
    public AbstractSquare getSquare(int i, int j) {
	assert squareExists(i, j);
	return squares[i][j];
    }


    /** Tells whether a square at the given coordinates possess a piece
     * @param i the square's column (X Coord)
     * @param j the square's row (Y Coord)
     * @return true if it does
     */
    public boolean hasPiece(int i, int j) {
	assert squareExists(i, j);
    	return squares[i][j].hasPiece();
    }

    /** Get the piece at the given coordinates
     * @param i the piece's square's column (X Coord)
     * @param j the piece's square's row (Y Coord)
     * @return the piece
     */
    public Piece getPiece(int i, int j) {
	assert hasPiece(i, j);
	return squares[i][j].getPiece();
    }

    /** Set the piece at the given coordinates
     * @param i the piece's square's column (X Coord)
     * @param j the piece's square's row (Y Coord)
     */
    public void setPiece(int i, int j, Piece p) {
	assert isSquareValid(i,j);
    	squares[i][j].setPiece(p);
    }


    /** Remove the piece contained by a given square
     * @param s the square
     */
    public void removePiece(AbstractSquare s) {
	removePiece(s.getColumn(), s.getRow());
    }

    /** Remove the piece at the given coordinates
     * @param i the piece's square's column (X Coord)
     * @param j the piece's square's row (Y Coord)
     */
    public void removePiece(int i, int j) {
	assert isSquareValid(i,j): "Removed piece at unvalid coordinates ("+i+','+j+')';
	squares[i][j].setPiece(null);
    }

    public void setCols(int x) { width = x; }

    public void setRows(int y) { height = y; }

    /** Get the number of columns of the board
     * @return the number of columns
     */
    public int getCols() {
	return width;
    }

    /** Get the number of rows of the board
     * @return the number of rows
     */
    public int getRows() {
	return height;
    }

    /** Get the maximum distance a rook could hypothetically reach
     * @return the distance
     */
    public int getMaxDistance() { return maxDistance; }
    

    /** Get the last piece played for a joker-type piece
     * @return the piece
     */
    public Piece getJokerPiece() { return jokerPiece; }

}



