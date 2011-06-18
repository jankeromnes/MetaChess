package metachess.board;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;

import metachess.square.AbstractSquare;
import metachess.square.SquareArea;

/** Class of a board specific Area
 * @author Agbeladem (7DD)
 * @version 0.8.0
 */
public class Area implements Iterable<SquareArea> {

    /** Enum of all the different kinds of areas depending on the color
	of the pieces involved */
    protected enum AreaColor { WHITE, BLACK, SYM, BOTH; }

   /** Get the area color designated by the following string
     * @param c the string
     * @return the area color
     */
    protected static AreaColor getAreaColor(String c) {
	if(c.equals("white"))
	    return AreaColor.WHITE;
	else if(c.equals("black"))
	    return AreaColor.BLACK;
	else if(c.equals("sym"))
	    return AreaColor.SYM;
	else
	    return AreaColor.BOTH;
    }

    protected String name;
    protected AreaColor color;
    private ArrayList<SquareArea> list;

    /** Create an area
     * @param constructor command string which contains the description
     * in the MCS format (v.4) of the new area
     * @param ab the abstract area to which this area belongs
     */
    public Area(String constructor, AbstractBoard ab) {

	list = new ArrayList<SquareArea>();

	StringReader sr = new StringReader(constructor);
	StreamTokenizer st = new StreamTokenizer(sr);
	st.wordChars('_', '_'+1);
	st.eolIsSignificant(true);

	try {
	    st.nextToken();
	    name = st.sval.toLowerCase();
	    st.nextToken();
	    color = getAreaColor(st.sval.toLowerCase());

	    int next = st.nextToken();
	    String s,t;
	    while(next != StreamTokenizer.TT_EOF) {
		s = st.sval;
		next = st.nextToken();
		t = st.sval;
		list.add(new SquareArea(s.charAt(0), s.charAt(1),
					t.charAt(0), t.charAt(1), ab));
		next = st.nextToken();
	    }
  
	} catch(IOException e) {
	    e.printStackTrace();
	} catch(NullPointerException e) {
	    System.out.println("Incorrect MCS format");
	    e.printStackTrace();
	}

	sr.close();

    }

    /** Tells whether a given abstract square's piece is in this area
     * @param as the abstract square
     * @return true if it is
     */
    public boolean containsSquare(AbstractSquare as) {
	assert as.hasPiece();

	if(color == AreaColor.WHITE && !as.getPiece().isWhite()
	   || color == AreaColor.BLACK && as.getPiece().isWhite())
	    return false;
	else {
	    boolean ret = false;
	    for(SquareArea sa : this)
		ret |= sa.containsSquare(as, color == AreaColor.SYM
					 && !as.getPiece().isWhite());

	    return ret;
	}
    }

    /** Get the name of this area
     * @return the name, as defined in the MCS file
     */
    public String getName() {
	return name;
    }

    public Iterator<SquareArea> iterator() {
	return list.iterator();
    }

    @Override
	public String toString() {
	return "Area "+name;
    }

}

