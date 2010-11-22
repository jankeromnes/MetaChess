package metachess.boards;

import metachess.builder.setupbuilder.SetupBuilderBox;
import metachess.game.Coords;
import metachess.model.PointBehaviour;
import metachess.squares.AbstractSquare;
import metachess.squares.GraphicalSquare;

/** Class of the editable board in the Setup Builderbox
 * @author Agbeladem (7DD)
 * @version 0.8.0
 */
public class EditableBoard extends AbstractBoard {

    private static final long serialVersionUID = 1L;
    private SetupBuilderBox parent;

    /** Create an editable board
     * @param arg the Setup Builderbox to which it belongs
     */
    public EditableBoard(SetupBuilderBox arg) {
	super();
	parent = arg;
	activeSquareX = -1;
	activeSquareY = -1;
    }

    /** Initialize this board
     * @param width its width
     * @param height its height
     */
    public void init(int width, int height) {
	setCols(width);
	setRows(height);
	endInit();
    }

    @Override
    public void playSquare(PointBehaviour c) {
	int i = c.getColumn();
	int j = c.getRow();
	switch(parent.getTool()) {
	case ADDING_PIECE:
	    if(! getSquare(i, j).isNull())
		setPiece(i, j, parent.getPiece());
	    update();
	    break;
	case ERASING_PIECE:
	    if(! getSquare(i, j).isNull())
		removePiece(i, j);
	    update();
	    break;
	case TOGGLING_SQUARE:
	    
	    if(getSquare(i, j).isNull())
		squares[i][j] = new AbstractSquare(i, j);
	    else
		removeSquare(i, j);
	    GraphicalSquare s = parent.getGraphicalBoard().getSquare(i, j);
	    s.setAbstractSquare(getSquare(i, j));
	    s.update();
	    break;
	}

    }

    /* To change the size of a square
    protected void initSquare(int i, int j) {
	super.initSquare(i,j);
	//squares[i][j].setDim(20);
    }
    */

}


