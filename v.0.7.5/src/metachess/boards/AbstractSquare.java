package metachess.boards;

import metachess.game.Piece;
import metachess.library.Colour;


public class AbstractSquare {

	private final int i;
    private final int j;
    private final String name;
    private final Colour color;
    private boolean isGreen;
    private Piece piece;
    public AbstractSquare(int x, int y, AbstractBoard abstractBoard) {
    	i = x;
		j = y;
		isGreen = false;
		piece = null;
		name = Character.toString((char)('A'+i))+(j+1);
		color = (i+j)%2 == 0 ? Colour.BLACK_BG : Colour.WHITE_BG;
    }
	
	public AbstractSquare(AbstractSquare s) {
		i = s.getColumn();
		j = s.getRow();
		isGreen = s.isGreen();
		piece = s.getPiece();
		name = s.getName();
		color = s.getColor();
	}

	public void removePiece() { piece = null; }

    public void setGreen(boolean green) { isGreen = green; } 
    
    public void setPiece(Piece p) { piece = p; }

    public boolean isGreen() { return isGreen; }
    public Colour getColor() { return color; }
    public Piece getPiece() { return piece; }
    public String getName() { return name; }
    public int getColumn() { return i; }
    public int getRow() { return j; }
    public boolean hasPiece() { return piece != null; }

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
