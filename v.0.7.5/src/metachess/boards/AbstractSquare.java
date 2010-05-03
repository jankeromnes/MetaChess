package metachess.boards;

import metachess.game.Piece;
import metachess.library.Colour;

/** Class of an Abstract Square
 * @author Jan (7DD)
 * @version 0.7.5
 */
public class AbstractSquare {

    private final int i;
    private final int j;
    private final String name;
    private final Colour color;
    private boolean isGreen;
    protected Piece piece;

    /** Create a new abstract square
     * @param x the square's column (X Coord)
     * @param y the square's row (Y Coord)
     */
    public AbstractSquare(int x, int y) {
    	i = x;
	j = y;
	isGreen = false;
	piece = null;
	name = Character.toString((char)('A'+i))+(j+1);
	color = (i+j)%2 == 0 ? Colour.BLACK_BG : Colour.WHITE_BG;
    }

    /** Create a copy of an abstract square
     * @param s the abstract square
     */
    public AbstractSquare(AbstractSquare s) {
	i = s.getColumn();
	j = s.getRow();
	isGreen = s.isGreen();
	piece = s.hasPiece()? s.getPiece(): null;
	name = s.getName();
	color = s.getColor();
    }

    public void removePiece() { piece = null; }

    public void setGreen(boolean green) { isGreen = green; } 
    public void setPiece(Piece p) { piece = p; }

    public boolean isGreen() { return isGreen; }
    public Colour getColor() { return color; }
    public Piece getPiece() {
	assert hasPiece(); 
	return piece;
    }
    public String getName() { return name; }
    public int getColumn() { return i; }
    public int getRow() { return j; }
    public boolean hasPiece() { return piece != null; }

    public boolean isNull() { return false; }

    public String toString() {

	StringBuilder s = new StringBuilder("square (");
	s.append(name);
	if(hasPiece()) {
	    s.append(" ; haspiece(");
	    s.append(piece);
	    s.append(")");
	}
	s.append(")");
	return s.toString();

    }


}
