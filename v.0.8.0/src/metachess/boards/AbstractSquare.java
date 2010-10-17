package metachess.boards;

import java.util.ArrayList;

import metachess.game.Piece;
import metachess.library.Colour;

/** Class of an Abstract Square
 * @author Jan (7DD) and Agbeladem (7DD) [v.0.8.3]
 * @version 0.7.5
 */
public class AbstractSquare {

    private final int i;
    private final int j;
    private final String name;
    private final Colour color;
    private boolean isGreen;

    protected Piece piece;
    protected ArrayList<AbstractSquare> choices;

    // CONSTRUCTORS

    /** Create an abstract square
     * @param x the square's column (X Coord)
     * @param y the square's row (Y Coord)
     */
    public AbstractSquare(int x, int y) {
    	i = x;
	j = y;
	isGreen = false;
	piece = null;
	name = new Coords(i, j).toString();
	choices = new ArrayList<AbstractSquare>();
	color = (i+j)%2 == 0 ? Colour.BLACK_BG : Colour.WHITE_BG;
    }

    /** Create a copy of an abstract square
     * @param s the abstract square
     */
    public AbstractSquare(AbstractSquare s) {

	i = s.getColumn();
	j = s.getRow();
	isGreen = s.isGreen();
	name = s.getName();
	color = s.getColor();
	choices = new ArrayList<AbstractSquare>(s.choices);
	piece = s.hasPiece()? s.getPiece(): null;

   }


    // BASIC METHODS



    /** Remove the piece contained by this square */
    public void removePiece() { piece = null; }

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

    /** Return the two characters name of this square
     * @return the name
     */
    public String getName() {
	return name;
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
     * <br/> Overriden by EmptySquare in AbstractBoard
     */
    public boolean isNull() { return false; }




    // METHODS CONCERNING THE LIST

    public void clearChoiceList() {
	choices.clear();
    }

    public void addChoice(AbstractSquare choice) {
	choices.add(choice);
    }

    public boolean setGreenSquares() {
	assert hasPiece();
	for(AbstractSquare as : choices)
	    as.setGreen(true);
	return !choices.isEmpty();
    }


    @Override
	public boolean equals(Object o) {
	assert o instanceof AbstractSquare;
	return name.equals(((AbstractSquare) o).name);
    }

    @Override
	public String toString() {
	StringBuilder s = new StringBuilder("square (");
	s.append(name);
	if(hasPiece()) {
	    s.append(" ; haspiece(");
	    s.append(piece);
	    s.append(")");
	}
	s.append(")");
	if(isNull()) s.append(" NULL ");
	return s.toString();
    }

}

