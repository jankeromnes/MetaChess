package metachess.builder.setupbuilder;

import metachess.boards.AbstractSquare;
import metachess.boards.EditableBoard;
import metachess.builder.SavePanel;
import metachess.game.Piece;
import metachess.library.Resource;

/** Class of the Setup Builderbox save panel
 * @author Agbeladem
 * @version 0.8.0
 */
public class SetupSavePanel extends SavePanel {

    private static final long serialVersionUID = 1L;
    private SetupBuilderBox parent;

    /** Creation of a Setup Builderbox save panel
     * @param b the Setup Builderbox
     */
    public SetupSavePanel(SetupBuilderBox b) {
	super(Resource.SETUPS);
	parent = b;
	
	loader.pack();
    }

    @Override
	public void write() {
	println("# Metachess=v.0.8.0/MCS=v.4");
	println("# Generated from builder");
	println("\nwidth\t= "+parent.getCols());
	println("height\t= "+parent.getRows());
	println("\n\t{BEGIN}\n");
	EditableBoard board = parent.getBoard();


	Piece p;
	int count = 0;
	boolean isNull = true;
	String lastName = null;
	StringBuilder s;

	for(AbstractSquare sq : board) {

	    int n = board.getIteratorLastBlank();

	    p = sq.getPiece();
	    String name = p.getMCSFormat();

	    // If empty squares have to be added or a piece to be repeated
	    if(n > 0 || sq.isNull() != isNull
	       || (!isNull && !name.equals(lastName)) ) {

		if(isNull && count > 0) {
		    s = new StringBuilder("D");
		    s.append(count);
		    s.append("\t");
		    print(s);
		} else if(count > 1) {
		    s = new StringBuilder("R");
		    s.append(count-1);
		    s.append("\t");
		    print(s);
		}
		if(n > 0)
		    print(n+"\t");
		count = 0;
	    }

	    if(sq.isNull()) {
		count++;
		isNull = true;
	    } else {

		// if another piece has to be added
		if(count == 0 || n > 0 || ! name.equals(lastName)) {
		    count = 1;
		    lastName = name;
		    print(name);
		}
		// if the same piece has been added
		else
		    count++;
		isNull = false;

	    }
	}

	if(isNull) {
	    s = new StringBuilder("D");
	    s.append(count);
	    print(s);
	} else if(count > 1) {
	    s = new StringBuilder("R");
	    s.append(count-1);
	    print(s);
	}

	board.resetIterator();

    }

    @Override
	protected void load(String name) {
	parent.load(name);
    }

}


