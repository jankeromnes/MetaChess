package metachess.builder.setupbuilder;

import java.awt.BorderLayout;
import javax.swing.JPanel;

import metachess.boards.EditableBoard;
import metachess.boards.GraphicBoard;
import metachess.game.Piece;

public class SetupBuilderBox extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final EditableBoard board;
	private final GraphicBoard gb;
    private final PieceSelectPanel psp;
    private Piece tool = null;
    private int rows = 8;
    private int cols = 8;

    public SetupBuilderBox() {
	super();
	setLayout(new BorderLayout());

	board = new EditableBoard(this);
	board.init(cols, rows);
	gb = new GraphicBoard(board);
	psp = new PieceSelectPanel(this);

	add(new SetupSavePanel(this), BorderLayout.NORTH);
	add(gb, BorderLayout.CENTER);
	add(new SetupSettingsPanel(this), BorderLayout.EAST);
	add(psp, BorderLayout.SOUTH);

    }

    public void setBoardDimensions(int i, int j) {
	board.init(i, j);
	gb.init();
	cols = i;
	rows = j;
	validate();
    }

    public void init() {
	psp.init();
    }

    public void changeTool(Piece p) {
	tool = p;
    }

    public Piece getTool() {
	return tool;
    }

    public int getCols() { return cols; }
    public int getRows() { return rows; }
    public EditableBoard getBoard() { return board; }

}

