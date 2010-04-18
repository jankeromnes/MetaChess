package metachess.builder.setupbuilder;

import metachess.boards.AbstractSquare;
import metachess.boards.EditableBoard;
import metachess.builder.SavePanel;
import metachess.game.Piece;
import metachess.library.Resource;


public class SetupSavePanel extends SavePanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SetupBuilderBox parent;

    public SetupSavePanel(SetupBuilderBox b) {
	super(Resource.SETUPS);
	parent = b;
    }


    public void write() {
	println("# Metachess=v.0.7.3/MCS=v.3");
	println("# Generated from builder");
	println("\nwidth\t= "+parent.getCols());
	println("height\t= "+parent.getRows());
	println("\n\t{BEGIN}\n");
	EditableBoard board = parent.getBoard();
	for(AbstractSquare sq : board) {
	    int n = board.getIteratorLastBlank();
	    if(n > 0)
		print("\t"+n);
	    Piece p = sq.getPiece();
	    StringBuilder s = new StringBuilder("\t");
	    s.append(p.isWhite()?'W':'B');
	    s.append(p.getName());
	    print(s.toString());

	}
	board.resetIterator();
    }

}
