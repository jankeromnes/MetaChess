package metachess.boards;

import metachess.builder.setupbuilder.SetupBuilderBox;

public class EditableBoard extends AbstractBoard {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SetupBuilderBox parent;

    public EditableBoard(SetupBuilderBox arg) {
	super();
	parent = arg;
	activeSquareX = -1;
	activeSquareY = -1;
    }

    public void init(int width, int height) {
	setCols(width);
	setRows(height);
	endInit();
    }

    protected void initSquare(int i, int j) {
	super.initSquare(i,j);
	//squares[i][j].setDim(20);
    }
    

    public void playSquare(int i, int j) {
    	squares[i][j].setPiece(parent.getTool());
    	update();
    }

}


