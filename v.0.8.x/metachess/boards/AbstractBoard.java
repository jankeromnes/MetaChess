package metachess.boards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import metachess.game.Coords;
import metachess.game.Move;
import metachess.game.Piece;
import metachess.loader.SetupLoader;
import metachess.model.PointBehaviour;
import metachess.squares.AbstractSquare;
import metachess.squares.EmptySquare;

/** Class of an Abstract Board.
 * This is a board that can be loaded from a MCS (setup) file.
 * @author Agbeladem and Jan (7DD)
 * @version 0.8.7
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

    protected ArrayList<String> promotions;
    protected HashMap<String, Area> areas;

    private int lastBlank;
    protected Piece jokerPiece;

    /** Create an empty abstract board */
    public AbstractBoard() {
    	jokerPiece = null;
	init();
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
    public void addArea(Area a) {
	areas.put(a.getName(), a);
    }

    // PROMOTION

    /** Add a given piece to the list of available pieces for a pawn promotion
     * @param piece the name of the piece to be added
     */
    public void addPromotionPiece(String piece) {
	promotions.add(piece);
    }

    /** Get the list of pieces available for promotion
     * @return the list of pieces as an array of strings
     */
    public ArrayList<String> getPromotionList() {
	return promotions;
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
	    boolean ret = squareExists(i,j) && (squares[i][j].isNull() || hasPiece(i,j));
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

    @Override
    public Iterator<AbstractSquare> iterator() { return browser ; }

    public void resetIterator() { browser.reset(); }
    
    /** Get the number of squares that were ignored to find a piece.
     * It is used by the SetupBuilder to create a MCS file
     * @return the number of squares
     */
    public int getIteratorLastBlank() { return lastBlank; }


    // INITIATION

    /** Initiate a given abstract square of the board. Automatically called a creation.
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

    /** Load a given setup in this abstract board
     * @param setup the setup's name
     */
    public void init(String setup) {
	init();
    	SetupLoader.load(this, setup);
    }

    private void init() {
    	areas = new HashMap<String, Area>();
	promotions = new ArrayList<String>();
    }

    /** Automatically launched after the setup's been loaded with init method */
    public void endInit() {
	maxDistance = Math.max(width, height);
    	squares = new AbstractSquare[width][height];
    	for(int j = height-1 ; j >= 0 ; j--)
	    for(int i = 0 ; i < width ; i++)
		initSquare(i, j);
    }

    // SQUARES

    /** Play a given move
     * @param m the move
     * @param keep whether 
     */
    public void playMove(Move m) {
	playSquare(m.getOldCoords());
    	playSquare(m.getNewCoords());
    }

    /** Literally play a given square (as a goal for a selected piece)
     * and keep the move in the logger
     * @param c the position of the square
     */
    public abstract void playSquare(PointBehaviour c);

    /** Tells whether given coordinates match a valid square of the board or not
     * <br/> A square is valid if it exists and if it was not removed
     * @param x the square's column (X Coord)
     * @param y the square's row (Y Coord)
     * @return true if it is valid
     */
    public boolean isSquareValid(int x, int y) {
	return squareExists(x, y) && !squares[x][y].isNull() ; 	
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

    /** Get the abstract square with the given coordinates
     * @param x the square's column (X Coord)
     * @param y the square's row (Y Coord)
     * @return the square
     */
    public AbstractSquare getSquare(int i, int j) {
	assert squareExists(i, j);
	return squares[i][j];
    }
   
    /** Get the abstract square with the given coordinates
     * @param c the coordinates
     * @return the square
     */
    public AbstractSquare getSquare(Coords c) {
	return getSquare(c.getColumn(), c.getRow());
    }

    // PIECES

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
	//assert isSquareValid(i,j);
	assert squareExists(i, j);
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
	// Removed assert in case of an explosion in an empty square :
	// assert isSquareValid(i,j): "Removed piece at unvalid coordinates "+new Coords(i, j);
	squares[i][j].setPiece(null);
    }

    /** Set the number of columns (width) of this board
     * @param x the new number of columns
     */
    public void setCols(int x) {
	width = x;
    }

    /** Set the number of rows (height) of this board
     * @param y the new number of rows
     */
    public void setRows(int y) {
	height = y;
    }

    // GETTERS

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


    // TERMINAL FORMAT / TOSTRING

    @Override
	public String toString() {
	assert width > 0 && height > 0;
	StringBuilder s = new StringBuilder();
	boolean[] line1 = new boolean[width+1];
	StringBuilder line2;
	for(int j = height-1 ; j >= 0 ; j--) { 
	    boolean isNull = true;
	    line2 = new StringBuilder();
	    for(int i = 0 ; i < width ; i++) {
		line1[i] = squares[i][j].isNull() && (j == height-1 || (squares[i][j+1].isNull() && (i == 0 || squares[i-1][j+1].isNull()))) && (i == 0 || squares[i-1][j].isNull()); 
		if(squares[i][j].isNull()) {
		    line2.append(isNull? "  " : "| ");
		    isNull = true;
		} else {
		    isNull = false;
		    line2.append('|');
		    line2.append(squares[i][j].hasPiece()?
				 squares[i][j].getPiece().getName().toUpperCase().charAt(0): ' ');
		}
	    }
	    line1[width] = squares[width-1][j].isNull() && (j == height-1 || squares[width-1][j+1].isNull());
	    for(int i = 0 ; i < width ; i++)
		s.append(line1[i] || line1[i+1] ? (line1[i]?"  ":"+ ") : (squares[i][j].isNull() && (j == height-1 || squares[i][j+1].isNull()))? "+ ": "+-");
	    s.append(line1[width]?" ":"+");
	    s.append("\n");
	    s.append(line2);
	    if(!isNull) s.append('|');
	    s.append("\n");
	}
	line1[0] = squares[0][0].isNull();
	line1[width] = squares[width-1][0].isNull();
	for(int i = 1 ; i < width ; i++) {
	    line1[i] = squares[i-1][0].isNull() && squares[i][0].isNull();
	    s.append(line1[i-1] || line1[i] ? (line1[i-1]?"  ":"+ ") : squares[i-1][0].isNull()? " +" : "+-");
	}
	s.append(line1[width-1] || line1[width] ? (line1[width-1]?"  ":"+ ") : "+-");
	s.append(line1[width]?" ":"+");
	return s.toString();
    }

}

