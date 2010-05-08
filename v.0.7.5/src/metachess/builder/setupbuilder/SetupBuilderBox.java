package metachess.builder.setupbuilder;

import java.awt.BorderLayout;
import javax.swing.JPanel;

import metachess.boards.EditableBoard;
import metachess.boards.GraphicalBoard;
import metachess.game.Piece;
import metachess.library.Loader;

/** Class of the Setup tab in the Builderbox
 * @author Agbeladem (7DD)
 * @version 0.8.0
 */
public class SetupBuilderBox extends JPanel {

    private static final long serialVersionUID = 1L;
    private final EditableBoard board;
    private final GraphicalBoard gb;
    private final ToolSelectPanel tsp;
    private Tool tool;
    private Piece piece;
    private int rows = 8;
    private int cols = 8;

    /** Create a new Setup Builderbox */
    public SetupBuilderBox() {
	super();
	setLayout(new BorderLayout());

	board = new EditableBoard(this);
	board.init(cols, rows);
	gb = new GraphicalBoard(board);
	gb.setDim(30);
	gb.init();
	tsp = new ToolSelectPanel(this);

	add(new SetupSavePanel(this), BorderLayout.NORTH);
	add(gb, BorderLayout.CENTER);
	add(new SetupSettingsPanel(this), BorderLayout.EAST);
	add(tsp, BorderLayout.SOUTH);

    }

    /** (Re)initialize this Setup Builderbox */
    public void init() {
	tsp.init();
    }

    public void load(String name) {
	Loader.loadSetup(board, name);
	tsp.init();
	gb.init();
    }

    // GETTERS / SETTERS

    /** Set the dimensions of the editable board
     * @param i the board's number of columns
     * @param j the board's number of rows
     */
    public void setBoardDimensions(int i, int j) {
	cols = i;
	rows = j;
	board.init(i, j);
	gb.init();
	validate();
    }

    /** Set the tool selected by the user
     * @param t the tool
     */
    public void setTool(Tool t) {
	tool = t;
    }

    /** Get the tool selected by the user
     * @return the tool
     */
    public Tool getTool() {
	return tool;
    }

    /** Set the piece selected by the user
     * @param p the piece
     */
    public void setPiece(Piece p) {
	piece = p;
    }

    /** Get the piece selected by the user 
     * @return the piece
     */
    public Piece getPiece() {
	return piece;
    }

    /** Get the editable board's number of columns
     * @return the number of columns
     */
    public int getCols() {
	return cols;
    }

    /** Get the editable board's number of rows
     * @return the number of rows
     */
    public int getRows() {
	return rows;
    }

    /** Get the editable board of this setup builder
     * @return the board
     */
    public EditableBoard getBoard() {
	return board;
    }

    /** Get the graphical board of this setup builder
     * @return the graphical board
     */
    public GraphicalBoard getGraphicalBoard() {
	return gb;
    }

}


