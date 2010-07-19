package metachess.boards;

import metachess.builder.setupbuilder.SetupBuilderBox;

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

    /** Change the content of a given square
     * @param i the square's colmun (X Coord)
     * @param j the square's row (Y Coord)
     */
    @Override
    public void playSquare(int i, int j) {
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
	    Square s = parent.getGraphicalBoard().getSquare(i, j);
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


