package metachess.squares;

import metachess.game.Coords;
import metachess.game.Piece;
import metachess.library.Colour;

/** Class of an Abstract Square
 * @author Jan (7DD)
 * @version 0.7.5
 */
public class AbstractSquare implements Cloneable {

    protected final int i;
    protected final int j;
    private final Colour color;
    private boolean isGreen;

    protected Piece piece;

    // CONSTRUCTORS

    /** Create an abstract square
     * @param x this square's column (X Coord)
     * @param y this square's row (Y Coord)
     */
    public AbstractSquare(int x, int y) {
    	i = x;
	j = y;
	isGreen = false;
	piece = null;
	color = (i+j)%2 == 0 ? Colour.BLACK_BG : Colour.WHITE_BG;
    }

    /** Create a copy of an abstract square
     * @param s the abstract square to copy
     */
    public AbstractSquare(AbstractSquare s) {
	i = s.getColumn();
	j = s.getRow();
	isGreen = s.isGreen();
	color = s.getColor();
	piece = s.hasPiece()? s.getPiece(): null;
   }


    // BASIC METHODS


    /** Remove the piece contained by this square */
    public void removePiece() {
	piece = null;
    }

    /** Set this square's activity
     * @param green true if the square in range of the active piece
     */
    public void setGreen(boolean green) {
	isGreen = green;
    }

    /** Set piece contained by this square
     * @param p the new piece
     */
    public void setPiece(Piece p) {
	piece = p;
    }

    /** Tell whether this square is green
     * @return true if it is active
     */
    public boolean isGreen() {
    	return isGreen;
    }

    /** Get the colour of this square
     * @return Colour.BLACK_BG or Colour.WHITE_BG (see library.Colour)
     */
    public Colour getColor() {
	return color;
    }

    /** Get the piece of this square
     * @return the piece
     */
    public Piece getPiece() {
	assert hasPiece(); 
	return piece;
    }

    /** Get the coordinates of this square
     * @return the coords
     */
    public Coords getCoords() {
	return new Coords(i, j);
    }

    /** Get the column of this square
     * @return the column (X Coord)
     */
    public int getColumn() {
	return i;
    }

    /** Get the row of this square
     * @return the row (Y Coord)
     */
    public int getRow() {
	return j;
    }

    /** Tells whether this square contains a piece
     * @return true if it does
     */
    public boolean hasPiece() {
	return piece != null;
    }

    /** Tells whether this square has been removed of the board
     * @return true if it has, false by default
     * <br/> Overriden to handle EmptySquare
     */
    public boolean isNull() { return false; }

    @Override
	public boolean equals(Object o) {
	assert o instanceof AbstractSquare;
	return getCoords().equals(((AbstractSquare) o).getCoords());
    }

    @Override
	public String toString() {
	StringBuilder s = new StringBuilder("abstract square (");
	s.append(getCoords());
	if(hasPiece()) {
	    s.append(" ; hasPiece(");
	    s.append(piece);
	    s.append(")");
	}
	if(isGreen()) s.append(" GREEN ");
	if(isNull()) s.append(" NULL ");
	s.append(")");
	return s.toString();
    }

    @Override
    public Object clone() {
	return new AbstractSquare(this);
    }

}

